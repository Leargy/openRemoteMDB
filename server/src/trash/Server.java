package trash;

import communication_tools.Component;
import communication_tools.Mediator;
import communication_tools.wrappers.AlertBag;

public abstract class Server implements Component, Runnable {
  protected final Mediator controller;
  public Server(Mediator controller) { this.controller = controller; }

  public abstract void closeConnection(AlertBag parcel);
}
