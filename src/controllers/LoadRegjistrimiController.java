package controllers;

import entities.Lexuesi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resourceFunctions.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class LoadRegjistrimiController extends EntityManagerClass implements Initializable {


    LexuesiInterface li = new LexuesiRepository();

    @FXML
    private AnchorPane formaRegjistrimitAnchor;

    @FXML
    private DatePicker dataRegjistrimitDatePicker;

    @FXML
    private Button konfirmoButton;

    @FXML
    private TextField adresaTextField;

    @FXML
    private DatePicker dataLindjesDatePicker;

    @FXML
    private TextField kontaktiTextField;

    @FXML
    private TextField mbiemriTextField;

    @FXML
    private CheckBox paguarCheckBox;

    @FXML
    private TextField emriTextField;

    @FXML
    private ImageView previousImageView;

    @FXML
    private ImageView clearFieldIV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateConstraintsSet();
    }


    public void shtoLexuesinAction() throws IOException {
        try {
            if(checkDatesValidation() && EmptyFieldsValidation() && MismatchTypeValidation()){
                registerUser();
            }
            else {
                Alert dateAlert = new Alert(Alert.AlertType.WARNING);
                dateAlert.setTitle("Verejtje");
                dateAlert.setHeaderText(null);
                dateAlert.setContentText("Ju lutemi , validoni te dhenat!");
                dateAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields()throws IOException{
        clearFieldIV.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearAllFields();
            }
        });
    }

    public void clearAllFields(){
        emriTextField.setText("");
        mbiemriTextField.setText("");
        adresaTextField.setText("");
        dataRegjistrimitDatePicker.setValue(null);
        dataLindjesDatePicker.setValue(null);
        kontaktiTextField.setText("");
        paguarCheckBox.setSelected(false);
    }

    public void turnBack()throws IOException{
        previousImageView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Parent loader;
                try {
                    loader = FXMLLoader.load(getClass().getResource("../views/RegjistroLexues.fxml"));
                    Stage x = new Stage();
                    x.setScene(new Scene(loader));
                    x.show();
                    Stage stage = (Stage)previousImageView.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public boolean checkDatesValidation(){
        if(dataLindjesDatePicker.getValue().getYear() < 2003 && dataLindjesDatePicker.getValue().getYear() > 1980){
            return true;
        }
        else {
            return false;
        }
    }

    public void registerUser() throws BibliotekaException {
        boolean paguar;
        Lexuesi lex = new Lexuesi(); lex.setEmri(emriTextField.getText());
        lex.setMbiemri(mbiemriTextField.getText()); lex.setAdresa(adresaTextField.getText());
        lex.setDataRegjistrimit(Date.valueOf(dataRegjistrimitDatePicker.getValue())); lex.setKontakti(kontaktiTextField.getText());
        lex.setVitiLindjes(Date.valueOf(dataLindjesDatePicker.getValue()));
        if (paguarCheckBox.isSelected()) {
            paguar = true;
        } else {
            paguar = false;
        }
        lex.setPaguar(paguar);
        li.create(lex);
        em.clear();
        showRegisteredUserAlert();
        clearAllFields();
    }

    public void showRegisteredUserAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Njoftim");
        alert.setHeaderText(null);
        alert.setContentText("Lexuesi eshte shtuar me sukses!");
        alert.showAndWait();
    }

    public void dateConstraintsSet(){
        dataRegjistrimitDatePicker.setValue(LocalDate.now());
        dataRegjistrimitDatePicker.setEditable(false);
    }

    public boolean EmptyFieldsValidation(){
        if(emriTextField.getText().isEmpty() || mbiemriTextField.getText().isEmpty() || adresaTextField.getText().isEmpty()
                || kontaktiTextField.getText().isEmpty() || dataLindjesDatePicker.getValue().toString().isEmpty() || paguarCheckBox.isSelected()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean MismatchTypeValidation(){
        if(!emriTextField.getText().matches("[a-zA-Z\\s]*") || !mbiemriTextField.getText().matches("[a-zA-Z\\s]*")
                || !adresaTextField.getText().matches("[a-zA-Z0-9',.\\s]*") || !kontaktiTextField.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
            return false;
        }
        else {
            return true;
        }
    }

}
