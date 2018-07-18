
package ui;

import databag.Lid;
import datatype.Geslacht;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import exception.DBException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import transactie.LidTrans;

/**
 * FXML Controller class
 *
 * @author Lamotte Tom
 */
public class LedenBeheerController1 implements Initializable {

    // referentie naar VIVESbike (main)
    private VIVESbike parent;
   
   
    
    @FXML
    TextField tfRijksregisternummer;
    
    @FXML
    TextField tfNaam;
   
    @FXML
    Button buLidToevoegen;
    
    @FXML
    TextField tfEmail;
    
    @FXML
    TextField tfTelefoon;
    
    @FXML
    Label laErrorBericht;
    
    @FXML
    TableColumn tcNaam;
    
    @FXML
    TableColumn tcVoornaam;
    
    @FXML
    TableColumn tcRijksregisternummer;
    
    @FXML
    TableView<Lid> taOverzicht;
    
    @FXML
    DatePicker dfDatum;
    
    @FXML
    CheckBox cbtStatus;
    
    @FXML
    RadioButton manBtn;
   
    @FXML
    RadioButton vrouwBtn;
    
    @FXML
    TextArea tfOpmerkingen;
    
    @FXML
    Button BuLidToevoegen;
    
    @FXML
    Button BuLidUitschrijven;
    
    @FXML
    Button BuLidWijzigen;
    
    @FXML
    Button BuOpslaan;
    
    @FXML
    Button BuAnnuleren;
    
    @FXML
    TextField tfVoornaam;
    boolean toevoegen =true;
   @Override
    public void initialize(URL location, ResourceBundle resources){
     laErrorBericht.setText("");
     Tooltip tool = new Tooltip("wissen");
     tool.setStyle("-fx-background-color: Blue; -fx-text-fill:white;");
     tool.setContentDisplay(ContentDisplay.RIGHT);
     Label label = new Label("X");
     label.setStyle("-fx-text-fill:red;");
     tool.setGraphic(label);
     BuAnnuleren.setTooltip(tool);
     tcVoornaam.setCellValueFactory(new PropertyValueFactory<>("voornaam"));
     tcNaam.setCellValueFactory(new PropertyValueFactory<>("naam"));
     tcRijksregisternummer.setCellValueFactory(new PropertyValueFactory<>("rijksregisternummer"));
     LidTrans lTrans = new LidTrans();
     try {
                
                ArrayList<Lid>leden = lTrans.zoekAlleLeden();
                ObservableList<Lid> rekeningen = FXCollections.observableArrayList(leden);
                taOverzicht.setItems(rekeningen);
            } catch (ApplicationException ae) {
                laErrorBericht.setText(ae.getMessage());
            } catch (DBException ae) {
                laErrorBericht.setText("onherstelbare fout: " + ae.
                  getMessage());
            } catch(Exception e){
                laErrorBericht.setText("onherstelbare fout: " +e.getMessage());
            }
         }
     
    @FXML
    public void wijzigenLid(ActionEvent event) throws Exception {
       
        BuOpslaan.setVisible(true);
        laErrorBericht.setText("");
        Lid lid = taOverzicht.getSelectionModel().getSelectedItem();
        if (lid == null) {
            laErrorBericht.setText("Gelieve een lid te selecteren");
        }else{
            try{
            tfRijksregisternummer.setText(lid.getRijksregisternummer());
            tfNaam.setText(lid.getNaam());
            tfVoornaam.setText(lid.getVoornaam());
            tfEmail.setText(lid.getEmail());
            tfTelefoon.setText(lid.getTelNr());
            dfDatum.setValue(lid.getStartDatumLidmaatschap());
            if(lid.getEindDatumLidmaatschap()!= null){
                cbtStatus.setSelected(true);
            } else cbtStatus.setSelected(false);
            if(lid.getGeslacht()==Geslacht.M){
                manBtn.setSelected(true);
            } else vrouwBtn.setSelected(true);
            tfOpmerkingen.setText(lid.getOpmerkingen());
            toevoegen=false;
        }catch(Exception e){
                e.getMessage();
         }
            
        }
    }
    
       @FXML
       public void bewarenLid(ActionEvent event) throws ApplicationException, Exception{
                LidTrans l = new LidTrans();
                Lid lid = new Lid();
                try{
                this.alleVeldenIngevuld();
                lid.setRijksregisternummer(new Rijksregisternummer(tfRijksregisternummer.getText()));
                lid.setVoornaam(tfVoornaam.getText());
                lid.setNaam(tfNaam.getText());
                lid.setEmail(tfEmail.getText());
                lid.setTelNr(tfTelefoon.getText());
                if(manBtn.isSelected()){
                    lid.setGeslacht(Geslacht.M);
                }else lid.setGeslacht(Geslacht.V);
                if(cbtStatus.isSelected()){
                lid.setEindeDatumLidmaatschap(LocalDate.now());
                }else {lid.setEindeDatumLidmaatschap(null);
                }
                lid.setStartDatumLidmaatschap(dfDatum.getValue());
                lid.setOpmerkingen(tfOpmerkingen.getText());
                if(tfOpmerkingen.getText().equals("")){
                    lid.setOpmerkingen(null);
                }      
                }catch(ApplicationException ex){
                 laErrorBericht.setText(ex.getMessage());
              }catch(Exception e){
                  laErrorBericht.setText(e.getMessage());
              }finally{
                 if(toevoegen==true){ 
                    l.toevoegenLid(lid);
                    taOverzicht.getItems().add(lid);
                    laErrorBericht.setText("Lid " +lid.getVoornaam()+ " "+lid.getNaam()+ " is toegevoegd");
                   
                    tfRijksregisternummer.setText("");
                    tfVoornaam.setText("");
                    tfNaam.setText("");
                    tfEmail.setText("");
                    tfTelefoon.setText("");
                    tfOpmerkingen.setText("");
                    cbtStatus.setSelected(false);
                     laErrorBericht.setText("");
                    BuOpslaan.setVisible(false);
                 }else{
                    Lid vroeger =taOverzicht.getSelectionModel().getSelectedItem();
                    taOverzicht.getItems().remove(vroeger);
                    taOverzicht.getItems().add(lid);
                    l.wijzigenLid(lid);
                    toevoegen=true;
                    tfRijksregisternummer.setText("");
                    tfVoornaam.setText("");
                    tfNaam.setText("");
                    tfEmail.setText("");
                    tfTelefoon.setText("");
                    tfOpmerkingen.setText("");
                    cbtStatus.setSelected(false);
                    laErrorBericht.setText("");
                    BuOpslaan.setVisible(false);
                }
              }
           }
       
