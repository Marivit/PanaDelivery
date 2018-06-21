package com.example.virginia.panadelivery.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class HistorialPedidosAdapter extends RecyclerView.Adapter<HistorialPedidosAdapter.ViewHolder> {

    public List<Pedido> historialPedidos;
    public HistorialPedidosAdapter(List<Pedido> pedidos) {

        this.historialPedidos = pedidos;

    }

    @NonNull
    @Override
    public HistorialPedidosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_historial_pedido,  parent, false);
        return new HistorialPedidosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fecha.setText(historialPedidos.get(position).getFecha());
        holder.monto.setText(historialPedidos.get(position).getMontoTotal());
        holder.hora.setText(historialPedidos.get(position).getHora());

    }





    @Override
    public int getItemCount() {
        return historialPedidos.size();
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
