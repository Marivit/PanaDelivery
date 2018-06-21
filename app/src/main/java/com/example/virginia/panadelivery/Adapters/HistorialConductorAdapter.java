package com.example.virginia.panadelivery.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class HistorialConductorAdapter extends RecyclerView.Adapter<HistorialConductorAdapter.ViewHolder> {

    List<Pedido> listaPedidosConductor;

    public HistorialConductorAdapter(List<Pedido> listaPedidosConductor) {
        this.listaPedidosConductor = listaPedidosConductor;
    }

    public HistorialConductorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_entrada_historial_conductor,  parent, false);
        return new HistorialConductorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialConductorAdapter.ViewHolder holder, int position) {
        holder.fecha.setText(listaPedidosConductor.get(position).getFecha());
        holder.monto.setText(listaPedidosConductor.get(position).getMontoTotal());
        holder.hora.setText(listaPedidosConductor.get(position).getHora());
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

        public ViewHolder(View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.fechaH);
            monto = itemView.findViewById(R.id.montoH);
            hora = itemView.findViewById(R.id.horaH);
        }
    }
}

