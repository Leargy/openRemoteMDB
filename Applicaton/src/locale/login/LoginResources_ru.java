package locale.login;

/**
 * Locale for russian language
 */
public class LoginResources_ru extends java.util.ListResourceBundle {
    private static final Object[][] contents = {
            // BEGINNING OF INTERNATIONALIZATION
            //{"RemoteMDB", "RemoteMDB"},
            {"Language", "Язык"},
            {"Login", "Логин"},
            {"Password", "Пароль"},
            {"Sign In", "Войти"},
            {"Sign Up", "Зарегистрироваться"},
            {"Exit", "Выход"}
            // ENDING OF INTERNATIONALIZATION
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
