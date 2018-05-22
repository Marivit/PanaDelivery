package com.example.virginia.panadelivery.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.virginia.panadelivery.Modelos.Panaderia;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class PanaderiasListAdapter extends RecyclerView.Adapter<PanaderiasListAdapter.ViewHolder> {



    List<Panaderia> panaderias;

    public PanaderiasListAdapter(List<Panaderia> panaderias) {
        this.panaderias = panaderias;
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
    }

    @Override
    public int getItemCount() {
        return 0;
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
