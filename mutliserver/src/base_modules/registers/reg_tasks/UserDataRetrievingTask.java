package base_modules.registers.reg_tasks;

import base_modules.readers.readertasks.ClientPackageRetrievingTask;
import patterns.mediator.Controllers;

import java.nio.channels.SocketChannel;

public final class UserDataRetrievingTask extends ClientPackageRetrievingTask {

    public UserDataRetrievingTask(Controllers controller, SocketChannel readyClientChannel) {
        super(controller, readyClientChannel);
    }

    // TODO: rewrite reading method from super class
}
