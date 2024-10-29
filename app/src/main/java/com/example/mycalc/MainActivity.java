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
    //private Button btn_calc;
    //private EditText inp1, inp2;
    //private TextView outtext;
    private EditText displayText;
    private double num1=0, num2=0;
    private String sym = "";
    private String inputNumString = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        displayText = findViewById(R.id.displayText);
        /*btn_calc = findViewById(R.id.button_calc);
        inp1 = findViewById(R.id.input1);
        inp2 = findViewById(R.id.input2);
        outtext = findViewById(R.id.outtext);*/

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //=================================================================

    private String getDText() {
        return displayText.getText().toString();
    }
    private void addDText(String btntxt){
        String txt = getDText();
        txt += btntxt;
        displayText.setText(txt);
    }
    public void btn_num(View v) {
        Button btn = (Button)v;
        String btntxt = btn.getText().toString();
        addDText(btntxt);
        inputNumString += btntxt;
    }

    private void setNum() {
//        if(num2 != 0) clear();
        if(!inputNumString.isEmpty()) {
            if(!sym.isEmpty()) num1 = Double.parseDouble(inputNumString);
            else num2 = Double.parseDouble(inputNumString);
            inputNumString = "";
        }
    }

    private void SetChar(String c){
        sym = c;
        addDText(c);
    }

    public void btn_plus(View v){
        if(!inputNumString.isEmpty()) {
            setNum();
            SetChar("+");
        }
    }
    public void btn_minus(View v){
        if(!inputNumString.isEmpty()) {
            setNum();
            SetChar("-");
        }
    }
    public void btn_divide(View v){
        if(!inputNumString.isEmpty()) {
            setNum();
            SetChar("/");
        }
    }
    public void btn_multiply(View v){
        if(!inputNumString.isEmpty()) {
            setNum();
            SetChar("*");
        }
    }
    public void btn_equal(View v){
        setNum();
        double res = 0;
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
                res = num1 / num2;
                break;
        }

        String txt = "=";
        txt += Double.toString(res);
        displayText.setText(txt);
    }
    public void btn_dot(View v){

    }
    private void clear()
    {
        displayText.setText("");
        num1 = 0;
        num2 = 0;
        inputNumString = "";
        sym = "";
    }

    public void btn_clear(View v){
        clear();
    }

    /*public void doMath(View v)
    {
        if(v.getId() == R.id.button_calc){
            if(!inp1.getText().toString().equals("")) {
                if (!inp2.getText().toString().equals("")) {
                    double n1 = Integer.parseInt(inp1.getText().toString());
                    double n2 = Integer.parseInt(inp2.getText().toString());
                    double res = n1 + n2;
                    outtext.setText(Double.toString(res));
                }
            }
        }
    }

    */

    @Override
    protected void onStart() {
        super.onStart();

        displayText = findViewById(R.id.displayText);
    }
}