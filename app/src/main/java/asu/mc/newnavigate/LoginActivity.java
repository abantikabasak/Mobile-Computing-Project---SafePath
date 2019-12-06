package asu.mc.newnavigate;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button signin;
    TextView signup;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText7);
        password = findViewById(R.id.editText6);
        signup = findViewById(R.id.textView2);
        signin = findViewById(R.id.button);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser!= null )
                {
                    Toast.makeText(LoginActivity.this, "You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);

                }

                else
                {
                    Toast.makeText(LoginActivity.this, "Please Login",Toast.LENGTH_SHORT).show();

                }

            }
        };

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = email.getText().toString();
                String pwd = password.getText().toString();
                if (Email.isEmpty()) {
                    email.setError("Please enter Email-Id");


                } else if (pwd.isEmpty()) {
                    password.setError("Please enter Password");
                } else if (Email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are EMPTY!", Toast.LENGTH_SHORT).show();
                } else if (!(Email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(Email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Error, Please Login Again!", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent isignup = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(isignup);
            }
        });
    }

    protected void onStart() {

        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }











}


