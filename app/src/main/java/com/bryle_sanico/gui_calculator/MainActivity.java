package com.bryle_sanico.gui_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvCurrent;
    TextView tvPrev;
    TextView tvOperation;
    String tmpHandler = "";
    Boolean isEqualClickedOnce= false;
    int decimalCtr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCurrent = findViewById(R.id.tv_currentNum);
        tvPrev = findViewById(R.id.tv_prevNum);
        tvOperation = findViewById(R.id.tv_operation);
    }

    public void actionBtn(View view) {
        if (isEqualClickedOnce) {
            // Reset isEqualClickedOnce to false and clear the UI elements.
            isEqualClickedOnce = false;
            tvPrev.setText("");
            tvOperation.setText("");
            tmpHandler = "";
        } else {
            String buttonText = ((TextView) view).getText().toString();
            if (Character.isDigit(buttonText.charAt(0)) || buttonText.equals(".")) {
                tmpHandler += buttonText;
                tvCurrent.setText(tmpHandler);

                // restrict from multiple inputs of period to prevent app crash
                if(buttonText.equals(".")){
                    decimalCtr++;
                }
            } else if (tvPrev.getText().toString().isEmpty() && (buttonText.equals("+") || buttonText.equals("-") || buttonText.equals("x") || buttonText.equals("÷"))) {
                tvOperation.setText(buttonText);
                tvPrev.setText(tmpHandler);
                decimalCtr--; // to counter the validation == 2 by subtracting 1 so the 2nd number can input decimal
                tvCurrent.setText("");
                tmpHandler = "";
            } else if (buttonText.equals("CE")) {
                tvCurrent.setText("");
                tmpHandler = "";
                decimalCtr = 0;
            } else if (buttonText.equals("C")) {
                tvPrev.setText("");
                tvCurrent.setText("");
                tvOperation.setText("");
                tmpHandler = "";
                decimalCtr = 0;
                isEqualClickedOnce = false;

            // Solving first num and second num inputs
            } else if (buttonText.equals("=")) {
                // Validations to prevent crashing the app
                if(tvPrev.getText().equals("") || !tvOperation.getText().equals("") && tvCurrent.getText().equals("") || decimalCtr == 2 || tvCurrent.getText().equals(".") || tvPrev.getText().equals(".")){
                    showToast("You cannot perform this action!");
                } else {
                    double n1, n2;
                    n1 = Double.parseDouble(tvPrev.getText().toString());
                    n2 = Double.parseDouble(tvCurrent.getText().toString());
                    calculate(n1, n2);
                }
            } else if(buttonText.equals("EXIT")){
                System.exit(0);
            }
        }
    }
    private void calculate(Double n1, Double n2){
        double calculate;
        String operation = tvOperation.getText().toString();
        switch(operation){
            case "+":
                calculate = n1 + n2;
                tvPrev.setText(n1+" + "+n2+" =");
                tvCurrent.setText(String.valueOf(calculate));
                break;
            case "-":
                calculate = n1 - n2;
                tvPrev.setText(n1+" - "+n2+" =");
                tvCurrent.setText(String.valueOf(calculate));
                break;
            case "x":
                calculate = n1 * n2;
                tvPrev.setText(n1+" x "+n2+" =");
                tvCurrent.setText(String.valueOf(calculate));
                break;
            case "÷":
                if(n1 == 0){
                    tvPrev.setText(n1+" ÷ "+n2+" =");
                    tvCurrent.setTextSize(40);
                    tvCurrent.setText("Error: Division by zero!");
                } else if (n2 == 0){
                    tvPrev.setText(n1+" ÷ "+n2+" =");
                    tvCurrent.setTextSize(40);
                    tvCurrent.setText("Error: Division by zero!");
                } else {
                    calculate = n1 / n2;
                    tvPrev.setText(n1+" ÷ "+n2+" =");
                    tvCurrent.setText(String.valueOf(calculate));
                }
                break;
        }
        decimalCtr = 0; // Reset period restriction after getting the results
        isEqualClickedOnce = true; // Prevent app crashing when equals is clicked multiple times after getting results
        tvOperation.setText(""); // Value Reset
        tmpHandler =""; // Value Reset
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
