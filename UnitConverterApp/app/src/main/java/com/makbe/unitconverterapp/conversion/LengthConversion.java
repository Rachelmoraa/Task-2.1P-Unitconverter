package com.makbe.unitconverterapp.conversion;

public class LengthConversion {
	public static double convert(double value, String sourceUnit, String destUnit) {
		double centimeterValue = convertToCentimeter(value, sourceUnit);
		return convertFromCentimeter(centimeterValue, destUnit);
	}

	private static double convertToCentimeter(double value, String sourceUnit) {
		double result;
		switch (sourceUnit) {
			case "Centimeter":
				result = value;
				break;
			case "Inch":
				result = value * 2.54;
				break;
			case "Foot":
				result = value * 30.48;
				break;
			case "Yard":
				result = value * 91.44;
				break;
			case "Mile":
				result = value * 0.001;  // TODO: Add mile to cm conversion
				break;
			default: result = -1;
		};
		return result;
	}

	private static double convertFromCentimeter(double centimeterValue, String destUnit) {
		double result;
		switch (destUnit) {
			case "Centimeter":
				result = centimeterValue;
				break;
			case "Inch":
				result = centimeterValue / 2.54;
				break;
			case "Foot":
				result = centimeterValue / 30.48;
				break;
			case "Yard":
				result = centimeterValue / 91.44;
				break;
			case "Mile":
				result = centimeterValue / 0.001; // TODO: Add cm to mile conversion
				break;
			default:
				result = -1;
		};
		return result;
	}
}
