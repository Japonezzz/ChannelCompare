package views;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLYouTubeController  implements Initializable {
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private AnchorPane YouTubeAnalyzePane;
    @FXML private Label comp_ch1_1;
    @FXML private Label comp_ch2_1;
    @FXML private Label comp_ch1_2;
    @FXML private Label med_res_inf_3;
    @FXML private Label med_res_inf_2;
    @FXML private Label med_res_inf_1;
    @FXML private Label comp_med_res_ch2_4;
    @FXML private Label comp_med_res_ch1_5;
    @FXML private Label med_res_inf_6;
    @FXML private Label comp_med_res_ch2_5;
    @FXML private Label comp_med_res_ch1_6;
    @FXML private Label med_res_inf_5;
    @FXML private Label comp_med_res_ch2_6;
    @FXML private Label med_res_inf_4;
    @FXML private JFXButton btn_comp_ch2;
    @FXML private Label comp_ch2_2;
    @FXML private Label comp_ch1_3;
    @FXML private Label comp_ch2_3;
    @FXML private Label comp_ch1_4;
    @FXML private Label comp_ch2_4;
    @FXML private Label comp_ch1_5;
    @FXML private Label comp_ch2_5;
    @FXML private Label inf_1;
    @FXML private JFXButton btn_comp_ch1;
    @FXML private Label inf_5;
    @FXML private Label inf_4;
    @FXML private Label inf_3;
    @FXML private Label inf_2;
    @FXML private Label comp_med_res_ch1_1;
    @FXML private Label comp_med_res_ch2_1;
    @FXML private Label comp_med_res_ch1_2;
    @FXML private Label comp_med_res_ch2_2;
    @FXML private Label comp_med_res_ch1_3;
    @FXML private Label comp_med_res_ch2_3;
    @FXML private Label comp_med_res_ch1_4;
    @FXML
    private JFXButton btn_toYouTubeMainDialog;
    @FXML
    void showYouTubeMainDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML_MainForm.fxml"));

        Parent root = fxmlLoader.load();
        FXML_MainFormController cont = fxmlLoader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage)YouTubeAnalyzePane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
