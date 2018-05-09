package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
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
    Settings s;

    @FXML
    void OnActionBtnSave(ActionEvent event) throws IOException {
        File localCache = new File(txtField.getText());
        if (PathToCachFile.getCanonicalPath() != localCache.getCanonicalPath()) {
            PathToCachFile = localCache;
            PathToCachFile.createNewFile();
        }
        s = new Settings(chkCache.isSelected(), chkTime.isSelected(), PathToCachFile.getCanonicalPath());
    }

    @FXML
    void OnActionBtnChange(ActionEvent event) {
        txtField.setDisable(false);
    }

    @FXML
    void OnActionChkCache(ActionEvent event) {
        if(chkCache.isSelected()) {
            GetCacheFile();
            txtField.setVisible(true);
            btnChange.setVisible(true);
            lbl.setVisible(true);
            return;
        }
        txtField.setVisible(false);
        btnChange.setVisible(false);
        lbl.setVisible(false);
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
        txtField.setDisable(true);
        txtField.setVisible(false);
        btnChange.setVisible(false);
        lbl.setVisible(false);
    }

    private void GetCacheFile() {
        PathToCachFile = new File("resources/cache.txt");
        txtField.setText(PathToCachFile.getAbsolutePath());
    }
}