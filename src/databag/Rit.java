
package databag;

import datatype.Rijksregisternummer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 *
 * @author Lamotte Tom
 */
public class Rit {
    private Integer id;
    private LocalDateTime starttijd;
    private LocalDateTime eindtijd;
    private BigDecimal prijs;
    private Rijksregisternummer lidRijksregisternummer;
    private int fietsRegistratienummer;
   

    
    public Rit(){
        
    }
    
    public Rit(Integer id, LocalDateTime starttijd, BigDecimal prijs, Rijksregisternummer nummer, int registratienummer)
    {
        this.id = id;
        this.starttijd = starttijd;
        this.prijs= prijs;
        lidRijksregisternummer = nummer;
        fietsRegistratienummer = registratienummer;

    }

    

    
    public Integer getRitID(){
        return id;
    }
    
    public void setRitID(Integer id){
        this.id =id;
    }
    
    public LocalDateTime getStarttijd(){
        return starttijd;
    }
    public void setStarttijd(LocalDateTime l){
        starttijd = l;
    }
    
    public LocalDateTime getEindtijd(){
        if (eindtijd == null) {
            System.out.println("Er is nog geen eindtijd voor deze rit");
        }
        return eindtijd;
    }
    
    public void setEindtijd(LocalDateTime eind){
        
        eindtijd = eind;
       
    }
    
    public BigDecimal getPrijs(){
        return prijs;
    }
    
    public void setPrijs(BigDecimal pr){
        prijs = pr;
    }
    public void setRijksregisternummer(Rijksregisternummer rr)
    {
        lidRijksregisternummer = rr;
    }
    
    public Rijksregisternummer getRijksregisternummer()
    {
        return lidRijksregisternummer;
    }
    
    public void setFietsregistratienummer(int nummer) {
        fietsRegistratienummer = nummer;
    }
    
    public int getFietsregistratienummer(){
        return fietsRegistratienummer;
    }
   
}
