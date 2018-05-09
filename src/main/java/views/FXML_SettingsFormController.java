package views;

import com.alibaba.fastjson.JSON;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Settings;

public class FXML_SettingsFormController implements Initializable {

    @FXML
    private AnchorPane settingsPane;

    @FXML
    private JFXButton btnToMainForm;

    @FXML
    private JFXCheckBox chkCache;

    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXButton btnChange;

    @FXML
    private Label lbl;

    @FXML
    private JFXCheckBox chkTime;

    @FXML
    private JFXButton btnSave;

    private File PathToCachFile;
    Settings s = new Settings();

    @FXML
    void OnActionBtnSave(ActionEvent event) throws IOException {
        File localCache = new File(txtField.getText());
        if (PathToCachFile.getCanonicalPath() != localCache.getCanonicalPath()) {
            PathToCachFile = localCache;
            PathToCachFile.createNewFile();
        }
        s = new Settings(chkCache.isSelected(), chkTime.isSelected(), PathToCachFile.getCanonicalPath());

        String toFile = JSON.toJSONString(s);
        File f = new File("resources/settings.txt");
        FileWriter writer = new FileWriter(f.getName());
        writer.write(toFile);
        writer.flush();

        OnActionToMainForm(event);
    }

    @FXML
    void OnActionBtnChange(ActionEvent event) {
        txtField.setDisable(false);
    }

    @FXML
    void OnActionChkCache(ActionEvent event) {
        if(chkCache.isSelected()) {
            txtField.setVisible(true);
            btnChange.setVisible(true);
            lbl.setVisible(true);
            return;
        }

        Visualization();
    }

    @FXML
    void OnActionChkTime(ActionEvent event) {

    }

    @FXML
    void OnActionToMainForm(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML_MainForm.fxml"));

        Parent root = fxmlLoader.load();
        FXML_MainFormController cont = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = (Stage)settingsPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    public void initialize(URL location, ResourceBundle resources) {
        File f = new File("resources/settings.txt");
        try {
            String jsonString = GetReadString(f);
            s = JSON.parseObject(jsonString, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PathToCachFile = new File(s.pathToFile);
        chkCache.setSelected(s.useCache);
        chkTime.setSelected(s.useTime);
        txtField.setText(s.pathToFile);

        Visualization();

        txtField.setDisable(true);
    }

    private String GetReadString(File f) throws IOException {
        String read="";
        FileReader reader = new FileReader(f.getName());
        {
            int c;
            while((c=reader.read())!=-1){

                read +=((char)c);
            }
        }
        return read;
    }

    private void Visualization() {
        if(!chkCache.isSelected()) {
            txtField.setVisible(false);
            btnChange.setVisible(false);
            lbl.setVisible(false);
        }
    }
}