package locale;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localizator {
    public static final Locale RUSSIA_USSR = new Locale("ru", "RU");
    public static final Locale SPAIN_PANAMA = new Locale("es", "PA");
    public static final Locale SLOVENIAN_SLOVENIA = new Locale("sl", "SL");
    public static final Locale UKRAINIAN_UKRAINE = new Locale("uk", "UA");
    public static final Locale DEFAULT = Locale.getDefault();

    public static ResourceBundle changeLocale(String bundleName, Locale newLocale) {
        ResourceBundle resources = null;
        try {
            resources = ResourceBundle.getBundle(bundleName, newLocale);
            return resources;
        } catch (MissingResourceException exception) {
            return null;
        }
    }
}
