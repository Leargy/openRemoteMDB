package sample.drawing_utils.directors.companies;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import organization.OrganizationWithUId;
import sample.drawing_utils.builders.companies.CompanyBuilder;
import sample.drawing_utils.builders.rooms.*;
import sample.drawing_utils.directors.rooms.*;
import sample.drawing_utils.materials.ArchitectureType;
import sample.drawing_utils.materials.Company;

public class CompanyDirector {
    private CompanyBuilder companyBuilder = new CompanyBuilder();
    private HallDirector hallDirector = new HallDirector();
    private OfficesDirector officesDirector = new OfficesDirector();
    private NegotiateDirector negotiateDirector = new NegotiateDirector();
    private CanteenDirector canteenDirector = new CanteenDirector();
    private StorageDirector storageDirector = new StorageDirector();
    private BossDirector bossDirector = new BossDirector();
    private AtticDirector atticDirector = new AtticDirector();
    private RoofDirector roofDirector = new RoofDirector();

    {
        changeBuilder(new HallBuilder());
        changeBuilder(new OfficeBuilder());
        changeBuilder(new NegotiateBuilder());
        changeBuilder(new CanteenBuilder());
        changeBuilder(new StorageBuilder());
        changeBuilder(new BossBuilder());
        changeBuilder(new AtticBuilder());
        changeBuilder(new RoofBuilder());
    }

    public Company make(OrganizationWithUId organization, Paint colour, Group group) {
        companyBuilder.destroy();
        companyBuilder.plan(organization);
        ArchitectureType type = ArchitectureType.defineType(organization.getEmployeesCount());
        switch (type) {
            case ROOFED: {
                companyBuilder.buildRoof(roofDirector.make(organization, colour, group));
            }
            case ATTICED: {
                companyBuilder.buildAttic(atticDirector.make(organization, colour, group));
            }
            case BOSSED: {
                companyBuilder.buildBoss(bossDirector.make(organization, colour, group));
            }
            case NEGOTIATED: {
                companyBuilder.buildNegotiate(negotiateDirector.make(organization, colour, group));
            }
            case STORAGED: {
                companyBuilder.buildStorage(storageDirector.make(organization, colour, group));
            }
            case CANTEENED: {
                companyBuilder.buildCanteen(canteenDirector.make(organization, colour, group));
            }
            case OFFICED: {
                companyBuilder.buildOffices(officesDirector.make(organization, colour, group));
            }
            default:
            case HALLED: {
                companyBuilder.buildHall(hallDirector.make(organization, colour, group));
                break;
            }
        }
        return companyBuilder.getResult();
    }

    public void changeBuilder(HallBuilder builder) {
        hallDirector.changeBuilder(builder);
    }

    public void changeBuilder(OfficeBuilder builder) {
        officesDirector.changeBuilder(builder);
    }

    public void changeBuilder(NegotiateBuilder builder) {
        negotiateDirector.changeBuilder(builder);
    }

    public void changeBuilder(CanteenBuilder builder) {
        canteenDirector.changeBuilder(builder);
    }

    public void changeBuilder(StorageBuilder builder) {
        storageDirector.changeBuilder(builder);
    }

    public void changeBuilder(BossBuilder builder) {
        bossDirector.changeBuilder(builder);
    }

    public void changeBuilder(AtticBuilder builder) {
        atticDirector.changeBuilder(builder);
    }

    public void changeBuilder(RoofBuilder builder) {
        roofDirector.changeBuilder(builder);
    }
}
