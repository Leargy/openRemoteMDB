package sample.buttons;

public interface ButtonFactory {
    IButton createConnectionButton();
    IButton createSignInButton();
    IButton createSignOutButton();
    IButton createSignUpButton();
    IButton createGoBackButton();
    IButton createInfoButton();
    IButton createUpdateButton();
}
