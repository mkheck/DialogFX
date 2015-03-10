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

        System.out.println(option);
    }
}
