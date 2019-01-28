/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assgn3;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
/**
 *
 * @author Joseph
 */
public class NewMain extends JFrame implements ActionListener {
    public static String callNumber = "";
    public static String title = "";
    public static int startYear;
    public static int endYear;

    private static int context = 1;
    private static HashMap<String, ArrayList> referenceMap = new HashMap<>();
    private static LibrarySearch libsrc;
    
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int MENU_HEIGHT = 50;
    private static final int MESSAGE_HEIGHT = 200;
    private static final int BUTTON_WIDTH = 150;
    private static final int labelGap = 10;
    private static final int posOffset = 20;
    private static String type = "";
    private static String author = "";
    private static String publisher = "";
    private static String organization = "";
    private static int year = 0;
    private static int userControl = 0;
    private static int position = 0;
   
    private static JTextArea fieldMessage;
    private static JTextArea fieldResult;

    private JScrollPane messageScrollPane;
    private JScrollPane resultScrollPane;
    private JPanel panelMaster;
    private JPanel panelWelcome;
    private JPanel panelButton;
    private JPanel panelMessage;
    private JPanel panelResult;
    private JPanel panelInputSection;
    private JPanel panelOutputSection;
    private JPanel panelAddReference;
    private JPanel panelSearchReference;
    
    private JPanel panelMessageCaption;
    private JPanel panelResultCaption;
    private JPanel panelAddCaption;
    private JPanel panelSearchCaption;
    private JPanel panelType;
    private JPanel panelCallNo;
    private JPanel panelAuthor;
    private JPanel panelTitle;
    private JPanel panelPublisher;
    private JPanel panelYear;
    private JPanel panelOrganization;
    private JPanel panelTitleKey;
    private JPanel panelStartYear;
    private JPanel panelEndYear;

    private JLabel labelAddReference;
    private JLabel labelSearchReference;
    private JLabel labelType;
    private JLabel labelCallNo;
    private JLabel labelAuthor;
    private JLabel labelTitle;
    private JLabel labelPublisher;
    private JLabel labelYear;
    private JLabel labelOrganization;
    private JLabel labelTitleKey;
    private JLabel labelStartYear;
    private JLabel labelEndYear;
    private JLabel labelMessage;
    private JLabel labelResult;
    
    private JButton searchButton;
    private JButton addButton;
    private JButton resetButton;

    private JTextArea textHelp;
    private JTextField fieldCallNo;
    private JTextField fieldAuthor;
    private JTextField fieldTitle;
    private JTextField fieldTitleKey;
    private JTextField fieldPublisher;
    private JTextField fieldYear;
    private JTextField fieldOrganization;
    private JTextField fieldStartYear;
    private JTextField fieldEndYear;
    private JComboBox typeList;
    
    private JMenuBar menubar;
    private JMenu command;
    private JMenuItem itemAdd;
    private JMenuItem itemSearch;
    private JMenuItem itemQuit;
    
    private static void createNewIndex(String title, int position) {
        StringTokenizer tokenlist = new StringTokenizer(title);
        ArrayList indexList;
        String key;

        while(tokenlist.hasMoreTokens()) { 
            key = tokenlist.nextToken().toLowerCase(); 
            if (referenceMap.containsKey(key)) {
                indexList = referenceMap.get(key);
            } else {
                indexList = new ArrayList();
            }
            indexList.add(position);
            referenceMap.put(key, indexList);
        };
    }
    
    private static ArrayList getIndexList(String title) {
        StringTokenizer tokenlist = new StringTokenizer(title);
        ArrayList positionList = new ArrayList();
        ArrayList indexList = new ArrayList();
        Iterator itr;
        String key;
        int pos = 0;
        
        while(tokenlist.hasMoreTokens()) { 
            key = tokenlist.nextToken().toLowerCase(); 
            if (referenceMap.containsKey(key)) {
                indexList.addAll(referenceMap.get(key));
            }
            if (positionList.isEmpty()) {
                positionList.addAll(indexList);
            } else {
                while (pos < positionList.size()) {
                    if (!indexList.contains(positionList.get(pos))) {
                        positionList.remove(pos);
                    }
                    pos++;
                }
            }
            indexList.clear();
        };
        return positionList;
    }
    
