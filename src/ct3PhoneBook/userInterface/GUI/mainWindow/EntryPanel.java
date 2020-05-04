package ct3PhoneBook.userInterface.GUI.mainWindow;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Friend;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.contactObjects.WorkFriend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

class EntryPanel extends JPanel {
    private final Person person;
    private final DrawMainWindow parentFrame;
    private final JCheckBox personSelect = new JCheckBox();

    protected EntryPanel(DrawMainWindow parent, Person person) throws NullPointerException {
        if (person == null || parent == null) {
            throw new NullPointerException();
        }
        this.person = person;
        this.parentFrame = parent;

        this.setLayout(new GridLayout(2,1));

        drawPopupMenu();
        displayPersonInfo(person);
        displayExtraInfo(person);

    }

    private void drawPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem popupDelete = popupMenu.add("Delete");
        popupDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DrawMainWindow mainWindow = EntryPanel.this.parentFrame;
                mainWindow.getContactList().delEntry(
                        EntryPanel.this.person);

                if (mainWindow.isSearching()) {
                    mainWindow.getSearchedList().delEntry(
                            EntryPanel.this.person);
                    mainWindow.updateCentralPanel(
                            mainWindow.getSearchedList());
                }
                else {
                    mainWindow.updateCentralPanel(
                            mainWindow.getContactList());
                }
            }
        });
        JMenuItem popupModify = popupMenu.add("Modify");
        this.setComponentPopupMenu(popupMenu);
    }

    private void displayPersonInfo(Person person) {
        JPanel personInfo = new JPanel(new GridLayout(2,1));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        namePanel.add(this.personSelect);
        namePanel.add(new JLabel(person.getName()));

        personInfo.add(namePanel);
        personInfo.add(new JPanel().add(
                new JLabel("Tel: " + person.getPhoneNumber())));

        this.add(personInfo);
    }

    private void displayExtraInfo(Person person) {
        JPanel extraInfo = new JPanel();
        if (person instanceof WorkFriend) {
            extraInfo.setLayout(new GridLayout(2, 1));
            extraInfo.add(new JPanel().add(
                    new JLabel("Org: " + ((WorkFriend) person).getOrganization())));
            extraInfo.add(new JPanel().add(
                    new JLabel("Position: " + ((WorkFriend) person).getPosition().name())));
            this.add(extraInfo);
        }
        else if (person instanceof Friend) {
            extraInfo.setLayout(new GridLayout(2, 1));
            GregorianCalendar birthday = ((Friend) person).getBirthday();
            int year = birthday.get(GregorianCalendar.YEAR);
            int month = birthday.get(GregorianCalendar.MONTH);
            int day = birthday.get(GregorianCalendar.DAY_OF_MONTH);
            extraInfo.add(new JPanel().add(
                    new JLabel("Bday: " + year + "-" + month + "-" + day)));
            extraInfo.add(new JPanel().add(
                    new JLabel("Note: " + ((Friend) person).getShortNotes())));
            this.add(extraInfo);
        }
    }

    public Person getPerson() {
        return this.person;
    }

    public boolean isSelected() {
        return this.personSelect.isSelected();
    }

    public void setSelected(boolean selected) {
        this.personSelect.setSelected(selected);
    }



}