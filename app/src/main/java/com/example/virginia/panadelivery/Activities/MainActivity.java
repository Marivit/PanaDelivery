package com.example.virginia.panadelivery.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virginia.panadelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//import static com.example.virginia.panadelivery.R.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseauth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseauth = FirebaseAuth.getInstance();

        if (firebaseauth.getCurrentUser() != null ) {
            finish();
            startActivity(new Intent(this, ProfileClienteActivity.class));
        }

        progressDialog = new ProgressDialog(this);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);

        buttonLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

    }

    private void LoginUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Porfavor ingrese un email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Porfavor ingrese la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        //Si cumple las validaciones, primero se muestra un ProgressDialog
        progressDialog.setMessage("Iniciando Sesión..");
        progressDialog.show();

        firebaseauth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Se logro hacer el login", Toast.LENGTH_SHORT).show();
                            db = FirebaseFirestore.getInstance();

                            db.collection("Usuarios").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Long rol = (Long) task.getResult().get("rol");
                                    Log.d("ROL", Long.toString(rol));
                                    if (rol == 1) {
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), ProfileClienteActivity.class));
                                    }
                                    if (rol == 2) {
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), ProfileConductorActivity.class));
                                    }

                                }
                            });



                            /* //Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/
                        } else {
                            Toast.makeText(getApplicationContext(), "El login fallo", Toast.LENGTH_SHORT).show();
                            /*// If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);*/
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
          LoginUser();
            //LoginUser();
        }
        if (view == textViewSignUp) {
            //will open register activity here
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

}
