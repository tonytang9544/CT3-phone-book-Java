package ct3PhoneBook.userInterface.GUI.addEntryWindow;

import ct3PhoneBook.contactObjects.CompanyPosition;
import ct3PhoneBook.contactObjects.Friend;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.contactObjects.WorkFriend;
import ct3PhoneBook.userInterface.GUI.mainWindow.DrawMainWindow;
import ct3PhoneBook.userInterface.commandLine.CommandLineUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;

public class DrawAddEntryWindow extends JFrame {
    private static final int ADD_ENTRY_WINDOW_WIDTH = 400;
    private static final int ADD_ENTRY_WINDOW_HEIGHT = 400;

    private final JComboBox typeOfContact;
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
    private final JComboBox position;
    private JPanel positionPanel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel confirmationPanel;

    private DrawMainWindow parentWindow;

    public DrawAddEntryWindow(DrawMainWindow mainWindow) {
        this.parentWindow = mainWindow;

        setTitle("Add Entry");
        setSize(ADD_ENTRY_WINDOW_WIDTH, ADD_ENTRY_WINDOW_HEIGHT);
        setResizable(false);

        typeOfContact = new JComboBox();
        typeOfContact.setEditable(false);
        typeOfContact.addItem("Person");
        typeOfContact.addItem("Friend");
        typeOfContact.addItem("WorkFriend");
        name = new JTextField(25);
        phoneNumber = new JTextField(25);
        birthday = new JTextField(25);
        notes = new JTextField(25);
        organization = new JTextField(25);
        position = new JComboBox();
        for (CompanyPosition p : CompanyPosition.values()) {
            position.addItem(p.name());
        }
        confirmButton = new JButton("OK");
        cancelButton = new JButton("Cancel");


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
                DrawAddEntryWindow.this.dispatchEvent(
                        new WindowEvent(DrawAddEntryWindow.this,
                                WindowEvent.WINDOW_CLOSING));
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addEntryToMainWindow();
            }
        });


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
                GregorianCalendar birthdayFormat = formattedBirthday(userInput);
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
                            JOptionPane.OK_OPTION);
                }
                break;

        }

    }

    private GregorianCalendar formattedBirthday(String userInput) {
        String[] userInputDecantenated = userInput.split("-");
        try {
            int year = Integer.parseInt(userInputDecantenated[0]);
            int month = Integer.parseInt(userInputDecantenated[1]);
            int day = Integer.parseInt(userInputDecantenated[2]);

            GregorianCalendar birthday = new GregorianCalendar();
            birthday.set(year, month, day);
            return birthday;
        }
        catch (Exception e) {
            return null;
        }
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
        namePanel.add(name);

        phoneNumberPanel = new JPanel();
        phoneNumberPanel.add(new JLabel("Tel: "));
        phoneNumberPanel.add(phoneNumber);

        birthdayPanel = new JPanel();
        birthdayPanel.add(new JLabel("Birthday (in yyyy-mm-dd format): "));
        birthdayPanel.add(birthday);

        notesPanel = new JPanel();
        notesPanel.add(new JLabel("Notes: "));
        notesPanel.add(notes);

        organizationPanel = new JPanel();
        organizationPanel.add(new JLabel("Org: "));
        organizationPanel.add(organization);

        positionPanel = new JPanel();
        positionPanel.add(new JLabel("Position: "));
        positionPanel.add(position);

        confirmationPanel = new JPanel();
        confirmationPanel.add(confirmButton);
        confirmationPanel.add(cancelButton);
    }



}
