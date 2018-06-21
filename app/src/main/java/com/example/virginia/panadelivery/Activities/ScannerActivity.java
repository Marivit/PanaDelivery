package com.example.virginia.panadelivery.Activities;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.virginia.panadelivery.Fragments.PedidoConductorFragment;
import com.example.virginia.panadelivery.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class ScannerActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG2 = "probando";
    String idPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        //Recibir idpedido
        Bundle bundle = getIntent().getExtras();
        idPedido=bundle.getString("idPedido");

        IntentIntegrator intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

        intent.setPrompt("Scan");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(true);
        intent.initiateScan();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Escaneo cancelado!", Toast.LENGTH_LONG).show();
            } else {
                compareQR(result.getContents());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        finish();
        startActivity(new Intent(getApplicationContext(), ProfileConductorActivity.class));
    }

    public void compareQR(String result){
        if (result.equals(idPedido)) {
            Log.d(TAG2, "son iguales");
            DocumentReference resultado = db.collection("Pedidos").document(idPedido);
            Map<String, Object> actualizarEstado = new HashMap<>();
            actualizarEstado.put("activo", 0);
            //FINALIZAR EL PEDIDO
            resultado.set(actualizarEstado, SetOptions.merge());
            Toast.makeText(this, "El pedido de finalizó con éxito!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Este QR no pertenece al pedido en curso." , Toast.LENGTH_LONG).show();
        }

    }

}
