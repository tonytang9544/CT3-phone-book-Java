package ct3PhoneBook.userInterface.GUI.mainWindow;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.fileLoaderSaver.VcfExporter;
import ct3PhoneBook.fileLoaderSaver.VcfParser;
import ct3PhoneBook.userInterface.GUI.entryWindow.EntryWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class DrawMainWindow extends JFrame {
    private static final int MAINWINDOW_WIDTH = 400;
    private static final int MAINWINDOW_HEIGHT = 450;

    private final JButton addEntryButton;
    private final JPanel contentsPanel;
    private final JPanel centralPanel;
    private final JCheckBox selectAllCheck;
    private final JLabel statusLabel;
    private JTextField searchField;
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
        statusLabel = new JLabel();
        // Creating menu bar
        this.setJMenuBar(createMenuBar());

        // Add components to window
        drawTopPanel();
        drawCentralPanel();
        drawStatusBar();

    }

    public JButton getAddEntryButton() {
        return this.addEntryButton;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu contactsMenu = new JMenu("Contacts");
        menuBar.add(fileMenu);
        menuBar.add(contactsMenu);

        JMenuItem importFileMenuItem = fileMenu.add("Import from vCard...");
        importFileMenuItem.addActionListener(actionEvent -> handleImport());

        this.exportFileMenuItem = fileMenu.add("Export current list to vCard...");
        this.exportFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ContactList listToExport;
                if (isSearching) {
                    listToExport = DrawMainWindow.this.searchedList;
                }
                else {
                    listToExport = DrawMainWindow.this.contactList;
                }
                handleExport(listToExport);
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
                handleDelete(listToDelete);
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
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        JPanel topPanel = new JPanel();
        JPanel upperTopPanel = new JPanel();
        upperTopPanel.add(searchField);
        upperTopPanel.add(searchButton);
        upperTopPanel.add(this.addEntryButton);
        JPanel lowerTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowerTopPanel.add(this.selectAllCheck);
        lowerTopPanel.add(new JLabel("Select/Unselect all"));
        lowerTopPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (DrawMainWindow.this.selectAllCheck.isEnabled()) {
                    DrawMainWindow.this.selectAllCheck.setSelected(
                            !DrawMainWindow.this.selectAllCheck.isSelected());
                    handleSelectAll();
                }
            }
        });
        topPanel.setLayout(new GridLayout(2,1));
        topPanel.add(upperTopPanel);
        topPanel.add(lowerTopPanel);

        // Associate actions
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleSearch();
                }
            }
        });

        this.addEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                handleAddEntry();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                handleSearch();
            }
        });

        selectAllCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                handleSelectAll();
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

    private void drawStatusBar() {
        this.statusLabel.setText("Welcome to CT3-Phone-Book-Java!");
        JPanel statusBar = new JPanel();
        statusBar.add(this.statusLabel, FlowLayout.LEFT);
        statusBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        statusBar.setBackground(Color.white);
        this.add(statusBar, BorderLayout.SOUTH);
    }

    public void updateSelectionAssociatedMenu() {
        boolean hasSelection = getAllSelectedEntries().getNumberOfEntries() > 0;
        this.exportSelectedEntries.setEnabled(hasSelection);
        this.batchDelete.setEnabled(hasSelection);
        int numberOfEntriesInContactPanel
                = this.getAllContactsPanelEntries().getNumberOfEntries();
        if (0 == numberOfEntriesInContactPanel) {
            this.selectAllCheck.setSelected(false);
            this.selectAllCheck.setEnabled(false);
        }
        else {
            this.selectAllCheck.setEnabled(true);
            this.selectAllCheck.setSelected(numberOfEntriesInContactPanel
                    == this.getAllSelectedEntries().getNumberOfEntries());
            for (Component i : this.contentsPanel.getComponents()) {
                if (i instanceof EntryPanel) {
                    ((EntryPanel) i).updateSelectRelatedMenu();
                }
            }
        }
        updateStatusBar();
    }

    private void updateStatusBar() {
        int totalContacts = this.contactList.getNumberOfEntries();
        int selectedContacts = getAllSelectedEntries().getNumberOfEntries();
        if (selectedContacts > 0) {
            this.statusLabel.setText("Number of contacts selected: "
                    + selectedContacts);
        }
        else if (this.isSearching) {
            int searchedContacts = this.searchedList.getNumberOfEntries();
            this.statusLabel.setText("Number of contacts found: "
                    + searchedContacts);
        }
        else {
            this.statusLabel.setText("Number of contacts in current phone book: "
                    + totalContacts);
        }
        this.statusLabel.revalidate();
        this.statusLabel.repaint();
    }

    public void handleDelete(ContactList listToDelete) {
        int numberToDelete = listToDelete.getNumberOfEntries();
        StringBuilder messageForDelete = new StringBuilder("Are you sure to delete entries for: ");
        if (0 == numberToDelete) {
            JOptionPane.showMessageDialog(
                    this,
                    "No contacts selected to delete. Abort.",
                    "Delete Aborted",
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }
        else {
            for (int i = 0; i < (Math.min(numberToDelete, 3)); i++) {
                messageForDelete.append(listToDelete.getEntryByIndex(i).getName()).append(" ");
            }
            if (numberToDelete <= 3) {
                messageForDelete.append("?");
            }
            else {
                messageForDelete.append("... " + numberToDelete + " entries?");
            }
        }

        int answer = JOptionPane.showConfirmDialog(
                this,
                messageForDelete,
                "Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (answer == 0) {
            for (int i = 0; i < numberToDelete; i++) {
                Person personToDelete = listToDelete.getEntryByIndex(i);
                this.contactList.delEntry(personToDelete);
                if (isSearching && this.searchedList.hasPerson(personToDelete)) {
                    searchedList.delEntry(personToDelete);
                }
            }
            updateCentralPanel(isSearching ? this.searchedList : this.contactList);
        }
    }

    private void handleAddEntry() {
        this.addEntryButton.setText("...");
        this.addEntryButton.setEnabled(false);
        EntryWindow.start(this, null);
    }

    public void handleExport(ContactList contactList) {
        if (contactList.getNumberOfEntries() == 0) {
            JOptionPane.showMessageDialog(
                    DrawMainWindow.this,
                    "No contacts in current list. Abort Export",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        File fileToSave;
        fileChooser.setDialogTitle("Export");
        fileChooser.setMultiSelectionEnabled(true);
        int chooserReturn = fileChooser.showSaveDialog(this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
            try{
                VcfExporter.writeContactListToFile(contactList, fileToSave);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error during export: " + e.getMessage(),
                        "Export Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Successfully exported "
                            + contactList.getNumberOfEntries()
                            + " entries.",
                    "Export Successful",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void handleSelectAll() {
        if (this.contentsPanel.getComponents().length > 0) {
            boolean isSelectingAllChecked = this.selectAllCheck.isSelected();
            for (Component i : this.contentsPanel.getComponents()) {
                if (i instanceof EntryPanel) {
                    ((EntryPanel) i).setSelected(isSelectingAllChecked);
                }
            }
        }
    }

    private void handleSearch() {
        String searchText = this.searchField.getText();

        if (searchText.equals("")) {
            this.updateCentralPanel(this.contactList);
            this.isSearching = false;
        }
        else {
            this.searchedList = this.contactList
                    .findEntryByString(searchText);
            this.updateCentralPanel(this.searchedList);
            this.isSearching = true;

        }
    }

    private void handleImport () {
        JFileChooser fileChooser = new JFileChooser();
        File[] filesToImport;
        fileChooser.setDialogTitle("Select vCard file");
        fileChooser.setMultiSelectionEnabled(true);
        int chooserReturn = fileChooser.showOpenDialog(this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION) {
            try {
                filesToImport = fileChooser.getSelectedFiles();
                int totalImportedContactNumber = 0;
                for (File f : filesToImport) {
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
                if (totalImportedContactNumber > 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Successfully imported "
                                    + totalImportedContactNumber + " entries.",
                            "Import Successful",
                            JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(
                            this,
                            "No Entries found! Please check file formats.",
                            "Import Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error during export! Error: " + e.getMessage(),
                        "Import Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
        updateCentralPanel(this.contactList);
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

    public ContactList getAllSelectedEntries() {
        ContactList selectedEntries = new ContactList();
        if (this.contentsPanel.getComponents().length > 0) {
            for (Component i : this.contentsPanel.getComponents()) {
                if (i instanceof EntryPanel && ((EntryPanel) i).isSelected()) {
                    selectedEntries.addEntry(((EntryPanel) i).getPerson());
                }
            }
        }
        return selectedEntries;
    }

    private ContactList getAllContactsPanelEntries() {
        ContactList contactsPanelEntries = new ContactList();
        if (this.contentsPanel.getComponents().length > 0) {
            for (Component i : this.contentsPanel.getComponents()) {
                if (i instanceof EntryPanel) {
                    contactsPanelEntries.addEntry(((EntryPanel) i).getPerson());
                }
            }
        }
        return contactsPanelEntries;
    }
}
