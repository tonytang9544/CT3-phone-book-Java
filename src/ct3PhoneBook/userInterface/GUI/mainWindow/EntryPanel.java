package ct3PhoneBook.userInterface.GUI.mainWindow;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Friend;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.contactObjects.WorkFriend;
import ct3PhoneBook.userInterface.GUI.entryWindow.EntryWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class EntryPanel extends JPanel {
    private final Person person;
    private final DrawMainWindow parentFrame;
    private final JCheckBox personSelect;
    private JMenuItem popupDeleteSelect;

    private static final Color colorBorder = new Color(100, 100, 100);
    private static final Color colorSelectedBackground = new Color(200, 200, 200);
    private static final Color colorDefaultBackground = UIManager.getColor ( "Panel.background" );

    protected EntryPanel(DrawMainWindow parent, Person person) throws NullPointerException {
        if (person == null || parent == null) {
            throw new NullPointerException();
        }
        this.person = person;
        this.parentFrame = parent;
        this.personSelect = new JCheckBox();
        personSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                parent.updateSelectionAssociatedMenu();
                EntryPanel.this.updateBackgroundColor();
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EntryPanel.this.personSelect.setSelected(
                        !EntryPanel.this.isSelected());
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    EntryWindow.start(
                            EntryPanel.this.parentFrame,
                            EntryPanel.this.getPerson());
                }
            }
        });

        this.setLayout(new GridLayout(2,1));

        drawPopupMenu();
        displayPersonInfo(person);
        displayExtraInfo(person);

        this.setBorder(BorderFactory.createLineBorder(colorBorder));
    }

    private void drawPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem popupDelete = popupMenu.add("Delete this entry");
        popupDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ContactList listToDelete = new ContactList();
                listToDelete.addEntry(EntryPanel.this.person);
                EntryPanel.this.parentFrame.handleDelete(listToDelete);
            }
        });

        this.popupDeleteSelect = popupMenu.add("Delete all selected");
        this.popupDeleteSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EntryPanel.this.parentFrame.handleDelete(
                        EntryPanel.this.parentFrame.getAllSelectedEntries());
            }
        });
        this.popupDeleteSelect.setEnabled(false);

        JMenuItem popupModify = popupMenu.add("Modify");
        popupModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EntryWindow.start(EntryPanel.this.parentFrame, EntryPanel.this.getPerson());
            }
        });

        JMenuItem popupExport = popupMenu.add("Export");
        popupExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ContactList listToExport = new ContactList();
                listToExport.addEntry(EntryPanel.this.person);
                EntryPanel.this.parentFrame.handleExport(listToExport);
            }
        });
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
            extraInfo.add(new JPanel().add(
                    new JLabel("Bday: " + Friend.birthdayToDashedString(
                            ((Friend) person).getBirthday()))));
            extraInfo.add(new JPanel().add(
                    new JLabel("Note: " + ((Friend) person).getShortNotes())));
            this.add(extraInfo);
        }
    }

    public void updateSelectRelatedMenu() {
        if (this.parentFrame.getAllSelectedEntries().getNumberOfEntries() > 0) {
            this.popupDeleteSelect.setEnabled(true);
        }
        else {
            this.popupDeleteSelect.setEnabled(false);
        }
    }

    private void updateBackgroundColor() {
        recursivelyUpdateBackground(this);
        this.revalidate();
        this.repaint();
    }

    private void recursivelyUpdateBackground(JPanel panel) {
        panel.setBackground(personSelect.isSelected() ?
                colorSelectedBackground : colorDefaultBackground);
        if (panel.getComponents().length > 0) {
            for (Component i : panel.getComponents()) {
                if (i instanceof JPanel) {
                    recursivelyUpdateBackground((JPanel) i);
                }
            }
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
