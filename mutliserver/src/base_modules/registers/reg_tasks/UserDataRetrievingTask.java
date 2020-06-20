package base_modules.registers.reg_tasks;

import base_modules.readers.readertasks.ClientPackageRetrievingTask;
import communication.ClientPackage;
import entities.User;
import patterns.mediator.Controllers;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class UserDataRetrievingTask {
    private final Controllers PERUSAL_CONTROLLER;
    public UserDataRetrievingTask(Controllers controller) {
        PERUSAL_CONTROLLER = controller;
    }

    // TODO: rewrite reading method from super class

}
