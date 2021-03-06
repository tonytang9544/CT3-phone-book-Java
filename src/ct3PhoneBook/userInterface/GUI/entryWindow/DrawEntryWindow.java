package ct3PhoneBook.userInterface.GUI.entryWindow;

import ct3PhoneBook.contactObjects.CompanyPosition;
import ct3PhoneBook.contactObjects.Friend;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.contactObjects.WorkFriend;
import ct3PhoneBook.userInterface.GUI.mainWindow.DrawMainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;

public class DrawEntryWindow extends JFrame{
    private static final int ADD_ENTRY_WINDOW_WIDTH = 400;
    private static final int ADD_ENTRY_WINDOW_HEIGHT = 400;

    private final JComboBox<String> typeOfContact;
    private JPanel typeOfContactPanel;
    private final JTextField name;
    private JPanel namePanel;
    private final JTextField phoneNumber;
    private JPanel phoneNumberPanel;
    private final JTextField birthday;
    private JPanel birthdayPanel;
    private final JTextField notes;
    private JPanel notesPanel;
    private final JTextField organization;
    private JPanel organizationPanel;
    private final JComboBox<String> position;
    private JPanel positionPanel;
    private final JButton confirmButton;
    private final JButton cancelButton;
    private JPanel confirmationPanel;

    private final DrawMainWindow parentWindow;
    private final Person personToModify;



