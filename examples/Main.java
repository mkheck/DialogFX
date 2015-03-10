/**
 * <p>
 *
 * </p>
 * <p>
 * File created on 10/03/15.
 * </p>
 * @author Rafael Campos Nunes
 */

import javafx.application.Application;
import javafx.stage.Stage;
import org.thehecklers.dialogfx.DialogFX;


public class Main extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        DialogFX dialog = new DialogFX(DialogFX.Type.QUESTION);
        dialog.setTitleText("Question");
        dialog.setMessage("Does this dialog works?");
        int option = dialog.showDialog();

        if(option == 0) {
            DialogFX dialog1 = new DialogFX(DialogFX.Type.INFO);
            dialog1.setTitleText("Info");
            dialog1.setMessage("It seems that you've pressed yes.");
            dialog1.showDialog();
        }
        else {
            DialogFX dialog2 = new DialogFX(DialogFX.Type.WARNING);
            dialog2.setTitleText("Warning");
            dialog2.setMessage("WEEEEEIRDO, you've pressed no :c");
            dialog2.showDialog();
        }
    }
}
