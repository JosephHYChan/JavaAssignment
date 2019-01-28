/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assgn3;

import static assgn3.NewMain.callNumber;
import static assgn3.NewMain.endYear;
import static assgn3.NewMain.startYear;
import static assgn3.NewMain.title;
import java.util.ArrayList;
import java.util.Iterator;
import static java.util.Objects.isNull;
import java.util.Vector;
import javax.swing.JTextArea;

/**
 *
 * @author Joseph
 */
public class LibrarySearch {
    private ArrayList<Reference> referencelist = new ArrayList();
    
    public LibrarySearch(){
        referencelist = new ArrayList<Reference>();
    }
    
    public ArrayList<Reference> getReferencelist() {
        return referencelist;
    }
    
    public void addBook(String callNumber, int year, String authors, String title, String publisher){
        this.referencelist.add(new Book(callNumber, year, authors, title, publisher));  
    }
    
    public void addJournal(String callNumber, int year, String title, String organization){
        this.referencelist.add(new Journal(callNumber, year, title, organization));
    }
    
    public boolean exists(Reference reference) {
        Iterator itr = this.referencelist.iterator();
        Reference existing;
        boolean found = false;
        
        while (itr.hasNext() && !found) {
            existing = (Reference)itr.next();
            if ((existing.getType().equals("book")) && (reference.getType().equals("book"))) {
                found = found || ((Book)existing).equals((Book)reference);
            } else if ((existing.getType().equals("journal")) && (reference.getType().equals("journal"))) {
                found = found || ((Journal)existing).equals((Journal)reference);
            }
        }
        return found;
    }
    
    public String searchReference(String callNumber, int startYear, int endYear){
        Reference reference;
        String result = new String();
        boolean match;
        int matchCount = 0;

        Iterator itr = this.referencelist.iterator();
        while (itr.hasNext()) {
            reference = (Reference) itr.next();
            match = reference.hasMatch(callNumber, startYear, endYear, "");
            if (match) {
                matchCount++;
                result = "Matched reference #" + String.valueOf(matchCount) + "\n";
                System.out.println("Matched book#"+ Integer.toString(matchCount));
                reference.printItem();
                if (reference.getType().equalsIgnoreCase("book")) {
                    result = result + ((Book)reference).getBookInfo();
                } else {
                    result = result + ((Journal)reference).getJournalInfo();
                }
                System.out.println();
            }
        }
        if (matchCount == 0) {
            System.out.println("No book found");
            result = "No book found";
        }
        return result;
    }
    
    public String searchReference(String callNumber, int startYear, int endYear, String title, ArrayList index){
        Reference reference;
        String result = new String();
        boolean match;
        int matchCount = 0;

        Iterator itr = index.iterator(); // this.referencelist.iterator();
        while (itr.hasNext()) {
            reference = (Reference) this.referencelist.get((int)itr.next());
            match = reference.hasMatch(callNumber, startYear, endYear, title);
            if (match) {
                matchCount++;
                result = "Matched reference #" + String.valueOf(matchCount) + "\n";
                System.out.println("Matched book#"+ Integer.toString(matchCount));
                reference.printItem();
                if (reference.getType().equalsIgnoreCase("book")) {
                    result = result + ((Book)reference).getBookInfo();
                } else {
                    result = result + ((Journal)reference).getJournalInfo();
                }
                System.out.println();
            }
        }
        if (matchCount == 0) {
            System.out.println("No book found");
            result = "No book found";
        }
        return result;
    }
}
        
    


