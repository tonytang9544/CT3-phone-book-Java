package ct3PhoneBook.userInterface.GUI.mainWindow;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.fileLoaderSaver.VcfExporter;
import ct3PhoneBook.fileLoaderSaver.VcfParser;
import ct3PhoneBook.userInterface.GUI.addEntryWindow.AddEntryWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

public class DrawMainWindow extends JFrame {
    private static final int MAINWINDOW_WIDTH = 400;
    private static final int MAINWINDOW_HEIGHT = 400;

    private final JButton addEntryButton;
    private final JPanel contentsPanel;
    private final JPanel centralPanel;
    private final JCheckBox selectAllCheck;
    private JMenuItem exportFileMenuItem;
    private JMenuItem exportSelectedEntries;
    private JMenuItem batchDelete;
    private JMenuItem sortByName;
    private JMenuItem sortByPhoneNumber;
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
        JMenu contactsMenu = new JMenu("Contacts");
        menuBar.add(fileMenu);
        menuBar.add(contactsMenu);

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
                handleExport(DrawMainWindow.this.contactList);
            }
        });

        this.exportSelectedEntries = fileMenu.add("Export selected entries to vCard...");
        this.exportSelectedEntries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ContactList selectedList = getAllSelectedEntries();
                if (selectedList.getNumberOfEntries() > 0) {
                    handleExport(selectedList);
                }
            }
        });

        JMenuItem exit = fileMenu.add("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DrawMainWindow.this.dispatchEvent(
                        new WindowEvent(DrawMainWindow.this,
                                WindowEvent.WINDOW_CLOSING));
            }
        });

        JMenuItem inverseSelect = contactsMenu.add("Inverse Selection");
        inverseSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for(Component i : DrawMainWindow.this.contentsPanel.getComponents()) {
                    if (i instanceof EntryPanel) {
                        ((EntryPanel) i).setSelected(!((EntryPanel) i).isSelected());
                    }
                }
            }
        });

        this.batchDelete = contactsMenu.add("Delete All Selected");
        this.batchDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ContactList listToDelete = getAllSelectedEntries();
                int answer = JOptionPane.showConfirmDialog(
                        DrawMainWindow.this,
                        "Are you sure to delete "
                                + listToDelete.getNumberOfEntries()
                                + " Entries?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                if (answer == 0) {
                    int numberToDelete = listToDelete.getNumberOfEntries();
                    for (int i = 0; i < numberToDelete; i++) {
                        DrawMainWindow.this.contactList.delEntry(
                                listToDelete.getEntryByIndex(i));
                    }
                    updateCentralPanel(DrawMainWindow.this.contactList);
                }
            }
        });

        this.sortByName = contactsMenu.add("Sort Entries By Name");
        this.sortByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (DrawMainWindow.this.isSearching()) {
                    DrawMainWindow.this.contactList.sortByName();
                    updateCentralPanel(DrawMainWindow.this.searchedList);
                }
                else {
                    DrawMainWindow.this.contactList.sortByName();
                    updateCentralPanel(DrawMainWindow.this.contactList);
                }
            }
        });

        this.sortByPhoneNumber = contactsMenu.add("Sort Entries By Phone Number");
        this.sortByPhoneNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (DrawMainWindow.this.isSearching()) {
                    DrawMainWindow.this.contactList.sortByPhoneNumber();
                    updateCentralPanel(DrawMainWindow.this.searchedList);
                }
                else {
                    DrawMainWindow.this.contactList.sortByPhoneNumber();
                    updateCentralPanel(DrawMainWindow.this.contactList);
                }
            }
        });
        return menuBar;
    }

    private ContactList getAllSelectedEntries() {
        ContactList selectedEntries = new ContactList();
        for(Component i : this.contentsPanel.getComponents()) {
            if (i instanceof EntryPanel && ((EntryPanel) i).isSelected()) {
                selectedEntries.addEntry(((EntryPanel) i).getPerson());
            }
        }
        return selectedEntries;
    }

    private ContactList getAllContactsPanelEntries() {
        ContactList contactsPanelEntries = new ContactList();
        for(Component i : this.contentsPanel.getComponents()) {
            if (i instanceof EntryPanel) {
                contactsPanelEntries.addEntry(((EntryPanel) i).getPerson());
            }
        }
        return contactsPanelEntries;
    }

    private void handleExport(ContactList contactList) {
        JFileChooser fileChooser = new JFileChooser();
        File fileToSave;
        fileChooser.setDialogTitle("Export");
        fileChooser.setMultiSelectionEnabled(true);
        int chooserReturn = fileChooser.showSaveDialog(this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
            if (contactList.getNumberOfEntries() > 0) {
                try{
                    VcfExporter.writeContactListToFile(contactList, fileToSave);
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

        // Update menu according to number of contacts in central panel
        boolean hasItemInCurrentContact = currentNumberOfEntries > 0;
        this.exportFileMenuItem.setEnabled(hasItemInCurrentContact);
        this.sortByName.setEnabled(hasItemInCurrentContact);
        this.sortByPhoneNumber.setEnabled(hasItemInCurrentContact);

        // Update menu according to number of selected contacts
        updateSelectionAssociatedMenu();
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

    public void updateSelectionAssociatedMenu() {
        boolean hasSelection = getAllSelectedEntries().getNumberOfEntries() > 0;
        this.exportSelectedEntries.setEnabled(hasSelection);
        this.batchDelete.setEnabled(hasSelection);
    }
}
