package com.example.virginia.panadelivery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virginia.panadelivery.Activities.MapsActivity;
import com.example.virginia.panadelivery.Fragments.PedidoConductorFragment;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidosListAdapter extends RecyclerView.Adapter<PedidosListAdapter.ViewHolder> {
    private String TAG = "PRUEBA:";
    public List<Pedido> pedidos;
    public Context context;
    FragmentManager fm;
    public boolean validar;
    private String TAG2 = "probando";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public PedidosListAdapter(List<Pedido> pedidos, Context context, FragmentManager fm, boolean validar) {
        this.pedidos = pedidos;
        this.context = context;
        this.fm = fm;
        this.validar = validar;
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

        holder.fecha.setText(pedidos.get(position).getFecha());
        holder.hora.setText(pedidos.get(position).getHora());
        holder.direccion.setText(pedidos.get(position).getDireccion());
        holder.latitud = pedidos.get(position).getLatitud();
        holder.longitud = pedidos.get(position).getLongitud();
        holder.idPedido = pedidos.get(position).getIdPedido();
        holder.correoCliente = pedidos.get(position).getCorreoCliente();
        holder.conductorEmail = pedidos.get(position).getConductorEmail();
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
        public String idPedido;
        public String correoCliente;
        public String conductorEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            numPedido = (TextView) mView.findViewById(R.id.numeroPedido);
            direccion = (TextView) mView.findViewById(R.id.descripcionDireccion);
            fecha = (TextView) mView.findViewById(R.id.fechaH);
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
                    if(validar){
                        validar();
                    } else if (!validar) {
                        Log.d(TAG, idPedido);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        FirebaseAuth firebaseAuth;


                        firebaseAuth = FirebaseAuth.getInstance();
                        final String emailConductor = firebaseAuth.getCurrentUser().getEmail();

                        // Actualizar el campo conductor y su correo para asignarselo a este
                        Map<String, Object> actPedido = new HashMap<>();
                        actPedido.put("estado", 2);
                        actPedido.put("conductor", emailConductor);

                        DocumentReference resultado = db.collection("Pedidos").document(idPedido);

                        resultado.set(actPedido, SetOptions.merge());

                        //Pasar algunos parametros necesarios
                        /*Bundle args = new Bundle();
                        args.putString("emailCliente", correoCliente);
                        args.putString("emailConductor", emailConductor);*/

                        //Cambiar de fragment al del pedido actual
                        FragmentTransaction ft = fm.beginTransaction();

                        final PedidoConductorFragment fragmentoP = new PedidoConductorFragment();
                        //fragmentoP.setArguments(args);
                        ft.replace(R.id.containerConductor, fragmentoP);
                        ft.commit();
                    }



                }


            });


        }

    }



    public void validar() {
        Toast.makeText(context, "Ya tienes un pedido en curso, debes esperar a que finalice!", Toast.LENGTH_LONG).show();
    }
}

