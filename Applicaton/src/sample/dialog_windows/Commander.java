package sample.dialog_windows;

import sample.dialog_windows.communication.ApplicationParcel;

public interface Commander {
    void setConnection(ApplicationParcel applicationParcel);
    void back();
    void signIn(String login, String password);
    void signOut();
    void signUp(String login, String password);
    void clear();
    void insert();
    void update();
    void info();
}
