package com.example.virginia.panadelivery.Adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class PedidosListAdapter extends RecyclerView.Adapter<PedidosListAdapter.ViewHolder> {

    public List<Pedido> pedidos;

    public PedidosListAdapter(List<Pedido> pedidos) {
        this.pedidos = pedidos;

    }
    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pedido, parent, false);
        Log.d("MMG", "Se creo un nuevo viewholder!");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.numPedido.setText(Integer.toString(pedidos.get(position).getNumPedido()));
        holder.fecha.setText(pedidos.get(position).getFecha());
        holder.hora.setText(pedidos.get(position).getHora());
        holder.direccion.setText(pedidos.get(position).getDireccion());


    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView numPedido;
        public TextView fecha;
        public TextView hora;
        public TextView direccion;
        public FloatingActionButton buttonUbicacion;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            numPedido = (TextView) mView.findViewById(R.id.numeroPedido);
            direccion = (TextView) mView.findViewById(R.id.descripcionDireccion);
            fecha = (TextView) mView.findViewById(R.id.fecha);
            hora = (TextView) mView.findViewById(R.id.hora);
            buttonUbicacion = (FloatingActionButton) mView.findViewById(R.id.buttonUbicacion);



        }
        public void bind() {
            buttonUbicacion.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {




                }

            });
        }

    }

}
