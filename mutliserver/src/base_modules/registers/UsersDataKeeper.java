package base_modules.registers;

import extension_modules.dbinteraction.DBInterConnector;
import entities.User;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class UsersDataKeeper implements Component {
    private final Controllers RECEPTION_CONTROLLER;
    private final Map<UUID, User> USERS = new HashMap<>();
    private final DBInterConnector DB_INTERCONNECTOR;

    public UsersDataKeeper(Controllers receptionController, DBInterConnector dbInterConnector) {
        RECEPTION_CONTROLLER = receptionController;
        DB_INTERCONNECTOR = dbInterConnector;
    }

    @Override
    public Controllers getController() { return RECEPTION_CONTROLLER; }

    public User addNewUser2DataBase(User newUser) {
        USERS.put(newUser.getID(), newUser);
        // TODO: add ineraction with database
        return newUser;
    }

    public User findUserByID(UUID userID) {
        // TODO: add interaction with database
        return USERS.get(userID);
    }
}
