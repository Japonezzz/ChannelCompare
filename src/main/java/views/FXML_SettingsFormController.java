package views;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FXML_SettingsFormController implements Initializable {

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
    void OnActionBtnChange(ActionEvent event) {

    }

    @FXML
    void OnActionChkCache(ActionEvent event) {

    }

    @FXML
    void OnActionChkTime(ActionEvent event) {

    }

    @FXML
    void OnActionToMainForm(ActionEvent event) {

    }



    public void initialize(URL location, ResourceBundle resources) {

    }
}