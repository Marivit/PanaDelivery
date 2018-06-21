package com.example.virginia.panadelivery.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.virginia.panadelivery.Activities.ProfileClienteActivity;
import com.example.virginia.panadelivery.R;
import com.example.virginia.panadelivery.Services.APIKeys;
import com.example.virginia.panadelivery.Services.QrService;
import com.example.virginia.panadelivery.Services.VolleyApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

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
    private TextView tiempoEntrega;
    private ImageView codigoQr;
    private QrService QrService = new QrService();
    private boolean validar=true, unsubscribe = false;
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
            ListenerRegistration registration = resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
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


                                validar=false;
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
                                   String latitudConductor = doc.getDocument().getString("latitudConductor");
                                   String longitudConductor = doc.getDocument().getString("longitudConductor");
                                   Log.d("LL", latitudConductor + " y " + longitudConductor);
                                   String latitudCliente = doc.getDocument().getString("latitud");
                                   String longitudCliente = doc.getDocument().getString("longitud");
                                   String latitudPanaderia = doc.getDocument().getString("latitudPanaderia");
                                   String longitudPanaderia = doc.getDocument().getString("longitudPanaderia");
                                    try {
                                        getTiempo(latitudCliente,longitudCliente,latitudConductor,longitudConductor, latitudPanaderia, longitudPanaderia);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }

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

                                i =  queryDocumentSnapshots.getDocumentChanges().size();

                                return;


                            }
                           else if (Integer.parseInt(doc.getDocument().get("activo").toString())== 0 &&  doc.getType() == DocumentChange.Type.MODIFIED) {
                                if (getContext() != null) {
                                    Toast.makeText(getContext(), "Se ha completado la orden!", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                    startActivity(new Intent(getContext(), ProfileClienteActivity.class));
                                }
                                unsubscribe = true;

                            }

                        }

                    }
                    Log.d("TAMANO", Integer.toString(queryDocumentSnapshots.getDocumentChanges().size()));
                    if (queryDocumentSnapshots.getDocumentChanges().size() == 0) {
                        validar();
                    }
                    if(validar){
                        validar();
                    }
                }
            });
            if (unsubscribe) {
                registration.remove();
                return;
            }
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
        tiempoEntrega = (TextView) view.findViewById(R.id.tiempo);
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

    public void getTiempo(String latitudDestino, String longitudDestino, String latitudConductor, String longitudConductor, String latitudPanaderia,
                          String longitudPanaderia) throws IOException {
        APIKeys ApiKeys = new APIKeys();
        String key = "&key=" + ApiKeys.getDirectionsKey();
        String direccionOrigen = "origin=" + latitudConductor + "," + longitudConductor;
        String direccionDestino = "&destination=" + latitudDestino + "," + longitudDestino;
        String waypoints = "&waypoints=" + latitudPanaderia + "," + longitudPanaderia;
        waypoints.replaceAll(" ","");
        String request = "https://maps.googleapis.com/maps/api/directions/json?" + direccionOrigen + direccionDestino + waypoints +  key;
        Log.d("URL", request);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, request, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RES",  response.toString());
                        try {
                           JSONArray te = response.getJSONArray("routes");
                           JSONObject te2 = te.getJSONObject(0);
                           JSONArray te3 = te2.getJSONArray("legs");
                           JSONObject te4 =te3.getJSONObject(0);
                           JSONObject te5 = te4.getJSONObject("duration");
                           String duracion = te5.getString("text");
                           tiempoEntrega.setText(duracion);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        VolleyApp.getInstance().getRequestQueue().add(jsonObjectRequest);

    }

}
