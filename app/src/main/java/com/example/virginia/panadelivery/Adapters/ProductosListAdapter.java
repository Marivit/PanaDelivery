package com.example.virginia.panadelivery.Adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductosListAdapter extends RecyclerView.Adapter<ProductosListAdapter.ViewHolder> {

    public List<Producto> productos;
    public List<Producto> checkout;
    private TextView montoTotal;
    private int sumaMonto = 0;


    public ProductosListAdapter(List<Producto> productos, List<Producto> checkout, TextView montoTotal) {
        this.productos = productos;
        this.checkout = checkout;
        this.montoTotal = montoTotal;
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

        holder.setProducto(productos.get(position));
        holder.nombreProducto.setText(productos.get(position).getNombre());
        Picasso.get().load(productos.get(position).getFoto()).resize(90,91).centerCrop().into(holder.imagenProducto);
        holder.descripcion.setText(productos.get(position).getDescripcion());
        holder.precioUnidad.setText(productos.get(position).getPrecio() + " BSS.");
        holder.unidadesTotales.setText(Integer.toString(productos.get(position).getCantidad()));
        holder.bind(productos.get(position));

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView nombreProducto;
        public TextView descripcion;
        public TextView cantidadProducto;
        public FloatingActionButton mas, menos;
        public CheckBox confirmarProducto;
        public Producto producto, productoCheckout;
        public ImageView imagenProducto;
        public TextView precioUnidad;
        public TextView precioTotal;
        public TextView unidadesTotales;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            nombreProducto = (TextView) mView.findViewById(R.id.nombre);
            mas = (FloatingActionButton) mView.findViewById(R.id.mas);
            menos = (FloatingActionButton) mView.findViewById(R.id.menos);
            cantidadProducto = (TextView) mView.findViewById(R.id.cantidadProducto);
            descripcion = (TextView) mView.findViewById(R.id.descripcion);
            confirmarProducto = (CheckBox) mView.findViewById(R.id.checkBoxAñadir);
            imagenProducto = (ImageView) mView.findViewById(R.id.imagenProducto);
            precioUnidad = (TextView) mView.findViewById(R.id.precioUnidad);
            precioTotal = (TextView) mView.findViewById(R.id.precioTotal);
            unidadesTotales = (TextView) mView.findViewById(R.id.unidadesTotales);

        }
        public void setProducto(Producto producto) {
            this.producto = producto;
            productoCheckout = new Producto();
            productoCheckout.setCantidad(0);
            productoCheckout.setNombre(producto.getNombre());
            productoCheckout.setId(producto.getId());
            productoCheckout.setFoto(producto.getFoto());
            productoCheckout.setDescripcion(producto.getDescripcion());
            Log.d("HOL","HOLA");

        }
        public void bind(final Producto p2 ) {
            mas.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view ) {



                    int numero = Integer.parseInt(cantidadProducto.getText().toString());
                    numero++;
                    if (numero > producto.getCantidad()) {
                        numero--;
                        Toast.makeText(mView.getContext(), "No se puede sobrepasar de la demanda total", Toast.LENGTH_SHORT).show();
                    }
                    productoCheckout.setCantidad(numero);
                    cantidadProducto.setText(Integer.toString(numero));
                    int pt = (numero * Integer.parseInt(p2.getPrecio()));
                    precioTotal.setText(Integer.toString(pt));
                    sumaMonto = sumaMonto + pt;
                    montoTotal.setText(Integer.toString(sumaMonto));

                }
            });
            menos.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view ) {


                    int numero = Integer.parseInt(cantidadProducto.getText().toString());
                    numero--;
                    if (numero < 0) {
                        numero = 0;
                        Toast.makeText(mView.getContext(), "No puede pedir unidades negativas", Toast.LENGTH_SHORT).show();
                    }
                    productoCheckout.setCantidad(numero);
                    cantidadProducto.setText(Integer.toString(numero));
                    int pt = (numero * Integer.parseInt(p2.getPrecio()));
                    precioTotal.setText(Integer.toString(pt));
                    int sumaMonto = Integer.parseInt(montoTotal.getText().toString());
                    montoTotal.setText(Integer.toString(sumaMonto));


                }
            });
            confirmarProducto.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view ) {


                    if (confirmarProducto.isChecked()) {
                        checkout.add(productoCheckout);



                    }
                    if (!confirmarProducto.isChecked()) {
                        checkout.remove(productoCheckout);
                    }


                    Log.d("LISTA CHECKOUT TAMANO", Integer.toString(checkout.size()));
                }
            });

        }

    }
}
