package transactie;

import databag.Rit;
import java.util.ArrayList;

/**
 *
 * @author Lamotte Tom
 */
public interface InterfaceRitTrans {

    Integer toevoegenRit(Rit rit) throws Exception;

    void afsluitenRit(Integer id) throws Exception;

    ArrayList zoekAlleRitten() throws Exception;

    Rit zoekRit(Integer ritID) throws Exception;

    Integer zoekEersteRitVanLid(String rr) throws Exception;

    ArrayList zoekActieveRittenVanLid(String rr) throws Exception;

    ArrayList zoekActieveRittenVanFiets(Integer regnr) throws Exception;

}