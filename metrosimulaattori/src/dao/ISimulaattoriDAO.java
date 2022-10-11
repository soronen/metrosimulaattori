package dao;

import entity.Simulaattori;

import java.util.List;

public interface ISimulaattoriDAO {
    /**
     * Lisää Simulator-olion tietokantaan
     *
     * @param s Simulaattori
     */
    void lisaaSimulaattori(Simulaattori s);

    /**
     * Hakee simulaattorin tietokannasta sen id:n perustella.
     *
     * @param id
     * @return Simulaattori-olio
     */
    Simulaattori haeSimulaattori(int id);

    /**
     * Poistaa Simulaattori-olion tietokannasta sen id:n perusteella.
     *
     * @param id
     */
    void poistaSimulaattori(int id);

    /**
     * Palauttaa kaikki tietokannan Simulator-oliot (jotka taas sisältävät kaikki Station ja ServicePoint -oliot..)
     *
     * @return List<Simulaattori> olio, joka sisältää kaikki tietokannan simulaattorit
     */
    List<Simulaattori> listaaSimulaattorit();
}
