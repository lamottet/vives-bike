
package databag;

import datatype.Geslacht;
import datatype.Rijksregisternummer;
import java.time.LocalDate;
/**
 *
 * @author Lamotte Tom
 */
public class Lid {
    private Rijksregisternummer rijksregisternummer;
    private String naam;
    private String voornaam;
    private Geslacht geslacht;
    private String telefoonnummer;
    private String email;
    private LocalDate startLidmaatschap;
    private LocalDate eindeLidmaatschap;
    private String opmerkingen;
   
    
    public Lid() {
        
    }
    
    public Lid(Rijksregisternummer rijksregisternummer, String naam, String voornaam,Geslacht geslacht,
           String telefoonnummer, String email, LocalDate startLidmaatschap,String opmerkingen){
            
        this.rijksregisternummer = rijksregisternummer;
        this.naam = naam;
        this.voornaam = voornaam;
        this.geslacht = geslacht;
        this.telefoonnummer = telefoonnummer;
        this.email = email;
        this.startLidmaatschap = startLidmaatschap;
        this.opmerkingen = opmerkingen;
        
    }
    
     public String getRijksregisternummer() {
         if(rijksregisternummer == null){
             return null;
         }else{
             return rijksregisternummer.getRijksregisternummer();
             }
         }
     
      public void setRijksregisternummer(Rijksregisternummer rijksnr){
      rijksregisternummer = rijksnr;
      }
      
      public String getNaam() {
          return naam;
      }
      public void setNaam(String naam) {
          this.naam = naam;
      }
      
      public String getVoornaam(){
          return voornaam;
      }
      
      public void setVoornaam(String voornaam){
          this.voornaam = voornaam;
      }
      
      public Geslacht getGeslacht() {
          return geslacht;
      }
      
      public  void setGeslacht(Geslacht g) {
          geslacht = g;
      }
      
      public String getTelNr(){
          return telefoonnummer;
      }
      
      public void setTelNr(String tel){
          telefoonnummer = tel;
      }
      
      public String getEmail(){
          return email;
      }
      public void setEmail(String email){
          this.email = email;
      }
      
      public LocalDate getStartDatumLidmaatschap(){
          return startLidmaatschap;
      }
      
      public void setStartDatumLidmaatschap(LocalDate startDatum){
          startLidmaatschap = startDatum;
      }
      
      public LocalDate getEindDatumLidmaatschap(){
          return eindeLidmaatschap;
      }
      
      public void setEindeDatumLidmaatschap(LocalDate eindDatum){
          eindeLidmaatschap = eindDatum;
      }
             
      public String getOpmerkingen(){
          return opmerkingen;
      }
      
      public void setOpmerkingen(String opmerkingen){
          this.opmerkingen = opmerkingen;
      }
    }
      
      
