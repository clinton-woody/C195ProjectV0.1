package model;

import Interface.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Contact {
    //Variables
    public String name;
    public int id;
    public static List<Integer> contactIds = new ArrayList<>();


    //Constructor

    public Contact(int id, String name) {

        setId(id);
        setName(name);
    }


    public static List getContactIds() {
        try {
            String idGrab = "SELECT Contact_ID FROM Contacts";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, idGrab);
            PreparedStatement psCI = DBQuery.getPreparedStatement();
            psCI.execute(); //Execute PreparedStatement
            ResultSet rsCI = psCI.getResultSet();
            ObservableList<Contact> allContacts = FXCollections.observableArrayList();
            while (rsCI.next()) {
                int nextId = rsCI.getInt("Contact_ID");
                contactIds.add(nextId);
            }
        return contactIds;
    }catch(Exception e)
        {
        e.printStackTrace();
        System.out.println("Error on Building Data");
        return null;
        }
    }

    public static ObservableList<Contact> getAllContacts() {

        try {
            String contactGrab = "SELECT Contact_ID, Contact_Name FROM Contacts";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, contactGrab);
            PreparedStatement psCo = DBQuery.getPreparedStatement();
            psCo.execute(); //Execute PreparedStatement
            ResultSet rsCo = psCo.getResultSet();
            ObservableList<Contact> allContacts = FXCollections.observableArrayList();
            while (rsCo.next()) {
                Contact nextContact = new Contact(
                        rsCo.getInt("Contact_ID"),
                        rsCo.getString("Contact_Name"));
                allContacts.add(nextContact);
            }
            //System.out.println(allContacts);
            return allContacts;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }

    }


/*
    public static Contact getSelectedContact() {

    }
*/
    public String getContactName(){
        return name;
    }

    public int getContactId(){
        return id;
    }

    public void setName (String newName){
        this.name = newName;
    }

    public void setId (int newId){
        this.id = newId;
    }

    public String toString(){
        return id+" "+name;
    }

}
