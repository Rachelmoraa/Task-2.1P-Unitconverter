package com.makbe.unitconverterapp.conversion;

public class WeightConversion {
	public static double convert(double value, String sourceUnit, String destUnit) {
		double gramValue = convertToGrams(value, sourceUnit);
		return convertFromGrams(gramValue, destUnit);
	}

	private static double convertToGrams(double value, String sourceUnit) {
		double result;
		switch (sourceUnit) {
			case "Gram":
				result = value;
				break;
			case "Pound":
				result = value * 453.592;
				break;
			case "Ounce":
				result = value * 28.3495;
				break;
			case "Ton":
				result = value * 907185;
				break;
			default:
				result = -1;
		};
		return result;
	}

	private static double convertFromGrams(double gramValue, String destUnit) {
		double result;
		switch (destUnit) {
			case "Gram":
				result = gramValue;
				break;
			case "Pound":
				result = gramValue / 453.592;
				break;
			case "Ounce":
				result = gramValue / 28.3495;
				break;
			case "Ton":
				result = gramValue / 907185;
				break;
			default: result = -1;
		};
		return result;
	}
}
