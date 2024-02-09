package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class with some static helper methods allowing for easier validation of data
 * @author Arkadiusz Nowak
 */

public class Validate {
    // Username: alphanumeric & min 3 and max 32 chars long
    private static final String REGEX_USERNAME = "^[A-Z0-9]\\w{2,31}$";
    // Password: alphanumeric & min 8 and max 255 chars long
    //"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{7,254}$"
    private static final String REGEX_PASSWORD = "^[_a-z0-9-]\\w{7,254}$";
    private static final String REGEX_EMAIL = "[_a-z0-9-]+([.+][_a-z0-9-]+)*@(ssl.)?(www.)?[a-z0-9-\\.]{1,255}\\.[a-zA-Z]{2,6}";

    public static Boolean isValidString(String str, String regex) {
        // Ensure the string is not empty (or whitespaced)
        if (str == null || str.isBlank()) {
            return false;
        }
        // Compile regex
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        // Find matches
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static Boolean isValidUsername(String username) {
        return isValidString(username, REGEX_USERNAME);
    }

    public static Boolean isValidPassword(String password) {
        return isValidString(password, REGEX_PASSWORD);
    }

    public static Boolean isValidEmail(String email) {
        return isValidString(email, REGEX_EMAIL);
    }
}