package ct3PhoneBook.contactObjects;

public class WorkFriend extends Person {
    private String organization;
    private CompanyPosition position;

    public WorkFriend(String name,
                      String phoneNumber,
                      String organization,
                      CompanyPosition position){
        super(name, phoneNumber);
        this.organization = organization;
        this.position = position;
    }

    public String getOrganization() {
        return this.organization;
    }

    public CompanyPosition getPosition() {
        return this.position;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setPosition(CompanyPosition position) {
        this.position = position;
    }

    public static WorkFriend convertPersonToWorkFriend(Person person,
                                                       String organization,
                                                       CompanyPosition position) {
        return new WorkFriend(person.getName(),
                person.getPhoneNumber(),
                organization,
                position);
    }

    @Override
    public String toString() {
        return this.getName() + ' '
                + this.getPhoneNumber() + ' '
                + this.getOrganization() + ' '
                + this.getPosition().name();
    }
}
