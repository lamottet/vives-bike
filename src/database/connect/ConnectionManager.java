
package database.connect;

/**
 *
 * @author Lamotte Tom
 */
import exception.DBException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection getConnection() throws DBException{
        try{
            //laden van de driver
            Class.forName(DBProp.getDriver()).newInstance();
            Connection con = DriverManager.getConnection(DBProp.getDBUrl(),DBProp.getLogin(),DBProp.getPaswoord());
            return con;
        } catch (ClassNotFoundException | InstantiationException|IllegalAccessException|SQLException ex){
            throw new DBException("Connectie met de database mislukt" + ex);
        }

     }
}
