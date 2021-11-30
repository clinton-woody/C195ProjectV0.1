package model;

import Interface.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Report {
    public final String ZERO = " 00:00:00";
    public String type;
    public String start;

    public Report(String type, String start){
        setType(type);
        setStart(start);
    }

    public String getType(){
        return type;
    }

    public String getStart(){
        return start;
    }

    public void setType(String newType){
        this.type = newType;
    }

    public void setStart(String newStart){
        this.start = newStart;
    }

}
