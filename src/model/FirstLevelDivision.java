package model;

import Interface.DBQuery;
import controller.CustomerUpdateForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FirstLevelDivision {


    //Variables
    public int id;
    public String name;
    public static int countryId;
    public static int divisionId = 0;

    public FirstLevelDivision(int id, String name) {
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
    public static ObservableList<FirstLevelDivision> getAllDivision(){
        ObservableList<FirstLevelDivision> allFirstLevelDivision = FXCollections.observableArrayList();
        try{
            String divisionGrab = "SELECT Division_ID, Division FROM first_level_divisions " +
                                  "WHERE Country_ID = ?";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, divisionGrab);
            PreparedStatement psD = DBQuery.getPreparedStatement();
            psD.setInt(1, CustomerUpdateForm.selectedID);
            psD.execute(); //Execute PreparedStatement
            ResultSet rsD = psD.getResultSet();
            while (rsD.next()) {
                FirstLevelDivision nextFirstLevelDivision = new FirstLevelDivision(
                        rsD.getInt("Division_ID"),
                        rsD.getString("Division"));
                allFirstLevelDivision.add(nextFirstLevelDivision);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }

        return allFirstLevelDivision;
    }

    public static ObservableList<FirstLevelDivision> divisionParser(){
        ObservableList<FirstLevelDivision> parsedDivision = FXCollections.observableArrayList();

            try{
                String divisionCountryConversion = "SELECT Division_ID, Division FROM first_level_divisions " +
                        "WHERE Division_ID = ?";

                DBQuery.setPreparedStatement(Interface.JDBC.conn, divisionCountryConversion);
                PreparedStatement psD2 = DBQuery.getPreparedStatement();
                psD2.setInt(1, CustomerUpdateForm.selectedDivisionId);
                psD2.execute(); //Execute PreparedStatement
                ResultSet rsD2 = psD2.getResultSet();
                while (rsD2.next()) {
                    FirstLevelDivision division = new FirstLevelDivision(
                            rsD2.getInt("Division_ID"),
                            rsD2.getString("Division"));
                    parsedDivision.add(division);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
                return null;
            }



        return parsedDivision;
    }


        public static int getCountryID(){//This works


            try{
                String getCountryID = "SELECT Country_ID FROM first_level_divisions " +
                        "WHERE Division_ID = ?";
                System.out.println("divisionId at start of method is: " + divisionId);
                DBQuery.setPreparedStatement(Interface.JDBC.conn, getCountryID);
                PreparedStatement psGCI = DBQuery.getPreparedStatement();
                psGCI.setInt(1, divisionId);
                psGCI.execute(); //Execute PreparedStatement
                ResultSet rsGCI = psGCI.getResultSet();
                while (rsGCI.next()) {
                    int i = (
                            rsGCI.getInt("Country_ID"));
                            FirstLevelDivision.countryId = i;
                }
                System.out.println(CustomerUpdateForm.selectedDivisionId);
                System.out.println(countryId); //Works to here

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");

            }


            System.out.println(countryId); //Works to here
        return countryId;
    }


    public static ObservableList<Country> divisionToCountry(){
        ObservableList<Country> selectedCountry = FXCollections.observableArrayList();

        try{
            String divisionCountryConversion = "SELECT first_level_divisions.Country_ID, countries.Country " +
                                               "FROM first_level_divisions " +
                                               "JOIN countries ON countries.Country_ID=first_level_divisions.Country_ID " +
                                               "WHERE first_level_divisions.Division_ID = ?";

            DBQuery.setPreparedStatement(Interface.JDBC.conn, divisionCountryConversion);
            PreparedStatement psD2 = DBQuery.getPreparedStatement();
            psD2.setInt(1, divisionId);
            psD2.execute(); //Execute PreparedStatement
            ResultSet rsD2 = psD2.getResultSet();
            while (rsD2.next()) {
                Country country = new Country(
                        rsD2.getInt("Country_ID"),
                        rsD2.getString("Country"));
                selectedCountry.add(country);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }

        return selectedCountry;
    }

    public String toString(){
        return id+" "+name;
    }
}