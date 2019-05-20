package controllers;

import entities.Libri;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import resourceFunctions.LibriInterface;
import resourceFunctions.LibriRepository;

import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShtoLibrinController implements Initializable {

    LibriInterface li = new LibriRepository();

    @FXML
    public Pane contentPane;
    public TextField nrKopjeveTextField;

    @Pattern(regexp = "[\\p{Digit}&&[123456789]]+")
    public TextField isbnTextField;

    public TextField publikuesiTextField;
    public TextField titulliTextField;
    public TextField autoretTextField;
    public TextField kategoriaTextField;
    public DatePicker dataPublikimitDatePicker;
    public DatePicker dataRegjistrimitDatePicker;
    public TextArea pershkrimiTextField;
    public ImageView backButton;
    public Button shtoButton;
    public MenuItem shtoLibriMenuItem;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstThingsToRun();
    }


    public void goBack() throws IOException {
        backButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Parent secondPane = FXMLLoader.load(getClass().getResource("../views/Kryefaqja.fxml"));
                    Stage x = new Stage();
                    x.setScene(new Scene(secondPane));
                    x.show();
                    Stage stage = (Stage) backButton.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void shtoLibrinAction(ActionEvent actionEvent) throws IOException {
        LibriInterface let = new LibriRepository();
        try {
            if(fieldsValidation() && CheckMismatchType() && CheckDates()) {
            Libri l = new Libri();
            l.setEmri(titulliTextField.getText());
            l.setIsbn(isbnTextField.getText());
            l.setDataPublikimit(Date.valueOf(dataPublikimitDatePicker.getValue()));
            l.setDataRegjistrimit(Date.valueOf(LocalDate.now()));
            l.setKategoria(kategoriaTextField.getText());
            l.setPershkrimi(pershkrimiTextField.getText());
            l.setNumriKopjeve(Byte.valueOf(nrKopjeveTextField.getText()));
            l.setPublikuesi(publikuesiTextField.getText());
                let.create(l);
                clearFields();
            }else{
                Alert warn = new Alert(Alert.AlertType.WARNING);
                warn.setTitle("Verejtje");
                warn.setContentText("Ju lutemi , validoni te dhenat!");
                warn.setHeaderText(null);
                warn.showAndWait();
            }
            // SET FOREIGN_KEY_CHECKS=0 ----------> Command Executed for Contraint Non-Integrity
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean fieldsValidation(){
        if(isbnTextField.getText().isEmpty() || titulliTextField.getText().isEmpty() || kategoriaTextField.getText().isEmpty()
                || nrKopjeveTextField.getText().isEmpty() || publikuesiTextField.getText().isEmpty() || dataPublikimitDatePicker.toString().isEmpty()
                || dataPublikimitDatePicker.toString().isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean CheckMismatchType(){
        if(!isbnTextField.getText().matches("[0-9]+") || !titulliTextField.getText().matches("[a-zA-Z',\\s]*")
                || !kategoriaTextField.getText().matches("[a-zA-Z',\\s]*") || !nrKopjeveTextField.getText().matches("[0-9]+")
                || !publikuesiTextField.getText().matches("[a-zA-Z',\\s]*")){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean CheckDates(){
        if(dataRegjistrimitDatePicker.getValue().isBefore(LocalDate.now()) || dataRegjistrimitDatePicker.getValue().getYear()>1900) {
            if (dataRegjistrimitDatePicker.getValue().minusMonths(2).isAfter(dataPublikimitDatePicker.getValue())) {
                return true;
            }
        }
        return false;
    }

    public void firstThingsToRun(){
        dataRegjistrimitDatePicker.setEditable(false);
        dataRegjistrimitDatePicker.setValue(LocalDate.now());
    }

    public void clearFields(){
        isbnTextField.setText("");
        titulliTextField.setText("");
        kategoriaTextField.setText("");
        autoretTextField.setText("");
        nrKopjeveTextField.setText("");
        publikuesiTextField.setText("");
        dataPublikimitDatePicker.setValue(null);
        pershkrimiTextField.setText("");
    }

    
}

