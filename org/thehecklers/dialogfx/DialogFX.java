package org.thehecklers.dialogfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Mark Heckler (mark.heckler@gmail.com, @HecklerMark)
 *
 * Updated by Rafael Nunes (rafaelnunes737@hotmail.com, @rafaellnunees)
 */
public final class DialogFX extends Stage {
    /**
     * Type of dialog box is one of the following, each with a distinct icon:
     * <p>
     *      ACCEPT = check mark icon
     * </p>
     * <p>
     *      ERROR = red 'X' icon
     * </p>
     * <p>
     *      WARNING = yellow 'triangle' (warning) icon
     * </p>
     * <p>
     *      INFO = blue 'i' (information) icon
     * </p>
     * <p>
     *      QUESTION = blue question mark icon
     * </p>
     * If no type is specified in the constructor, the default is INFO.
     */
    public enum Type { ACCEPT, ERROR, WARNING, INFO, QUESTION };

    private Type type;
    private AnchorPane popUp;
    private Stage stage;
    private Scene scene;

    private DialogFXController controller = new DialogFXController();

    private int buttonCancel = -1;
    private int buttonCount = 0;
    private int buttonSelected = -1;
    private List<String> stylesheets = new ArrayList<>();
    
    /**
     * Default constructor for a DialogFX dialog box. Creates an INFO box.
     * 
     * @see Type
     */
    public DialogFX() {
        try {
            initDialog(Type.INFO);
        }catch(IOException ex) {
            System.err.println("Unable to initialize the DialogFX");
            System.err.println("Error: " + ex.getMessage());
        }

        controller.setMessage(new Label(""));
        controller.setIcon(new ImageView());
        controller.setButtonHBox(new HBox(10));
    }
    
    /**
     * Constructor for a DialogFX dialog box that accepts one of the enumerated
     * types listed above.
     * 
     * @param t The type of DialogFX dialog box to create.
     * @see Type
     */
    public DialogFX(Type t) {
        try {
            initDialog(t);
        }catch(IOException ex) {
            System.err.println("Unable to initialize the DialogFX");
            System.err.println("Error: " + ex.getMessage());
        }

        controller.setMessage(new Label(""));
        controller.setIcon(new ImageView());
        controller.setButtonHBox(new HBox(10));
    }
    
