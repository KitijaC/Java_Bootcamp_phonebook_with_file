import java.util.UUID;

public class Contact {

    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Contact() {
        this.id = UUID.randomUUID();
    }

    public Contact(UUID id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return new StringBuilder()
                .append( id + ",")
                .append( firstName + ",")
                .append( lastName + ",")
                .append( phoneNumber + ",")
                .append( email + "\n")
                .toString();
    }
}
