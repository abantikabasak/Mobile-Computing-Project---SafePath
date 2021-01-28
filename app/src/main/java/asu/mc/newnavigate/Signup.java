package asu.mc.newnavigate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import asu.mc.newnavigate.LoginActivity;

public class Signup extends AppCompatActivity {
    EditText email,password;
    Button signUp;
    TextView signin;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText7);
        password = findViewById(R.id.editText6);
        signin = findViewById(R.id.textView2);
        signUp = findViewById(R.id.button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String pwd = password.getText().toString();
                if(Email.isEmpty())
                {
                    email.setError("Please enter Email-Id");
                    email.requestFocus();
                }

                else if(pwd.isEmpty())
                {
                    password.setError("Please enter Password");
                    password.requestFocus();
                }

                else if(Email.isEmpty() && pwd.isEmpty())
                {
                    Toast.makeText(Signup.this,"Fields are EMPTY!",Toast.LENGTH_SHORT).show();
                }

                else if(!(Email.isEmpty()&&pwd.isEmpty()))
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(Email,pwd).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(Signup.this,"Sign Up unsuccessful! Please try again!",Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                startActivity(new Intent(Signup.this,MainActivity.class));
                            }
                        }
                    });
                }

                else
                {
                    Toast.makeText(Signup.this, "Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }


        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
