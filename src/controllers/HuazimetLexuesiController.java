package controllers;

import entities.Lexuesi;
import entities.Libri;
import entities.ReferencaHuazime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;
import resourceFunctions.BibliotekaException;
import resourceFunctions.EntityManagerClass;
import resourceFunctions.ObjectInterface;
import resourceFunctions.ObjectRepository;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HuazimetLexuesiController extends EntityManagerClass implements Initializable {

    ObjectInterface oi = new ObjectRepository();
    ObservableList<Object> obslist = FXCollections.observableArrayList();

    @FXML
    private TextField emriLexuesitTextField;

    @FXML
    private TableColumn<Lexuesi, Integer> col_id;

    @FXML
    private TableColumn<Libri, String> col_titulli;

    @FXML
    private Label idLabel;

    @FXML
    private TableColumn<ReferencaHuazime, Date> col_huazuar;

    @FXML
    private TableColumn<Libri , String> col_isbn;

    @FXML
    private Label huazimiFunditLabel;

    @FXML
    private Label regjistruarLabel;

    @FXML
    private TableView<ReferencaHuazime> huazimetTableView;

    @FXML
    private Label nrHuazimeveLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumnsProperties();
    }

    public void TableColumnsProperties(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("lexuesiId"));
        col_titulli.setCellValueFactory(new PropertyValueFactory<>("emri"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        col_huazuar.setCellValueFactory(new PropertyValueFactory<>("dataHuazimit"));
        TableModelData(obslist);
    }

    public void TableModelData(ObservableList obslist){
        String [] fullName = emriLexuesitTextField.getText().split(" ");

    }

    public void AutoCompletion(){
        try {
            List<Lexuesi> ReadersList = (List<Lexuesi>) oi.findAll();
            TextFields.bindAutoCompletion(emriLexuesitTextField , ReadersList);
        } catch (BibliotekaException e) {
            e.printStackTrace();
        }
    }
}
