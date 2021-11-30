//WORKING
package model;

import Interface.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 This is the User class.  This class creates the parameters for users.
 */
public class User {

    //Variables
    public int id;
    public String name;
    public static String currentUser;
    //public static List<Integer> userIds = new ArrayList<>();

    /**
     *This is the user constructor method.
     * @param id user id
     * @param name user name
     */
    public User(int id, String name) {
        setId(id);
        setName(name);
    }

    /**
     *This is the getAllUsers method.  This method sends a SELECT statement to the database that returns all users as
     * an ObservableList
     * @return all users as an ObservableList
     */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try{
            String userGrab = "SELECT User_ID, User_Name FROM Users";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, userGrab);
            PreparedStatement psU = DBQuery.getPreparedStatement();
            psU.execute();
            ResultSet rsU = psU.getResultSet();
            while (rsU.next()) {
                User nextUser = new User(
                        rsU.getInt("User_ID"),
                        rsU.getString("User_Name"));
                allUsers.add(nextUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
        return allUsers;
    }

    /**
     *This is the gatUserIds method.  This method sends a SELECT statement to the database that returns a list of
     *userIds.
     * @return Returns userIds as a list.
     */
    public static List getUserIds() {
        try {
            String idGrab = "SELECT User_ID FROM Users";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, idGrab);
            PreparedStatement psUI = DBQuery.getPreparedStatement();
            psUI.execute(); //Execute PreparedStatement
            ResultSet rsUI = psUI.getResultSet();
            ObservableList<User> allUsers = FXCollections.observableArrayList();
            List<Integer> userIds = new ArrayList<>();
            while (rsUI.next()) {
                int nextId = rsUI.getInt("User_ID");
                userIds.add(nextId);
            }
            return userIds;
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    /**
     * This is the toString method.  This is a method that overrides the usual embedded toString method.
     * @return returns a String
     */
    public String toString(){
        return id+" "+name;
    }


    /**
     * This is the getName method.  This the getter method for name.
     * @return Returns name as a String.
     */
    public String getName(){
        return name;
    }

    /**
     * This is the getId method.  This is the getter method for id.
     * @return Returns id as an int.
     */
    public int getId(){
        return id;
    }

    /**
     * This is the setName method.  This is the setter method for name.
     * @param newName Takes String newName
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * This is the setId method.  This is the setter method for id.
     * @param newId Takes int newId
     */
    public void setId(int newId){
        this.id = newId;
    }
}