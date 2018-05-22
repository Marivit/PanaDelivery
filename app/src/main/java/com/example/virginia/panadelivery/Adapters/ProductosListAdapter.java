package com.example.virginia.panadelivery.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class ProductosListAdapter extends RecyclerView.Adapter<ProductosListAdapter.ViewHolder> {

    public List<Producto> productos;

    public ProductosListAdapter(List<Producto> productos) {
        this.productos = productos;

    }
    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_producto, parent, false);
        Log.d("MMG", "Se creo un nuevo viewholder!");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nombreProducto.setText(productos.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView nombreProducto;
        public TextView proveedorProducto;
        public TextView cantidadProducto;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nombreProducto = (TextView) mView.findViewById(R.id.nombre);

        }
    }
}
