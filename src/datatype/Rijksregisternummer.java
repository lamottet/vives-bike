
package datatype;

import exception.ApplicationException;

/**
 *
 * @author Lamotte Tom
 */
public class Rijksregisternummer {

   // public static Rijksregisternummer valueOf(String string) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   // }

    private String rijksregisternummer;
    
    public Rijksregisternummer(String rr)throws ApplicationException, Exception{
        //controle correct rijksregisternummer
        try{            
            int deel1 = Integer.parseInt(rr.substring(0, 9));
            int controlegetal = Integer.parseInt(rr.substring(9, 11));
            if((97-(deel1%97)==controlegetal) ||(97-((2000000000+deel1)%97)==controlegetal)){
               rijksregisternummer = rr; 
            }
            else{
                throw new Exception();
            }
        }
        catch(Exception e){
            throw new Exception("geen geldig rijksregisternummer");
        }
        
    }

    public String getRijksregisternummer() {
        return rijksregisternummer;
    }
    
}