    private static void importDataBase(String inFileName) {
        BufferedReader bufferedReader;
        FileReader fileReader;
        Book newBook;
        Journal newJournal;
        String[] tokens = {"", ""};
        String line;
        String attribute;
        StringTokenizer tokenlist;
        int attributeCount = 0;

        try {
            fileReader = new FileReader(inFileName);
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                if (false == line.isEmpty()) {
                    tokenlist = new StringTokenizer(line, "=");
                    attribute = tokenlist.nextToken().trim();
                    if (attribute.equalsIgnoreCase("type")) {
                        type = tokenlist.nextToken().trim().replace("\"","");
                        attributeCount = 1;
                    } else if (attribute.equalsIgnoreCase("callNumber")) {
                        callNumber = tokenlist.nextToken().trim().replace("\"","");
                        attributeCount += 1;
                    } else if (attribute.equalsIgnoreCase("authors")) {
                        author = tokenlist.nextToken().trim().replace("\"","");
                        attributeCount += 1;
                    } else if (attribute.equalsIgnoreCase("title")) {
                        title = tokenlist.nextToken().trim().replace("\"","");
                        attributeCount += 1;
                    } else if (attribute.equalsIgnoreCase("publisher")) {
                        publisher = tokenlist.nextToken().trim().replace("\"","");
                        attributeCount += 1;
                    } else if (attribute.equalsIgnoreCase("year")) {
                        year = Integer.parseInt(tokenlist.nextToken().trim().replace("\"",""));
                        attributeCount += 1;
                    } else if (attribute.equalsIgnoreCase("organization")) {
                        organization = tokenlist.nextToken().trim().replace("\"","");
                        attributeCount += 1;
                    }
                    if ((attributeCount == 6) && (type.equalsIgnoreCase("book"))) {
                        
                        newBook = new Book(callNumber, year, author, title, publisher);
                        if(libsrc.exists(newBook) == false){
                            libsrc.addBook(callNumber, year, author, title, publisher);
                            createNewIndex(title, position);
                            position += 1;
                        }
                    } else if ((attributeCount == 5) && (type.equalsIgnoreCase("journal"))) {
                        newJournal = new Journal(callNumber, year, title, organization);
                        if(libsrc.exists(newJournal) == false){
                            libsrc.addJournal(callNumber, year, title, organization);
                            createNewIndex(title, position);
                            position += 1;
                        }
                    }
                }
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + inFileName + "'");                
        } catch(IOException ex) {
            System.out.println("Error reading file '" + inFileName + "'");                  
        }
    }
    
    private static void exportDataBase(String outFileName) {
        BufferedWriter bufferedWriter;
        FileWriter fileWriter;
        int referenceCount = 0;

        Iterator itr = libsrc.getReferencelist().iterator();
        try {
            fileWriter  = new FileWriter(outFileName);
            bufferedWriter  = new BufferedWriter (fileWriter);
            while (itr.hasNext()) {
                Reference reference = (Reference)itr.next();
                if (referenceCount > 0) {
                    bufferedWriter.newLine(); 
                }
                type = reference.getType();
                bufferedWriter.write("type = ");
                bufferedWriter.write("\""+type+"\"");
                bufferedWriter.newLine();
                if (type.equalsIgnoreCase("book")) {
                    bufferedWriter.write("callnumber = ");
                    bufferedWriter.write("\""+((Book)reference).getCallNumber()+"\"");
                    bufferedWriter.newLine(); 
                    bufferedWriter.write("authors = ");
                    bufferedWriter.write("\""+((Book)reference).getAuthors()+"\"");
                    bufferedWriter.newLine(); 
                    bufferedWriter.write("title = ");
                    bufferedWriter.write("\""+((Book)reference).getTitle()+"\"");
                    bufferedWriter.newLine(); 
                    bufferedWriter.write("publisher = ");
                    bufferedWriter.write("\""+((Book)reference).getPublisher()+"\"");
                    bufferedWriter.newLine(); 
                    bufferedWriter.write("year = ");
                    bufferedWriter.write("\""+String.valueOf(((Book)reference).getYear())+"\"");
                    bufferedWriter.newLine(); 
                } else if (type.equalsIgnoreCase("journal")) {
                    bufferedWriter.write("callnumber = ");
                    bufferedWriter.write("\""+((Journal)reference).getCallNumber()+"\"");
                    bufferedWriter.newLine(); 
                    bufferedWriter.write("title = ");
                    bufferedWriter.write("\""+((Journal)reference).getTitle()+"\"");
                    bufferedWriter.newLine(); 
                    bufferedWriter.write("organization = ");
                    bufferedWriter.write("\""+((Journal)reference).getOrganization()+"\"");
                    bufferedWriter.newLine(); 
                    bufferedWriter.write("year = ");
                    bufferedWriter.write("\""+String.valueOf(((Journal)reference).getYear())+"\"");
                    bufferedWriter.newLine(); 
                }
                referenceCount += 1;
            }
            bufferedWriter.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + outFileName + "'");                
        } catch(IOException ex) {
            System.out.println("Error reading file '" + outFileName + "'");                  
        }
    }
    
    private static void processRequest() {
        Book newBook;
        Journal newJournal;

        while (userControl != 3) {
            if (userControl == 1){
                if (type.equalsIgnoreCase("book")){
                    newBook = new Book(callNumber, year, author, title, publisher);
            
                    if(libsrc.exists(newBook) == false){
                        libsrc.addBook(callNumber, year, author, title, publisher);
                        createNewIndex(title, position);
                        position += 1;
                    }
                } else if(type.equalsIgnoreCase("journal")){
                    newJournal = new Journal(callNumber, year, title, organization);
                    if(libsrc.exists(newJournal) == false){
                        libsrc.addJournal(callNumber, year, title, organization);
                        createNewIndex(title, position);
                        position += 1;
                    }
                }
                userControl = 0;
            }
            else if (userControl == 2){
                if (title.isEmpty()) {
                    fieldResult.append(libsrc.searchReference(callNumber, startYear, endYear));
                } else {
                    fieldResult.append(libsrc.searchReference(callNumber, startYear, endYear, title, getIndexList(title)));
                }
                userControl = 0;
            }
            try{
                Thread.currentThread().sleep(1000);//sleep for 1000 ms
            } catch(InterruptedException ie){
                 //If this thread was interrupted by another thread 
            }
        }
    }
    
    private static void setUserControl(int setValue) {
        userControl = setValue;
    }
    
    private static void constructUserInterface() {
        NewMain gui = new NewMain("Library Search");
        gui.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        gui.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent windowEvent){
                setUserControl(3);
            }        
        });    
        gui.createWindowContents();
        gui.setPreferredSize(new Dimension(WIDTH, HEIGHT-MESSAGE_HEIGHT-posOffset));
        gui.pack();
        gui.setVisible(true);
    }
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        libsrc = new LibrarySearch();
        String inputfile = "";
        String outputfile = "";
        
        if ((args.length == 0) || (args.length > 2)) {
            System.out.println("Must specify the output file. Specification of input file is optional");
        } else if (args.length == 2) {
            inputfile = args[0];
            outputfile = args[1];
            importDataBase(inputfile);
        } else {   
            outputfile = args[0];
        }
        if (!outputfile.isEmpty()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    constructUserInterface();
                }
            });
            processRequest();
            exportDataBase(outputfile);
        }
        System.out.println("Library Search Application Exited");
        System.exit(0);
    }

    public NewMain(){
        super();
    }

    public NewMain(String title){
        super(title);
    }

    private void createLabelField(JPanel container, JLabel label, 
                                  JTextField field, int labelWidth, 
                                  int labelHeight, int fieldWidth, 
                                  int fieldHeight, int gap) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        
        label.setPreferredSize(new Dimension(labelWidth, labelHeight));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        field.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.addActionListener(this);
        
        container.add(Box.createRigidArea(new Dimension(labelGap, 0)));
        container.add(label);
        container.add(Box.createRigidArea(new Dimension(labelGap, 0)));
        container.add(field);
        container.add(Box.createHorizontalGlue());
    }
    
    private void createLabelComboBox(JPanel container, JLabel label, 
                                     JComboBox field, int labelWidth, 
                                     int labelHeight, int fieldWidth, 
                                     int fieldHeight, int gap) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        
        label.setPreferredSize(new Dimension(labelWidth, labelHeight));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        field.setSelectedIndex(0);
        field.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.addActionListener(this);
        
        container.add(Box.createRigidArea(new Dimension(labelGap, 0)));
        container.add(label);
        container.add(Box.createRigidArea(new Dimension(labelGap, 0)));
        container.add(field);
        container.add(Box.createHorizontalGlue());
    }
    
    private void createCaption(JPanel container, JLabel label, 
                               int captionWidth, int captionHeight, 
                               int padding, int gap) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        label.setPreferredSize(new Dimension(captionWidth, captionHeight));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        container.add(Box.createRigidArea(new Dimension(labelGap, 0)));
        container.add(label);
        container.add(Box.createRigidArea(new Dimension(padding-captionWidth-labelGap, 0)));
        container.add(Box.createHorizontalGlue());
    }
    
    protected void createWindowContents() {
        int offset = 5;
        int shiftup = 0;
        int captionWidth = 200;
        int captionHeight = 20;
        int comboWidth = 30;
        int comboHeight = 20;
        int labelWidth = 90;
        int labelHeight = 20;
        int fieldWidth = 300;
        int fieldHeight = 20;
        int buttonWidth = 300;
        int buttonHeight = 30;
        String[] typeItems = { "book", "journal"};
        
        menubar = new JMenuBar();
        command = new JMenu("Commands");
        
        itemAdd = new JMenuItem();
        itemAdd.setText("Add");
        itemAdd.setActionCommand("Load_Add_Reference");
        itemAdd.addActionListener( this );
        
        itemSearch = new JMenuItem();
        itemSearch.setText("Search");
        itemSearch.setActionCommand("Load_Search_Reference");
        itemSearch.addActionListener( this );

        itemQuit = new JMenuItem();
        itemQuit.setText("Quit");
        itemQuit.setActionCommand("Quit");
        itemQuit.addActionListener( this );
        
        command.add( itemAdd );
        command.add( itemSearch );
        command.add( itemQuit );

        menubar.add(command);
             
        panelMaster = new JPanel();
        panelMaster.setPreferredSize(new Dimension(HEIGHT-MENU_HEIGHT, WIDTH));

        panelWelcome = new JPanel();
        panelWelcome.setPreferredSize(new Dimension(HEIGHT-MENU_HEIGHT, WIDTH));
        
        panelAddReference = new JPanel();
        panelAddReference.setPreferredSize(new Dimension(HEIGHT-MENU_HEIGHT-MESSAGE_HEIGHT, WIDTH-BUTTON_WIDTH));
        
        panelSearchReference = new JPanel();
        panelSearchReference.setPreferredSize(new Dimension(HEIGHT-MENU_HEIGHT-MESSAGE_HEIGHT, WIDTH-BUTTON_WIDTH));

        panelButton = new JPanel();
        panelButton.setPreferredSize(new Dimension(HEIGHT-MENU_HEIGHT-MESSAGE_HEIGHT, BUTTON_WIDTH));

        panelInputSection = new JPanel();
        panelInputSection.setPreferredSize(new Dimension(HEIGHT-MENU_HEIGHT-MESSAGE_HEIGHT, WIDTH));
        
        panelOutputSection = new JPanel();
        panelOutputSection.setPreferredSize(new Dimension(MESSAGE_HEIGHT, WIDTH));

        panelMessage = new JPanel();
        panelMessage.setPreferredSize(new Dimension(MESSAGE_HEIGHT, WIDTH));
        
        panelResult = new JPanel();
        panelResult.setPreferredSize(new Dimension(MESSAGE_HEIGHT, WIDTH));
        
        textHelp = new JTextArea("\n\n\nWelcome to Library Search\n\n" +
                                 "Choose a command from the \"Commands\" menu above for\n" +
                                 "adding a reference, searching reference, or quitting the\n" +
                                 "program.");
        textHelp.setPreferredSize(new Dimension(WIDTH, HEIGHT-MENU_HEIGHT));
        textHelp.setEditable(false);
        
        panelAddCaption = new JPanel();
        labelAddReference = new JLabel("Adding Reference");
        createCaption(panelAddCaption, labelAddReference, captionWidth,
                      captionHeight, WIDTH-BUTTON_WIDTH, labelGap);
        
        panelSearchCaption = new JPanel();
        labelSearchReference = new JLabel("Searching references");
        createCaption(panelSearchCaption, labelSearchReference, captionWidth,
                      captionHeight, WIDTH-BUTTON_WIDTH, labelGap);
        
        panelMessageCaption = new JPanel();
        labelMessage = new JLabel("Messages");
        createCaption(panelMessageCaption, labelMessage, captionWidth,
                      captionHeight, WIDTH-BUTTON_WIDTH, labelGap);
        
        panelResultCaption = new JPanel();
        labelResult = new JLabel("Search Results");
        createCaption(panelResultCaption, labelResult, captionWidth,
                      captionHeight, WIDTH-BUTTON_WIDTH, labelGap);
        
        panelType = new JPanel();
        labelType = new JLabel("Type");
        typeList = new JComboBox(typeItems);
        typeList.setSelectedItem("book");
        typeList.setActionCommand("Select");
        createLabelComboBox(panelType, labelType, typeList, labelWidth, 
                            labelHeight, comboWidth, comboHeight, labelGap);

        panelCallNo = new JPanel();
        labelCallNo = new JLabel("Call No:");
        fieldCallNo = new JTextField();
        createLabelField(panelCallNo, labelCallNo, fieldCallNo, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);
        
        panelAuthor = new JPanel();
        labelAuthor = new JLabel("Authors:");
        fieldAuthor = new JTextField();
        createLabelField(panelAuthor, labelAuthor, fieldAuthor, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);
        
        panelTitle = new JPanel();
        labelTitle = new JLabel("Title:");
        fieldTitle = new JTextField();
        createLabelField(panelTitle, labelTitle, fieldTitle, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);
        
        panelPublisher = new JPanel();
        labelPublisher = new JLabel("Publisher:");
        fieldPublisher = new JTextField();
        createLabelField(panelPublisher, labelPublisher, fieldPublisher, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);
        
        panelYear = new JPanel();
        labelYear = new JLabel("Year:");
        fieldYear = new JTextField();
        createLabelField(panelYear, labelYear, fieldYear, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);

        panelOrganization = new JPanel();
        labelOrganization = new JLabel("Organization:");
        fieldOrganization = new JTextField();
        createLabelField(panelOrganization, labelOrganization, fieldOrganization, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);

        panelTitleKey = new JPanel();
        labelTitleKey = new JLabel("Title Keywords:");
        fieldTitleKey = new JTextField();
        createLabelField(panelTitleKey, labelTitleKey, fieldTitleKey, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);

        panelStartYear = new JPanel();
        labelStartYear = new JLabel("Start Year:");
        fieldStartYear = new JTextField();
        createLabelField(panelStartYear, labelStartYear, fieldStartYear, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);

        panelEndYear = new JPanel();
        labelEndYear = new JLabel("End Year:");
        fieldEndYear = new JTextField();
        createLabelField(panelEndYear, labelEndYear, fieldEndYear, labelWidth, 
                         labelHeight, fieldWidth, fieldHeight, labelGap);

        fieldMessage = new JTextArea();
        fieldMessage.setPreferredSize(new Dimension(WIDTH, MESSAGE_HEIGHT));

        fieldResult = new JTextArea();
        fieldResult.setPreferredSize(new Dimension(WIDTH, MESSAGE_HEIGHT));

        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setActionCommand("Reset");
        resetButton.addActionListener(this);

        addButton = new JButton(" Add ");
        addButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setActionCommand("Add");
        addButton.addActionListener(this);

        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setActionCommand("Search");
        searchButton.addActionListener(this);
        
        messageScrollPane = buildScrollPane(panelMessage, panelMessageCaption, fieldMessage);
        resultScrollPane = buildScrollPane(panelResult, panelResultCaption, fieldResult);
        buildMasterPanel(panelMaster, context);
        
        this.setJMenuBar(menubar);
        this.getContentPane().add(panelMaster);
    }
    
    private void buildDefaultPanel(JPanel panel) {
        // Construct the window with welcome message and help text
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.white);
        panel.add(Box.createRigidArea(new Dimension(posOffset, 0)));
        panel.add(textHelp);
    }
    
    private void buildAddReferenceButtonPanel(JPanel panel) {
        // Construct button section of Add Reference Window
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.removeAll();
        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(resetButton);
        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(addButton);
        panel.revalidate();
        panel.repaint();
    }
    
    private void buildSearchReferenceButtonPanel(JPanel panel) {
        // Construct button section of Search Reference Window
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.removeAll();
        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(resetButton);
        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(searchButton);
        panel.revalidate();
        panel.repaint();
    }
    
    private void buildAddBookPanel(JPanel panel, int posOffset) {
        // Construct input fields section of Add Book Window
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.removeAll();
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelAddCaption);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelType);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelCallNo);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelAuthor);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelPublisher);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelTitle);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelYear);
        panel.revalidate();
        panel.repaint();
    }
    
    private void buildAddJournalPanel(JPanel panel, int posOffset) {
        // Construct input fields section of Add Journal Window
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.removeAll();
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelAddCaption);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelType);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelCallNo);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelOrganization);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelTitle);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelYear);
        panel.add(Box.createVerticalGlue());
        panel.revalidate();
        panel.repaint();
    }
    
    private void buildSearchPanel(JPanel panel, int posOffset) {
        // Construct input fields section of Search Reference Window
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.removeAll();
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelSearchCaption);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelCallNo);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelTitleKey);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelStartYear);
        panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
        panel.add(panelEndYear);
        panel.add(Box.createVerticalGlue());
        panel.revalidate();
        panel.repaint();
    }

    private void resetAddReferenceFields() {
        // Clear input fields of Add Book or Journal Window
        fieldCallNo.setText("");
        fieldAuthor.setText("");
        fieldTitle.setText("");
        fieldPublisher.setText("");
        fieldOrganization.setText("");
        fieldYear.setText("");
        fieldMessage.setText("");
    }
    
    private void resetSearchReferenceFields() {
        // Clear input fields of Search Reference Window
        fieldCallNo.setText("");
        fieldTitleKey.setText("");
        fieldStartYear.setText("");
        fieldEndYear.setText("");
        fieldMessage.setText("");
    }
    
    private JScrollPane buildScrollPane(JPanel container, JPanel panel, JTextArea message) {
        // Construct Message box of Add Book or Journal Window
        JScrollPane scrollPane;
        
        scrollPane = new JScrollPane(message, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(panel);
        container.add(scrollPane);
        return scrollPane;
    }
    
    private void buildOutputSectionPanel(JPanel panel, int style) {
        // Construct Output section (Message or Search Result) of the Window Frame
        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createRigidArea(new Dimension(labelGap, 0)));
        if (style==2) {
            panel.add(panelMessage);
        } else {
            panel.add(panelResult);
        }
        panel.add(Box.createRigidArea(new Dimension(labelGap, 0)));
        panel.revalidate();
        panel.repaint();
    }
    
    private void buildInputSectionPanel(JPanel panel, int style) {
        // Construct Input section (Add or Search Section with buttons) of the Window Frame
        panel.removeAll();
        if (style==2) {
            resetAddReferenceFields();
            buildAddBookPanel(panelAddReference, posOffset);
            buildAddReferenceButtonPanel(panelButton);
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(panelAddReference);
            panel.add(panelButton);
        } else if (style==3) {
            resetSearchReferenceFields();
            buildSearchPanel(panelSearchReference, ((int)2.5*posOffset));
            buildSearchReferenceButtonPanel(panelButton);
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(panelSearchReference);
            panel.add(panelButton);
        }
        panel.revalidate();
        panel.repaint();
    }
    
    private void buildMasterPanel(JPanel panel, int style) {
        // Construct the entire Window Frame based on command selection
        panel.removeAll();
        if (style==1) {
            buildDefaultPanel(panelWelcome);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(panelWelcome);
        } else {
            buildInputSectionPanel(panelInputSection, style);
            buildOutputSectionPanel(panelOutputSection, style);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(panelInputSection);
            panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
            panel.add(panelOutputSection);
            panel.add(Box.createRigidArea(new Dimension(0, posOffset)));
            panel.add(Box.createVerticalGlue());
        } 
        panel.revalidate();
        panel.repaint();
    }
    

    /**
     * @param args the command line arguments
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        int abort = 0;
        
        if(ae.getActionCommand().equalsIgnoreCase("Add")){
            callNumber = fieldCallNo.getText();
            title = fieldTitle.getText();
            type = typeList.getSelectedItem().toString();
            try {
                year = Integer.parseInt(fieldYear.getText(), 10);
            } catch (NumberFormatException e) {
                year = -1;
            }
            if(type.equals("book")){
                author = fieldAuthor.getText();
                publisher = fieldPublisher.getText();
                if (callNumber.isEmpty()) {
                    fieldMessage.append("Call Number of the book must be entered\n");
                    abort = 1;
                }
                if (author.isEmpty()) {
                    fieldMessage.append("Author of the book must be entered\n");
                    abort = 1;
                }
                if (title.isEmpty()) {
                    fieldMessage.append("Title of the book must be entered\n");
                    abort = 1;
                }
                if (publisher.isEmpty()) {
                    fieldMessage.append("Publisher of the book must be entered\n");
                    abort = 1;
                }
                if (fieldYear.getText().isEmpty()) {
                    fieldMessage.append("Year of the book must be entered\n");
                    abort = 1;
                }
            }
            else{
                organization = fieldOrganization.getText();
                if (callNumber.isEmpty()) {
                    fieldMessage.append("Call Number of the journal must be entered\n");
                    abort = 1;
                }
                if (title.isEmpty()) {
                    fieldMessage.append("Title of the journal must be entered\n");
                    abort = 1;
                }
                if (organization.isEmpty()) {
                    fieldMessage.append("Organization of the journal must be entered\n");
                    abort = 1;
                }
                if (fieldYear.getText().isEmpty()) {
                    fieldMessage.append("Year of the journal must be entered\n");
                    abort = 1;
                }
            }
            if (year == -1) {
                fieldMessage.append("The year entered, " + fieldYear.getText() + "  is invalid year format\n");
                abort = 1;
            } else if ((year < 1000) || (year > 9999)) {
                if (!fieldYear.getText().isEmpty()) {
                    fieldMessage.append("The year entered, " + fieldYear.getText() + " must be between 1000 and 9999\n");
                }
                abort = 1;
            }
            if (abort==0) {
                setUserControl(1);
            }
        } else if(ae.getActionCommand().equalsIgnoreCase("Search")){
            callNumber = fieldCallNo.getText();
            title = fieldTitleKey.getText();
            try {
                startYear = Integer.parseInt(fieldStartYear.getText(), 10);
            } catch (NumberFormatException e) {
                if (fieldStartYear.getText().isEmpty()) {
                    startYear = 0;
                } else {
                    startYear = -1;
                }
            }
            try {
                endYear = Integer.parseInt(fieldEndYear.getText(), 10);
            } catch (NumberFormatException e) {
                if (fieldEndYear.getText().isEmpty()) {
                    endYear = 0;
                } else {
                    endYear = -1;
                }
            }
            if (startYear == -1) {
                fieldMessage.append("The start year entered, " + fieldStartYear.getText() + "  is invalid year format\n");
            } else if (endYear == -1) {
                fieldMessage.append("The end year entered, " + fieldEndYear.getText() + "  is invalid year format\n");
            } else if ((startYear < 1000) || (startYear > 9999)) {
                if (!fieldStartYear.getText().isEmpty()) {
                    fieldMessage.append("The start year entered, " + fieldStartYear.getText() + " must be between 1000 and 9999\n");
                }
                else {
                    setUserControl(2);
                }
            } else if ((endYear < 1000) || (endYear > 9999)) {
                if (!fieldEndYear.getText().isEmpty()) {
                    fieldMessage.append("The end year entered, " + fieldEndYear.getText() + " must be between 1000 and 9999\n");
                }
                else {
                    setUserControl(2);
                }
            } else {
                setUserControl(2);
            }
        } else if(ae.getActionCommand().equalsIgnoreCase("Reset")){
            resetAddReferenceFields();
            resetSearchReferenceFields();
        } else if(ae.getActionCommand().equalsIgnoreCase("Select")){
            type = typeList.getSelectedItem().toString();
            if (context==2) {
                resetAddReferenceFields();
                if (type.equalsIgnoreCase("book")) {
                    buildAddBookPanel(panelAddReference, posOffset);
                } else {
                    buildAddJournalPanel(panelAddReference, posOffset);
                }
            }               
            System.out.println("Type = " + type);
        } else if(ae.getActionCommand().equalsIgnoreCase("Load_Add_Reference")){
            context = 2;
            buildMasterPanel(panelMaster, context);
        } else if(ae.getActionCommand().equalsIgnoreCase("Load_Search_Reference")){
            context = 3;
            buildMasterPanel(panelMaster, context);
        } else if(ae.getActionCommand().equalsIgnoreCase("Quit")){
            setUserControl(3);
        }
        if (context>1) {
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.pack();
        }
    }
}