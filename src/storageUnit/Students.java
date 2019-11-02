package storageUnit;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by odinahka on 9/7/2019.
 */
public class Students {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty otherName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phoneNo;
    private final SimpleStringProperty gender;

    public Students(String ID, String fName, String oName, String lName,String email, String phoneNo, String gender) {
        this.ID = new SimpleStringProperty(ID);
        firstName = new SimpleStringProperty(fName);
        lastName = new SimpleStringProperty(lName);
        this.otherName = new SimpleStringProperty(oName);
        this.email = new SimpleStringProperty(email);
        this.gender = new SimpleStringProperty(gender);
        this.phoneNo = new SimpleStringProperty(phoneNo);

    }

    public String getID() {
        return ID.get();
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getOtherName() {
        return otherName.get();
    }

    public SimpleStringProperty otherNameProperty() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName.set(otherName);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhoneNo() {
        return phoneNo.get();
    }

    public SimpleStringProperty phoneNoProperty() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo.set(phoneNo);
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }
}



