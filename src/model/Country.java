package model;

import Interface.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 This is the Country class
 */
public class Country {
    //CLASS VARIABLES
    public int id;
    public String name;
    public static int formId = 0;

    /**
     * This is the Country constructor.
     * @param id country id
     * @param name country name
     */
    public Country(int id, String name) {
        setId(id);
        setName(name);
    }

    /**
     * This is the getName method.  This is the getter for name.
     * @return Returns name as a String.
     */
    public String getName(){
        return name;
    }

    /**
     * This is the getId method.  This is the getter for id.
     * @return Returns id as an int.
     */
    public int getId(){
        return id;
    }

    /**
     * This is the setName method.  This is the setter for name.
     * @param newName Takes String newName.
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * This is the setId method.  This is the setter for setId.
     * @param newId Takes int newId.
     */
    public void setId(int newId){
        this.id = newId;
    }

    /**
     * This is the getAllCountries method.  This method sends a SELECT statement to the database that returns an
     * ObservableList of countries.
     * @return  Returns allCountries as an ObservableList.
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try{
            String countryGrab = "SELECT Country_Id, Country FROM countries";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, countryGrab);
            PreparedStatement psC = DBQuery.getPreparedStatement();
            psC.execute(); //Execute PreparedStatement
            ResultSet rsC = psC.getResultSet();
            while (rsC.next()) {
                Country nextCountry = new Country(
                        rsC.getInt("Country_ID"),
                        rsC.getString("Country"));
                allCountries.add(nextCountry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }

        return allCountries;
    }

    /**
     * This is the toString method.  This method overrides the normat toString method.
     * @return Returns a String.
     */
    public String toString(){
        return id+" "+name;
    }
}
