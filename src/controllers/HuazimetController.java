package controllers;

import entities.Lexuesi;
import entities.Libri;
import entities.ReferencaHuazime;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.JasperReportSource;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;
import resourceFunctions.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HuazimetController extends EntityManagerClass implements Initializable {

    LexuesiInterface li = new LexuesiRepository();
    ObjectInterface oi = new ObjectRepository();
    LibriInterface libriResource = new LibriRepository();

    @FXML
    private AnchorPane huazimiAnchorPane;

    @FXML
    private DatePicker dataHuazimitDatePicker;

    @FXML
    private TextField libriTextField;

    @FXML
    private Label huazimiFunditLabel;

    @FXML
    private TextField isbnTextField;

    @FXML
    private Button listaHuazimeveButton;

    @FXML
    private TextField lexuesiTextField;

    @FXML
    private Button regjistroHuaziminButton;

    @FXML
    private TextField referencaIdTextField;

    @FXML
    private DatePicker dataKthimitDatePicker;

    @FXML
    private Label idLabel;

    @FXML
    private Label emriLabel;

    @FXML
    private ImageView previousImageView;

    @FXML
    private Label mbiemriLabel;

    @FXML
    private ImageView reloadImageView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            lexuesiFieldAutoCompletion();
            libriFieldAutoCompletion();
        } catch (BibliotekaException e) {
            e.printStackTrace();
        }
        dateRefined();
        fieldsConstraints();
    }

    public void detailsLoad() throws Exception {
        reloadImageView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!lexuesiTextField.getText().isEmpty()) {
                    String[] fullName = lexuesiTextField.getText().split(" ");
                    Lexuesi detailed = (Lexuesi) em.createNamedQuery("Lexuesi.findByEmriDheMbiemri")
                            .setParameter("emri", fullName[0].replaceAll(" ", "")).setParameter("mbiemri", fullName[1].replaceAll(" ", ""))
                            .getSingleResult();
                    idLabel.setText(String.valueOf(detailed.getLexuesiId()));
                    emriLabel.setText(detailed.getEmri());
                    mbiemriLabel.setText(detailed.getMbiemri());
                    huazimiFunditLabel.setText("");
                    String lastDate = String.valueOf(em.createNamedQuery("ReferencaHuazime.findLexuesiID")
                            .setParameter("lexuesi", 1).getSingleResult());
                    huazimiFunditLabel.setText(lastDate);
                } else {
                    idLabel.setText("");
                    emriLabel.setText("");
                    mbiemriLabel.setText("");
                }
                isbnAndReferenceIdGenerator();
            }
        });
    }

    public void addReference() throws IOException {
        regjistroHuaziminButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (fieldsValidation()) {
                        Libri model = (Libri) em.createNamedQuery("Libri.findByEmri").setParameter("emri", libriTextField.getText()).getSingleResult();
                        ReferencaHuazime RH = new ReferencaHuazime();
                        RH.setLexuesiId(Integer.parseInt(idLabel.getText()));
                        RH.setDataHuazimit(Date.valueOf(dataHuazimitDatePicker.getValue()));
                        RH.setDataKthimit(Date.valueOf(dataHuazimitDatePicker.getValue().plusDays(30)));
                        RH.setLibriId(model.getLibriId());
                        oi.create(RH);
                            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                            confirm.setTitle("Konfirmim");
                            confirm.setHeaderText(null);
                            confirm.setContentText("Ju keni regjistruar me sukses huazimin");
                            confirm.showAndWait();
                    } else {
                        Alert warning = new Alert(Alert.AlertType.WARNING);
                        warning.setTitle("Verejtje!");
                        warning.setHeaderText(null);
                        warning.setContentText("Ju lutemi , validoni te dhenat!");
                        warning.showAndWait();
                    }
                } catch (BibliotekaException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadMain() throws IOException {
        previousImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Parent secondPane = FXMLLoader.load(getClass().getResource("../views/Kryefaqja.fxml"));
                    Stage x = new Stage();
                    x.setScene(new Scene(secondPane));
                    x.show();
                    Stage stage = (Stage) previousImageView.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fieldsConstraints() {
        int lastAutoIncrementedNumber = (int) em.createNamedQuery("ReferencaHuazime.nextNumber").getSingleResult() + 1;
        String nextAInumber = String.valueOf(lastAutoIncrementedNumber);
        referencaIdTextField.setText(nextAInumber);
        ///////////////////////////////////
        dataKthimitDatePicker.setEditable(false);
        dataHuazimitDatePicker.setEditable(false);
        referencaIdTextField.setEditable(false);
    }

    public void isbnAndReferenceIdGenerator() {
        if(!libriTextField.getText().isEmpty()) {
            Libri l = (Libri) em.createNamedQuery("Libri.findByEmri").setParameter("emri", libriTextField.getText()).getSingleResult();
            isbnTextField.setText(l.getIsbn());
        }
        else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Verejtje!");
            warning.setHeaderText(null);
            warning.setContentText("Ju lutemi , validoni te dhenat!");
            warning.showAndWait();
        }

    }

    public void dateRefined() throws DateTimeException {
        try {
            dataHuazimitDatePicker.setValue(LocalDate.now());
            dataKthimitDatePicker.setValue(LocalDate.now().plusDays(30));
        } catch (DateTimeException e) {
            e.printStackTrace();
        }
    }

    public void lexuesiFieldAutoCompletion() throws BibliotekaException {
        try {
            List<Lexuesi> lista = li.findAll();
            ArrayList<String> lexuesit = new ArrayList<String>();
            for (Lexuesi l : lista) {
                lexuesit.add(l.getEmri() + " " + l.getMbiemri());
            }
            TextFields.bindAutoCompletion(lexuesiTextField, lexuesit);
        } catch (BibliotekaException e) {
            e.getMessage();
        }
    }

    public void libriFieldAutoCompletion() throws BibliotekaException {
        try {
            List<Libri> lista = libriResource.findAll();
            ArrayList<String> librat = new ArrayList<>();
            for (Libri l : lista) {
                librat.add(l.getEmri());
            }
            TextFields.bindAutoCompletion(libriTextField, librat);
        } catch (BibliotekaException e) {
            e.getMessage();
        }
    }

    public boolean fieldsValidation() {
        if (lexuesiTextField.getText().isEmpty() || libriTextField.getText().isEmpty()
                || isbnTextField.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean CheckReference(){
        List<ReferencaHuazime> listaRH = null;
        return true;
    }
}

