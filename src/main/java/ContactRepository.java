import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ContactRepository {

    private final String contactFileName = "contacts.csv";

    public ContactRepository() {
        try {
            this.createContactsFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void createContactsFile() throws IOException {
        File contactsFile = new File(this.contactFileName);
        contactsFile.createNewFile();
    }

    public void addToContactsFile(Contact contact) throws IOException {
        FileWriter contactsFile = new FileWriter(this.contactFileName, true);
        contactsFile.write(contact.toString());
        contactsFile.close();
    }

    public ArrayList<Contact> getContacts() {
        return new ArrayList<>();
    }
}
