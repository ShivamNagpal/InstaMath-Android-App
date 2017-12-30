package com.nagpal.shivam.instamath.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nagpal.shivam.instamath.R;

import java.util.ArrayList;

public class NumberConversionActivity extends AppCompatActivity {
    private static final String decimalStr = "Decimal";
    private static final String binaryStr = "Binary";
    private static final String octalStr = "Octal";
    private static final String hexadecimalStr = "HexaDecimal";
    private EditText etInput;
    private EditText etOutput;
    private Spinner spnrInput;
    private Spinner spnrOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_conversion);

        initViews();

        ArrayList<String> numberTypeArrayList = new ArrayList<>();
        numberTypeArrayList.add(decimalStr);
        numberTypeArrayList.add(binaryStr);
        numberTypeArrayList.add(octalStr);
//        numberTypeArrayList.add(hexadecimalStr);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(NumberConversionActivity.this, android.R.layout.simple_spinner_item, numberTypeArrayList);

        spnrInput.setAdapter(stringArrayAdapter);
        spnrOutput.setAdapter(stringArrayAdapter);
        spnrOutput.setSelection(1);

        spnrInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etInput.setText(null);
                switch (adapterView.getSelectedItem().toString()) {
                    case decimalStr:
                        etInput.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                        break;
                    case binaryStr:
                        etInput.setKeyListener(DigitsKeyListener.getInstance("01"));
                        break;
                    case octalStr:
                        etInput.setKeyListener(DigitsKeyListener.getInstance("01234567"));
                        break;
                    case hexadecimalStr:
                        etInput.setKeyListener(DigitsKeyListener.getInstance("0123456789ABCDEF"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnSubmit = findViewById(R.id.submit_button_number_conversion_activity);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                switch (spnrInput.getSelectedItem().toString() + spnrOutput.getSelectedItem().toString()) {

                    case decimalStr + binaryStr:
                        result = fromDecimal(2, etInput.getText().toString());
                        break;

                    case binaryStr + decimalStr:
                        result = toDecimal(2, etInput.getText().toString());
                        break;

                    case binaryStr + octalStr:
                        result = binaryToOctal(etInput.getText().toString());
                        break;

                    case decimalStr + octalStr:
                        result = fromDecimal(8, etInput.getText().toString());
                        break;

                    case octalStr + decimalStr:
                        result = toDecimal(8, etInput.getText().toString());
                        break;
                }
                etOutput.setText(result);
            }
        });
    }

    private void initViews() {
        spnrInput = findViewById(R.id.input_type_spinner_number_conversion_activity);
        spnrOutput = findViewById(R.id.output_type_spinner_number_conversion_activity);
        etInput = findViewById(R.id.input_type_edit_text_number_conversion_activity);
        etOutput = findViewById(R.id.output_type_edit_text_number_conversion_activity);
    }

    private String fromDecimal(int base, String str) {
        int decimalNumber;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            decimalNumber = Integer.parseInt(str);
            while (decimalNumber != 0) {
                stringBuilder.insert(0, decimalNumber % base);
                decimalNumber /= base;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String toDecimal(int base, String str) {
        int inputNumber;
        int i = 0;
        int decimalNumber = 0;
        int val;
        try {
            inputNumber = Integer.parseInt(str);
            while (inputNumber != 0) {
                val = inputNumber % 10;
                inputNumber /= 10;
                decimalNumber += val * Math.pow(base, i++);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return Integer.toString(decimalNumber);
    }

    private String binaryToOctal(String str) {
        int binaryNumber;
        int val;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            binaryNumber = Integer.parseInt(str);
            while (binaryNumber != 0) {
                val = binaryNumber % 1000;
                binaryNumber /= 1000;
                stringBuilder.insert(0, toDecimal(2, Integer.toString(val)));
                // TODO: Implement using string actions.
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
