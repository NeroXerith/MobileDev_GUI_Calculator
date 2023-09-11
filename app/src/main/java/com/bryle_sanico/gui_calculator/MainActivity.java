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
    boolean isEqualClickedOnce = false;
    int decimalCtr = 0;

    private static final String ADD = "+";
    private static final String SUBTRACT = "-";
    private static final String MULTIPLY = "x";
    private static final String DIVIDE = "÷";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews(); // Initialize the UI elements.
    }

    // Initialize UI elements.
    private void initializeViews() {
        tvCurrent = findViewById(R.id.tv_currentNum);
        tvPrev = findViewById(R.id.tv_prevNum);
        tvOperation = findViewById(R.id.tv_operation);
        enableDecimalButton(true); // Enable the decimal button by default.
    }

    public void actionBtn(View view) {
        String buttonText = ((TextView) view).getText().toString();

        if (isEqualClickedOnce) {
            clearUIElements(); // If "=" was clicked previously, clear UI elements.
        } else if (Character.isDigit(buttonText.charAt(0)) || buttonText.equals(".")) {
            handleDigitInput(buttonText); // Handle digit input.
        } else if (tvPrev.getText().toString().isEmpty() && isOperator(buttonText)) {
            handleOperatorInput(buttonText); // Handle operator input.
        } else {
            handleAction(buttonText); // Handle other actions.
        }
    }

    // Handle digit input.
    private void handleDigitInput(String buttonText) {
        tmpHandler += buttonText;
        tvCurrent.setText(tmpHandler);

        if (buttonText.equals(".")) {
            decimalCtr++;
            enableDecimalButton(false); // Disable the decimal button.
        }
    }

    // Check if the button text represents an operator.
    private boolean isOperator(String buttonText) {
        return buttonText.equals(ADD) || buttonText.equals(SUBTRACT) ||
                buttonText.equals(MULTIPLY) || buttonText.equals(DIVIDE);
    }

    // Handle operator input.
    private void handleOperatorInput(String buttonText) {
        tvOperation.setText(buttonText);
        tvPrev.setText(tmpHandler);
        decimalCtr--;
        tvCurrent.setText("");
        tmpHandler = "";
        enableDecimalButton(true); // Enable the decimal button.
    }

    // Handle actions like "CE," "C," "=", and "EXIT."
    private void handleAction(String buttonText) {
        switch (buttonText) {
            case "CE":
                clearCurrent();
                break;
            case "C":
                clearUIElements();
                break;
            case "=":
                performCalculation();
                break;
            case "EXIT":
                finish();
                break;
        }
    }

    // Clear the current input.
    private void clearCurrent() {
        tvCurrent.setText("");
        tmpHandler = "";
        decimalCtr = 0;
        enableDecimalButton(true); // Enable the decimal button.
    }

    // Clear all UI elements and reset flags.
    private void clearUIElements() {
        tvPrev.setText("");
        tvCurrent.setText("");
        tvOperation.setText("");
        tmpHandler = "";
        decimalCtr = 0;
        isEqualClickedOnce = false;
        enableDecimalButton(true); // Enable the decimal button.
    }

    // Perform the calculation.
    private void performCalculation() {
        if (tvPrev.getText().toString().isEmpty() ||
                !tvOperation.getText().toString().isEmpty() && tvCurrent.getText().toString().isEmpty() ||
                decimalCtr == 2 || tvCurrent.getText().toString().equals(".") || tvPrev.getText().toString().equals(".")) {
            showToast("You cannot perform this action!");
        } else {
            try {
                double n1 = Double.parseDouble(tvPrev.getText().toString());
                double n2 = Double.parseDouble(tvCurrent.getText().toString());
                calculate(n1, n2);
            } catch (NumberFormatException e) {
                showToast("Invalid input!");
            }
        }
        enableDecimalButton(true); // Enable the decimal button.
    }

    // Perform the actual calculation.
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
                    tvCurrent.setTextSize(40);
                    tvCurrent.setText("Error: Division by zero!");
                } else {
                    result = n1 / n2;
                }
                break;
        }
        tvPrev.setText(n1 + " " + operation + " " + n2 + " =");
        tvCurrent.setText(String.valueOf(result));
        decimalCtr = 0;
        isEqualClickedOnce = true;
        tvOperation.setText("");
        tmpHandler = "";
        enableDecimalButton(true); // Enable the decimal button.
    }

    // Show a toast message.
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Enable or disable the decimal button.
    private void enableDecimalButton(boolean enabled) {
        Button decimalButton = findViewById(R.id.btn_dot);
        decimalButton.setEnabled(enabled);
    }
}
