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
                .collection("Usuarios").document(email)
                .collection("pedidos").whereEqualTo("estado","En espera");
        Query resultado1 = db
                .collection("Usuarios").document(email)
                .collection("pedidos").whereEqualTo("estado","En proceso");
        Query resultado2 = db
                .collection("Usuarios").document(email)
                .collection("pedidos").whereEqualTo("estado","En tránsito");
        Query resultado3 = db
                .collection("Usuarios").document(email)
                .collection("pedidos").whereEqualTo("estado","Completo");

        if(resultado!=null){
            resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Log.d(TAG, e.getMessage());

                    }
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        //TODO: Agregar modified

                        if(doc.getType() == DocumentChange.Type.ADDED) {
                            String estado = "";
                            estado=doc.getDocument().getString("estado");
                            Log.d(TAG2, estado);
                            String conductor = "";
                            conductor=doc.getDocument().getString("conductor");
                            Log.d(TAG3, conductor);
                            String monto = "";
                            monto=doc.getDocument().getString("montoTotal");
                            Log.d(TAG4, monto);
                            String panaderia = "";
                            panaderia=doc.getDocument().getString("panaderia");
                            Log.d(TAG5, panaderia);

                            textViewEstado.setText(estado);
                            textViewMonto.setText(monto);
                            textViewConductor.setText(conductor);
                            textViewPanaderia.setText(panaderia);
                            try {
                                Bitmap qr = QrService.generarQr(doc.getDocument().getId(), black, white);
                                codigoQr.setImageBitmap(qr);
                            } catch (WriterException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                }
            });
         }
         if(resultado1!=null){
            resultado1.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Log.d(TAG, e.getMessage());

                    }
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        //TODO: Agregar modified

                        if(doc.getType() == DocumentChange.Type.ADDED) {
                            String estado = "";
                            estado=doc.getDocument().getString("estado");
                            Log.d(TAG2, estado);
                            String conductor = "";
                            conductor=doc.getDocument().getString("conductor");
                            Log.d(TAG3, conductor);
                            String monto = "";
                            monto=doc.getDocument().getString("montoTotal");
                            Log.d(TAG4, monto);
                            String panaderia = "";
                            panaderia=doc.getDocument().getString("panaderia");
                            Log.d(TAG5, panaderia);

                            textViewEstado.setText(estado);
                            textViewMonto.setText(monto);
                            textViewConductor.setText(conductor);
                            textViewPanaderia.setText(panaderia);
                            try {
                                Bitmap qr = QrService.generarQr(doc.getDocument().getId(), black, white);
                                codigoQr.setImageBitmap(qr);

                            } catch (WriterException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
        if(resultado2!=null){
            resultado2.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Log.d(TAG, e.getMessage());

                    }
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        //TODO: Agregar modified

                        if(doc.getType() == DocumentChange.Type.ADDED) {
                            String estado = "";
                            estado=doc.getDocument().getString("estado");
                            Log.d(TAG2, estado);
                            String conductor = "";
                            conductor=doc.getDocument().getString("conductor");
                            Log.d(TAG3, conductor);
                            String monto = "";
                            monto=doc.getDocument().getString("montoTotal");
                            Log.d(TAG4, monto);
                            String panaderia = "";
                            panaderia=doc.getDocument().getString("panaderia");
                            Log.d(TAG5, panaderia);

                            textViewEstado.setText(estado);
                            textViewMonto.setText(monto);
                            textViewConductor.setText(conductor);
                            textViewPanaderia.setText(panaderia);
                            try {
                                Bitmap qr = QrService.generarQr(doc.getDocument().getId(), black, white);
                                codigoQr.setImageBitmap(qr);
                            } catch (WriterException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
        if(resultado3!=null){
            resultado3.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Log.d(TAG, e.getMessage());

                    }
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        //TODO: Agregar modified

                        if(doc.getType() == DocumentChange.Type.ADDED) {
                            String estado = "";
                            estado=doc.getDocument().getString("estado");
                            Log.d(TAG2, estado);
                            String conductor = "";
                            conductor=doc.getDocument().getString("conductor");
                            Log.d(TAG3, conductor);
                            String monto = "";
                            monto=doc.getDocument().getString("montoTotal");
                            Log.d(TAG4, monto);
                            String panaderia = "";
                            panaderia=doc.getDocument().getString("panaderia");
                            Log.d(TAG5, panaderia);

                            textViewEstado.setText(estado);
                            textViewMonto.setText(monto);
                            textViewConductor.setText(conductor);
                            textViewPanaderia.setText(panaderia);
                            try {
                                Bitmap qr = QrService.generarQr(doc.getDocument().getId(), black, white);
                                codigoQr.setImageBitmap(qr);

                            } catch (WriterException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
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
}
