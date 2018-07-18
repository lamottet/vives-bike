
package databag;
import datatype.Standplaats;
import datatype.Status;
/**
 *
 * @author Lamotte Tom
 */
public class Fiets {
    private Integer registratienummer;
    private Standplaats standplaats;
    private Status status;
    private String opmerking;
   
    
    public Fiets(){
    }
    
    public Fiets(Integer registratienummer, Standplaats standplaats, Status status,String opmerking)
    {
        this.registratienummer = registratienummer;
        this.standplaats = standplaats;
        this.status = status;
        this.opmerking = opmerking;
    }
  
    public Integer getRegistratienummer(){
        return registratienummer;
    }
    
    public void setRegistratienummer(Integer nummer){
        registratienummer = nummer;
    }
    
    public Standplaats getStandplaats(){
        return standplaats;
    }
        
    public void setStandplaats(Standplaats sp){
        standplaats = sp;
    }  
    
    public Status getStatus(){
        return status;
    }
    
    public void setStatus(Status status){
        this.status = status;
    }
    
    public String getOpmerking(){
        return opmerking;
    }
    
    public void setOpmerking(String opmerking){
       this.opmerking =opmerking;
    }
    public String toString(){
      return  "Registratienummer: " + getRegistratienummer() + "\n" + "Standplaats: " + getStandplaats() + "\n" + "Status: " + getStatus() + "\n" + "Opmerkingen: " +getOpmerking();
    }
 }
