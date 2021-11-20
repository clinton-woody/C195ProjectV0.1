//WORKING
package model;

import Interface.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class User {

    //Variables
    public int id;
    public String name;
    public static String currentUser;
    public static List<Integer> userIds = new ArrayList<>();

    //Constructor
    public User(int id, String name) {
        setId(id);
        setName(name);
    }

    //SQL Statements
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

    public static List getUserIds() {
        try {
            String idGrab = "SELECT User_ID FROM Users";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, idGrab);
            PreparedStatement psUI = DBQuery.getPreparedStatement();
            psUI.execute(); //Execute PreparedStatement
            ResultSet rsUI = psUI.getResultSet();
            ObservableList<User> allUsers = FXCollections.observableArrayList();
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

    //toString Override
    public String toString(){
        return id+" "+name;
    }


    //Getters
    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    //Setters
    public void setName(String newName){
        this.name = newName;
    }

    public void setId(int newId){
        this.id = newId;
    }
}