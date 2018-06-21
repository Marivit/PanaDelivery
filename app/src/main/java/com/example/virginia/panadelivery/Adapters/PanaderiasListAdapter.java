package com.example.virginia.panadelivery.Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.virginia.panadelivery.Fragments.PanaderiasListFragment;
import com.example.virginia.panadelivery.Fragments.ProductosListFragment;
import com.example.virginia.panadelivery.Modelos.Panaderia;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;
import android.support.v4.app.FragmentManager;

import java.util.List;

public class PanaderiasListAdapter extends RecyclerView.Adapter<PanaderiasListAdapter.ViewHolder> {



    List<Panaderia> panaderias;
    FragmentManager fm;
    public PanaderiasListAdapter(List<Panaderia> panaderias, FragmentManager fm) {
        this.panaderias = panaderias;
        this.fm = fm;
    }

    public PanaderiasListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_panaderias,  parent, false);
        Log.d("MMG", "Se creo un nuevo viewholder!");
        return new PanaderiasListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PanaderiasListAdapter.ViewHolder holder, int position) {
            holder.nombrePanaderia.setText(panaderias.get(position).getNombre());
            holder.direccion.setText(panaderias.get(position).getDireccion());
            Bundle bundle = new Bundle();
            String id = panaderias.get(position).getId();
            bundle.putString("id", id);
            bundle.putString("nombre", panaderias.get(position).getNombre());

            final ProductosListFragment fragmentoPL = new ProductosListFragment();
            fragmentoPL.setArguments(bundle);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view ) {
                   FragmentTransaction ft = fm.beginTransaction();


                    ft.replace(R.id.contenedorCliente, fragmentoPL, "PRODUCTOS");
                    ft.commit();
                }
            });
    }

    @Override
    public int getItemCount() {
        return panaderias.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView nombrePanaderia;
        public TextView direccion;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nombrePanaderia = (TextView) mView.findViewById(R.id.nombrePanaderia);
            direccion = (TextView) mView.findViewById(R.id.direccionPanaderia);


        }
    }

}
