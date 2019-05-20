package controllers;

import entities.Lexuesi;
import entities.Libri;
import entities.ReferencaHuazime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import resourceFunctions.BibliotekaException;
import resourceFunctions.EntityManagerClass;
import resourceFunctions.ObjectInterface;
import resourceFunctions.ObjectRepository;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ListaHuazimeveController extends EntityManagerClass implements Initializable {

    ObservableList oblist = FXCollections.observableArrayList();

    @FXML
    private TableView<ReferencaHuazime> tabela;

    @FXML
    private TableColumn<ReferencaHuazime, Date> col_dataKthimit;

    @FXML
    private TableColumn<ReferencaHuazime , Integer> col_id;

    @FXML
    private TableColumn<Libri, String> col_titulli;

    @FXML
    private TableColumn<ReferencaHuazime, Date> col_dataHuazimit;

    @FXML
    private TableColumn<? , String> col_lexuesi;

    @FXML
    private TableColumn<Libri, String> col_isbn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadValuesFactory();
    }

    public void LoadValuesFactory(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("referencaId"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        col_titulli.setCellValueFactory(new PropertyValueFactory<>("emri"));
        col_dataHuazimit.setCellValueFactory(new PropertyValueFactory<>("dataHuazimit"));
        col_lexuesi.setCellValueFactory(new PropertyValueFactory<>("emri"));
        col_dataKthimit.setCellValueFactory(new PropertyValueFactory<>("dataKthimit"));
        LoadReferencat();
        tabela.setItems(oblist);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void LoadReferencat(){
        List<ReferencaHuazime> huazimet = (List<ReferencaHuazime>) em.createNamedQuery("ReferencaHuazime.findAll").getResultList();
        for (ReferencaHuazime h : huazimet){
            oblist.add(h);
        }
    }
}
