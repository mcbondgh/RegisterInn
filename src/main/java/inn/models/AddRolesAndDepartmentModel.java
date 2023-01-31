package inn.models;

import inn.database.DbConnection;

public class AddRolesAndDepartmentModel extends DbConnection {

    //THIS METHOD WHEN INVOKED TAKES IN ROLE NAME AS AN ARGUMENT AND INSERTS A NEW USER ROLE TYPE INTO THE roles TABLE.
    public int addNewRoleType(String roleName) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO roles VALUES(DEFAULT, '"+roleName+"', DEFAULT, DEFAULT, DEFAULT)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            flag = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //THIS METHOD WHEN INVOKED TAKES IN ROLE NAME AS AN ARGUMENT AND DELETES SAME FROM THE roles TABLE.
    public int deleteRoleType(String roleName) {
        int flag = 0;
        try {
            String deleteQuery = "DELETE FROM roles WHERE(name = '"+roleName+"')";
            prepare = CONNECTOR().prepareStatement(deleteQuery);
            flag = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //THIS METHOD WHEN INVOKED TAKES IN DEPARTMENT NAME AS AN ARGUMENT AND INSERTS A NEW DEPARTMENT TYPE INTO THE designation TABLE.
    public int addNewDepartment(String name) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO designation VALUES(DEFAULT, '"+name+"', DEFAULT, DEFAULT)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            flag = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //THIS METHOD WHEN INVOKED TAKES IN ROLE NAME AS AN ARGUMENT AND DELETES SAME FROM THE designation TABLE.
    public int deleteDesignation(String roleName) {
        int flag = 0;
        try {
            String deleteQuery = "DELETE FROM designation WHERE(name = '"+roleName+"')";
            prepare = CONNECTOR().prepareStatement(deleteQuery);
            flag = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //THIS METHOD WHEN INVOKED TAKES IN ROLE NAME AS AN ARGUMENT AND INSERTS A NEW ID TYPE INTO THE it_types TABLE.
    public int addNewIdType(String roleName) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO id_types VALUES(DEFAULT, '"+roleName+"', DEFAULT, DEFAULT)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            flag = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //THIS METHOD WHEN INVOKED TAKES IN ROLE NAME AS AN ARGUMENT AND DELETES SAME FROM THE roles TABLE.
    public int deleteIdType(String roleName) {
        int flag = 0;
        try {
            String deleteQuery = "DELETE FROM id_types WHERE(name = '"+roleName+"')";
            prepare = CONNECTOR().prepareStatement(deleteQuery);
            flag = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //THIS METHOD WHEN INVOKED TAKES IN CATEGORY NAME AS AN ARGUMENT AND INSERTS SAME FROM THE roomsCategory TABLE.
    public int addNewRoomsCategory(String categoryName, Double price) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO roomsCategory VALUES(DEFAULT, '"+categoryName+"', DEFAULT, '"+price+"', DEFAULT)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            flag = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //THIS METHOD WHEN INVOKED TAKES IN CATEGORY NAME AS AN ARGUMENT AND DELETES SAME FROM THE roomsCategory TABLE.
    public int deleteRoomsCategory(String categoryName) {
        int flag = 0;
        try {
            String deleteQuery = "DELETE FROM roomsCategory WHERE(name = '"+categoryName+"')";
            prepare = CONNECTOR().prepareStatement(deleteQuery);
            flag = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }



}//END CLASS
