package com.example.virginia.panadelivery.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Adapters.HistorialConductorAdapter;
import com.example.virginia.panadelivery.Adapters.HistorialPedidosAdapter;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class historialConductorFragment extends Fragment {
    private FirebaseAuth fireAuth;
    private FirebaseFirestore db;
    private List<Pedido> historialPedidos;
    private RecyclerView vistaHistorial;
    private HistorialConductorAdapter adaptador;


    private OnFragmentInteractionListener mListener;

    public historialConductorFragment() {
        // Required empty public constructor
    }


/*
    public static historialConductorFragment newInstance(String param1, String param2) {
        historialConductorFragment fragment = new historialConductorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View mView = inflater.inflate(R.layout.fragment_historial_conductor, container, false);
        vistaHistorial = (RecyclerView)  mView.findViewById(R.id.historialConductor);
        historialPedidos = new ArrayList<>();
        adaptador = new HistorialConductorAdapter(historialPedidos);
        vistaHistorial.setHasFixedSize(true);
        vistaHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
        vistaHistorial.setAdapter(adaptador);
        fireAuth = fireAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        final String email = fireAuth.getCurrentUser().getEmail();
        Query resultado = db
                .collection("Pedidos").whereEqualTo("conductor", email);

        if (resultado != null) {
            resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d("E", e.getMessage());
                    }
                    else {
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED ) {
                                if (Integer.parseInt(doc.getDocument().get("activo").toString())== 0){
                                    Pedido pedido = doc.getDocument().toObject(Pedido.class);
                                    historialPedidos.add(pedido);
                                    adaptador.notifyDataSetChanged();
                                }

                            }
                        }
                    }
                }
            });
        }
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
