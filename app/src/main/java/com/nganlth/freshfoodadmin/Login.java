package com.nganlth.freshfoodadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nganlth.freshfoodadmin.Model.Admin;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText edEmail, edPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    public static CheckBox chkSave;
    DatabaseReference mDataAdmin;
    ArrayList<Admin> list_ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDataAdmin = FirebaseDatabase.getInstance().getReference("Admin");
        reference();
        loadData();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
        }
        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });

    }
    private void login() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if (email.isEmpty()){
            edEmail.setError("Email is required");
            edEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Please provide valid email");
            return;
        }
        if (password.isEmpty()){
            edPassword.setError("Password is required");
            edPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            edPassword.setError("Min password length should be 6 characters");
            edPassword.requestFocus();
            return;
        }

        mDataAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){

                    if (data.child("email").getValue(String.class).equals(email)){
                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        boolean check = chkSave.isChecked();
        SaveTT(email, password, check);
    }
    private void reference(){
        chkSave = findViewById(R.id.chkSave);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }
    private void SaveTT(String email, String pwd, boolean check){
        SharedPreferences preferences = getSharedPreferences("thongtin.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        if (check){
            editor.putString("Email", email);
            editor.putString("Password", pwd);
            editor.putBoolean("Check", check);
        }else {
            editor.clear();
        }
        editor.commit();

    }
    private void loadData(){
        SharedPreferences pref =getSharedPreferences("thongtin.dat", MODE_PRIVATE);
        boolean check = pref.getBoolean("Check", false);
        if (check){
            edEmail.setText(pref.getString("Email", ""));
            edPassword.setText(pref.getString("Password", ""));
            chkSave.setChecked(check);
        }
    }
}