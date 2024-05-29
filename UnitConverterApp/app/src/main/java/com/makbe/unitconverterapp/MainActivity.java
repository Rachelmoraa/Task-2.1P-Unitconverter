package com.makbe.unitconverterapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.makbe.unitconverterapp.conversion.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {

	private EditText sourceValueEditText;
	private Spinner unitTypeSpinner;
	private Spinner sourceUnitSpinner;
	private Spinner destUnitSpinner;
	private TextView resultTextView;

	private List<UnitType> unitTypes;

	private final UnitType lengthType = new UnitType("Length", Arrays.asList("Inch", "Foot", "Yard", "Mile"));
	private final UnitType weightType = new UnitType("Weight", Arrays.asList("Pound", "Ounce", "Ton"));
	private final UnitType temperatureType = new UnitType("Temperature", Arrays.asList("Celsius", "Fahrenheit", "Kelvin"));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sourceValueEditText = findViewById(R.id.sourceValueEditText);
		unitTypeSpinner = findViewById(R.id.unitTypeSpinner);
		sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
		destUnitSpinner = findViewById(R.id.destUnitSpinner);
		resultTextView = findViewById(R.id.resultTextView);

		unitTypes = new ArrayList<>();
		unitTypes.add(lengthType);
		unitTypes.add(weightType);
		unitTypes.add(temperatureType);

		ArrayAdapter<UnitType> unitTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitTypes);
		unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unitTypeSpinner.setAdapter(unitTypeAdapter);
		unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				populateUnitSpinners(unitTypes.get(position).getUnits());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	private void populateUnitSpinners(List<String> units) {
		ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
		unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sourceUnitSpinner.setAdapter(unitAdapter);
		destUnitSpinner.setAdapter(unitAdapter);
	}

	public void convertUnits(View view) {
		String sourceValueStr = sourceValueEditText.getText().toString();
		if (TextUtils.isEmpty(sourceValueStr)) {
			showError("Please enter a value");
			return;
		}

		if (!isValidInput(sourceValueStr)) {
			showError("Invalid input value");
			return;
		}

		double sourceValue = Double.parseDouble(sourceValueStr);
		String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
		String destUnit = destUnitSpinner.getSelectedItem().toString();

		if (sourceUnit.equals(destUnit)) {
			showError("Source and destination units are the same");
			return;
		}

		double result = performConversion(sourceValue, sourceUnit, destUnit);

		resultTextView.setText(String.format("%.3f %s = %.3f %s", sourceValue, sourceUnit, result, destUnit));

//		if (result >= 0) {
//			resultTextView.setText(String.format("%.3f %s = %.3f %s", sourceValue, sourceUnit, result, destUnit));
//		} else {
//			showError("Invalid conversion");
//		}
	}

	private double performConversion(double value, String sourceUnit, String destUnit) {
		double result;
		switch (unitTypeSpinner.getSelectedItem().toString()) {
			case "Length":
				result = LengthConversion.convert(value, sourceUnit, destUnit);
				break;
			case "Weight":
				result = WeightConversion.convert(value, sourceUnit, destUnit);
				break;
			case "Temperature":
				result = TemperatureConversion.convert(value, sourceUnit, destUnit);
				break;
			default: result = -1;
		};

		return result;
	}

	private boolean isValidInput(String input) {
		try {
			double value = Double.parseDouble(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void showError(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

}
