package com.example.calc_exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtResult;
    double firstNumber = 0;
    String currentOperation = "";
    boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);

        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int id : numberIds) {
            Button btn = findViewById(id);
            btn.setOnClickListener(this::numberPressed);
        }


        findViewById(R.id.btnAdd).setOnClickListener(v ->
                operatorPressed("+", "Addition"));

        findViewById(R.id.btnSub).setOnClickListener(v ->
                operatorPressed("-", "Subtraction"));

        findViewById(R.id.btnMul).setOnClickListener(v ->
                operatorPressed("*", "Multiplication"));

        findViewById(R.id.btnDiv).setOnClickListener(v ->
                operatorPressed("/", "Division"));

        findViewById(R.id.btnEqual).setOnClickListener(v ->
                calculateResult());

        findViewById(R.id.btnClear).setOnClickListener(v ->
                clearAll());
    }

    private void numberPressed(View view) {
        Button btn = (Button) view;
        String number = btn.getText().toString();

        if (isNewInput) {
            txtResult.setText(number);
            isNewInput = false;
        } else {
            txtResult.setText(txtResult.getText().toString() + number);
        }

        showToast("Number " + number + " pressed");
        showDialog("Number Pressed", "You pressed number " + number);
    }

    private void operatorPressed(String op, String name) {
        firstNumber = Double.parseDouble(txtResult.getText().toString());
        currentOperation = op;
        isNewInput = true;

        showToast(name + " selected");
        showDialog("Operation Selected", "You selected " + name);
    }

    private void calculateResult() {
        double secondNumber = Double.parseDouble(txtResult.getText().toString());
        double result = 0;

        switch (currentOperation) {
            case "+":
                result = firstNumber + secondNumber;
                break;

            case "-":
                result = firstNumber - secondNumber;
                break;

            case "*":
                result = firstNumber * secondNumber;
                break;

            case "/":
                if (secondNumber == 0) {
                    showToast("Error");
                    showDialog("Error", "Cannot divide by zero");
                    return;
                }
                result = firstNumber / secondNumber;
                break;

            default:
                showDialog("Error", "No operation selected");
                return;
        }

        txtResult.setText(String.valueOf(result));
        isNewInput = true;

        showToast("Result is " + result);
        showDialog("Result", "The answer is " + result);
    }

    private void clearAll() {
        txtResult.setText("0");
        firstNumber = 0;
        currentOperation = "";
        isNewInput = true;

        showToast("Calculator cleared");
        showDialog("Clear", "Calculator has been reset");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }
}