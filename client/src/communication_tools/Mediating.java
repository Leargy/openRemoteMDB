package communication_tools;

import сlient.Client;

/**
 * Интерфэйс для объекта посредника
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public interface Mediating {
    void notify(Component component, Segment parcel);
    Client getClient();
}
