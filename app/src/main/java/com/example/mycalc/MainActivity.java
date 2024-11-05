package com.example.mycalc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // =================================================================================== //

    private EditText displayText, lastText;
    private double num1=0, num2=0;
    private String sym="", inputString="";
    private boolean answered = false, dotted = false;


    public void btn_num(View v){
        if(answered) {
            answered = false;
            clear();
        }

        if(inputString.equals("0")) return;
        Button btn = (Button)v;
        String c = btn.getText().toString();
        inputString += c;

        if(sym == "") num1 = Double.parseDouble(inputString);
        else num2 = Double.parseDouble(inputString);

        displayText.setText(inputString);
    }
    public void btn_op(View v){
        if((inputString == "") || (sym != "")) return;
        Button btn = (Button)v;
        sym = btn.getText().toString();
        inputString = "";
        lastText.setText(num1+sym);
        displayText.setText("");
    }
    public void btn_plusminus(View v){
        if(answered) return;
        if(inputString == "") return;

        char lastInputChar = inputString.charAt(inputString.length()-1);

        if(sym == "") {
            num1 = num1 * -1;
            inputString = Double.toString(num1);
        }
        else {
            num2 = num2 * -1;
            inputString = Double.toString(num2);
        }
        if(!dotted) inputString = inputString.substring(0, inputString.length()-2);
        else if(lastInputChar == '.') inputString = inputString.substring(0, inputString.length()-1);
        displayText.setText(inputString);
    }
    public void btn_dot(View v){
        if(answered) return;
        if(inputString == "") {
            if(answered) {
                answered = false;
                clear();
            }

            if(inputString.equals("0")) clear();
            Button btn = (Button)v;
            String c = btn.getText().toString();
            inputString += c;

            if(sym == "") num1 = Double.parseDouble(inputString);
            else num2 = Double.parseDouble(inputString);

            displayText.setText(inputString);
        }
        if(dotted) {
            if(inputString.charAt(inputString.length()-1) == '.')
            {
                inputString = inputString.substring(0,inputString.length()-1);
                dotted = false;
            }
            else return;
        }
        else {
            inputString += ".";
            dotted = true;
        }
        if(sym == "") num1 = Double.parseDouble(inputString);
        else num2 = Double.parseDouble(inputString);
        displayText.setText(inputString);
    }
    public void btn_equal(View v){
        if((sym == "") || (inputString == "")) return;

        lastText.setText(lastText.getText().toString() + num2 + "=");

        double res = -16;
        boolean divzero = false;
        switch (sym){
            case "+":
                res = num1 + num2;
                break;
            case "-":
                res = num1 - num2;
                break;
            case "*":
                res = num1 * num2;
                break;
            case "/":
                if(num2 == 0) {
                    clear();
                    divzero = true;
                }
                else res = num1 / num2;
                break;
        }
        if(divzero) displayText.setText("Division on 0!");
        else displayText.setText(Double.toString(res));
        answered = true;
    }
    public void btn_clear(View v) {clear();}
    private void clear() {
        num1 = 0;
        num2 = 0;
        inputString = "";
        sym = "";
        lastText.setText("");
        displayText.setText("");
        dotted = false;
    }
    public void btn_backspace(View v){
        if(answered) return;
        if(inputString == "") return;

        if(inputString.charAt(inputString.length()-1) == '.') dotted = false;
        inputString = inputString.substring(0, inputString.length()-1);
        if(inputString.equals("")) {
            if(sym == "") num1 = 0;
            else num2 = 0;
        }
        else {
            if(sym == "") num1 = Double.parseDouble(inputString);
            else num2 = Double.parseDouble(inputString);
        }
        displayText.setText(inputString);
    }


    @Override
    protected void onStart() {
        super.onStart();
        displayText = findViewById(R.id.displayText);
        lastText = findViewById(R.id.lastText);
    }

}