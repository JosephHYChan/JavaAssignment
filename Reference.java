/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assgn3;

import java.util.Iterator;
import static java.util.Objects.isNull;

/**
 *
 * @author Joseph
 */
public class Reference {
    private String callNumber;
    private int year;
    private String title;
    private String type;
    
    public Reference(){
        callNumber = "";
        year = 0;
        title = "";
        type = "";
    }

    public Reference(String callNumber, int year, String title, String type){
        this.callNumber = callNumber;
        this.year = year;
        this.title = title;
        this.type = type;
        
    }

    public String getCallNumber(){
        
        return callNumber;
    }

    public int getYear(){
        
        return year;
    }

    public String getTitle(){
        
        return title;
    }

    public String getType(){
        
        return type;
    }

    public boolean equals(Reference anotherReference) {
        boolean same = true;

        same = same && (anotherReference.getCallNumber().equalsIgnoreCase(this.getCallNumber()));
        same = same && (anotherReference.getYear() == this.getYear());
        same = same && (anotherReference.getTitle().equalsIgnoreCase(this.getTitle()));

        return same;
    }
    
    public boolean hasMatch(String callNumber, int startYear, int endYear, String title) {
        boolean match = true;

        if (!(callNumber.isEmpty() || isNull(callNumber))) {
            match = match && (callNumber.equalsIgnoreCase(this.getCallNumber()));
        }
        if ((startYear != 0) && (endYear != 0)) {
            match = match && (this.getYear() >= startYear) && (this.getYear() <= endYear);
        }
        else if (startYear != 0) {
            match = match && (this.getYear() >= startYear);
        }
        else if (endYear != 0) {
            match = match && (this.getYear() <= endYear);
        }
        /*
        if (!(title.isEmpty() || isNull(title))) {
            match = match && this.getTitle().toLowerCase().matches("(.*)"+title.toLowerCase()+"(.*)");
        }*/
        return match;
    }
    
    public void printItem(){
        System.out.println("Call Number: " + getCallNumber());
        System.out.println("Year: " + getYear());
        System.out.println("Title: " + getTitle());
    }
    
    public String getReferenceInfo(){
        String info = new String();
        info = "Call Number: " + getCallNumber() + "\n";
        info = info + "Year: " + getYear() + "\n";
        info = info + "Title: " + getTitle() + "\n";
        return info;
    }

}
