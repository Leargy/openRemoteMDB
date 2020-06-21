package server_core;

import communication_tools.ReportsFormatter;
import essential_modules.dispatchers.DispatchController;
import essential_modules.dispatchers.Dispatchers;
import essential_modules.processors.Processors;
import essential_modules.processors.SubProcessorController;
import essential_modules.readers.PerusalController;
import essential_modules.readers.Perusals;
import essential_modules.registers.ReceptionController;
import essential_modules.registers.Registers;
import communication_tools.Report;
import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import extension_modules.ClassUtils;
import extension_modules.databases_interaction.tables_keepers.clients.LoggedClientsTableKeeper;
import extension_modules.databases_interaction.tables_keepers.organizations.LoggedOrganizationTableKeeper;
import extension_modules.databases_interaction.tables_loaders.clients.LoggedClientsTableLoader;
import extension_modules.databases_interaction.tables_loaders.organizations.LoggedOrganizationTableLoader;
import extension_modules.databases_interaction.tables_managers.clients.UsersTableManager;
import extension_modules.databases_interaction.tables_managers.organizations.OrganizationsTableManager;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import communication_tools.uplink_bags.TransportableBag;

public class ServerController implements Controllers {
    private static final int MAX_SIMULTANEOUS_SENDS = 50;
    private final Registers REGISTER;
    private final Perusals PERUSALER;
    private final Processors SUBPROCESSOR;
    private final Dispatchers DISPATCHER;
    public final TablesOperator OPERATOR;
    public final KeeperOperator KEEPER;

    public ServerController() {
        REGISTER = new ReceptionController(this);
        PERUSALER = new PerusalController();
        SUBPROCESSOR = new SubProcessorController(this);
        DISPATCHER = new DispatchController(this, MAX_SIMULTANEOUS_SENDS);
        OPERATOR = new TablesOperator(
                new UsersTableManager(new LoggedClientsTableLoader(), new LoggedClientsTableKeeper()),
                new OrganizationsTableManager(new LoggedOrganizationTableLoader(), new LoggedOrganizationTableKeeper())
        );
        KEEPER = new KeeperOperator(new ExecutorsKeeper(), new TransmissionsKeeper());
    }

    protected class TablesOperator {
        private final UsersTableManager CLIENTS_MANAGER;
        private final OrganizationsTableManager ORGANIZATIONS_MANAGER;

        public TablesOperator(UsersTableManager clients, OrganizationsTableManager organizations) {
            CLIENTS_MANAGER = clients;
            ORGANIZATIONS_MANAGER = organizations;
        }

    }

    protected class KeeperOperator {
        public final ExecutorsKeeper SYSTEMS;
        public final TransmissionsKeeper LINKS;

        public KeeperOperator(ExecutorsKeeper systems, TransmissionsKeeper links) {
            SYSTEMS = systems;
            LINKS = links;
        }
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {

        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }
}
