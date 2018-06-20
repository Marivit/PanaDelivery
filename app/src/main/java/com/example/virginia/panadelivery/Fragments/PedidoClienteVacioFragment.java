package com.example.virginia.panadelivery.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.virginia.panadelivery.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidoClienteVacioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidoClienteVacioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidoClienteVacioFragment extends Fragment {

    private String texto="";
    private TextView textViewMostrar;

    public PedidoClienteVacioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedidoClienteVacioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PedidoClienteVacioFragment newInstance(String param1, String param2) {
        PedidoClienteVacioFragment fragment = new PedidoClienteVacioFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            texto = getArguments().getString("textoMostrar");
            textViewMostrar.setText(texto);
            this.getArguments().getString("id")
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_pedido_cliente_vacio, container, false);
        //textViewMostrar = (TextView) mView.findViewById(R.id.textoMostrar);
        return mView;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
