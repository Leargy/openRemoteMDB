package sample.drawing_utils.builders.companies;

import organization.OrganizationWithUId;
import sample.drawing_utils.builders.rooms.*;
import sample.drawing_utils.directors.rooms.HallDirector;
import sample.drawing_utils.materials.Company;
import sample.drawing_utils.materials.Room;

public class CompanyBuilder {
    private Company product;
    public void destroy() {
        product = null;
    }

    public Company getResult() {
        return product;
    }

    public void plan(OrganizationWithUId link) {
        product = new Company(link);
    }

    public void buildHall(Room hall) {
        product.installRoom(HallBuilder.SITE, hall);
    }

    public void buildOffices(Room offices) {
        product.installRoom(OfficeBuilder.SITE, offices);
    }

    public void buildCanteen(Room canteen) {
        product.installRoom(CanteenBuilder.SITE, canteen);
    }

    public void buildStorage(Room storage) {
        product.installRoom(StorageBuilder.SITE, storage);
    }

    public void buildNegotiate(Room negotiate) {
        product.installRoom(NegotiateBuilder.SITE, negotiate);
    }

    public void buildBoss(Room boss) {
        product.installRoom(BossBuilder.SITE, boss);
    }

    public void buildAttic(Room attic) { product.installRoom(AtticBuilder.SITE, attic); }

    public void buildRoof(Room roof) {
        product.installRoom(RoofBuilder.SITE, roof);
    }
}
