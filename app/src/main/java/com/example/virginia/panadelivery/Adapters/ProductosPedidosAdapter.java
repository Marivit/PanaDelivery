package com.example.virginia.panadelivery.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductosPedidosAdapter extends RecyclerView.Adapter<ProductosPedidosAdapter.ViewHolder> {

    List<Producto> productosPedido;

    public ProductosPedidosAdapter(List<Producto> productosPedido) {
        this.productosPedido = productosPedido;
    }

    public ProductosPedidosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("ENTRO ACA", "ENTRO ACA");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_pedido_historial,  parent, false);
        return new ProductosPedidosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosPedidosAdapter.ViewHolder holder, int position) {
            Producto p2 = productosPedido.get(position);
            holder.nombre.setText(p2.getNombre());
            holder.descripcion.setText(p2.getDescripcion());
            holder.cantidad.setText(Integer.toString(p2.getCantidad()));
        try {
            Picasso.get().load(p2.getFoto()).resize(90, 91).centerCrop().into(holder.imagen);

        }
        catch(IllegalArgumentException exc) {
            Log.d("MALA IMAGEN", "Hay una imagen mal agregada en la base de datos");

        }
    }

    @Override
    public int getItemCount() {
        return productosPedido.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;
        public TextView cantidad;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenHistorial);
            nombre = itemView.findViewById(R.id.nombreHistorial);
            descripcion = itemView.findViewById(R.id.descripcionHistorial);
            cantidad = itemView.findViewById(R.id.cantidadHistorial);

        }
    }
}
