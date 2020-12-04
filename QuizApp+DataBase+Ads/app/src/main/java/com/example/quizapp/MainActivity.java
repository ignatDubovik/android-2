package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

private TextView registerTV, statisticsTV;
private FirebaseAuth mAuth;
private EditText nametext;
private EditText passwordText;
private ProgressBar progressBar;
private User userProfile;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Button startbutton=(Button)findViewById(R.id.button);
        nametext=(EditText)findViewById(R.id.editName);
        passwordText = (EditText)findViewById(R.id.editPassword);
        progressBar = (ProgressBar)findViewById(R.id.authbar);
        progressBar.setVisibility(View.INVISIBLE);
        registerTV = (TextView)findViewById(R.id.register);
        statisticsTV = (TextView)findViewById(R.id.statistics);
        mAuth = FirebaseAuth.getInstance();

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        statisticsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StatisticActivity.class);
                startActivity(intent);
            }
        });

    }

    private void userLogin()
    {
        String email = nametext.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if(email.isEmpty())
        {
            nametext.setError("Введите email");
            return;
        }
        if(password.isEmpty())
        {
            passwordText.setError("Введите пароль");
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            nametext.setError("Введите корректный email");
            return;
        }

        if(password.length()<6)
        {
            passwordText.setError("6 символов минимум");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //редирект
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String userID;
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    userID = user.getUid();

                    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            userProfile = dataSnapshot.getValue(User.class);
                            if(userProfile!=null) {

                                Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                                //intent.putExtra("myname",name);
                                intent.putExtra("userMaxPoints", userProfile.maxPoints);
                                intent.putExtra("userEmail", userProfile.email);
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this,"Ошибка",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
                else{
                    Toast.makeText(MainActivity.this,"Неудача!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

}