
package transactie;

import databag.Lid;
import java.util.ArrayList;

/**
 *
 * @author Lamotte Tom
 */
public interface InterfaceLidTrans {

    void toevoegenLid(Lid l) throws Exception;

    void wijzigenLid(Lid teWijzigenLid) throws Exception;

    void uitschrijvenLid(String rr) throws Exception;

    Lid zoekLid(String rijksregisternummer) throws Exception;

    ArrayList<Lid> zoekAlleLeden() throws Exception;
}
