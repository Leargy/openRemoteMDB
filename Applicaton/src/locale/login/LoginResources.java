package locale.login;

/**
 * Main english translation
 * for login interface
 */
public class LoginResources extends java.util.ListResourceBundle {
    private static final Object[][] contents = {
            // BEGINNING OF INTERNATIONALIZATION
            //{"RemoteMDB", "RemoteMDB"},
            {"Language", "Language"},
            {"Login", "Login"},
            {"Password", "Password"},
            {"Sign In", "Sign In"},
            {"Sign Up", "Sign Up"}
            // ENDING OF INTERNATIONALIZATION
    };


    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
