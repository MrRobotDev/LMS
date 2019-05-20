package controllers;

import entities.Libri;
import entities.ReferencaHuazime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import resourceFunctions.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class KryefaqjaController extends EntityManagerClass implements Initializable {

    private final ObservableList<PieChart.Data> details = FXCollections.observableArrayList();
    ObservableList<Libri> oblist = FXCollections.observableArrayList();

    @FXML
    public Pane rootPane;
    public TabPane contentTabPane;
    public BarChart<?, ?> huazimetChart;
    public PieChart lexuesitRegjistruarPieChart;
    public MenuItem regjistroLexuesMenuItem;
    public MenuItem shtoLibrinMenuItem;
    public MenuItem RaportetMenuItem;
    public Label TodayNewBooksNumber;
    public Label TodayBorrowedBooksNumber;
    public Label TodayNewReadersNumber;

    Dconnection source = new Dconnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series set = new XYChart.Series<>();

        set.getData().add(new XYChart.Data("Steve Jobs", 16));
        set.getData().add(new XYChart.Data("Elon Musk", 12));
        set.getData().add(new XYChart.Data("Alkemisti", 27));
        set.getData().add(new XYChart.Data("Kronike ne Gure", 18));
        set.getData().add(new XYChart.Data("Procesi", 30));
        set.getData().add(new XYChart.Data("Liri", 8));

        huazimetChart.getData().addAll(set);

        details.addAll(new PieChart.Data("Janar", 90));
        details.add(new PieChart.Data("Shkurt", 68));
        details.add(new PieChart.Data("Mars", 110));
        details.add(new PieChart.Data("Prill", 140));

        lexuesitRegjistruarPieChart.setData(details);
        lexuesitRegjistruarPieChart.setTitle("Regjistrimet e Lexuesve");
        lexuesitRegjistruarPieChart.setLegendSide(Side.BOTTOM);
        lexuesitRegjistruarPieChart.setLabelsVisible(true);
        lexuesitRegjistruarPieChart.setClockwise(false);
        lexuesitRegjistruarPieChart.setStartAngle(90);
        NewBooksUpdate();
        BorrowedBooksUpdate();
    }

    public void loadSecond(javafx.event.ActionEvent actionEvent) throws IOException {
       loadResource("../views/ShtoLibrin.fxml");
    }

    public void loadTableView(javafx.event.ActionEvent actionEvent) throws IOException {
        loadResource("../views/TabelaLibrit.fxml");
    }

    public void loadRegjistroLexuesForm(javafx.event.ActionEvent actionEvent) throws IOException {
        loadResource("../views/RegjistroLexues.fxml");
    }

    public void loadHuazimet(javafx.event.ActionEvent actionEvent)throws IOException{
        loadResource("../views/Huazimet.fxml");
    }

    public void loadLexuesiReports(javafx.event.ActionEvent actionEvent)throws IOException{
        loadResource("../views/RaportetLexuesi.fxml");
    }

    public void loadResource(String s)throws IOException{
        Parent loader = null;
        loader = FXMLLoader.load(getClass().getResource(s));
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(loader));
        mainStage.setResizable(false);
        mainStage.show();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void LoadHuazimetForm(javafx.event.ActionEvent actionEvent)throws IOException{
        loadResource("../views/ListaHuazimeve.fxml");
    }

    public void NewBooksUpdate(){
        short registered = 0;
        LibriInterface li = new LibriRepository();
        List<Libri> lst = (List<Libri>) em.createQuery("select l from Libri l").getResultList();
        for(Libri l : lst){
            if(l.getDataRegjistrimit().equals(LocalDate.now())){
                registered++;
            }
        }
        TodayNewBooksNumber.setText(String.valueOf(registered));
    }

    public void BorrowedBooksUpdate(){
        short borrowed = 0;
        ObjectInterface oi = new ObjectRepository();
        List<ReferencaHuazime> rh = (List<ReferencaHuazime>)em.createQuery("select rh from ReferencaHuazime rh").getResultList();
        for(ReferencaHuazime r : rh){
            if(r.getDataHuazimit().equals(LocalDate.now())){
                borrowed++;
            }
        }
        TodayBorrowedBooksNumber.setText(String.valueOf(borrowed));
    }
}
