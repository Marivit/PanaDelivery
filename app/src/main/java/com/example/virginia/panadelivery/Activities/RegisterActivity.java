package com.example.virginia.panadelivery.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virginia.panadelivery.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    //Variables
    private EditText editTextName;
    private EditText editTextLastname;
    private EditText editTextFecha;
    private EditText editTextTelefono;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private TextView textViewDireccion;
    private ProgressDialog progressDialog;
    private String direccionLatLng;
    private double latitud;
    private double longitud;
    //Base de datos
    private FirebaseAuth firebaseauth;
    private FirebaseFirestore firestore;
    //Ubicacion
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;
    private int PLACE_PICKER_REQUEST=1;
    private boolean mLocationPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseauth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextLastname = (EditText) findViewById(R.id.editTextLastName);
        editTextFecha = (EditText) findViewById(R.id.editTextFecha);
        editTextTelefono = (EditText) findViewById(R.id.editTextTelefono);
        textViewDireccion = (TextView) findViewById(R.id.textDireccion);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
        textViewDireccion.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
        }
        if (view == textViewSignIn) {
            //will open login activity here
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(view == textViewDireccion) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }

            try {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent i = builder.build(this);
                startActivityForResult(i, PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
                //Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
                //Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
            } catch (Exception e) {
                //Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode== RESULT_OK){
                Place place= PlacePicker.getPlace(this, data);
                if (place == null) {
                    Toast.makeText(this, "No place selected", Toast.LENGTH_LONG).show();
                    //Log.i(TAG, "No place selected");
                    return;
                }
                String address = String.format("Direccion: %s", place.getAddress());
                textViewDireccion.setText(address);
                direccionLatLng = String.format("%s", place.getLatLng());
                latitud= place.getLatLng().latitude;
                longitud= place.getLatLng().longitude;
                //Mensajito
                // Toast.makeText(this, direccionLatLng, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        //updateLocationUI();
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();
        String fechaN = editTextFecha.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();
        String direccion = textViewDireccion.getText().toString().trim();

        //Validaciones
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Porfavor ingrese un email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Porfavor ingrese la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(fechaN) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(direccion)){
            Toast.makeText(this, "Porfavor rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        //Si cumple las validaciones, primero se muestra un ProgressDialog
        progressDialog.setMessage("Registrando usuario..");
        progressDialog.show();

        firebaseauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /*// Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/

                            Map<String, Object> usuario = new HashMap<>();

                            usuario.put("nombre", editTextName.getText().toString().trim());
                            usuario.put("apellido", editTextLastname.getText().toString().trim());
                            usuario.put("fechaNacimiento", editTextFecha.getText().toString().trim());
                            usuario.put("email", editTextEmail.getText().toString().trim());
                            usuario.put("telefono", editTextTelefono.getText().toString().trim());
                            //usuario.put("direccion", direccion);
                            usuario.put("latitud", latitud);
                            usuario.put("longitud", longitud);
                            usuario.put("rol", 1);


                            firestore.collection("Usuarios").document(editTextEmail.getText().toString().trim()).set(usuario)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("FIREBASE", "DocumentSnapshot successfully written!");
                                            terminar();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("FIREBASE", "Error writing document", e);
                                        }
                                    });

                            progressDialog.cancel();
                            Toast.makeText(RegisterActivity.this, "Registro exitoso!", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR REGISTRO", "createUserWithEmail:failure", task.getException());
                            progressDialog.cancel();
                            Toast.makeText(RegisterActivity.this, "Fallo en el registro, intente de nuevo.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });


    }

    public void terminar() {
        finish();
        startActivity(new Intent(this, ProfileClienteActivity.class));
    }
}
