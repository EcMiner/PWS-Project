package com.daan.pws.utilities;

public class NumberUtil {

	public static boolean isInteger(String string) {
		try {
			Integer.valueOf(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isShort(String string) {
		try {
			Short.valueOf(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isDouble(String string) {
		try {
			Double.valueOf(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isLong(String string) {
		try {
			Long.valueOf(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isFloat(String string) {
		try {
			Float.valueOf(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}


}
