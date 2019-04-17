package com.saifintex.utils;

import java.util.Random;

public class StringUtils {
	
	private static final Random generator = new Random();
	
	public static String capitalFirstLater(String string) {
		StringBuilder nameBuilder = null;
		if (!string.isEmpty()) {
			String separate[] = string.split(" ");
			nameBuilder = new StringBuilder();

			for (String partOfNames : separate) {
				String capitalLetterPart = partOfNames.substring(0, 1).toUpperCase()
						+ partOfNames.substring(1).toLowerCase();
				nameBuilder.append(capitalLetterPart + " ");
			}
			string = nameBuilder.toString().trim();
		}

		return string;
	}
	
	public static int generateSixDigitRandomNumber() {
		return 100000 + generator.nextInt(900000);
	}
}
