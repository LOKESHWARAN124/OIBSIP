package com.example.unitconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner unitFrom, unitTo;
    private Button convertButton;
    private TextView resultText;

    private String[] units = {"Centimeters", "Meters", "Grams", "Kilograms"};
    private double[] conversionFactors = {0.01, 100, 0.001, 1000}; // Conversion factors for each unit pair

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        unitFrom = findViewById(R.id.unitFrom);
        unitTo = findViewById(R.id.unitTo);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitFrom.setAdapter(adapter);
        unitTo.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        String fromUnit = unitFrom.getSelectedItem().toString();
        String toUnit = unitTo.getSelectedItem().toString();
        double inputValue = Double.parseDouble(this.inputValue.getText().toString());

        int fromIndex = getIndex(fromUnit);
        int toIndex = getIndex(toUnit);

        double result = inputValue * (conversionFactors[toIndex] / conversionFactors[fromIndex]);

        resultText.setText(inputValue + " " + fromUnit + " = " + result + " " + toUnit);
    }

    private int getIndex(String unit) {
        for (int i = 0; i < units.length; i++) {
            if (units[i].equals(unit)) {
                return i;
            }
        }
        return -1;
    }
}