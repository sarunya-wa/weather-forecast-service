package com.example.weather.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForecastInputValidatorUtil {
    public  static String extractZipCodeFromAddress(String address) {
        // Assumption: Only US Address is supported (No International Address (where zipcode is alphanumeric))
        // Regular expression to match a 5-digit zip code
        String zipCodePattern = "\\b\\d{5}\\b";
        Pattern pattern = Pattern.compile(zipCodePattern);
        Matcher matcher = pattern.matcher(address);

        if (matcher.find()) {
            return matcher.group(); // Return the first matched zip code
        } else {
            throw new IllegalArgumentException("No valid zip code found in the address");
        }
    }
}
