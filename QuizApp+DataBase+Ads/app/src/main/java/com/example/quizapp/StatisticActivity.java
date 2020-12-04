package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StatisticActivity extends AppCompatActivity {

    private ListView listView;
    //private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private TextView tv;
    //ArrayList<User> users = new ArrayList<User>();
    ArrayList<UserWOPassrord> userArrayList = new ArrayList<UserWOPassrord>();
    ArrayList<String> userArrayListString = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        listView = (ListView)findViewById(R.id.listViewTop);
        tv = (TextView)findViewById(R.id.goBackFromStatistics);
        reference = FirebaseDatabase.getInstance().getReference("Users");


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        //final ArrayAdapter<UserWOPassrord> adapter = new ArrayAdapter<UserWOPassrord>(StatisticActivity.this,android.R.layout.list_content,userArrayList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(StatisticActivity.this,android.R.layout.simple_list_item_1,userArrayListString);

        listView.setAdapter(adapter);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User value = dataSnapshot.getValue(User.class);
                //users.add(value);
                UserWOPassrord u = new UserWOPassrord(value.email, value.maxPoints);
                userArrayList.add(u);
                for(int i = userArrayList.size()-1 ; i > 0 ; i--){
                    for(int j = 0 ; j < i ; j++){
                        if(Integer.parseInt(userArrayList.get(j).maxPoints) < Integer.parseInt(userArrayList.get(j+1).maxPoints) ){
                            UserWOPassrord tmp = userArrayList.get(j);
                            userArrayList.set(j, userArrayList.get(j+1));
                            userArrayList.set(j+1, tmp);
                        }
                    }
                }

                userArrayListString.clear();
                if(!userArrayList.isEmpty()) {
                    for (int i = 0; i < userArrayList.size(); i++) {
                        userArrayListString.add("" + userArrayList.get(i).email + ", " + userArrayList.get(i).maxPoints);
                    }
                }
                //Collections.sort(userArrayList, Collections.<UserWOPassrord>reverseOrder());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                adapter.notifyDataSetChanged();
            }
        });
    }
}