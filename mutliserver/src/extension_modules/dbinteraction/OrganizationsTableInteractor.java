package extension_modules.dbinteraction;

import communication.Report;
import communication.ReportsFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import organization.OrganizationWithUId;
import entities.User;
import extension_modules.ClassUtils;

import java.sql.*;

// Organizations Table Structure:
// 1  id integer
// 2  name varchar
// 3  fullname varchar
// 4  type varchar
// 5  employeescount integer
// 6  annualturnover float
// 7  creationdate timestamp
// 8  coordinates_x integer
// 9  coordinates_y double
// 10 zipcode varchar
// 11 location_x double
// 12 location_y double
// 13 location_z integer
// 14 user_login characteer varying
public class OrganizationsTableInteractor implements TablesInteractor {
    public static final String DB_TABLE_NAME = "organizations";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Report clearUserOrganization(User user) throws SQLException {
        int length = countUsersOrganizations(user);
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement clearing = currentConnection
                .prepareStatement("DELETE FROM " + DB_TABLE_NAME + " WHERE user_login = ?;");
        clearing.setString(1, user.getLogin());
        logger.info("Executing SQL request \"clear\"");
        int result = clearing.executeUpdate();
        if (length == result){
            logger.info("SQL request \"clear\" was correctly executed");
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }else{
            logger.error("SQL request was wrongly executed");
            throw new SQLException();
        }
//        return (length == result)? ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod()) : ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
    }
    public Report insertUserOrganization(Integer key, OrganizationWithUId organizationWithUId) throws SQLException {
        if (getDBOrganizationId(organizationWithUId) != -1) {
            throw new SQLException("Такой элемент уже существует!");
        }
//        System.out.println(organizationWithUId.getOrganization().hashCode());
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement insertion = currentConnection
                .prepareStatement("INSERT INTO " + DB_TABLE_NAME + "(name, fullname, type, employeescount, annualturnover, creationdate, coordinates_x, coordinates_y, zipcode, location_x, location_y, location_z, user_login, hash_cod)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
        insertion.setString(1, organizationWithUId.getName());
        insertion.setString(2, organizationWithUId.getFullname());
        insertion.setString(3, organizationWithUId.getType().name());
        insertion.setInt(4, organizationWithUId.getEmployeesCount());
        insertion.setFloat(5, organizationWithUId.getAnnualTurnover());
        insertion.setTimestamp(6, Timestamp.valueOf(organizationWithUId.getCreationDate()));
        insertion.setInt(7, organizationWithUId.getCoordinates().getX());
        insertion.setFloat(8, organizationWithUId.getCoordinates().getY());
        insertion.setString(9, organizationWithUId.getAddress().getZipCode());
        insertion.setDouble(10, organizationWithUId.getAddress().getTown().getX());
        insertion.setLong(11, organizationWithUId.getAddress().getTown().getY());
        insertion.setDouble(12, organizationWithUId.getAddress().getTown().getZ());
        insertion.setString(13, organizationWithUId.getUserLogin());
        insertion.setInt(14, organizationWithUId.getOrganization().hashCode());
        int result = 0;
        try {
            logger.info("Executing SQL request \"insert\"");
            result = insertion.executeUpdate();
        }catch (SQLException ex) {
            logger.error(ex.getMessage());
//            System.out.println(ex.getMessage());
        }
        if (result == 1) {
            logger.info("SQL request was correctly executed");
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }
        else {
            logger.error("SQL request was wrongly executed");
            throw new SQLException();
        }
//        return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public Report removeUserOrganizationByKey(User user, Integer key) throws SQLException {
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement removing = currentConnection.prepareStatement("DELETE FROM " + DB_TABLE_NAME + " WHERE user_login = ? AND id = ?;");
        removing.setString(1, user.getLogin());
        removing.setInt(2, key);
        int result = 0;
        try {
            logger.info("Executing SQL request \"remove by key\"");
            result= removing.executeUpdate();
        }catch (SQLException ex) {
            logger.error(ex.getMessage());
//            System.out.println(ex.getMessage());
        }
//        System.out.println(result);
        if (result != 1){
            logger.error("SQL request was wrongly executed");
            throw new SQLException();
        }
            //            return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
        else {
            logger.info("SQL request was correctly executed");
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }
    }

    public Report replaceUserOrganizationIfLower(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        int length = countLowerUsersOrganizations(user, organizationWithUId);
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement replacing = currentConnection.prepareStatement("UPDATE " + DB_TABLE_NAME + " SET name = ?,"
                + " fullname = ?, type = ?, employeescount = ?, annualturnover = ?, creationdate = ?, coordinates_x = ?, coordinates_y = ?, zipcode = ?, location_x = ?,"
                + "location_y = ?, location_z = ?, hash_cod = ? WHERE user_login = ? AND name < ?;");
        replacing.setString(1, organizationWithUId.getName());
        replacing.setString(2, organizationWithUId.getFullname());
        replacing.setString(3, organizationWithUId.getType().name());
        replacing.setInt(4, organizationWithUId.getEmployeesCount());
        replacing.setFloat(5, organizationWithUId.getAnnualTurnover());
        replacing.setTimestamp(6, Timestamp.valueOf(organizationWithUId.getCreationDate()));
        replacing.setInt(7, organizationWithUId.getCoordinates().getX());
        replacing.setFloat(8, organizationWithUId.getCoordinates().getY());
        replacing.setString(9, organizationWithUId.getAddress().getZipCode());
        replacing.setDouble(10, organizationWithUId.getAddress().getTown().getX());
        replacing.setLong(11, organizationWithUId.getAddress().getTown().getY());
        replacing.setDouble(12, organizationWithUId.getAddress().getTown().getZ());
        replacing.setInt(13,organizationWithUId.getOrganization().hashCode());
        replacing.setString(14, organizationWithUId.getUserLogin());
        replacing.setString(15, organizationWithUId.getName());
        int replaced = 0;
        try {
            logger.info("Executing SQL request \"replace if lower\"");
            replaced = replacing.executeUpdate();
        }catch (SQLException ex) {
            logger.error(ex.getMessage());
//            System.out.println(ex.getMessage());
        }
        if (replaced == length) {
            logger.info("SQL request was correctly executed");
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }else {
            logger.error("SQL request was wrongly executed");
            throw new SQLException();
        }
//        return (replaced == length)? ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod())
//                : ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public Report replaceUserOrganizationIfGreater(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        int length = countGreaterUsersOrganizations(user, organizationWithUId);
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement replacing = currentConnection.prepareStatement("UPDATE " + DB_TABLE_NAME + " SET name = ?,"
                + " fullname = ?, type = ?, employeescount = ?, annualturnover = ?, creationdate = ?, coordinates_x = ?, coordinates_y = ?, zipcode = ?, location_x = ?,"
                + "location_y = ?, location_z = ? WHERE user_login = ? AND name > ?;");
        replacing.setString(1, organizationWithUId.getName());
        replacing.setString(2, organizationWithUId.getFullname());
        replacing.setString(3, organizationWithUId.getType().name());
        replacing.setInt(4, organizationWithUId.getEmployeesCount());
        replacing.setFloat(5, organizationWithUId.getAnnualTurnover());
        replacing.setTimestamp(6, Timestamp.valueOf(organizationWithUId.getCreationDate()));
        replacing.setInt(7, organizationWithUId.getCoordinates().getX());
        replacing.setFloat(8, organizationWithUId.getCoordinates().getY());
        replacing.setString(9, organizationWithUId.getAddress().getZipCode());
        replacing.setDouble(10, organizationWithUId.getAddress().getTown().getX());
        replacing.setLong(11, organizationWithUId.getAddress().getTown().getY());
        replacing.setDouble(12, organizationWithUId.getAddress().getTown().getZ());
//        replacing.setInt(13,key);
        replacing.setString(13, organizationWithUId.getUserLogin());
        replacing.setString(14, organizationWithUId.getName());
        int replaced = -1;
        try {
            logger.info("Executing SQL request \"replace if greater\"");
            replaced = replacing.executeUpdate();
        }catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        if(replaced == length){
            logger.info("SQL request was correctly executed");
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }else {
            logger.error("SQL request was wrongly executed");
            throw new SQLException();
        }
//        return (replaced == length)? ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod())
//                : ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public Report removeLowerUserOrganization(User user, OrganizationWithUId organizationWithUId) throws SQLException {
        int length = countLowerUsersOrganizations(user, organizationWithUId);
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement removing = currentConnection.prepareStatement("DELETE FROM " + DB_TABLE_NAME + " WHERE user_login = ? AND name < ?");
        removing.setString(1, user.getLogin());
        removing.setString(2, organizationWithUId.getName());
        int removed = -1;
        try {
            logger.info("Executing SQL request \"remove lower\"");
            removed = removing.executeUpdate();
        }catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        if (removed == length) {
            logger.info("SQL request was correctly executed");
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }else {
            logger.error("SQL request was wrongly executed");
            throw new SQLException();
        }
//        return (removed == length)? ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod())
//                : ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());

    }

    public Report updateUserOrganization(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        System.out.println("ID = " + organizationWithUId.getOrganization().getID());
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement updating = currentConnection.prepareStatement("UPDATE " + DB_TABLE_NAME + " SET name = ?,"
                + " fullname = ?, type = ?, employeescount = ?, annualturnover = ?, creationdate = ?, coordinates_x = ?, coordinates_y = ?, zipcode = ?, location_x = ?,"
                + "location_y = ?, location_z = ?, hash_cod = ? WHERE user_login = ? AND id = ?;");
        updating.setString(1, organizationWithUId.getName());
        updating.setString(2, organizationWithUId.getFullname());
        updating.setString(3, organizationWithUId.getType().name());
        updating.setInt(4, organizationWithUId.getEmployeesCount());
        updating.setFloat(5, organizationWithUId.getAnnualTurnover());
        updating.setTimestamp(6, Timestamp.valueOf(organizationWithUId.getCreationDate()));
        updating.setInt(7, organizationWithUId.getCoordinates().getX());
        updating.setFloat(8, organizationWithUId.getCoordinates().getY());
        updating.setString(9, organizationWithUId.getAddress().getZipCode());
        updating.setDouble(10, organizationWithUId.getAddress().getTown().getX());
        updating.setLong(11, organizationWithUId.getAddress().getTown().getY());
        updating.setDouble(12, organizationWithUId.getAddress().getTown().getZ());
        updating.setInt(13,organizationWithUId.getOrganization().hashCode());
        updating.setString(14, user.getLogin());
        updating.setInt(15, organizationWithUId.getOrganization().getID());

        try {
            logger.info("Executing SQL request \"update\"");
            updating.executeUpdate();
        }catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new SQLException("wrong sql request");
        }
        logger.info("SQL request was correctly executed");
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public int countUsersOrganizations(User user) throws SQLException {
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement selection = currentConnection
                .prepareStatement("SELECT COUNT(*) AS rowcount FROM " + DB_TABLE_NAME + " WHERE user_login = ?;");
        selection.setString(1, user.getLogin());
        logger.info("Executing SQL request \"count user's organizations\"");
        ResultSet resultSet = selection.executeQuery();
        int result = 0;
        resultSet.next();
        result = resultSet.getInt("rowcount");
        return result;
    }

    public int countGreaterUsersOrganizations(User user, OrganizationWithUId organizationWithUId) throws SQLException{
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement counting = currentConnection
                .prepareStatement("SELECT COUNT(*) AS rowcount FROM " + DB_TABLE_NAME + " WHERE user_login = ? AND name > ?;");
        counting.setString(1, user.getLogin());
        counting.setString(2, organizationWithUId.getName());
        logger.info("Executing SQL request \"count greater user's organizations\"");
        ResultSet resultSet = counting.executeQuery();
        int result = 0;
        resultSet.next();
        result = resultSet.getInt("rowcount");
        return result;
    }

    public int countLowerUsersOrganizations(User user, OrganizationWithUId organizationWithUId) throws SQLException {
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        PreparedStatement counting = currentConnection
                .prepareStatement("SELECT COUNT(*) AS rowcount FROM " + DB_TABLE_NAME + " WHERE user_login LIKE ? AND name < ?;");
        counting.setString(1, user.getLogin());
        counting.setString(2, organizationWithUId.getName());
        logger.info("Executing SQL request \"count lower user's organizations\"");
        ResultSet resultSet = counting.executeQuery();
        int result = 0;
        resultSet.next();
        result = resultSet.getInt("rowcount");
        return result;
    }

    public int getDBOrganizationId(OrganizationWithUId organizationWithUId) {
//        System.out.println(organizationWithUId.getOrganization().hashCode());
        int currentOrganizationId = -1;
        try {
            Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
            logger.info("Preparing SQL request");
//            PreparedStatement counting = currentConnection.prepareStatement("SELECT * FROM " + DB_TABLE_NAME + " WHERE hash_cod = ?");
            PreparedStatement counting = currentConnection.prepareStatement("SELECT * FROM organizations WHERE (name = ? AND fullname = ? AND type = ? AND employeescount = ? AND annualturnover = ? AND coordinates_x = ? AND coordinates_y = ? AND zipcode = ? AND location_x = ? AND location_y = ? AND location_z = ? AND user_login = ?);");
            counting.setString(1, organizationWithUId.getName());
            counting.setString(2, organizationWithUId.getFullname());
            counting.setString(3, organizationWithUId.getType().name());
            counting.setInt(4, organizationWithUId.getEmployeesCount());
            counting.setFloat(5, organizationWithUId.getAnnualTurnover());
//            counting.setTimestamp(6, Timestamp.valueOf(organizationWithUId.getCreationDate()));
            counting.setInt(6, organizationWithUId.getCoordinates().getX());
            counting.setFloat(7, organizationWithUId.getCoordinates().getY());
            counting.setString(8, organizationWithUId.getAddress().getZipCode());
            counting.setDouble(9, organizationWithUId.getAddress().getTown().getX());
            counting.setLong(10, organizationWithUId.getAddress().getTown().getY());
            counting.setDouble(11, organizationWithUId.getAddress().getTown().getZ());
            counting.setString(12, organizationWithUId.getUserLogin());
//            counting.setInt(1,organizationWithUId.getOrganization().hashCode());
            logger.info("Executing SQL request \"get organizations id\"");
            ResultSet resultSet = counting.executeQuery();
            resultSet.next();
            currentOrganizationId = resultSet.getInt("id");
        }catch (SQLException ex) {
            logger.error(ex.getMessage());
//            System.out.println(ex.getMessage());
        }
        System.out.println("id " + currentOrganizationId);
        return currentOrganizationId;
    }

    public void setHashCod(OrganizationWithUId organizationWithUId) {
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        logger.info("Preparing SQL request");
        try {
            PreparedStatement counting = currentConnection.prepareStatement("UPDATE " + DB_TABLE_NAME + " SET hash_cod = ? WHERE id = ?");
            counting.setInt(1, organizationWithUId.getOrganization().hashCode());
            counting.setInt(2, organizationWithUId.getOrganization().id);
            counting.executeUpdate();
//            System.out.println("hash " + organizationWithUId.getOrganization().hashCode());
        }catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
}
