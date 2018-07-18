
package database.connect;
import java.util.Properties;
import java.io.FileInputStream;
/**
 *
 * @author Lamotte Tom
 */
public class DBProp {
    private static String dbUrl;
    private static String driver;
    private static String login;
    private static String paswoord;
 
    private DBProp() {
        Properties appProperties = new Properties();
        try(FileInputStream in = new FileInputStream("DB.properties")){
            appProperties.load(in);
            
            dbUrl = appProperties.getProperty("dbUrl");
            driver = appProperties.getProperty("driver");
            login = appProperties.getProperty("login");
            paswoord = appProperties.getProperty("paswoord");
            
        }catch(java.io.IOException ex){
            System.out.println("Properties file niet gevonden");
        }
    }
       public static String getDBUrl() {
           if(dbUrl == null){
               DBProp db = new DBProp();
           }
           return dbUrl;
       }
        
       public static String getDriver(){
           if(driver == null){
               DBProp db = new DBProp();
           }
           return driver;
       }   
       
       public static String getLogin(){
           if(login == null){
               DBProp db = new DBProp();
           }
           return login;
       }
       public static String getPaswoord(){
           if(paswoord == null){
               DBProp db = new DBProp();
           }
           return paswoord;
       }
           
           
           
           
           
       }

