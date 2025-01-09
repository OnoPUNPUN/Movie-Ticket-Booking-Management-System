/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package movie.ticket.booking.management.system;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author tonmo
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button signIn_close;

    @FXML
    private Hyperlink signIn_createAccount;

    @FXML
    private AnchorPane signIn_form;

    @FXML
    private Button signIn_loginBtn;

    @FXML
    private Button signIn_minimize;

    @FXML
    private PasswordField signIn_password;

    @FXML
    private TextField signIn_username;

    @FXML
    private Hyperlink signUp_alreadyHaveAccount;

    @FXML
    private Button signUp_btn;

    @FXML
    private Button signUp_close;

    @FXML
    private TextField signUp_email;

    @FXML
    private AnchorPane signUp_form;

    @FXML
    private Button signUp_minimize;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private TextField signUp_username;

    // TOOLS FOR DATABASE
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public boolean validEmail()
    {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher match = pattern.matcher(signUp_email.getText());

        Alert alert;

        if(match.find() && match.group().matches(signUp_email.getText()))
        {
            return true;
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();
            return false;
        }
    }

    /*public void signup()
    {
        String sql = "INSERT INTO admin (email, username, password) VALUES (?,?,?)";
        String sql1 = "SELECT username FROM admin";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, signUp_email.getText());
            prepare.setString(2, signUp_username.getText());
            prepare.setString(3, signUp_password.getText());

            Alert alert;

            if(signUp_email.getText().isEmpty() || signUp_username.getText().isEmpty() || signUp_password.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }
            else if(signUp_password.getText().length() < 8)
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password must be at least 8 characters");
                alert.showAndWait();
            }

            else {

                if(validEmail())
                {
                    // Maybe need some fixes
                    prepare = connect.prepareStatement(sql1);
                    result = prepare.executeQuery();
                    if(result.next()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText(signUp_username.getText()+ " was already registered");
                        alert.showAndWait();

                    }else {
                        prepare.execute();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully created a new account");
                        alert.showAndWait();

                        //AFTER WE CREATE
                        signUp_email.setText("");
                        signUp_username.setText("");
                        signUp_password.setText("");
                    }
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/
    public void signup() {
        String sql = "INSERT INTO admin (email, username, password) VALUES (?,?,?)";
        String sql1 = "SELECT username FROM admin WHERE username = ?";
        connect = database.connectDb();

        try {
            Alert alert;

            if (signUp_email.getText().isEmpty() || signUp_username.getText().isEmpty() || signUp_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            } else if (signUp_password.getText().length() < 8) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password must be at least 8 characters");
                alert.showAndWait();
            } else {
                if (validEmail()) {
                    prepare = connect.prepareStatement(sql1);
                    prepare.setString(1, signUp_username.getText());
                    result = prepare.executeQuery();

                    if (result.next()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText(signUp_username.getText() + " is already registered");
                        alert.showAndWait();
                    } else {
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1, signUp_email.getText());
                        prepare.setString(2, signUp_username.getText());
                        prepare.setString(3, signUp_password.getText());
                        prepare.execute();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully created a new account");
                        alert.showAndWait();

                        signUp_email.setText("");
                        signUp_username.setText("");
                        signUp_password.setText("");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private double x = 0;
    private double y = 0;

    public void signin()
    {
       String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
       connect = database.connectDb();

       try {
           prepare = connect.prepareStatement(sql);
           prepare.setString(1, signIn_username.getText());
           prepare.setString(2, signIn_password.getText());

           result = prepare.executeQuery();

           Alert alert;

           // CEHCK IF THE USERNAME OR PASSWORD IS EMPTY
           if(signIn_username.getText().isEmpty() || signIn_password.getText().isEmpty())
           {
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error Message");
               alert.setHeaderText(null);
               alert.setContentText("Please fill all the fields");
               alert.showAndWait();

           } else {

               if(result.next())
               {
                   getData.username = signIn_username.getText();

                   alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Information Message");
                   alert.setHeaderText(null);
                   alert.setContentText("You have successfully logged in");
                   alert.showAndWait();

                   // TO HIDE THE FROM
                   signIn_loginBtn.getScene().getWindow().hide();

                   Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                   Stage stage = new Stage();
                   Scene scene = new Scene(root);

                   root.setOnMousePressed((MouseEvent event) ->{
                       x = event.getSceneX();
                       y = event.getSceneY();
                   });

                   root.setOnMouseDragged((MouseEvent event) ->{
                       stage.setX(event.getScreenX() - x);
                       stage.setY(event.getScreenY() - y);
                   });

                   stage.initStyle(StageStyle.TRANSPARENT);
                   stage.setScene(scene);
                   stage.show();
               } else {
                   alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Error Message");
                   alert.setHeaderText(null);
                   alert.setContentText("Wrong Username or Password");
                   alert.showAndWait();
               }
           }
       } catch (Exception e){e.printStackTrace();}
    }

    public void switchForm(ActionEvent event)
    {
        if(event.getSource()==signIn_createAccount)
        {
            signIn_form.setVisible(false);
            signUp_form.setVisible(true);
        } else if(event.getSource()==signUp_alreadyHaveAccount)
        {
            signUp_form.setVisible(false);
            signIn_form.setVisible(true);
        }
    }

    public  void signIn_close()
    {
        System.exit(0);
    }

    public void signIn_minimize()
    {
        Stage stage = (Stage)signIn_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void signUp_close()
    {
        System.exit(0);
    }

    public void signUp_minimize()
    {
        Stage stage = (Stage)signUp_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        signIn_form.setVisible(true);
        signUp_form.setVisible(false);
    }    
    
}
