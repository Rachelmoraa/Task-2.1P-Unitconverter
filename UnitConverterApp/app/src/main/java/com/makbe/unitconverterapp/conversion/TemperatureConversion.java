package com.makbe.unitconverterapp.conversion;

public class TemperatureConversion {
	public static double convert(double value, String sourceUnit, String destUnit) {
		double celsiusValue = convertToCelsius(value, sourceUnit);
		return convertFromCelsius(celsiusValue, destUnit);
	}

	private static double convertToCelsius(double value, String sourceUnit) {
		double result;
		switch (sourceUnit) {
			case "Celsius":
				result = value;
				break;
			case "Fahrenheit":
				result = ((value - 32) / 1.8);
				break;
			case "Kelvin":
				result = value - 273.15;
				break;
			default:
				result = -1;
				break;
		};
		return result;
	}

	private static double convertFromCelsius(double celsiusValue, String destUnit) {
		double result;
		switch (destUnit) {
			case "Celsius":
				result = celsiusValue;
				break;
			case "Fahrenheit":
				result = ((celsiusValue * 1.8) + 32);
				break;
			case "Kelvin":
				result = celsiusValue + 273.15;
				break;
			default:
				result = -1;
		};
		return result;
	}
}
