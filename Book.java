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
public class Book extends Reference {
    private String authors;
    private String publisher;
    
    public Book(){
        authors = "";
        publisher = "";
        
    }
    
    public Book(String callNumber, int year, String authors, String title, String publisher){
        super(callNumber, year, title, "book");
        this.authors = authors;
        this.publisher = publisher;
        
    }
    
    public String getAuthors(){
        
        return authors;
    }
    
    public String getPublisher(){
        return publisher;
    }
    
    boolean equals(Book anotherBook) {
        boolean same = super.equals(anotherBook);
        
        same = same && (anotherBook.getAuthors().equalsIgnoreCase(this.getAuthors()));
        same = same && (anotherBook.getPublisher().equalsIgnoreCase(this.getPublisher()));

        return same;
    }

    boolean hasMatch(String callNumber, int startYear, int endYear, String authors, String title, String publisher) {
        boolean match = super.hasMatch(callNumber, startYear, endYear, title);
        
        if (!(authors.isEmpty() || isNull(authors))) {
            match = match && (authors.equalsIgnoreCase(this.getAuthors()));
        }
        if (!(publisher.isEmpty() || isNull(publisher))) {
            match = match && (publisher.equalsIgnoreCase(this.getPublisher()));
        }
        return match;
    }

    public void printItem(){
        System.out.println("Call Number: " + getCallNumber());
        System.out.println("Year: " + getYear());
        System.out.println("Title: " + getTitle());
        System.out.println("Authors: " + getAuthors());
        System.out.println("Publisher: " + getPublisher());
    }

    public String getBookInfo(){
        String info = new String();
        info = "Call Number: " + getCallNumber() + "\n";
        info = info + "Authors: " + getAuthors() + "\n";
        info = info + "Title: " + getTitle() + "\n";
        info = info + "Publisher: " + getPublisher() + "\n";
        info = info + "Year: " + getYear() + "\n";
        return info;
    }
}
