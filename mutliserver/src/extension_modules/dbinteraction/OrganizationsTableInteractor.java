package extension_modules.dbinteraction;

import communication.Report;
import communication.ReportsFormatter;
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


    public Report clearUserOrganization(User user) throws SQLException {
        int length = countUsersOrganizations(user);
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement clearing = currentConnection
                .prepareStatement("DELETE FROM " + DB_TABLE_NAME + " WHERE user_login = ?;");
        clearing.setString(1, user.getLogin());
        int result = clearing.executeUpdate();
        if (length == result){
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }else throw new SQLException();
//        return (length == result)? ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod()) : ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
    }
    public Report insertUserOrganization(Integer key, OrganizationWithUId organizationWithUId) throws SQLException {
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement insertion = currentConnection
                .prepareStatement("INSERT INTO " + DB_TABLE_NAME + "(name, fullname, type, employeescount, annualturnover, creationdate, coordinates_x, coordinates_y, zipcode, location_x, location_y, location_z, user_login, collection_key)"
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
        insertion.setInt(14, key);
        int result = 0;
        try {
            result = insertion.executeUpdate();
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (result == 1)
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        else throw new SQLException();
//        return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public Report removeUserOrganizationByKey(User user, Integer key) throws SQLException {
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement removing = currentConnection.prepareStatement("DELETE FROM " + DB_TABLE_NAME + " WHERE user_login = ? AND collection_key = ?;");
        removing.setString(1, user.getLogin());
        removing.setInt(2, key);
        int result = 0;
        try {
            result= removing.executeUpdate();
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(result);
        if (result != 1)
            throw new SQLException();
            //            return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
        else return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public Report replaceUserOrganizationIfLower(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        int length = countLowerUsersOrganizations(user, organizationWithUId);
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement replacing = currentConnection.prepareStatement("UPDATE " + DB_TABLE_NAME + " SET name = ?,"
                + " fullname = ?, type = ?, employeescount = ?, annualturnover = ?, creationdate = ?, coordinates_x = ?, coordinates_y = ?, zipcode = ?, location_x = ?,"
                + "location_y = ?, location_z = ?, collection_key = ? WHERE user_login = ? AND name < ?;");
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
        replacing.setInt(13,key);
        replacing.setString(14, organizationWithUId.getUserLogin());
        replacing.setString(15, organizationWithUId.getName());
        int replaced = 0;
        try {
            replaced = replacing.executeUpdate();
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (replaced == length) {
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }else throw new SQLException();
//        return (replaced == length)? ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod())
//                : ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public Report replaceUserOrganizationIfGreater(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        int length = countGreaterUsersOrganizations(user, organizationWithUId);
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement replacing = currentConnection.prepareStatement("UPDATE " + DB_TABLE_NAME + " SET name = ?,"
                + " fullname = ?, type = ?, employeescount = ?, annualturnover = ?, creationdate = ?, coordinates_x = ?, coordinates_y = ?, zipcode = ?, location_x = ?,"
                + "location_y = ?, location_z = ?, collection_key = ?  WHERE user_login = ? AND name > ?;");
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
        replacing.setInt(13,key);
        replacing.setString(14, organizationWithUId.getUserLogin());
        replacing.setString(15, organizationWithUId.getName());
        int replaced = replacing.executeUpdate();
        if(replaced == length){
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }else throw new SQLException();
//        return (replaced == length)? ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod())
//                : ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public Report removeLowerUserOrganization(User user, OrganizationWithUId organizationWithUId) throws SQLException {
        int length = countLowerUsersOrganizations(user, organizationWithUId);
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement removing = currentConnection.prepareStatement("DELETE FROM " + DB_TABLE_NAME + " WHERE user_login = ? AND name < ?");
        removing.setString(1, user.getLogin());
        removing.setString(2, organizationWithUId.getName());
        int removed = removing.executeUpdate();
        if (removed == length) {
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }else throw new SQLException();
//        return (removed == length)? ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod())
//                : ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());

    }

    public Report updateUserOrganization(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement updating = currentConnection.prepareStatement("UPDATE " + DB_TABLE_NAME + " SET name = ?,"
                + " fullname = ?, type = ?, employeescount = ?, annualturnover = ?, creationdate = ?, coordinates_x = ?, coordinates_y = ?, zipcode = ?, location_x = ?,"
                + "location_y = ?, location_z = ?, collection_key = ? WHERE user_login = ?;");
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
        updating.setInt(13,key);
        updating.setString(14, organizationWithUId.getUserLogin());
        updating.executeUpdate();
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public int countUsersOrganizations(User user) throws SQLException {
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement selection = currentConnection
                .prepareStatement("SELECT COUNT(*) AS rowcount FROM " + DB_TABLE_NAME + " WHERE user_login = ?;");
        selection.setString(1, user.getLogin());
        ResultSet resultSet = selection.executeQuery();
        int result = 0;
        resultSet.next();
        result = resultSet.getInt("rowcount");
        return result;
    }

    public int countGreaterUsersOrganizations(User user, OrganizationWithUId organizationWithUId) throws SQLException{
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement counting = currentConnection
                .prepareStatement("SELECT COUNT(*) AS rowcount FROM " + DB_TABLE_NAME + " WHERE user_login = ? AND name > ?;");
        counting.setString(1, user.getLogin());
        counting.setString(2, organizationWithUId.getName());
        ResultSet resultSet = counting.executeQuery();
        int result = 0;
        resultSet.next();
        result = resultSet.getInt("rowcount");
        return result;
    }

    public int countLowerUsersOrganizations(User user, OrganizationWithUId organizationWithUId) throws SQLException {
        Connection currentConnection = DataBaseConnector.getInstance().retrieveCurrentConnection();
        PreparedStatement counting = currentConnection
                .prepareStatement("SELECT COUNT(*) AS rowcount FROM " + DB_TABLE_NAME + " WHERE user_login LIKE ? AND name < ?;");
        counting.setString(1, user.getLogin());
        counting.setString(2, organizationWithUId.getName());
        ResultSet resultSet = counting.executeQuery();
        int result = 0;
        resultSet.next();
        result = resultSet.getInt("rowcount");
        return result;
    }

}
