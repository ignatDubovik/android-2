package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.bgora.rpnlibrary.Calculator;
import com.github.bgora.rpnlibrary.exceptions.NoSuchFunctionFound;
import com.github.bgora.rpnlibrary.exceptions.WrongArgumentException;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    Button btnClear,btnOpenBracket,btnCloseBracket,btnClearLast,btnSin,btnCos,btnTan,btnCot,
            btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnDivide,btnMultiply,btnMinus,
            btnPeriod, btnPower,btnPlus,btnEqual, btnPercent,btnSqrRoot,btnSquare;
    TextView tvInput, tvOutput;
    Calculator calc = Calculator.createDefaultCalculator();
    String s, message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Calculator calc = Calculator.createDefaultCalculator();
        btnClear = findViewById(R.id.btnClear);
        btnOpenBracket = findViewById(R.id.btnOpenBracket);
        btnCloseBracket = findViewById(R.id.btnCloseBracket);
        btnClearLast = findViewById(R.id.btnClearLast);
        btnSin = findViewById(R.id.btnSin);
        btnCos = findViewById(R.id.btnCos);
        btnTan = findViewById(R.id.btnTan);
        btnCot = findViewById(R.id.btnCot);
        btnDivide = findViewById(R.id.btnDivide);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnMinus = findViewById(R.id.btnMinus);
        btnPeriod = findViewById(R.id.btnPeriod);
        btnPower = findViewById(R.id.btnPower);
        btnPlus = findViewById(R.id.btnPlus);
        btnEqual = findViewById(R.id.btnEqual);
        btnPercent = findViewById(R.id.btnPercent);
        btnSqrRoot=findViewById(R.id.btnSqrRoot);
        btnSquare = findViewById(R.id.btnSquare);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        tvInput = findViewById(R.id.tvInput);
        tvOutput = findViewById(R.id.tvOutput);

        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tvInput.setText("");
                tvOutput.setText("");
            }
        });

        btn0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"9");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"-");
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"+");
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"*");
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"/");
            }
        });

        btnPeriod.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+".");
            }
        });

        btnPower.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"^");
            }
        });

        btnOpenBracket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"(");
            }
        });

        btnCloseBracket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+")");
            }
        });

        btnSin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"sin(");
            }
        });

        btnCos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"cos(");
            }
        });

        btnTan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"tg(");
            }
        });

        btnCot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"ctg(");
            }
        });

        btnClearLast.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(tvInput.getText().toString().length()>0) {
                    s = tvInput.getText().toString();
                    String sNew="1";

                    if(s.charAt(s.length()-1)=='n'||s.charAt(s.length()-1)=='s'||s.charAt(s.length()-1)=='g')
                    {
                        if(s.length()>2)
                        {
                            if(s.charAt(s.length()-3)=='c' || s.charAt(s.length()-1)=='n')
                            {
                                sNew = s.substring(0, s.length() - 3);
                            }
                            else{
                                sNew = s.substring(0, s.length() - 2);
                            }
                        }
                        else{
                            sNew = s.substring(0, s.length() - 2);
                        }

                    }
                    else {

                        sNew = s.substring(0, s.length() - 1);
                    }
                    tvInput.setText(sNew);
                }
            }
        });

        btnPercent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"/100");
            }
        });

        btnSqrRoot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"^0.5");
            }
        });
        btnSquare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                s = tvInput.getText().toString();
                tvInput.setText(s+"^2");
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(tvInput.getText().toString().length()>0) {
                    s = tvInput.getText().toString();
                    int openBracketNumber=0, closeBracketNumber=0;
                    boolean isBracketsNormal = true;
                    for(int i=0;i<s.length();i++)
                    {
                        if(s.charAt(i)=='(')
                        {
                            openBracketNumber++;

                            if(i!=0)
                            {
                                char previousSymbol = s.charAt(i-1);
                                if(previousSymbol!='/' && previousSymbol!='*' && previousSymbol!='+'
                                && previousSymbol!='-' && previousSymbol!='^' && previousSymbol!='('
                                && previousSymbol!='n' && previousSymbol!='s' && previousSymbol!='g')
                                {
                                    isBracketsNormal=false;
                                }
                            }
                            if(i!=s.length()-1)
                            {
                                char nextSymbol = s.charAt(i+1);
                                if(nextSymbol!='1' && nextSymbol!='2' && nextSymbol!='3'
                                        && nextSymbol!='4' && nextSymbol!='5' && nextSymbol!='('
                                        && nextSymbol!='6' && nextSymbol!='7'
                                        && nextSymbol!='8' && nextSymbol!='9' && nextSymbol!='s'
                                        && nextSymbol!='c' && nextSymbol!='t' && nextSymbol!='8')
                                {
                                    isBracketsNormal=false;
                                }

                            }
                            else{
                                isBracketsNormal=false;
                            }


                        }
                        else if(s.charAt(i)==')'){
                            closeBracketNumber++;

                            if(i!=s.length()-1)
                            {
                                char nextSymbol = s.charAt(i+1);

                                if(nextSymbol!='/' && nextSymbol!='*' && nextSymbol!='+'
                                        && nextSymbol!='-' && nextSymbol!='^'
                                        && nextSymbol!=')')
                                {
                                    isBracketsNormal=false;
                                }

                            }
                            if(i!=0)
                            {
                                char previousSymbol = s.charAt(i-1);
                                if(previousSymbol!='1' && previousSymbol!='2' && previousSymbol!='3'
                                        && previousSymbol!='4' && previousSymbol!='5'
                                        && previousSymbol!=')' && previousSymbol!='6'
                                        && previousSymbol!='7' && previousSymbol!='8'
                                        && previousSymbol!='9')
                                {
                                    isBracketsNormal=false;
                                }


                            }


                        }
                    }

                    if(openBracketNumber==closeBracketNumber && isBracketsNormal==true) {


                        try {
                            BigDecimal result = calc.calculate(s);
                            tvOutput.setText("" + result);
                        } catch (WrongArgumentException e) {
                            message = "Ошибка!";
                            tvOutput.setText(message);
                            e.printStackTrace();
                        } catch (NoSuchFunctionFound noSuchFunctionFound) {
                            message = "Ошибка!";
                            tvOutput.setText(message);
                            noSuchFunctionFound.printStackTrace();
                        } catch (Exception e) {
                            message = "Ошибка!";
                            tvOutput.setText(message);
                        }
                    }
                    else
                    {
                        message = "Проверьте скобки!";
                        tvOutput.setText(message);
                    }
                }
            }
        });


    }
}