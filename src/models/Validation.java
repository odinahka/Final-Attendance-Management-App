package models;

import javafx.scene.control.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by odinahka on 9/14/2019.
 */
public class Validation {
    public boolean validateID(TextField id)
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(id.getText());
        if(m.find() && m.group().equals(id.getText()))
            return true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Number");
        alert.setHeaderText(null);
        alert.setContentText("Please Enter a Valid Number");
        alert.showAndWait();
        return false;
    }
    public boolean validateFirstName(TextField firstName)
    {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(firstName.getText());
        if(m.find() && m.group().equals(firstName.getText()))
            return true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate First Name");
        alert.setHeaderText(null);
        alert.setContentText("Please Enter a Valid First Name");
        alert.showAndWait();
        return false;
    }
    public boolean validateLastName(TextField lastName) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(lastName.getText());
        if (m.find() && m.group().equals(lastName.getText()))
            return true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Last Name");
        alert.setHeaderText(null);
        alert.setContentText("Please Enter a Valid Last Name");
        alert.showAndWait();
        return false;
    }
   public boolean validateEmail(TextField email) {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z0-9]+)+");
        Matcher m = p.matcher(email.getText());
        if (m.find() && m.group().equals(email.getText()))
            return true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Email");
        alert.setHeaderText(null);
        alert.setContentText("Please Enter a Valid Email");
        alert.showAndWait();
        return false;
    }

    public boolean validatePhoneNumber(TextField phoneNo) {
        Pattern p = Pattern.compile("(0|234)?[7-9][0-1][0-9]{8}");
        Matcher m = p.matcher(phoneNo.getText());
        if (m.find() && m.group().equals(phoneNo.getText()))
            return true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Phone Number Validation");
        alert.setHeaderText(null);
        alert.setContentText("Please Enter a Valid Phone Number");
        alert.showAndWait();
        return false;
    }

    public boolean validatePassword(PasswordField pw) {
        Pattern p = Pattern.compile("((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@%]).{6,15})");
        Matcher m = p.matcher(pw.getText());
        if (m.matches())
            return true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Password");
        alert.setHeaderText(null);
        alert.setContentText("Password must contain atleast one (digit, lowercase character, uppercase character, special character) and within the range of 6 to 15");
        alert.showAndWait();
        return false;
    }

    public boolean validateField(TextField username, DatePicker date, RadioButton male, RadioButton female)
    {
        if(username.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate UserName");
            alert.setHeaderText(null);
            alert.setContentText("Please Validate Username!");
            alert.showAndWait();
            return false;
        }

        if(date.getEditor().getText().isEmpty() )
        {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Validate fields");
            alert2.setHeaderText(null);
            alert2.setContentText("Please Validate Date of Birth (DOB)");
            alert2.showAndWait();
            return false;
        }
        if(!(male.isSelected()|| female.isSelected())) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information Dialog");
            alert1.setHeaderText(null);
            alert1.setContentText("Please Validate Gender");
            alert1.showAndWait();
            return false;
        }


        return true;
    }
    public boolean validateField(TextField username, RadioButton male, RadioButton female)
    {
        if(username.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate UserName");
            alert.setHeaderText(null);
            alert.setContentText("Please Validate Username!");
            alert.showAndWait();
            return false;
        }
        if(!(male.isSelected()|| female.isSelected())) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information Dialog");
            alert1.setHeaderText(null);
            alert1.setContentText("Please Validate Gender");
            alert1.showAndWait();
            return false;
        }


        return true;
    }
}
