package com.example.virginia.panadelivery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class panaderia_listFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment inicial
        FragmentManager fragmentManager =getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedorCliente, new PanaderiaFragment()).commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panaderia_list, container, false);
    }


}
