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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

public class FirestoreService {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "FService";
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public FirestoreService() {}


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

    public void checkout(final List<Producto> productosCheckout, String idPanaderia) {
        String email = auth.getCurrentUser().getEmail();



        // Disminuir cantidad en stock
       final  CollectionReference reference = db.collection("Panaderias").document(idPanaderia).collection("Productos");
        for (int j = 0; j < productosCheckout.size(); j++) {
            final Map<String, Long> dataToAdd = new HashMap<>();
            final String idProd = productosCheckout.get(j).getId();
            final int demanda = productosCheckout.get(j).getCantidad();
            Log.d("ID", productosCheckout.get(j).getId());

          reference.document(productosCheckout.get(j).getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()) {
                      Long cantidad = (Long) task.getResult().get("cantidad");
                      Long cantidadFinal = cantidad - demanda;
                      Long cantidadNueva = cantidadFinal;

                      dataToAdd.put("cantidad", cantidadNueva);
                      reference.document(idProd).set(dataToAdd);

                  }
              }
          });



        }



        // Agregar a pedidos


    }


}
