package com.example.virginia.panadelivery.Adapters;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.virginia.panadelivery.Activities.ProfileClienteActivity;
import com.example.virginia.panadelivery.Fragments.PedidoClienteFragment;
import com.example.virginia.panadelivery.Fragments.detalleHistorialFragment;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class HistorialPedidosAdapter extends RecyclerView.Adapter<HistorialPedidosAdapter.ViewHolder> {

    public List<Pedido> historialPedidos;
    private FragmentManager fm;
    private Activity actividadActual;
    public HistorialPedidosAdapter(List<Pedido> pedidos) {

        this.historialPedidos = pedidos;

    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public void setActividadActual(Activity pedidoActual) {
        this.actividadActual = pedidoActual;
    }

    @NonNull
    @Override
    public HistorialPedidosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_historial_pedido,  parent, false);
        return new HistorialPedidosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int posicion = position;
        holder.fecha.setText(historialPedidos.get(position).getFecha());
        holder.monto.setText(historialPedidos.get(position).getMontoTotal());
        holder.hora.setText(historialPedidos.get(position).getHora());
        if (historialPedidos.get(position).getActivo() ==  1) {
            holder.activo.setText("activo");
            if (actividadActual instanceof ProfileClienteActivity) {
              ((ProfileClienteActivity) actividadActual).seleccionarPedidoActualDrawer();
                holder.detallePedido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fm.beginTransaction().replace(R.id.contenedorCliente, new PedidoClienteFragment()).commit();


                    }
                })
                ;
            }


        }
        else {
            holder.activo.setText("Terminado");
            holder.detallePedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    String coso = historialPedidos.get(posicion).getIdPedido();
                    args.putString("idPedido", historialPedidos.get(posicion).getIdPedido());
                    detalleHistorialFragment dhf = new detalleHistorialFragment();
                    dhf.setArguments(args);
                    fm.beginTransaction().replace(R.id.contenedorCliente, dhf).commit();
                }
            });
        }
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
        public TextView activo;
        public Button detallePedido;
        public ViewHolder(View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.fechaH);
            monto = itemView.findViewById(R.id.montoH);
            hora = itemView.findViewById(R.id.horaH);
            activo = itemView.findViewById(R.id.pedidoActivoCliente);
            detallePedido = itemView.findViewById(R.id.buttonDetalle);


        }
    }
}
