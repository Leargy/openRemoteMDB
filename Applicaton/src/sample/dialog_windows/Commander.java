package sample.dialog_windows;

import sample.dialog_windows.communication.Parcel;

public interface Commander {
    void setConnection(Parcel parcel);
    void signIn();
    void signOut();
    void signUp();
    void clear();
    void insert();
    void update();
    void info();
}
