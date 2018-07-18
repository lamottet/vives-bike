
package ui;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Lamotte Tom
 */
public class VIVESbike extends Application {

    private final Stage stage = new Stage();
   

    @Override
    public void start(Stage primaryStage) throws Exception {
       
        showlaadLeden();
        stage.show();
    }

    public void showStartScherm() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Startscherm.fxml"));  
            Parent root = loader.load();
            StartschermController controller =(StartschermController)loader.getController();
            controller.setParent(this);
           
            Scene scene = new Scene(root);
      
            stage.setTitle("VIVESBike - Administratie");
            stage.setScene(scene);
            scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());
        
        } catch (IOException e) {
            System.out.println("SYSTEEMFOUT bij laden startscherm: " + e.getMessage());
          
        }
    }
  
   
    public void showlaadLeden() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("LedenBeheer_1.fxml"));
            Parent root = loader.load();
            LedenBeheerController1 c; 
            c = (LedenBeheerController1)loader.getController();
            c.setParent(this);
            
            Scene scene = new Scene(root); 
            stage.setTitle("Leden beheren");
            stage.setScene(scene);
            scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());

        } catch (IOException e) {
            System.out.println("SYSTEEMFOUT bij laden ledenbeheer: " + e.getMessage());
        }
    }
   
    public Stage getPrimaryStage() {
        return stage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
