package com.makbe.unitconverterapp;

import java.util.List;

public class UnitType {

	private final String name;
	private final List<String> units;

	public UnitType(String name, List<String> units) {
		this.name = name;
		this.units = units;
	}

	public String getName() {
		return name;
	}

	public List<String> getUnits() {
		return units;
	}

	@Override
	public String toString() {
		return name;
	}
}
