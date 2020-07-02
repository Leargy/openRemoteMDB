package sample.buttons;

import sample.dialog_windows.Commander;

public class AbstractButtonsFactory implements ButtonFactory {
    private Commander totalCommander;

    public AbstractButtonsFactory(Commander totalCommander) {
        this.totalCommander = totalCommander;
    }

    @Override
    public IButton createConnectionButton() {
        return new ConnectionIButton(totalCommander);
    }

    @Override
    public IButton createSignInButton() {
        return null;
    }

    @Override
    public IButton createSignOutButton() {
        return null;
    }

    @Override
    public IButton createSignUpButton() {
        return null;
    }

    @Override
    public IButton createGoBackButton() {
        return new BackButton(totalCommander);
    }

    @Override
    public IButton createInfoButton() {
        return null;
    }

    @Override
    public IButton createUpdateButton() {
        return null;
    }
}
