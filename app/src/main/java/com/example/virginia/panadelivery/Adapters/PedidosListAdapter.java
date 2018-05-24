package com.example.virginia.panadelivery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.virginia.panadelivery.Activities.MapsActivity;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class PedidosListAdapter extends RecyclerView.Adapter<PedidosListAdapter.ViewHolder> {

    public List<Pedido> pedidos;
    public Context context;

    public PedidosListAdapter(List<Pedido> pedidos, Context context) {
        this.pedidos = pedidos;
        this.context = context;

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
        holder.latitud = pedidos.get(position).getLatitud();
        holder.longitud = pedidos.get(position).getLongitud();
        holder.bind();

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
        public FloatingActionButton buttonElegir;
        public String latitud;
        public String longitud;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            numPedido = (TextView) mView.findViewById(R.id.numeroPedido);
            direccion = (TextView) mView.findViewById(R.id.descripcionDireccion);
            fecha = (TextView) mView.findViewById(R.id.fecha);
            hora = (TextView) mView.findViewById(R.id.hora);
            buttonUbicacion = (FloatingActionButton) mView.findViewById(R.id.buttonUbicacion);
            buttonElegir = (FloatingActionButton) mView.findViewById(R.id.buttonElegir);



        }

        public void bind() {
            buttonUbicacion.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {


                  Log.d("Mensaje:", String.valueOf(latitud));
                  Log.d("Mensaje2:", String.valueOf(longitud));
                    //context.startActivity(new Intent(context, ProfileClienteActivity.class));

                    //view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), MapsActivity.class));


                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    intent.putExtra("latitud", latitud);
                    intent.putExtra("longitud", longitud);
                    view.getContext().startActivity(intent);


                }

            });
            buttonElegir.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    
                }
            });
        }


    }

}