        @FXML
        public void annulerenLid(ActionEvent event){
              laErrorBericht.setText("");
              tfRijksregisternummer.setText("");
              tfVoornaam.setText("");
              tfNaam.setText("");
              tfTelefoon.setText("");
              tfEmail.setText("");
              tfOpmerkingen.setText("");
              dfDatum.setValue(null);
              cbtStatus.setSelected(false);
          }
          
          
        @FXML
         public void uitschrijvenLid(ActionEvent event) throws ApplicationException,DBException,Exception {
         laErrorBericht.setText("");
         //een lid kan zich maar uitschrijven als er 1 geselecteerd is in de tabel
         Lid i = taOverzicht.getSelectionModel().getSelectedItem();
         if(i!= null){
             LidTrans p = new LidTrans();
             i.setEindeDatumLidmaatschap(LocalDate.now());
             try{
                 //lid uitschrijven uit database
                  p.uitschrijvenLid(i.getRijksregisternummer());
                  }catch(ApplicationException a){
                   laErrorBericht.setText(a.getMessage());
                }catch(DBException a){
                  laErrorBericht.setText("onherstelbare fout: " +a.getMessage());
            }catch(Exception e){
                 laErrorBericht.setText(e.getMessage());
              }
        } else {
           laErrorBericht.setText("Er is geen lid geselecteerd");
              }
         }

         @FXML
         public void toevoegenLid() throws ApplicationException,DBException,Exception{
          LidTrans lt = new LidTrans();
          Lid l = new Lid();
          BuOpslaan.setVisible(false);
             try{
                this.alleVeldenIngevuld();
                l.setRijksregisternummer(new Rijksregisternummer(tfRijksregisternummer.getText()));
                l.setVoornaam(tfVoornaam.getText());
                l.setNaam(tfNaam.getText());
                l.setEmail(tfEmail.getText());
                l.setTelNr(tfTelefoon.getText());
                if(manBtn.isSelected()){
                    l.setGeslacht(Geslacht.M);
                }else l.setGeslacht(Geslacht.V);
                if(cbtStatus.isSelected()){
                l.setEindeDatumLidmaatschap(LocalDate.now());
                }else l.setEindeDatumLidmaatschap(null);
                l.setStartDatumLidmaatschap(dfDatum.getValue());
                l.setOpmerkingen(tfOpmerkingen.getText());
                if(tfOpmerkingen.getText().equals("")){
                    l.setOpmerkingen(null);
                } 
                lt.toevoegenLid(l);
                taOverzicht.getItems().add(l);
                 laErrorBericht.setText("Lid " +l.getVoornaam()+ " "+l.getNaam()+ " is toegevoegd");
                tfRijksregisternummer.setText("");
                tfVoornaam.setText("");
                tfNaam.setText("");
                tfEmail.setText("");
                tfTelefoon.setText("");
                tfOpmerkingen.setText("");
                cbtStatus.setSelected(false);
           }catch(DBException e){
              laErrorBericht.setText("onherstelbare fout: "+e.getMessage());
           }catch(ApplicationException e){
                  laErrorBericht.setText(e.getMessage());
          }catch(Exception e){
              laErrorBericht.setText(e.getMessage());
          }
        }
             
           private void alleVeldenIngevuld() throws ApplicationException{
            if (tfRijksregisternummer.getText().trim().equals("")){
              throw new ApplicationException("Rijksregisternummer mag niet leeg zijn.");
            }
           if(tfVoornaam.getText().trim().equals("")){
             throw new ApplicationException("Voornaam mag niet leeg zijn");
           }
           if (tfNaam.getText().trim().equals("")){
              throw new ApplicationException("Naam mag niet leeg zijn.");
           }
           
           if (tfEmail.getText().trim().equals("")){
              throw new ApplicationException("Email mag niet leeg zijn.");
           }
           if (tfTelefoon.getText().trim().equals("")){
              throw new ApplicationException("Telefoon mag niet leeg zijn");
           }
        }
         
         
     /**
     * Referentie naar parent (start) instellen
     *
     * @param p
     */
    public void setParent(VIVESbike p) {
        parent = p;
    }

}