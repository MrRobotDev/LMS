package controllers;

import entities.Libri;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.ByteStringConverter;
import resourceFunctions.BibliotekaException;
import resourceFunctions.EntityManagerClass;
import resourceFunctions.LibriInterface;
import resourceFunctions.LibriRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TabelaLibritController extends EntityManagerClass implements Initializable {

        LibriInterface li = new LibriRepository();
            ObservableList<Libri> oblist = FXCollections.observableArrayList();

        @FXML
        public ImageView editContent;
        public ImageView applyContent;
        public ImageView deleteContent;
        public ImageView previousContent;
        public TableView<Libri> tabela;
        public TableColumn<Libri, Integer> col_libriId;
        public TableColumn<Libri, String> col_titulli;
        public TableColumn<Libri, String> col_isbn;
        public TableColumn<Libri, String> col_kategoria;
        public TableColumn<Libri, String> col_publikuesi;
        public TableColumn<Libri, Byte> col_kopje;


        @Override
        public void initialize(URL location, ResourceBundle resources) {
            defaultTableModel();
            EdittingCellValueFactory();
            tabela.setEditable(true);
        }

        public void deleteAction() throws IOException {
            deleteContent.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Konfirmim");
                        alert.setHeaderText(null);
                        alert.setContentText("Konfirmo per te fshire librin?");
                        Optional<ButtonType> confirm = alert.showAndWait();
                        if (confirm.get() == ButtonType.OK) {
                            if(tabela.getSelectionModel().getSelectedItem() != null) {
                                deleteBook();
                            }
                            else{
                                Alert art = new Alert(Alert.AlertType.ERROR);
                                art.setTitle("Error");
                                art.setHeaderText(null);
                                art.setContentText("Nuk keni zgjedhur liber per te fshire!");
                                art.show();
                            }
                        }
                        if (confirm.get() == ButtonType.CANCEL) {
                            Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            cancelAlert.setTitle("Njoftim");
                            cancelAlert.setHeaderText(null);
                            cancelAlert.setContentText("Eshte anuluar fshirja e librit!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void goBack() throws IOException {
            previousContent.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        Parent secondPane = FXMLLoader.load(getClass().getResource("../views/Kryefaqja.fxml"));
                        Stage x = new Stage();
                        x.setScene(new Scene(secondPane));
                        x.show();
                        Stage stage = (Stage) previousContent.getScene().getWindow();
                        stage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void defaultTableModel() {
            col_libriId.setCellValueFactory(new PropertyValueFactory<>("libriId")); //ketu shenohet saktesisht emra e variables ne klasen libri
            col_titulli.setCellValueFactory(new PropertyValueFactory<>("emri"));
            col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            col_kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
            col_publikuesi.setCellValueFactory(new PropertyValueFactory<>("publikuesi"));
            col_kopje.setCellValueFactory(new PropertyValueFactory<>("numriKopjeve"));

            loadLibriModel(oblist);
            tabela.setItems(oblist);
            tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

        public void loadLibriModel(ObservableList oblist) {
            LibriInterface li = new LibriRepository();
            List<Libri> res = (List<Libri>) em.createQuery("SELECT l FROM Libri l").getResultList();
            for (Libri l : res) {
                Libri model = new Libri();
                model.setEmri(l.getEmri());
                model.setIsbn(l.getIsbn());
                model.setLibriId(l.getLibriId());
                model.setKategoria(l.getKategoria());
                model.setPublikuesi(l.getPublikuesi());
                model.setNumriKopjeve(l.getNumriKopjeve());
                oblist.add(new Libri(l.getLibriId(), l.getEmri(), l.getIsbn(), l.getKategoria(), l.getPershkrimi(), l.getPublikuesi(), l.getDataPublikimit(), l.getDataRegjistrimit(), l.getNumriKopjeve()));
            }
        }

        public void deleteBook() {
            Libri l = tabela.getSelectionModel().getSelectedItem();
            tabela.getItems().remove(l);
            em.getTransaction().begin();
            Libri x = em.merge(l);
            em.remove(x);
            em.getTransaction().commit();
        }


        public void changedIsbnCellEvent(TableColumn.CellEditEvent<Libri, String> edditedCell) {
            Libri l = tabela.getSelectionModel().getSelectedItem();
            if(edditedCell.getNewValue().matches("[0-9-]+")) {
                l.setIsbn(edditedCell.getNewValue());
            }
            else{
                Alert art = new Alert(Alert.AlertType.ERROR);
                art.setTitle("Validimi");
                art.setHeaderText(null);
                art.setContentText("Kontrollo te dhenat e shenuara tek ISBN");
                art.show();
            }
        }

        public void changedTitulliCellEvent(TableColumn.CellEditEvent<Libri , String> edditedCell){
            Libri l = tabela.getSelectionModel().getSelectedItem();
            if(edditedCell.getNewValue().matches("[a-zA-Z',\\s]*")) {
                l.setEmri(edditedCell.getNewValue());
            }
            else {
                Alert art = new Alert(Alert.AlertType.ERROR);
                art.setTitle("Validimi");
                art.setHeaderText(null);
                art.setContentText("Kontrollo te dhenat e shenuara tek Titulli");
                art.show();
            }
        }

        public void changedKategoriaCellEvent(TableColumn.CellEditEvent<Libri , String> edditedCell){
            Libri l = tabela.getSelectionModel().getSelectedItem();
            if(edditedCell.getNewValue().matches("[a-zA-Z',\\s]*")) {
                l.setKategoria(edditedCell.getNewValue());
            }
            else {
                Alert art = new Alert(Alert.AlertType.ERROR);
                art.setTitle("Validimi");
                art.setHeaderText(null);
                art.setContentText("Kontrollo te dhenat e shenuara tek Kategoria");
                art.show();
            }
        }

        public void changedPublikuesiCellEvent(TableColumn.CellEditEvent<Libri , String> edditedCell){
            Libri l = tabela.getSelectionModel().getSelectedItem();
            if(edditedCell.getNewValue().matches("[a-zA-Z',\\s]*")) {
                l.setPublikuesi(edditedCell.getNewValue());
            }
            else{
                Alert art = new Alert(Alert.AlertType.ERROR);
                art.setTitle("Validimi");
                art.setHeaderText(null);
                art.setContentText("Kontrollo te dhenat e shenuara tek Publikuesi");
                art.show();
            }
        }

        public void changedNumriKopjeveCellEvent(TableColumn.CellEditEvent<Libri , Byte> edditedCell){
            Libri l = tabela.getSelectionModel().getSelectedItem();
            if(edditedCell.getNewValue().toString().matches("[0-9]+")) {
                l.setNumriKopjeve(edditedCell.getNewValue());
            }
            else{
                Alert art = new Alert(Alert.AlertType.ERROR);
                art.setTitle("Validimi");
                art.setHeaderText(null);
                art.setContentText("Kontrollo te dhenat e shenuara tek NumriKopjeve!");
                art.show();
            }
        }


        public void FireTableDataChanges() {
            applyContent.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ObservableList<Libri> listaLibrave = tabela.getItems();
                    for (Libri l : listaLibrave) {
                        try {
                            AlertEditConfirmation(l);
                        } catch (BibliotekaException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        public void AlertEditConfirmation(Libri l) throws BibliotekaException {

                if(SelectedRowValidationWhenUpdate()) {

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
            }
                else {
                    Alert art = new Alert(Alert.AlertType.ERROR);
                    art.setTitle("Validimi");
                    art.setHeaderText(null);
                    art.setContentText("Ju lutem validoni te dhenat!");
                    art.show();
            }
        }

        public void EdittingCellValueFactory() {
            col_isbn.setCellFactory(TextFieldTableCell.forTableColumn());
            col_kategoria.setCellFactory(TextFieldTableCell.forTableColumn());
            col_kopje.setCellFactory(TextFieldTableCell.<Libri, Byte>forTableColumn(new ByteStringConverter()));
            col_publikuesi.setCellFactory(TextFieldTableCell.forTableColumn());
            col_titulli.setCellFactory(TextFieldTableCell.forTableColumn());
        }

        public boolean SelectedRowValidationWhenUpdate(){
            Libri l = tabela.getSelectionModel().getSelectedItem();
            if(!l.getEmri().matches("[a-zA-Z',\\s]*") || !l.getIsbn().matches("[0-9-]+") || !l.getKategoria().matches("[a-zA-Z',\\s]*")
                    || !l.getPublikuesi().matches("[a-zA-Z',\\s]*") || !l.getNumriKopjeve().toString().matches("[0-9]+")){
                return false;
            }
            return true;
        }


}
