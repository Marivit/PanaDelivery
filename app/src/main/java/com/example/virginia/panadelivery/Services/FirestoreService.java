package com.example.virginia.panadelivery.Services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.virginia.panadelivery.Modelos.Panaderia;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

public class FirestoreService {


    String TAG = "FService";
    FirebaseFirestore db;
    FirebaseAuth auth;

    public FirestoreService() {
       this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }


   public List<Producto> getProductosPanaderia(String id ) {
       final List<Producto> listaProductos = new ArrayList<>();
       db.collection("Panaderias").document(id).collection("Productos").addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

               if (e != null) {
                   Log.d(TAG, e.getMessage());

               }

               for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                   //TODO: Agregar modified

                   if(doc.getType() == DocumentChange.Type.ADDED) {
                       String name = doc.getDocument().getString("nombre");
                       Log.d(TAG, name);
                       Producto producto = doc.getDocument().toObject(Producto.class);

                       listaProductos.add(producto);
                        Log.d(TAG, "Se agrego algo a la lista!");

                   }
               }

           }
       });

       return listaProductos;
    }

    public void checkout(final List<Producto> productosCheckout, String idPanaderia, final String nombrePanaderia) {
        String email = auth.getCurrentUser().getEmail();
        Log.d("CHECKOUT", email);


        // Disminuir cantidad en stock
       final  CollectionReference reference = db.collection("Panaderias").document(idPanaderia).collection("Productos");
        for (int j = 0; j < productosCheckout.size(); j++) {
            final Map<Object, Object> dataToAdd = new HashMap<>();
            final String idProd = productosCheckout.get(j).getId();
            final String nombre = productosCheckout.get(j).getNombre();

            final int demanda = productosCheckout.get(j).getCantidad();
            Log.d("ID", productosCheckout.get(j).getId());

          reference.document(productosCheckout.get(j).getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()) {
                      Long cantidad = (Long) task.getResult().get("cantidad");
                      Long cantidadFinal = cantidad - demanda;
                      Long cantidadNueva = cantidadFinal;

                      dataToAdd.put((String) "cantidad", (Long) cantidadNueva);
                      dataToAdd.put((String) "nombre", (String)nombre );

                      reference.document(idProd).set(dataToAdd);

                  }
              }
          });



        }



        // Agregar a pedidos
        final Map<Object, Object> dataPedido = new HashMap<>();
        Log.d("AP", "Se agregara a pedidos");

        final DocumentReference reference2 = db.collection("Usuarios").document(email);

        reference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if (task.isSuccessful()) {
                          Map<Object, Object> dataPedido = new HashMap<>();
                          dataPedido.put("latitud",  Double.toString( (Double) task.getResult().get("latitud")));
                          dataPedido.put("longitud", Double.toString((Double) task.getResult().get("longitud")));
                          dataPedido.put("conductor", "conductorPlaceHolder");
                          dataPedido.put("estado", "En espera");
                          dataPedido.put("montoTotal", "Placeholder");
                          dataPedido.put("panaderia", nombrePanaderia);



                          // TODO: Quitar placeholder de detalle direccion

                          Date date = new Date();
                          SimpleDateFormat Formater = new SimpleDateFormat("dd/MM/yyyy");
                          String  fecha = Formater.format(date);
                          Calendar calendar = Calendar.getInstance();
                          SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                          String hora = timeFormat.format(calendar.getTime());


                          dataPedido.put("fecha",fecha );
                          dataPedido.put("hora",hora);


                          dataPedido.put("direccion", "PLACEHOLDER");
                          Log.d("OBJETO", dataPedido.toString());
                          reference2.collection("pedidos").add(dataPedido);

                      }
                }
        }
        );





    }

    public void getRol(String email) {



    }

    public Long getNumPedidos() {
       final Map<String, Long> numPedidos = new HashMap<>();

         db.collection("Extra").document("data").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {


             @Override

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Long numerito = (Long) task.getResult().get("numPedidos");
                    numerito++;
                    numPedidos.put("numPedidos", numerito);
                }
            }
        });
        db.collection("Extra").document("data").set(numPedidos);
        return numPedidos.get("numPedidos");

    }
}