    public DrawEntryWindow(DrawMainWindow mainWindow, Person person) {
        this.parentWindow = mainWindow;
        this.personToModify = person;

        setSize(ADD_ENTRY_WINDOW_WIDTH, ADD_ENTRY_WINDOW_HEIGHT);
        setResizable(false);

        typeOfContact = new JComboBox<>();
        typeOfContact.setEditable(false);
        typeOfContact.addItem("Person");
        typeOfContact.addItem("Friend");
        typeOfContact.addItem("WorkFriend");
        name = new JTextField(25);
        phoneNumber = new JTextField(25);
        birthday = new JTextField(25);
        notes = new JTextField(25);
        organization = new JTextField(25);
        position = new JComboBox<>();
        for (CompanyPosition p : CompanyPosition.values()) {
            position.addItem(p.name());
        }
        confirmButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        initialiseWindow();
        constructAllPanels();
        updateWindow();

        typeOfContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateWindow();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DrawEntryWindow.this.dispatchEvent(
                        new WindowEvent(DrawEntryWindow.this,
                                WindowEvent.WINDOW_CLOSING));
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                handleConfirmButton();
            }
        });

    }

    private void handleConfirmButton() {
        if (this.personToModify != null){
            this.parentWindow.getContactList().delEntry(this.personToModify);
        }
        addEntryToMainWindow();
    }


    private void addEntryToMainWindow() {
        String contactType = (String) typeOfContact.getSelectedItem();
        switch (contactType) {
            case "Person":
                this.parentWindow.getContactList().addEntry(
                        new Person(name.getText(), phoneNumber.getText()));
                this.parentWindow.updateCentralPanel(
                        this.parentWindow.getContactList());
                this.dispatchEvent(
                        new WindowEvent(this, WindowEvent.WINDOW_CLOSED));
                break;
            case "WorkFriend":
                this.parentWindow.getContactList().addEntry(
                        new WorkFriend(name.getText(),
                                phoneNumber.getText(),
                                organization.getText(),
                                CompanyPosition.valueOf(
                                        (String) position.getSelectedItem())));
                this.parentWindow.updateCentralPanel(
                        this.parentWindow.getContactList());
                this.dispatchEvent(
                        new WindowEvent(this, WindowEvent.WINDOW_CLOSED));
                break;
            case "Friend":
                String userInput = birthday.getText();
                GregorianCalendar birthdayFormat = Friend.stringToBirthday(userInput);
                if (birthdayFormat != null) {
                    this.parentWindow.getContactList().addEntry(
                            new Friend(name.getText(),
                                    phoneNumber.getText(),
                                    birthdayFormat,
                                    notes.getText()));
                    this.parentWindow.updateCentralPanel(
                            this.parentWindow.getContactList());
                    this.dispatchEvent(
                            new WindowEvent(this, WindowEvent.WINDOW_CLOSED));
                }
                else {
                    JOptionPane.showMessageDialog(this,
                            "Incorrect birthday format. Please try again",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
        }

    }

    private void initialiseWindow() {
        if (this.personToModify == null) {
            setTitle("Add Entry");
        }
        else {
            setTitle("Modify Entry");
            updatePersonContentsToForm(personToModify);
            if (personToModify instanceof WorkFriend) {
                typeOfContact.setSelectedItem("WorkFriend");
                WorkFriend workFriend = (WorkFriend) personToModify;
                organization.setText(workFriend.getOrganization());
                position.setSelectedItem(workFriend.getPosition().name());
            }
            else if (personToModify instanceof Friend) {
                typeOfContact.setSelectedItem("Friend");
                Friend friend = (Friend) personToModify;
                notes.setText(friend.getShortNotes());
                birthday.setText(Friend.birthdayToDashedString(friend.getBirthday()));
            }
        }
    }

    private void updatePersonContentsToForm(Person person) {
        name.setText(person.getName());
        phoneNumber.setText(person.getPhoneNumber());
    }

    private void updateWindow() {
        String contactType = (String) typeOfContact.getSelectedItem();
        String previousName = name.getText();
        String previousPhoneNumber = phoneNumber.getText();
        String previousBirthday = birthday.getText();
        String previousNote = notes.getText();
        String previousOrganization = organization.getText();
        int previousPosition = position.getSelectedIndex();

        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();

        switch (contactType) {
            case "Person":
                this.setLayout(new GridLayout(4, 1));
                this.add(typeOfContactPanel);
                this.add(namePanel);
                name.setText(previousName);
                this.add(phoneNumberPanel);
                phoneNumber.setText(previousPhoneNumber);
                this.add(confirmationPanel);
                break;
            case "Friend":
                this.setLayout(new GridLayout(6, 1));
                this.add(typeOfContactPanel);
                this.add(namePanel);
                name.setText(previousName);
                this.add(phoneNumberPanel);
                phoneNumber.setText(previousPhoneNumber);
                this.add(birthdayPanel);
                birthday.setText(previousBirthday);
                this.add(notesPanel);
                notes.setText(previousNote);
                this.add(confirmationPanel);
                break;
            case "WorkFriend":
                this.setLayout(new GridLayout(6, 1));
                this.add(typeOfContactPanel);
                this.add(namePanel);
                name.setText(previousName);
                this.add(phoneNumberPanel);
                phoneNumber.setText(previousPhoneNumber);
                this.add(organizationPanel);
                organization.setText(previousOrganization);
                this.add(positionPanel);
                position.setSelectedIndex(previousPosition);
                this.add(confirmationPanel);
                break;
            default:
                this.setLayout(new GridLayout(4, 1));
                this.add(typeOfContactPanel);
                this.add(namePanel);
                name.setText(previousName);
                this.add(phoneNumberPanel);
                phoneNumber.setText(previousPhoneNumber);
                this.add(confirmationPanel);
                break;
        }
        this.revalidate();
        this.repaint();
    }

    private void constructAllPanels() {
        typeOfContactPanel = new JPanel();
        typeOfContactPanel.add(new JLabel("Type of contact: "));
        typeOfContactPanel.add(typeOfContact);

        namePanel = new JPanel();
        namePanel.add(new JLabel("Name: "));
        name.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleConfirmButton();
                }
            }
        });
        namePanel.add(name);

        phoneNumberPanel = new JPanel();
        phoneNumberPanel.add(new JLabel("Tel: "));
        phoneNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleConfirmButton();
                }
            }
        });
        phoneNumberPanel.add(phoneNumber);

        birthdayPanel = new JPanel();
        birthdayPanel.add(new JLabel("Birthday (in yyyy-mm-dd format): "));
        birthday.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleConfirmButton();
                }
            }
        });
        birthdayPanel.add(birthday);

        notesPanel = new JPanel();
        notesPanel.add(new JLabel("Notes: "));
        notes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleConfirmButton();
                }
            }
        });
        notesPanel.add(notes);

        organizationPanel = new JPanel();
        organizationPanel.add(new JLabel("Org: "));
        organization.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleConfirmButton();
                }
            }
        });
        organizationPanel.add(organization);

        positionPanel = new JPanel();
        positionPanel.add(new JLabel("Position: "));
        position.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleConfirmButton();
                }
            }
        });
        positionPanel.add(position);

        confirmationPanel = new JPanel();
        confirmationPanel.add(confirmButton);
        confirmationPanel.add(cancelButton);
    }
}
