package com.daan.pws.utilities;

public class StringUtil {

	public static String capitalize(String string, int startIndex, int endIndex) {
		return string.substring(startIndex, endIndex).toUpperCase() + string.substring(endIndex);
	}

}
