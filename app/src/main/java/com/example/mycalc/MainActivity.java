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
    private boolean answered = false, dotting = false;

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
        if(answered) clear();
        Button btn = (Button)v;
        String btntxt = btn.getText().toString();
        addDText(btntxt);
        inputNumString += btntxt;
    }
    private void clear()
    {
        displayText.setText("");
        num1 = 0;
        num2 = 0;
        inputNumString = "";
        sym = "";
        answered = false;
        dotting = false;
    }

    public void btn_clear(View v){
        clear();
    }

    private void setNum() {
        if(inputNumString.isEmpty()) return;
        if(!sym.isEmpty()) num1 = Double.parseDouble(inputNumString);
        else num2 = Double.parseDouble(inputNumString);
        inputNumString = "";
        dotting = false;
    }

    private void SetChar(String c){
        sym = c;
        addDText(c);
    }

    public void btn_plus(View v){
        if(inputNumString.isEmpty()) return;
        setNum();
        SetChar("+");
    }
    public void btn_minus(View v){
        if(inputNumString.isEmpty()) return;
        setNum();
        SetChar("-");
    }
    public void btn_divide(View v){
        if(inputNumString.isEmpty()) return;
        setNum();
        SetChar("/");
    }
    public void btn_multiply(View v){
        if(inputNumString.isEmpty()) return;
        setNum();
        SetChar("*");
    }
    public void btn_equal(View v){
        if(inputNumString.isEmpty()) return;
        setNum();
        double res = 0;
        boolean divzer = false;
        switch (sym){
            case "+":
                res = num2 + num1;
                break;
            case "-":
                res = num2 - num1;
                break;
            case "*":
                res = num2 * num1;
                break;
            case "/":
                try {
                    res = num2 / num1;
                } catch (Exception e) {
                    divzer = true;
                }
                break;
        }

        String txt = "=";
        if(divzer) txt += "ERR";
        else txt += Double.toString(res);

        displayText.setText(txt);
        inputNumString = "";
        answered = true;
    }

    /* ============================================TODO======================================= */

    // add - to inputNumString
    public void btn_plusminus(View v){
        if(inputNumString.isEmpty()) return;
        String dt = displayText.getText().toString();
        if(sym.isEmpty())
        {
            if(dt.charAt(0) == '-')
            {
                displayText.setText(dt.substring(1));

                // FIX WITH DOT
                inputNumString = inputNumString.substring(1);
            }
            else {
                String tmp = "-";
                tmp += dt;
                displayText.setText(tmp);

                // FIX WITH DOT
                inputNumString = "-" + inputNumString;
            }
        }
        else {
            if(inputNumString.charAt(0) == '-')
            {
                char[] dtc = dt.toCharArray();
                for(int i=0;i<dt.length();i++)
                {
                    if(dtc[i] == sym.charAt(0))
                    {
                        String start = dt.substring(0, i+1);
                        String end = dt.substring(i + 3);
                        String res = start + end;
                        displayText.setText(res);
                    }
                }
                // FIX WITH DOT
                inputNumString = inputNumString.substring(1);
            }
            else {
                displayText.setText(dt.replace(sym, sym + "(-"));

                // FIX WITH DOT
                inputNumString = "-" + inputNumString;
            }
        }
    }
    public void btn_dot(View v){
        if(inputNumString.isEmpty()) return;
        String dt = displayText.getText().toString();
        if(sym.isEmpty())
        {
            if((inputNumString.charAt(0) == '.') || (inputNumString.charAt(1) == '.'))
            {
                displayText.setText(dt.substring(1));
                // FIX WITH MINUS
                inputNumString = inputNumString.substring(1);
            }
            else {
                String tmp = ".";
                tmp += dt;
                displayText.setText(tmp);
                // FIX WITH MINUS
                inputNumString = "." + inputNumString;
            }
        }
        else {
            if((inputNumString.charAt(0) == '.') || (inputNumString.charAt(1) == '.'))
            {
                char[] dtc = dt.toCharArray();
                for(int i=0;i<dt.length();i++)
                {
                    if(dtc[i] == sym.charAt(0))
                    {
                        String start = dt.substring(0, i+1);
                        String end = dt.substring(i + 2);
                        String res = start + end;
                        displayText.setText(res);
                    }
                }
                // FIX WITH MINUS
                inputNumString = inputNumString.substring(1);
            }
            else {
                displayText.setText(dt.replace(sym, sym + "."));

                // FIX WITH MINUS
                inputNumString = "." + inputNumString;
            }
        }
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