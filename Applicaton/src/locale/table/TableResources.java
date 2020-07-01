package locale.table;

public class TableResources extends java.util.ListResourceBundle {
    private static final Object[][] contents = {
            // BEGINNING OF INTERNATIONALIZATION
            {"Exit", "Exit"},
            {"Language", "Language"},
            {"Filter", "Filter"},
            {"Search", "Search"},
            {"Insert", "Insert"},
            {"Clear", "Clear"},
            {"Table", "Table"},
            {"Map", "Map"},
            {"Name", "Name"},
            {"Full Name", "Full Name"},
            {"Type", "Type"},
            {"Employees Count", "Employees Count"},
            {"Annual Turnover", "Annual Turnover"},
            {"Zip Code", "Zip Code"},
            {"Creation date", "Creation date"},
            {"Owner", "Owner"},
            {"Interaction", "Interaction"},
            {"No contents in table", "No contents in table"}
            // ENDING OF INTERNATIONALIZATION
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
