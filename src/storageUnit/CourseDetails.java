package storageUnit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by odinahka on 9/19/2019.
 */
public class CourseDetails {
    private final SimpleStringProperty regdNumber;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty otherName;
    private final SimpleStringProperty lastName;
    private final SimpleIntegerProperty lectureNumber;
    private final SimpleIntegerProperty NumberAttended;
    private final SimpleIntegerProperty AttendancePercentage;

    public CourseDetails(String rNumber, String fName, String oName, String lName, int lNumber, int nAttended, int aPercentage) {
        this.regdNumber = new SimpleStringProperty(rNumber);
        firstName = new SimpleStringProperty(fName);
        lastName = new SimpleStringProperty(lName);
        this.otherName = new SimpleStringProperty(oName);
        this.NumberAttended = new SimpleIntegerProperty(nAttended);
        this.lectureNumber = new SimpleIntegerProperty(lNumber);
        this.AttendancePercentage = new SimpleIntegerProperty(aPercentage);

    }

    public String getRegdNumber() {
        return regdNumber.get();
    }

    public SimpleStringProperty regdNumberProperty() {
        return regdNumber;
    }

    public void setRegdNumber(String regdNumber) {
        this.regdNumber.set(regdNumber);
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

    public int getLectureNumber() {
        return lectureNumber.get();
    }

    public SimpleIntegerProperty lectureNumberProperty() {
        return lectureNumber;
    }

    public void setLectureNumber(int lectureNumber) {
        this.lectureNumber.set(lectureNumber);
    }

    public int getNumberAttended() {
        return NumberAttended.get();
    }

    public SimpleIntegerProperty numberAttendedProperty() {
        return NumberAttended;
    }

    public void setNumberAttended(int numberAttended) {
        this.NumberAttended.set(numberAttended);
    }

    public int getAttendancePercentage() {
        return AttendancePercentage.get();
    }

    public SimpleIntegerProperty attendancePercentageProperty() {
        return AttendancePercentage;
    }

    public void setAttendancePercentage(int attendancePercentage) {
        this.AttendancePercentage.set(attendancePercentage);
    }
}
