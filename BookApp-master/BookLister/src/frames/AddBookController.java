/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import booklister.FXMLDocumentController;
import static booklister.FXMLDocumentController.conn;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Book;

/**
 * FXML Controller class
 *
 * @author Mathieu
 */
public class AddBookController implements Initializable {

    @FXML
    private Button buttonConfirm;
    @FXML
    private DatePicker readingDateField;
    @FXML
    private CheckBox readStateField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextArea descriptionField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void handleButtonConfirm(ActionEvent event) throws SQLException {
            
        String dateLecture = "";
        if (!titleField.getText().equals("") && !authorField.getText().equals("")){
            
            if (readingDateField.getValue() == null) 
                dateLecture = "";
            
            else
                dateLecture = readingDateField.getValue().toString();
            
            String query = "INSERT INTO \"Books\" (nom, auteur, description, \"dateLecture\", \"etatLecture\") VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            try{
            
                statement.setString(1, titleField.getText()); 
                statement.setString(2, authorField.getText());
                statement.setString(3, descriptionField.getText());
                statement.setString(4, dateLecture);
                statement.setBoolean(5, readStateField.selectedProperty().get());

                statement.executeUpdate();
                
                ResultSet tableKeys = statement.getGeneratedKeys();
                tableKeys.next();
                int autoGeneratedID = tableKeys.getInt(1);
                
            }catch(Exception e){
                e.printStackTrace(System.err);
            }
            
            Stage stage = (Stage) buttonConfirm.getScene().getWindow();
            stage.close();
            
            
        }
           
        //Book livre = new Book(titleField.getText(), authorField.getText(), description, dateLecture, readStateField.selectedProperty().get()); 
        //+ titleField.getText() + "," + authorField.getText() + "," + 
        //description + "," + dateLecture + "," + readStateField.selectedProperty().get() 
    }

}
  