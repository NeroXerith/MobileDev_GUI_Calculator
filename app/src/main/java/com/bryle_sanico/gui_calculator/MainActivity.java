package com.bryle_sanico.gui_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvCurrent;
    TextView tvPrev;
    TextView tvOperation;
    String tmpHandler = "";
    Boolean isEqualClickedOnce = false;
    int decimalCtr = 0;

    private static final String ADD = "+";
    private static final String SUBTRACT = "-";
    private static final String MULTIPLY = "x";
    private static final String DIVIDE = "÷";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize TextViews
        tvCurrent = findViewById(R.id.tv_currentNum);
        tvPrev = findViewById(R.id.tv_prevNum);
        tvOperation = findViewById(R.id.tv_operation);
    }

    public void actionBtn(View view) {
        if (isEqualClickedOnce) {
            // If "=" was clicked previously, clear the UI elements and reset flags.
            clearUIElements();
        } else {
            String buttonText = ((TextView) view).getText().toString();
            if (Character.isDigit(buttonText.charAt(0)) || buttonText.equals(".")) {
                // Handle digit input
                handleDigitInput(buttonText);
            } else if (tvPrev.getText().toString().isEmpty() && isOperator(buttonText)) {
                // Handle operator input when there's no previous number
                handleOperatorInput(buttonText);
            } else if (buttonText.equals("CE")) {
                clearCurrent();
            } else if (buttonText.equals("C")) {
                clearUIElements();
            } else if (buttonText.equals("=")) {
                performCalculation();
            } else if (buttonText.equals("EXIT")) {
                finish();
            }
        }
    }

    private void handleDigitInput(String buttonText) {
        tmpHandler += buttonText;
        tvCurrent.setText(tmpHandler);
        // Counts the decimal when clicked to satisfy the decimalCtr == 2 validation
        if (buttonText.equals(".")) {
            decimalCtr++;
            Button decimalButton = findViewById(R.id.btn_dot);
            decimalButton.setEnabled(false);
        }
    }

    private boolean isOperator(String buttonText) {
        // Check if the button text represents an operator
        return buttonText.equals(ADD) || buttonText.equals(SUBTRACT) ||
                buttonText.equals(MULTIPLY) || buttonText.equals(DIVIDE);
    }

    private void handleOperatorInput(String buttonText) {
        tvOperation.setText(buttonText);
        tvPrev.setText(tmpHandler);
        decimalCtr--;
        tvCurrent.setText("");
        tmpHandler = "";
        Button decimalButton = findViewById(R.id.btn_dot);
        decimalButton.setEnabled(true);
    }

    private void clearCurrent() {
        tvCurrent.setText("");
        tmpHandler = "";
        decimalCtr = 0;
        if(decimalCtr < 2) {
            Button decimalButton = findViewById(R.id.btn_dot);
            decimalButton.setEnabled(true);
        }
    }

    private void clearUIElements() {
        // Clear all UI elements and reset flags
        tvPrev.setText("");
        tvCurrent.setText("");
        tvOperation.setText("");
        tmpHandler = "";
        decimalCtr = 0;
        isEqualClickedOnce = false;
        Button decimalButton = findViewById(R.id.btn_dot);
        decimalButton.setEnabled(true);
    }

    private void performCalculation() {
        if (tvPrev.getText().toString().isEmpty() ||
                !tvOperation.getText().toString().isEmpty() && tvCurrent.getText().toString().isEmpty() ||
                decimalCtr == 2 || tvCurrent.getText().toString().equals(".") || tvPrev.getText().toString().equals(".")) {
            // Validation to prevent invalid calculations
            showToast("You cannot perform this action!");
        } else {
            try {
                // Parse the previous and current numbers
                double n1 = Double.parseDouble(tvPrev.getText().toString());
                double n2 = Double.parseDouble(tvCurrent.getText().toString());
                calculate(n1, n2);
            } catch (NumberFormatException e) {
                showToast("Invalid input!");
            }
        }
    }

    private void calculate(double n1, double n2) {
        String operation = tvOperation.getText().toString();
        double result = 0;
        switch (operation) {
            case ADD:
                result = n1 + n2;
                break;
            case SUBTRACT:
                result = n1 - n2;
                break;
            case MULTIPLY:
                result = n1 * n2;
                break;
            case DIVIDE:
                if (n2 == 0) {
                    // Handle division by zero
                    tvCurrent.setTextSize(40);
                    tvCurrent.setText("Error: Division by zero!");
                } else {
                    result = n1 / n2;
                }
                break;
        }

        // Display the result and update UI elements
        tvPrev.setText(n1 + " " + operation + " " + n2 + " =");
        tvCurrent.setText(String.valueOf(result));

        decimalCtr = 0;
        isEqualClickedOnce = true;
        tvOperation.setText("");
        tmpHandler = "";
        Button decimalButton = findViewById(R.id.btn_dot);
        decimalButton.setEnabled(true);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
