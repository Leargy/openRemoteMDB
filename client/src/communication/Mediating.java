package communication;

import сlient.Client;

import java.io.IOException;

/**
 * Интерфэйс для объекта посредника
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public interface Mediating {
    void notify(Component component, Segment parcel);
    Client getClient();
    void setApplicationMediator(sample.dialog_windows.communication.Mediating applicationMediator);
}