    /**
     * Public method used to add custom buttons to a DialogFX dialog.
     * 
     * @param labels A list of String variables. While technically unlimited,
     * usability makes the practical limit around three.
     */
    public void addButtons(List<String> labels) {
        addButtons(labels, -1, -1);
    }

    
    /**
     * Public method used to add custom buttons to a DialogFX dialog.
     * Additionally, default and cancel buttons are identified so user can
     * trigger them with the ENTER key (default) and ESCAPE (cancel).
     * 
     * @param labels A list of String variables. While technically unlimited,
     * usability makes the practical limit around three.
     * @param defaultBtn Position within the list of labels of the button to 
     * designate as the default button.
     * @param cancelBtn Position within the list of labels of the button to 
     * designate as the cancel button.
     */
    public void addButtons(List<String> labels, int defaultBtn, int cancelBtn) {
        List<String> buttonLabels = labels;
        
        for (int i=0; i < labels.size(); i++) {
            final Button btn = new Button(labels.get(i));
            
            btn.setDefaultButton(i==defaultBtn);
            btn.setCancelButton(i==cancelBtn);

            if ( i == defaultBtn ) {
                Platform.runLater( new Runnable() {
                    @Override
                    public void run() {
                        btn.requestFocus();
                    }
                } );
            }
            
            buttonCancel = cancelBtn;
            
            btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent evt) {
                    buttonSelected = buttonLabels.indexOf(((Button) evt.getSource()).getText());
                    stage.close();
                }
            });
            controller.getButtonHBox().getChildren().add(btn);
        }

        /*BorderPane.setAlignment(buttonBox, Pos.CENTER);
        BorderPane.setMargin(buttonBox, new Insets(10,5,10,5));
        pane.setBottom(buttonBox);
        buttonCount = labels.size();  */
    }
    
    private void addOKButton() {
        List<String> labels = new ArrayList<>(1);
        labels.add("OK");
        
        addButtons(labels, 0, 0);
    }
    
    private void addYesNoButtons() {
        /*
         * No default or cancel buttons designated, by design.
         * Some cases would require the Yes button to be default & No to cancel,
         * while others would require the opposite. You as the developer can 
         * assign default/cancel Yes/No buttons using the full addButtons()
         * method if required. You have the power!
         */
        List<String> labels = new ArrayList<>(2);
        labels.add("Yes");
        labels.add("No");
        
        addButtons(labels);
    }
    
    /**
     * Allows developer to add stylesheet for DialogFX dialog, supplementing or 
     * overriding existing styling.
     * 
     * @param stylesheet String variable containing the name or path/name 
     * of the stylesheet to add to the dialog's scene and contained controls.
     */
    public void addStylesheet(String stylesheet) {
        //stylesheet = stylesheet;
        try {
            String newStyle  = this.getClass().getResource(stylesheet).toExternalForm();
            stylesheets.add(newStyle);
        } catch (Exception ex) {
            System.err.println("Unable to find specified stylesheet: " + stylesheet);
            System.err.println("Error setMessage: " + ex.getMessage());
        }
    }
    
    private void initDialog(Type t) throws IOException {
        stage = new Stage();

        popUp = FXMLLoader.load(getClass().getResource("DialogFX.fxml"));



        setType(t);
        stage.initModality(Modality.APPLICATION_MODAL);
        /*stage.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);*/
    }

    private void loadIconFromResource(String fileName) {
        Image imgIcon = new Image(getClass().getResourceAsStream(fileName));
        controller.getIcon().setPreserveRatio(true);
        controller.getIcon().setFitHeight(32);
        controller.getIcon().setImage(imgIcon);
    }
   
    /**
     * Sets the text displayed within the DialogFX dialog box. Word wrap ensures
     * that all text is displayed.
     * 
     * @param msg String variable containing the text to display.
     */
    public void setMessage(String msg) {
        controller.getMessage().setText(msg);
        controller.getMessage().setWrapText(true);
    }
   
    /**
     * Sets the modality of the DialogFX dialog box.
     * 
     * @param isModal Boolean. A true value = APPLICATION_MODAL, false = NONE.
     */
    public void setModal(boolean isModal) {
        stage.initModality((isModal ? Modality.APPLICATION_MODAL : Modality.NONE));
    }
    
    /**
     * Sets the text diplayed in the title bar of the DialogFX dialog box.
     * 
     * @param title String containing the text to place in the title bar.
     */
    public void setTitleText(String title) {
        stage.setTitle(title);
    }
    
    /**
     * Sets the Type of DialogFX dialog box to display.
     * 
     * @param typeToSet One of the supported types of dialogs.
     * @see Type
     */
    public void setType(Type typeToSet) {
        type = typeToSet;
    }
    
    private void populateStage() {
        String iconFile;
        
        switch ( type ) {
            case ACCEPT:
                iconFile = "dialog-accept.png";
                addOKButton();
                break;
            case ERROR:
                iconFile = "dialog-error.png";
                addOKButton();
                break;
            case WARNING:
                iconFile = "dialog-warning.png";
                addOKButton();
                break;
            case INFO:
                iconFile = "dialog-info.png";
                addOKButton();
                break;
            case QUESTION:
                iconFile = "dialog-question.png";
                break;
            default:
                iconFile = "dialog-info.png";
                break;
        }
        
        try {
            loadIconFromResource(iconFile);
        } catch (Exception ex) {
            System.err.println("Exception trying to load icon file: " + ex.getMessage());
        }

        //TODO: Clean this.
        /*BorderPane.setAlignment(icon, Pos.CENTER);
        BorderPane.setMargin(icon, new Insets(15, 5, 5, 5));
        pane.setLeft(icon);
        
        BorderPane.setAlignment(setMessage, Pos.CENTER);
        BorderPane.setMargin(setMessage, new Insets(5,5,5,5));
        pane.setCenter(setMessage);*/

        // Disabled this feature by now.
        /*for (int i=0;i<stylesheets.size();i++) {
            try {
                scene.getStylesheets().add(stylesheets.get(i));
            } catch (Exception ex) {
                System.err.println("Unable to load specified stylesheet: " + stylesheets.get(i));
                System.err.println(ex.getMessage());
            }
        }*/

        scene = new Scene(popUp, 300, 100);
        stage.setScene(scene);
    }
    
    /**
     * Displays the DialogFX dialog box and waits for user input.
     * 
     * @return The index of the button pressed.
     */
    public int showDialog() {
        populateStage();
        if ( type == Type.QUESTION ) {
            if ( buttonCount == 0 ) {
                addYesNoButtons();
            }
        }
        
        stage.setResizable(false);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.showAndWait();
        return ( buttonSelected == -1 ? buttonCancel : buttonSelected );
    }
}
