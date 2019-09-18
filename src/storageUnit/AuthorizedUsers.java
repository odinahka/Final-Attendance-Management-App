package storageUnit;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by odinahka on 9/7/2019.
 */
public class AuthorizedUsers {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty phoneNo;
    private final SimpleStringProperty gender;
    public AuthorizedUsers(String ID, String fName, String lName, String email, String phoneNo, String username, String password, String gender)
    {
        this.ID = new SimpleStringProperty(ID);
        firstName = new SimpleStringProperty(fName);
        lastName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
        this.username =new SimpleStringProperty(username);
        this.password =new SimpleStringProperty(password);
        this.gender = new SimpleStringProperty(gender);
        this.phoneNo = new SimpleStringProperty(phoneNo);
    }

    public String getID(){return ID.get();}
    public String getFirstName()
    {
        return firstName.get();
    }
    public String getLastName()
    {
        return lastName.get();
    }
    public String getEmail()
    {
        return email.get();
    }
    public String getPhoneNo(){return phoneNo.get();}
    public String getUsername(){ return username.get();}
    public String getPassword(){ return password.get();}
    public String getGender(){return gender.get();}

    public void setID(String ID){this.ID.set(ID);}
    public void setFirstName(String fName)
    {
        firstName.set(fName);
    }
    public void setLastName(String lName)
    {
        lastName.set(lName);
    }
    public void setEmail(String email)
    {
        this.email.set(email);
    }
    public void setPhoneNo(String phoneNo){this.phoneNo.set(phoneNo);}
    public void setUsername(String username){this.username.set(username);}
    public void setpassword(String password){this.password.set(password);}

}
