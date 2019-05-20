package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import resourceFunctions.Dconnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.ResourceBundle;

public class RaportetLexuesiController implements Initializable {

    @FXML
    private AnchorPane repjortsAnchorPane;

    @FXML
    private TextField lexuesiTextField;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private Button showButton;

    @FXML
    private ImageView previousContent;

    @FXML
    private DatePicker toDatePicker;

    Dconnection source = new Dconnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ShowReport(){
        showButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ValidatedReport();
            }
        });
    }

    public void ValidatedReport(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        if(lexuesiTextField.getText().trim().equals("")){
            if(fromDatePicker.getValue()!=null && toDatePicker.getValue()!=null) {
                String sql = "select * from lexuesi where dataRegjistrimit between '" + sdf.format(Date.valueOf(fromDatePicker.getValue()))
                        + "' and '" + sdf.format(Date.valueOf(toDatePicker.getValue())) + "'";
                ReportTemplate(sql);
            }
            if(fromDatePicker.getValue()==null && toDatePicker.getValue()!= null){
                String sql = "select * from lexuesi where dataRegjistrimit < '" + sdf.format(Date.valueOf(toDatePicker.getValue()))+"'";
                ReportTemplate(sql);
            }
            if(fromDatePicker.getValue()!=null && toDatePicker.getValue()==null){
                String sql = "select * from lexuesi where dataRegjistrimit > '" + sdf.format(Date.valueOf(fromDatePicker.getValue()))+"'";
                ReportTemplate(sql);
            }
        }
            else if(!lexuesiTextField.getText().trim().isEmpty()){

            if(fromDatePicker.getValue()==null && toDatePicker.getValue()==null) {
                String sql = "select * from lexuesi where emri like '" + lexuesiTextField.getText() + "%' or mbiemri like '"
                        + lexuesiTextField.getText() + "%'";
                ReportTemplate(sql);
            }
            if(fromDatePicker.getValue()!=null && toDatePicker.getValue()==null) {
                String sql = "select * from lexuesi where (emri like '" + lexuesiTextField.getText() + "%' or mbiemri like '"
                        + lexuesiTextField.getText() + "%') and (dataRegjistrimit > '" +sdf.format(Date.valueOf(fromDatePicker.getValue()))+"')";
                ReportTemplate(sql);
            }
            if(fromDatePicker.getValue()==null && toDatePicker.getValue()!=null){
                String sql = "select * from lexuesi where (emri like '" + lexuesiTextField.getText() + "%' or mbiemri like '"
                        + lexuesiTextField.getText() +"%') and (dataRegjistrimit < '"
                        +sdf.format(Date.valueOf(toDatePicker.getValue()))+"')";
                ReportTemplate(sql);
            }
            if(fromDatePicker.getValue()!=null && toDatePicker.getValue()!=null){
                String sql = "select * from lexuesi where (emri like '"+lexuesiTextField.getText()+"%'" + "or mbiemri like '"
                        + lexuesiTextField.getText()+"%')" +" and (dataRegjistrimit between '"+sdf.format(Date.valueOf(fromDatePicker.getValue()))
                        +"' and '" + sdf.format(Date.valueOf(toDatePicker.getValue()))+"')";
                ReportTemplate(sql);
            }
        }
                if(lexuesiTextField.getText().trim().isEmpty() && fromDatePicker.getValue()==null && toDatePicker.getValue()==null){
                    Alert reportAl = new Alert(Alert.AlertType.ERROR);
                    reportAl.setTitle("Error");
                    reportAl.setHeaderText(null);
                    reportAl.setContentText("Ju lutemi , plotesoni fushat per te gjeneruar raport!");
                    reportAl.show();
                }
    }

    public void ReportTemplate(String SqlStatement){
        try {
            JasperDesign jd = JRXmlLoader.load("C:\\Users\\Thinkpad\\IdeaProjects\\Biblioteka\\src\\Reports\\Simple_Blue.jrxml");
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SqlStatement);
            jd.setQuery(query);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr , null , source.getCon());
            JasperViewer.viewReport(jp , false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void goBack()throws IOException{
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
}
