/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assgn3;

import static java.util.Objects.isNull;

/**
 *
 * @author Joseph
 */
public class Journal extends Reference{
    private String organization;
    
    public Journal(){
        organization = "";
        
    }
    
    public Journal(String callNumber, int year, String title, String organization){
        super(callNumber, year, title, "journal");
        this.organization = organization;
        
    }

    public String getOrganization(){
        
        return organization;
    }
    
    public boolean equals(Journal anotherJournal) {
        boolean same = super.equals(anotherJournal);
    
        same = same && (anotherJournal.getOrganization().equalsIgnoreCase(this.getOrganization()));

        return same;
    }

    public boolean hasMatch(String callNumber, int startYear, int endYear, String title, String organization) {
        boolean match = super.hasMatch(callNumber, startYear, endYear, title);
    
        if (!(organization.isEmpty() || isNull(organization))) {
           match = match && (organization.equalsIgnoreCase(this.getOrganization()));
        }
        return match;
    }
    
    public void printItem(){
        System.out.println("Call Number: " + getCallNumber());
        System.out.println("Year: " + getYear());
        System.out.println("Title: " + getTitle());
        System.out.println("Organization: " + getOrganization());
    }
    
    public String getJournalInfo(){
        String info = new String();
        info = "Call Number: " + getCallNumber() + "\n";
        info = info + "Title: " + getTitle() + "\n";
        info = info + "Organization: " + getOrganization() + "\n";
        info = info + "Year: " + getYear() + "\n";
        return info;
    }
}
