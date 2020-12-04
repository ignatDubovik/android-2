package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText signUpEmail, signUpPassword;
    private Button signUpButton;
    private TextView goBackFromSigningUp;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        signUpEmail = (EditText)findViewById(R.id.signUpEmail);
        signUpPassword = (EditText)findViewById(R.id.signUpPassword);
        signUpButton = (Button)findViewById(R.id.SignUpButton);
        goBackFromSigningUp = (TextView)findViewById(R.id.goBackFromSigningUp);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        goBackFromSigningUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);


            }
        });

    }

    private void registerUser()
    {
        final String email = signUpEmail.getText().toString().trim();
        final String password = signUpPassword.getText().toString().trim();
        final int maxPoints = 0;
        if(email.isEmpty())
        {
            signUpEmail.setError("Введите email");
            return;
        }
        if(password.isEmpty())
        {
            signUpPassword.setError("Введите пароль");
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmail.setError("Введите корректный email");
            return;
        }

        if(password.length()<6)
        {
            signUpPassword.setError("6 символов минимум");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user = new User(email, password, maxPoints);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Регистрация успешна!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                //редирект
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"Провал!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Провал!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

}