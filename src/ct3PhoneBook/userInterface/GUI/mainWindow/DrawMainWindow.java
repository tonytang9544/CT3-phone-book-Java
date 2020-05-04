package ct3PhoneBook.userInterface.GUI.mainWindow;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.fileLoaderSaver.VcfExporter;
import ct3PhoneBook.fileLoaderSaver.VcfParser;
import ct3PhoneBook.userInterface.GUI.addEntryWindow.AddEntryWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DrawMainWindow extends JFrame {
    private static final int MAINWINDOW_WIDTH = 400;
    private static final int MAINWINDOW_HEIGHT = 400;

    private final JButton addEntryButton;
    private final JPanel contentsPanel;
    private final JPanel centralPanel;
    private final JCheckBox selectAllCheck;
    private JMenuItem exportFileMenuItem;
    private boolean isSearching;

    private final ContactList contactList;
    private ContactList searchedList;

    public DrawMainWindow() {
        setTitle("CT3-PhoneBook-Java");
        setSize(MAINWINDOW_WIDTH, MAINWINDOW_HEIGHT);
        setResizable(false);
        addEntryButton = new JButton("Add");
        contentsPanel = new JPanel();
        centralPanel = new JPanel();
        selectAllCheck = new JCheckBox();
        isSearching = false;
        contactList = new ContactList();
        // Creating menu bar
        this.setJMenuBar(createMenuBar(this));

        // Add components to window
        drawTopPanel();
        drawCentralPanel();

    }

    private void performAddEntryButtonAction() {
        this.addEntryButton.setText("...");
        this.addEntryButton.setEnabled(false);
        AddEntryWindow.start(this, null);
    }

    public JButton getAddEntryButton() {
        return this.addEntryButton;
    }

    private JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem importFileMenuItem = fileMenu.add("Import from vCard...");
        importFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                handleImport();
                updateCentralPanel(DrawMainWindow.this.contactList);
            }
        });

        this.exportFileMenuItem = fileMenu.add("Export current list to vCard...");
        this.exportFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                handleExport();
            }
        });

        JMenuItem exit = fileMenu.add("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        return menuBar;
    }

    private void handleExport() {
        JFileChooser fileChooser = new JFileChooser();
        File fileToSave;
        fileChooser.setDialogTitle("Export");
        fileChooser.setMultiSelectionEnabled(true);
        int chooserReturn = fileChooser.showSaveDialog(this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
            if (this.contactList.getNumberOfEntries() > 0) {
                try{
                    VcfExporter.writeContactListToFile(this.contactList, fileToSave);
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        }
    }

    private void handleImport () {
        JFileChooser fileChooser = new JFileChooser();
        File[] filesToImport;
        fileChooser.setDialogTitle("Select vCard file");
        fileChooser.setMultiSelectionEnabled(true);
        int chooserReturn = fileChooser.showOpenDialog(this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION) {
            filesToImport = fileChooser.getSelectedFiles();
            int totalImportedContactNumber = 0;
            for (File f : filesToImport) {
                try {
                    ContactList contactsInOneFile
                            = VcfParser.parseVcf(f);
                    int numberOfEntryInOneFile
                            = contactsInOneFile.getNumberOfEntries();
                    totalImportedContactNumber += numberOfEntryInOneFile;
                    if (numberOfEntryInOneFile >= 0) {
                        for (int i = 0; i < numberOfEntryInOneFile; i++) {
                            this.contactList
                                    .addEntry(contactsInOneFile
                                            .getEntryByIndex(i));
                        }
                    }

                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        }
    }

    public void updateCentralPanel(ContactList contacts) {
        int currentNumberOfEntries
                = contacts.getNumberOfEntries();
        this.contentsPanel.removeAll();
        this.centralPanel.revalidate();
        this.centralPanel.repaint();
        if (currentNumberOfEntries > 0) {
            this.contentsPanel.setLayout(
                    new GridLayout(currentNumberOfEntries, 1));
            for (int i = 0; i < currentNumberOfEntries; i++) {
                this.contentsPanel.add(new EntryPanel(this,
                        contacts.getEntryByIndex(i)
                ));
            }
        }
        else {
            this.contentsPanel.add(new JLabel("No contacts."));
        }
        // repaint is important!!!
        this.centralPanel.revalidate();
        this.centralPanel.repaint();
        this.exportFileMenuItem.setEnabled(currentNumberOfEntries > 0);
    }

    private void drawTopPanel() {
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        JPanel topPanel = new JPanel();
        JPanel upperTopPanel = new JPanel();
        upperTopPanel.add(searchField);
        upperTopPanel.add(searchButton);
        upperTopPanel.add(this.addEntryButton);
        JPanel lowerTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowerTopPanel.add(this.selectAllCheck);
        lowerTopPanel.add(new JLabel("Select/Unselect all"));
        topPanel.setLayout(new GridLayout(2,1));
        topPanel.add(upperTopPanel);
        topPanel.add(lowerTopPanel);

        // Associate actions
        this.addEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                performAddEntryButtonAction();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String searchText = searchField.getText();

                if (searchText.equals("")) {
                    DrawMainWindow.this.updateCentralPanel(DrawMainWindow.this.contactList);
                    DrawMainWindow.this.isSearching = false;
                }
                else {
                    DrawMainWindow.this.searchedList = DrawMainWindow.this.contactList
                            .findEntryByString(searchText);
                    DrawMainWindow.this.updateCentralPanel(DrawMainWindow.this.searchedList);
                    DrawMainWindow.this.isSearching = true;

                }
            }
        });

        selectAllCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (DrawMainWindow.this.contentsPanel.getComponents().length > 0) {
                    for (Component i : DrawMainWindow.this.contentsPanel.getComponents()) {
                        if (i instanceof EntryPanel) {
                            ((EntryPanel) i).setSelected(DrawMainWindow.this.selectAllCheck.isSelected());
                        }
                    }
                }
            }
        });

        this.add(topPanel, BorderLayout.NORTH);
    }

    private void drawCentralPanel() {

        JScrollPane contactScroll = new JScrollPane(this.contentsPanel);
        contactScroll.setPreferredSize(new Dimension(380, 260));
        contactScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contactScroll.getVerticalScrollBar().setUnitIncrement(16);
        updateCentralPanel(this.contactList);
        this.centralPanel.add(contactScroll, BorderLayout.CENTER);
        this.add(this.centralPanel, BorderLayout.CENTER);
    }

    public ContactList getContactList() {
        return this.contactList;
    }

    public ContactList getSearchedList() {
        return this.searchedList;
    }

    public boolean isSearching() {
        return isSearching;
    }
}
