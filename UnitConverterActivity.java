package com.makbe.unitconverterapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class UnitConverterActivity extends AppCompatActivity {
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
				populateUnitSpinners(unitTypes.get(position).units());
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
		String resultStr = String.format(Locale.ENGLISH, "%.3f %s = %.3f %s", sourceValue, sourceUnit, result, destUnit);

		resultTextView.setText(resultStr);

	}

	private double performConversion(double value, String sourceUnit, String destUnit) {
		return switch (unitTypeSpinner.getSelectedItem().toString()) {
			case "Length" -> LengthConversion.convert(value, sourceUnit, destUnit);
			case "Weight" -> WeightConversion.convert(value, sourceUnit, destUnit);
			case "Temperature" -> TemperatureConversion.convert(value, sourceUnit, destUnit);
			default -> -1;
		};
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

record UnitType(String name, List<String> units) {
	@NotNull
	@Override
	public String toString() {
		return name;
	}
}

class LengthConversion {
	public static double convert(double value, String sourceUnit, String destUnit) {
		double centimeterValue = convertToCentimeter(value, sourceUnit);
		return convertFromCentimeter(centimeterValue, destUnit);
	}

	private static double convertToCentimeter(double value, String sourceUnit) {
		return switch (sourceUnit) {
			case "Centimeter" -> value;
			case "Inch" -> value * 2.54;
			case "Foot" -> value * 30.48;
			case "Yard" -> value * 91.44;
			case "Mile" -> value * 160934.4;
			default -> -1;
		};
	}

	private static double convertFromCentimeter(double centimeterValue, String destUnit) {
		return switch (destUnit) {
			case "Centimeter" -> centimeterValue;
			case "Inch" -> centimeterValue / 2.54;
			case "Foot" -> centimeterValue / 30.48;
			case "Yard" -> centimeterValue / 91.44;
			case "Mile" -> centimeterValue / 160934.4;
			default -> -1;
		};
	}
}

class WeightConversion {
	public static double convert(double value, String sourceUnit, String destUnit) {
		double gramValue = convertToGrams(value, sourceUnit);
		return convertFromGrams(gramValue, destUnit);
	}

	private static double convertToGrams(double value, String sourceUnit) {
		return switch (sourceUnit) {
			case "Gram" -> value;
			case "Pound" -> value * 453.592;
			case "Ounce" -> value * 28.3495;
			case "Ton" -> value * 907185;
			default -> -1;
		};
	}

	private static double convertFromGrams(double gramValue, String destUnit) {
		return switch (destUnit) {
			case "Gram" -> gramValue;
			case "Pound" -> gramValue / 453.592;
			case "Ounce" -> gramValue / 28.3495;
			case "Ton" -> gramValue / 907185;
			default -> -1;
		};
	}
}

class TemperatureConversion {
	public static double convert(double value, String sourceUnit, String destUnit) {
		double celsiusValue = convertToCelsius(value, sourceUnit);
		return convertFromCelsius(celsiusValue, destUnit);
	}

	private static double convertToCelsius(double value, String sourceUnit) {
		return switch (sourceUnit) {
			case "Celsius" -> value;
			case "Fahrenheit" -> ((value - 32) / 1.8);
			case "Kelvin" -> value - 273.15;
			default -> -1;
		};
	}

	private static double convertFromCelsius(double celsiusValue, String destUnit) {
		return switch (destUnit) {
			case "Celsius" -> celsiusValue;
			case "Fahrenheit" -> ((celsiusValue * 1.8) + 32);
			case "Kelvin" -> celsiusValue + 273.15;
			default -> -1;
		};
	}
}

