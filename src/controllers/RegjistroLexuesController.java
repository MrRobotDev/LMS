package controllers;

import entities.Lexuesi;
import entities.Libri;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import resourceFunctions.BibliotekaException;
import resourceFunctions.EntityManagerClass;
import resourceFunctions.LexuesiInterface;
import resourceFunctions.LexuesiRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegjistroLexuesController extends EntityManagerClass implements Initializable {

    ObservableList<Lexuesi> oblist = FXCollections.observableArrayList();
    LexuesiInterface li = new LexuesiRepository();
    @FXML
    private Button perditesoButton;

    @FXML
    private Button regjistroButton;

    @FXML
    private Button raportButton;

    @FXML
    private Button fshijeButton;

    @FXML
    private AnchorPane lexuesitRootPane;

    @FXML
    private ImageView previousParentImageView;

    @FXML
    private TableView lexuesitTableView;

    @FXML
    private TableColumn<Lexuesi , Integer> col_id;
    @FXML
    private TableColumn<Lexuesi , String> col_emri;
    @FXML
    private TableColumn<Lexuesi , String> col_mbiemri;
    @FXML
    private TableColumn<Lexuesi , String> col_adresa;
    @FXML
    private TableColumn<Lexuesi , LocalDate> col_dataRegjistrimit;
    @FXML
    private TableColumn<Lexuesi , String> col_kontakti;
    @FXML
    private TableColumn<Lexuesi , LocalDate> col_dataLindjes;
    @FXML
    private TableColumn<Lexuesi , Boolean> col_pagesa;



    public void shtoLexues()throws IOException{
        regjistroButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Parent loader = null;
                try {
                    loader = FXMLLoader.load(getClass().getResource("../views/LoadRegjistrimi.fxml"));
                    Stage x = new Stage();
                    x.setScene(new Scene(loader));
                    x.show();
                    Stage stage = (Stage)regjistroButton.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void backToParent(){
        previousParentImageView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Parent loader = null;
                try{
                    loader = FXMLLoader.load(getClass().getResource("../views/Kryefaqja.fxml"));
                    lexuesitRootPane.getChildren().setAll(loader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTableData();
        EdditingCellValueFactory();
        lexuesitTableView.setEditable(true);
    }

    public void fshijLexuesin()throws IOException{
        fshijeButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Lexuesi l = (Lexuesi) lexuesitTableView.getSelectionModel().getSelectedItem();
                if(l!=null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Konfirmim");
                alert.setHeaderText(null);
                alert.setContentText("Konfirmo per te fshire lexuesin?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    if(l != null) {
                        lexuesitTableView.getItems().remove(l);
                        em.getTransaction().begin();
                        Lexuesi x = em.merge(l);
                        em.remove(x);
                        em.getTransaction().commit();
                    }
                } if(result.get() == ButtonType.CANCEL) {
                    Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    cancelAlert.setTitle("Njoftim");
                    cancelAlert.setHeaderText(null);
                    cancelAlert.setContentText("Eshte anuluar fshirja e lexuesit!");
                }
            }
            else {
                        Alert nullAlert = new Alert(Alert.AlertType.ERROR);
                        nullAlert.setTitle("Erorr");
                        nullAlert.setHeaderText(null);
                        nullAlert.setContentText("Nuk keni perzgjedhur asnje lexuer per te fshire!");
                        nullAlert.show();
                    }
                }
        });
    }

    public void loadTableData(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("lexuesiId"));
        col_emri.setCellValueFactory(new PropertyValueFactory<>("emri"));
        col_mbiemri.setCellValueFactory(new PropertyValueFactory<>("mbiemri"));
        col_adresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        col_dataRegjistrimit.setCellValueFactory(new PropertyValueFactory<>("dataRegjistrimit"));
        col_kontakti.setCellValueFactory(new PropertyValueFactory<>("kontakti"));
        col_dataLindjes.setCellValueFactory(new PropertyValueFactory<>("vitiLindjes"));
        col_pagesa.setCellValueFactory(new PropertyValueFactory<>("paguar"));
        List<Lexuesi> lista = em.createQuery("SELECT l from Lexuesi l").getResultList();
        for (Lexuesi l : lista){
            oblist.add(new Lexuesi(l.getLexuesiId() , l.getEmri() , l.getMbiemri() , l.getAdresa() , l.getDataRegjistrimit() , l.getKontakti() , l.getVitiLindjes(), l.isPaguar()));
        }
        lexuesitTableView.setItems(oblist);
        lexuesitTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void EdditingCellValueFactory(){
        col_emri.setCellFactory(TextFieldTableCell.forTableColumn());
        col_mbiemri.setCellFactory(TextFieldTableCell.forTableColumn());
        col_adresa.setCellFactory(TextFieldTableCell.forTableColumn());
        col_kontakti.setCellFactory(TextFieldTableCell.forTableColumn());
    //    col_dataLindjes.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        col_pagesa.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter())) ;
    }

    public void changedEmriCellEvent(TableColumn.CellEditEvent<Lexuesi , String> editedCell){
        Lexuesi lex = (Lexuesi) lexuesitTableView.getSelectionModel().getSelectedItem();
        if(editedCell.getNewValue().matches("[a-zA-Z'\\s]*")){
            lex.setEmri(editedCell.getNewValue());
        }
        else {
            Alert art = validationAlert(editedCell.getNewValue());
            art.show();
        }
    }

    public void changedMbiemriCellEvent(TableColumn.CellEditEvent<Lexuesi , String> editedCell){
        Lexuesi lex = (Lexuesi)lexuesitTableView.getSelectionModel().getSelectedItem();
        if(editedCell.getNewValue().matches("[a-zA-Z'\\s]*")){
            lex.setMbiemri(editedCell.getNewValue());
        }
        else{
            Alert art = validationAlert(editedCell.getNewValue());
            art.show();
        }
    }

    public void changedAdresaCellEvent(TableColumn.CellEditEvent<Lexuesi , String> editedCell){
        Lexuesi lex = (Lexuesi)lexuesitTableView.getSelectionModel().getSelectedItem();
        if(editedCell.getNewValue().matches("[a-zA-Z0-9,'\\s]*")){
            lex.setAdresa(editedCell.getNewValue());
        }
        else{
            Alert art = validationAlert(editedCell.getNewValue());
            art.show();
        }
    }

    public void changedKontaktiCellEvent(TableColumn.CellEditEvent<Lexuesi , String> editedCell){
        Lexuesi lex = (Lexuesi) lexuesitTableView.getSelectionModel().getSelectedItem();
        if(editedCell.getNewValue().matches("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\"\n" +
                "+ \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")){
            lex.setKontakti(editedCell.getNewValue());
        }
        else {
            Alert art = validationAlert(editedCell.getNewValue());
            art.show();
        }
    }

//    public void changedDataLindjesCellEvent(TableColumn.CellEditEvent<Lexuesi , LocalDate> editedCell){
//        Lexuesi lex = (Lexuesi) lexuesitTableView.getSelectionModel().getSelectedItem();
//        if(editedCell.getNewValue().toString().matches("\\d{2}/\\d{2}/\\d{4}")){
//            lex.setVitiLindjes(Date.valueOf(editedCell.getNewValue()));
//        }
//        else {
//            Alert art = validationAlert(String.valueOf(editedCell.getNewValue()));
//            art.show();
//        }
//    }

    public Alert validationAlert(String o){
        Alert art = new Alert(Alert.AlertType.ERROR);
        art.setTitle("Validimi");
        art.setHeaderText(null);
        art.setContentText("'" + o + "' nuk eshte e validuar!");
        art.close();
        return art;
    }

    public void perditesoLexuesin(){
        perditesoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Lexuesi l = (Lexuesi)lexuesitTableView.getSelectionModel().getSelectedItem();
//                ObservableList<Lexuesi> listaLexuesve = lexuesitTableView.getItems();
//                for (Lexuesi l : listaLexuesve) {
                    try {
                        AlertEditConfirmation(l);
                    } catch (BibliotekaException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    public void AlertEditConfirmation(Lexuesi l) throws BibliotekaException {
        if(lexuesitTableView.getSelectionModel().getSelectedItem() != null) {
            if (SelectedRowValidationWhenUpdate()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Konfirmim");
                alert.setHeaderText(null);
                alert.setContentText("Konfirmo per te perditesuar librin?");

                Optional<ButtonType> confirm = alert.showAndWait();

                if (confirm.get() == ButtonType.OK) {

                    li.edit(l);
                    alert.close();

                }
                if (confirm.get() == ButtonType.CANCEL) {
                    alert.close();
                }
            } else {
                Alert art = new Alert(Alert.AlertType.ERROR);
                art.setTitle("Validimi");
                art.setHeaderText(null);
                art.setContentText("Ju lutemi , validoni te dhenat!");
                art.show();
                art.close();
            }
        }else {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Error");
            nullAlert.setHeaderText(null);
            nullAlert.setContentText("Nuk keni zgjedhur lexues per te perditesuar!");
            nullAlert.show();
        }
    }

    public boolean SelectedRowValidationWhenUpdate(){
        Lexuesi l = (Lexuesi) lexuesitTableView.getSelectionModel().getSelectedItem();
        if(!l.getEmri().matches("[a-zA-Z',\\s]*") || !l.getMbiemri().matches("[a-zA-Z',\\s]*") || !l.getAdresa().matches("[a-zA-Z0-9',\\s]*")){
//                || !l.getKontakti().matches("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\"\n" +
//                "+ \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")){
            return false;
        }
        return true;
    }
}
