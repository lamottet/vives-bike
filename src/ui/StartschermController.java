
package ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


/**
 * FXML Controller class
 *
 * @author Lamotte Tom
 */
public class StartschermController implements Initializable {

    // referentie naar VIVESbike (main)
    private VIVESbike parent;
    
    @FXML
    Button buLeden;
    
    @FXML
    Button buFietsen;
    
    @FXML
    Button buRitten;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
       
 
  


    /**
     * Referentie naar parent (start) instellen
     *
     * @param parent referentie naar de runnable class die alle oproepen naar de
     * schermen bestuurt
     */
    public void setParent(VIVESbike p) {
        parent = p;
    //}
  //  @FXML
    //public void keuzePagina(ActionEvent event){
    //  parent.showlaadLeden();
    // }
}
}

