package server_core.configurators;

import parameters.server.BaseServerParameters;
import parameters.server.ConfiguredBaseServerParameters;

public interface ServerConfigurators {
    ConfiguredBaseServerParameters configure(BaseServerParameters baseParameters);
}
