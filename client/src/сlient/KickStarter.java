package сlient;

import communication.Mediator;

public class KickStarter {
    public final Mediator mediator;
    public final Servant servant;
    public KickStarter() {
        mediator = new Mediator();
        servant = (Servant) mediator.getServant();
    }

}
