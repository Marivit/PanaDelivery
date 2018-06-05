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

public class CheckoutListAdapter extends RecyclerView.Adapter<CheckoutListAdapter.ViewHolder> {

    public List<Producto> checkout;
    public CheckoutListAdapter( List<Producto> checkout) {

        this.checkout = checkout;
        Log.d("HOLA", "GOLA");
    }
    
    @NonNull
    @Override
    public CheckoutListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_checkout,  parent, false);
        return new CheckoutListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutListAdapter.ViewHolder holder, int position) {
        holder.nombreProducto.setText(checkout.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return this.checkout.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView nombreProducto;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nombreProducto = mView.findViewById(R.id.nombreProducto);

        }
    }
}
