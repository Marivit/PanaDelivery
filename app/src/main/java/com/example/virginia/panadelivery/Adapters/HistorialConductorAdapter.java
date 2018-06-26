package com.example.virginia.panadelivery.Adapters;

import android.app.Activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.virginia.panadelivery.Activities.ProfileConductorActivity;
import com.example.virginia.panadelivery.Fragments.PedidoClienteFragment;
import com.example.virginia.panadelivery.Fragments.PedidoConductorFragment;
import com.example.virginia.panadelivery.Fragments.detalleHistorialFragment;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class HistorialConductorAdapter extends RecyclerView.Adapter<HistorialConductorAdapter.ViewHolder> {

    List<Pedido> listaPedidosConductor;
    Activity actividadActual;
    FragmentManager fm;
    public HistorialConductorAdapter(List<Pedido> listaPedidosConductor) {
        this.listaPedidosConductor = listaPedidosConductor;
    }

    public void setActividadActual(Activity actividadActual) {
        this.actividadActual = actividadActual;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public HistorialConductorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_entrada_historial_conductor,  parent, false);
        return new HistorialConductorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialConductorAdapter.ViewHolder holder, int position) {
        int posicion = position;
        holder.fecha.setText(listaPedidosConductor.get(position).getFecha());
        holder.monto.setText(listaPedidosConductor.get(position).getMontoTotal());
        holder.hora.setText(listaPedidosConductor.get(position).getHora());
        if (listaPedidosConductor.get(position).getActivo() ==  1) {
        holder.detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.containerConductor, new PedidoConductorFragment()).commit();
            }
        }

        );}
        else {
            Bundle args = new Bundle();
            String coso = listaPedidosConductor.get(posicion).getIdPedido();
            args.putString("idPedido", listaPedidosConductor.get(posicion).getIdPedido());
            detalleHistorialFragment dhf = new detalleHistorialFragment();
            dhf.setArguments(args);
            fm.beginTransaction().replace(R.id.containerConductor, dhf);
        }
    }

    @Override
    public int getItemCount() {
        return listaPedidosConductor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView fecha;
        public TextView monto;
        public TextView hora;
        public Button detalle;
        public ViewHolder(View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.fechaH);
            monto = itemView.findViewById(R.id.montoH);
            hora = itemView.findViewById(R.id.horaH);
            detalle = itemView.findViewById(R.id.detallePedido);
        }
    }
}

