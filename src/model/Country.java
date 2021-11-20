package model;

import Interface.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Country {


    //Variables
    public int id;
    public String name;
    public static int formId = 0;

    public Country(int id, String name) {
        setId(id);
        setName(name);
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

    //Methods
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

    public String toString(){
        return id+" "+name;
    }
}
