/**
 *
 */
package tr.com.turkcell.sahi.util;

/**
 * @author Furkan Birgul
 */
public class PasswordUtil {

    /**
     * Default constructor.
     */
    public PasswordUtil() {
        super();
    }

    /**
     * Converts password part of Step object to string as Masking format
     *
     * @param step
     * @return newStep as String like "_sahi._setValue(_sahi._password(0), \"Yesil741\")"
     */
    public static String sahiPasswordMasking(String step) {

        String newStep = "";
        boolean replacePassword = false;

        if (step.contains("_sahi._setValue(_sahi._password")) {

            for (int i = 0; i < step.length(); i++) {

                if (step.charAt(i) == '"') {
                    if (replacePassword) {
                        replacePassword = false;
                    } else {
                        replacePassword = true;
                    }

                    continue;
                }

                if (replacePassword) {
                    newStep += "*";
                    continue;
                }

                newStep += step.charAt(i);
            }
            return newStep;
        } else {
            return step;
        }
    }

    /**
     * @param key
     * @return
     */
    public static boolean isContainsPassword(String key) {
        return key.toLowerCase().contains("password");
    }

    /**
     * @param key
     * @param maskingCharacter
     * @return Masked string
     */
    public static String stringMasking(String key, char maskingCharacter) {

        String result = "";

        if (key == null || maskingCharacter == '\u0000') {
            return null;
        }

        for (int i = 0; i < key.length(); i++) {
            result += maskingCharacter;
        }

        return result;
    }
}
