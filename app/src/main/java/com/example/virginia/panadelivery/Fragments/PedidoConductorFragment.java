package com.example.virginia.panadelivery.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.virginia.panadelivery.Activities.ProfileConductorActivity;
import com.example.virginia.panadelivery.Activities.ScannerActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;
import com.example.virginia.panadelivery.Services.APIKeys;
import com.example.virginia.panadelivery.Services.HttpService;
import com.example.virginia.panadelivery.Services.TrackerService;
import com.example.virginia.panadelivery.Services.VolleyApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import static android.content.Context.LOCATION_SERVICE;


public class PedidoConductorFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private String emailConductor;

    private String TAG = "resultConductor";
    private String TAG2 = "probando";

    private TextView textViewEstado2;
    private TextView textViewMonto2;
    private TextView textViewConductor2;
    private TextView textViewPanaderia2;
    private Button buttonEstado;
    private String idPedido;
    private Pedido pedido;
    private static final int PERMISSIONS_REQUEST = 1;
    private HttpService httpService =  new HttpService();

    public PedidoConductorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtenerPedidoActual();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_pedido_conductor, container, false);

        buttonEstado = (Button) view.findViewById(R.id.buttonEstado);
        textViewEstado2 = (TextView) view.findViewById(R.id.textViewEstado2);
        textViewMonto2 = (TextView) view.findViewById(R.id.textViewMonto2);
        textViewPanaderia2 = (TextView) view.findViewById(R.id.textViewPanaderia2);
        textViewConductor2 = (TextView) view.findViewById(R.id.textViewConductor2);

        buttonEstado.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG2, "Entro en boton");
                Log.d(TAG2, pedido.getIdPedido());
                DocumentReference resultado = db.collection("Pedidos").document(pedido.getIdPedido());
                Map<String, Object> actualizarEstado = new HashMap<>();

                if(pedido.getEstado()==2){
                    pedido.setEstado(3);
                    actualizarEstado.put("estado", 3); //Pasar a completado
                    resultado.set(actualizarEstado, SetOptions.merge());
                    textViewEstado2.setText("Completado");
                    //Aqui comienza el tracking
                    LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(getContext(),  "Please enable location services", Toast.LENGTH_SHORT).show();
                        //finish();
                    }

                    // Check location permission is granted - if it is, start
                    // the service, otherwise request the permission
                    int permission = ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION);
                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        startTrackerService();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSIONS_REQUEST);
                    }
                }
                else if(pedido.getEstado()==3){
                    /*pedido.setEstado(4);
                    actualizarEstado.put("activo", 0); //FINALIZAR EL PEDIDO, aqui iria el QR
                    resultado.set(actualizarEstado, SetOptions.merge());
                    textViewEstado2.setText("Entregado");*/

                    Intent intent = new Intent(getContext(), ScannerActivity.class);
                    intent.putExtra("idPedido", pedido.getIdPedido());
                    startActivity(intent);

                }

                configurarBoton();
            }
        });


        return view;

    }

    private void startTrackerService() {
        Intent intent = new Intent(getContext(), TrackerService.class);
        intent.putExtra("idPedido", pedido.getIdPedido());

        getActivity().startService(intent);

    }

    private void configurarBoton(){
        if(pedido.getEstado()==2){
            Drawable d = getResources().getDrawable(R.color.colorMorado);
            buttonEstado.setText("Completar");
            buttonEstado.setBackground(d);
        }
        if(pedido.getEstado()==3){
            Activity activity = getActivity();
            if(activity != null && isAdded()) {
                Drawable d = getResources().getDrawable(R.color.colorVerde);
                buttonEstado.setText("Entregar");
                buttonEstado.setBackground(d);
            }
        }

    }

    private void obtenerPedidoActual() {
        firebaseAuth = FirebaseAuth.getInstance();
        emailConductor = firebaseAuth.getCurrentUser().getEmail();
        Query resultado = db
                .collection("Pedidos").whereEqualTo("conductor", emailConductor);
        if(resultado!=null) {
            resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Log.d(TAG, e.getMessage());
                        return;

                    }
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        //TODO: Agregar modified

                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            if (Integer.parseInt(doc.getDocument().get("activo").toString())== 1) {
                                idPedido = doc.getDocument().getId();
                                int estado;
                                estado=doc.getDocument().getLong("estado").intValue();
                                pedido = new Pedido(idPedido, estado);
                                Log.d(TAG2, idPedido);
                                String conductor = "";
                                conductor = doc.getDocument().getString("conductor");
                                String monto = "";
                                monto = doc.getDocument().getString("montoTotal");
                                String panaderia = "";
                                panaderia = doc.getDocument().getString("panaderia");

                                setearEstados(estado);
                                textViewMonto2.setText(monto);
                                textViewConductor2.setText(conductor);
                                textViewPanaderia2.setText(panaderia);
                                configurarBoton();
                            }
                            else {
                                validar();
                            }
                            Log.d(TAG2, String.valueOf(pedido));
                        }
                    }
                }
            });
        }
        if(resultado == null){
            Log.d(TAG2, "SON NULL");
            validar();
        }

    }

    public void validar() {
        if(getActivity()!=null){
            ((ProfileConductorActivity) getActivity()).mostrarEmpty();
        }
    }

    public void setearEstados(int estado){
        if (estado==1){
            textViewEstado2.setText("En espera");
        }
        if (estado==2){
            textViewEstado2.setText("En tránsito");
        }
        if (estado==3){
            textViewEstado2.setText("Completado");
        }
        if (estado==4){
            textViewEstado2.setText("Entregado");
        }
    }

}
