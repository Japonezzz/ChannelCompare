package views;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXML_MainFormController implements Initializable {
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private JFXButton btn_toYouTube;

    @FXML
    private JFXButton btn_toSettings;
    @FXML
    private AnchorPane YouTubeMainPane;

    @FXML
    void showYouTubeDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLYouTube.fxml"));

        Parent root = fxmlLoader.load();
        FXMLYouTubeController cont = fxmlLoader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage)YouTubeMainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void showSettingsForm(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML_SettingsForm.fxml"));

        Parent root = fxmlLoader.load();
        FXML_SettingsFormController cont = fxmlLoader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage)YouTubeMainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
