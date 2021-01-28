package asu.mc.newnavigate;


import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

class CrimeSample {
    private String location;
    private String type_of_crime;
    private Date date;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String date1) {
        int firstindex = date1.indexOf(":");
        int secondindex = date1.indexOf(":",firstindex+1);
        time = date1.substring(date1.indexOf("T")+1,secondindex);
        //System.out.println(time);

    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType_of_crime() {
        return type_of_crime;
    }

    public void setType_of_crime(String type_of_crime) {
        this.type_of_crime = type_of_crime;
    }

    public Date getDate() {
        return date;
    }

    public String toString()
    {
        return "Crime Sample{"+"date = "+date+"time = "+time+"location  = " + location+ "type of crime = " + type_of_crime+"}";

    }

    public void setDate(String date1) throws ParseException {
        //this.date = date;
        System.out.println(date1);

        //String date2 = date1.substring(0,date1.indexOf("T"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        date = formatter.parse(date1);






    }
}

