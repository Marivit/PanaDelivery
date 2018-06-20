package com.example.virginia.panadelivery.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.virginia.panadelivery.Activities.ProfileClienteActivity;
import com.example.virginia.panadelivery.R;
import com.example.virginia.panadelivery.Services.QrService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.WriterException;

import javax.annotation.Nullable;

public class PedidoClienteFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG="User actual:";
    private String TAG2="Estado:";
    private String TAG3="Conductor:";
    private String TAG4="Monto:";
    private String TAG5="Panaderia:";
    private TextView textViewEstado;
    private TextView textViewMonto;
    private TextView textViewConductor;
    private TextView textViewPanaderia;
    private ImageView codigoQr;
    private QrService QrService = new QrService();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int black = getResources().getColor(R.color.black);
        final int white = getResources().getColor(R.color.color3);

        firebaseAuth = FirebaseAuth.getInstance();

        Log.d(TAG, String.valueOf(firebaseAuth.getCurrentUser().getEmail()));
        final String  email= firebaseAuth.getCurrentUser().getEmail();

        Query resultado = db
                .collection("Pedidos").whereEqualTo("cliente", email);
                //.whereEqualTo("activo","1"); //En espera

        if(resultado!=null){
            resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Log.d(TAG, e.getMessage());

                    }
                    for (int i = 0; i < queryDocumentSnapshots.getDocumentChanges().size(); i++ ) {

                    DocumentChange doc = queryDocumentSnapshots.getDocumentChanges().get(i);
                        Log.d(TAG, String.valueOf(doc));
                        if(doc.getType() == DocumentChange.Type.ADDED || doc.getType() == DocumentChange.Type.MODIFIED) {
                            if (Integer.parseInt(doc.getDocument().get("activo").toString())== 1){
                                Long estado;
                                estado=doc.getDocument().getLong("estado");
                                Log.d(TAG2, String.valueOf(estado));
                                String conductor = "";
                                conductor=doc.getDocument().getString("conductor");
                                //Log.d(TAG3, conductor);
                                String monto = "";
                                monto=doc.getDocument().getString("montoTotal");
                                Log.d(TAG4, monto);
                                String panaderia = "";
                                panaderia=doc.getDocument().getString("panaderia");
                                Log.d(TAG5, panaderia);
                                if (doc.getDocument().getString("latitudConductor") != null && doc.getDocument().getString("longitudConductor") != null) {
                                   String latitud = doc.getDocument().getString("latitudConductor");
                                   String longitud = doc.getDocument().getString("longitudConductor");
                                   Log.d("LL", latitud + " y " + longitud);
                                }
                                setearEstados(estado);
                                textViewMonto.setText(monto);
                                textViewPanaderia.setText(panaderia);
                                if(conductor != null) {
                                    textViewConductor.setText(conductor);
                                } else {
                                    textViewConductor.setText("---");
                                }
                                try {
                                    Bitmap qr = QrService.generarQr(doc.getDocument().getId(), black, white);
                                    codigoQr.setImageBitmap(qr);
                                } catch (WriterException e1) {
                                    e1.printStackTrace();
                                }

                            }
                            else if (Integer.parseInt(doc.getDocument().get("activo").toString())== 0 && i == queryDocumentSnapshots.getDocumentChanges().size() ) {
                                validar();
                            }
                        }

                    }
                    Log.d("TAMANO", Integer.toString(queryDocumentSnapshots.getDocumentChanges().size()));
                    if (queryDocumentSnapshots.getDocumentChanges().size() == 0) {
                        validar();
                    }
                }
            });
         } else {
            validar();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pedido_cliente, container, false);
        textViewEstado = (TextView) view.findViewById(R.id.textViewEstado);
        textViewMonto = (TextView) view.findViewById(R.id.textViewMonto);
        textViewConductor = (TextView) view.findViewById(R.id.textViewConductor);
        textViewPanaderia = (TextView) view.findViewById(R.id.textViewPanaderia);
        codigoQr = (ImageView) view.findViewById(R.id.qr);
        return view;
    }

    public void setearEstados(Long estado){
        if (estado==1){
            textViewEstado.setText("En espera");
        }
        if (estado==2){
            textViewEstado.setText("En tránsito");
        }
        if (estado==3){
            textViewEstado.setText("Completado");
        }
    }

    public void validar() {
        if(getActivity()!=null){

            ((ProfileClienteActivity) getActivity()).mostrarEmpty();
        }
    }
}
