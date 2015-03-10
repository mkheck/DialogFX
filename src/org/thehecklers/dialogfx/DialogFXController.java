package org.thehecklers.dialogfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rafael Nunes on 06/10/14.
 *
 */
public class DialogFXController implements Initializable {

    @FXML
    private ImageView icon;
    @FXML
    private Label message;
    @FXML
    private HBox buttonHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {}

    public HBox getButtonHBox() {
        return buttonHBox;
    }

    public void setButtonHBox(HBox buttonHBox) {
        this.buttonHBox = buttonHBox;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public Label getMessage() {
        return message;
    }

    public void setMessage(Label message) {
        this.message = message;
    }
}
