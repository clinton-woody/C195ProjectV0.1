package model;

import Interface.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 This is the Contact class
 */
public class Contact {
    //CLASS Variables
    public String name;
    public int id;
    public static List<Integer> contactIds = new ArrayList<>();

    /**
     * This is the Contact constructor method.
     * @param id contact id
     * @param name contact name
     */
    public Contact(int id, String name) {
        setId(id);
        setName(name);
    }

    /**
     * This is the getContactIds method.  This method sends a SELECT statement to the database that returns all contact
     * ids as a List.
     * @return Returns contactIds as a list.
     */
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

    /**
     * This is the getAllContacts method.  This method sends a SELECT statement to the database that returns allContacts
     * as an ObservableList.
     * @return Returns allCOntacts as an ObservableList.
     */
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
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    /**
     * This is the getContactName method.  This is the getter method for name.
     * @return Returns name as a String.
     */
    public String getContactName(){
        return name;
    }

    /**
     * This is the getContactId method.  This is the getter method for id.
     * @return Returns id as an int.
     */
    public int getContactId(){
        return id;
    }

    /**
     * This is the setName method.  This is the setter method for name.
     * @param newName Takes String newName.
     */
    public void setName (String newName){
        this.name = newName;
    }

    /**
     * This is the setId method.  This is the setter method for id.
     * @param newId Takes int newId.
     */
    public void setId (int newId){
        this.id = newId;
    }

    public String toString(){
        return id+" "+name;
    }

}
