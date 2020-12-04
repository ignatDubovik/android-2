package com.example.quizapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;

    String questions[] = {
            "Каким советским писателем был написан роман 'Тихий Дон'?",
            "Атом, имеющий электрический заряд, называется...",
            "Мгновенная скорость изменения функции характеризуется ее...",
            "Предложение 'Мне завтра на тренировку' является...",
            "Фраза 'Весь зал аплодировал стоя' является...",
            "Сколькими способами можно составить команду из 4 человек для соревнования из 7 пловцов?",
            "Выберите формулу этилового спирта",
            "Выберите правильную расцветку белорусского флага",
            "Выберите существующее словосочетание (collocation)",
            "Классическая генетика базируется на принципах, которые известны как..."
    };
    String answers[] = {"М. Шолохов","ион","производной","односоставным неполным","метонимией","35","C2H5OH","бело-красно-белая","markedly different","законы Менделя"};
    String opt[] = {
            "А. Толстой","М. Шолохов","М. Горький","М. Булгаков",
            "молекула","протон","нейтрон","ион",
            "интегралом","производной","первообразной","приращением",
            "двусоставным неполным","односоставным неполным","односоставным полным","двусоставным полным",
            "метонимией","метафорой","олицетворением","лилотой",
            "70","35","28","21",
            "CH3OH","C4H9OH","C3H7OH","C2H5OH",
            "красно-зеленая","бело-красно-зеленая","бело-красно-белая","вернись к предыдущему ответу",
            "markedly different","a decline of popularity","a widespread idea","exceptionally hard",
            "законы Менделя","законы де Моргана","законы Кориолиса","законы каменных джунглей"
    };
    int flag=0;
    public static int marks=0,correct=0,wrong=0;
    public static int userMaxPoints = 0;
    public static String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        final TextView score = (TextView)findViewById(R.id.textView4);
        TextView textView=(TextView)findViewById(R.id.DispName);
        Intent intent = getIntent();
        //String name= intent.getStringExtra("myname");
        String email= intent.getStringExtra("userEmail");
        if(intent.getStringExtra("userMaxPoints")!=null){
            userMaxPoints= Integer.parseInt(intent.getStringExtra("userMaxPoints"));
        }
        else userMaxPoints=0;

        userID= intent.getStringExtra("userID");


            textView.setText("Привет, " + email);

        submitbutton=(Button)findViewById(R.id.button3);
        quitbutton=(Button)findViewById(R.id.buttonquit);
        tv=(TextView) findViewById(R.id.tvque);

        radio_g=(RadioGroup)findViewById(R.id.answersgrp);
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        rb4=(RadioButton)findViewById(R.id.radioButton4);
        tv.setText(questions[flag]);
        rb1.setText(opt[0]);
        rb2.setText(opt[1]);
        rb3.setText(opt[2]);
        rb4.setText(opt[3]);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int color = mBackgroundColor.getColor();
                //mLayout.setBackgroundColor(color);

                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Необходимо выбрать 1 вариант ответа", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
//                Toast.makeText(getApplicationContext(), ansText, Toast.LENGTH_SHORT).show();
                if(ansText.equals(answers[flag])) {
                    correct++;
                    Toast.makeText(getApplicationContext(), "Верно!", Toast.LENGTH_SHORT).show();
                }
                else {
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Неверно!", Toast.LENGTH_SHORT).show();
                }

                flag++;

                if (score != null)
                    score.setText(""+correct);

                if(flag<questions.length)
                {
                    tv.setText(questions[flag]);
                    rb1.setText(opt[flag*4]);
                    rb2.setText(opt[flag*4 +1]);
                    rb3.setText(opt[flag*4 +2]);
                    rb4.setText(opt[flag*4 +3]);
                }
                else
                {
                    marks=correct;
                    Intent in = new Intent(getApplicationContext(),ResultActivity.class);
                    startActivity(in);
                }
                radio_g.clearCheck();
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ResultActivity.class);

                startActivity(intent);
            }
        });
    }

}