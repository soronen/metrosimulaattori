package application.controller;

import application.simu.framework.IMoottori;
import application.simu.framework.Tapahtuma;
import application.simu.model.OmaMoottori;
import application.simu.model.Palvelupiste;
import application.simu.model.TapahtumanTyyppi;
import application.view.IVisualisointi;
import application.view.graphviewcontroller;

/**
 * IKontrolleri-luokka, on Kontrolleri-luokan rajapinta.
 * Hieman turha kun meillä on vain 1 kontrolleri..
 *
 * @author Eetu Soronen, Emil Ålgars
 * @version 1
 */
public interface IKontrolleri {
    /**
     * Hakee moottorin, asettaa sen parametrit, asettaa simulaattorin kellon nollaan ja käynnistää simulaation.
     * Jos simu on jo käynnissä, luo virheilmoitusponnahdusikkunan.
     */
    void kaynnistaSimulointi();

    /**
     * Asettaa moottorin parametrit. Metron ja aseman kapasiteetit sekä saapumisen ja palvelupisteiden käsittelyajan odotusarvot ja varianssit.
     */
    void asetaMoottorinParametrit();

    /**
     * Kutsuu setKaynnissa(false) -metodia.
     * Jos moottori != null asettaa simlointiajan nollaksi ja poistaa simulaattorin.
     */
    void resetSimulator();

    /**
     * pysäyttää!!1! ⛔🚫🛑🚫  simulaattorin kesken asetmmalla simulointiajan nollaan.
     * asettaa moottorin null arvoksi pienen viiveen kuluttua tästä, kun muutkin säikeet ovat saaneet kuulla uutiset
     * jotta konsoliin ei tulisi virheilmoituksia
     */
    void stopSimulation();

    /**
     * Palauttaa moottorin. Jos moottori = null, luo sen.
     *
     * @return OmaMoottori-olio, jonka saapmuisen jakauma on asetettu (arrivalMean ja arrivalVariance);
     */
    IMoottori getMoottori();

    /**
     * Palauttaa moottorin palvelupisteet
     *
     * @return Palvelupiste[]-taulukko, joka sisältää moottorin palvelupisteet. [0] = Entrance, [1] = Sales, [2] = Check, [3] = Metro
     */
    Palvelupiste[] getPalvelupisteet();

    /**
     * Nopeuttaa simulaattoria laskemalla simulaattorin viivettä 10%:lla.
     */
    void nopeuta();

    /**
     * Korottaa mottorin viivettä 10%:lla ja lisää tulokseen vielä ykkösen.
     * Ykkönen lisätään, jotta viive nousee nollastakin.
     */
    void hidasta();

    /**
     * OmaMoottorin kutsuma metodi, joka kutsuu UI:n päivitäUI()-metodia jokaisen tapahtuman käsittelyn yhteydessä.
     * Vastaa käyttöliittymän päivittämisestä.
     *
     * @param t Tapahtuma-olio, joka sisältää tiedon tapahtuneesta tapahtumasta.
     */
    void paivitaUI(Tapahtuma t);

    /**
     * Asettaa simulaattorin keston moottorissa ja tallentaa sen kontrollerin simukesto-muuttujaan.
     *
     * @param simukesto asetetaan simulaattorin kestoksi
     */
    void setsimulaattorinKesto(int simukesto);

    /**
     * Asettaa simulaattorin viiveen parametrina annettuun arvoon.
     *
     * @param simuviive long arvo, joka asetetaan simulaattorin viiveeksi.
     */
    void setSimulaattorinViive(int simuviive);

    /**
     * Palauttaa metro-palvelupisteen kapasiteetin.
     *
     * @return Metro-palvelupisteen kapasiteetti int-arvona.
     */
    int getMetronKapasiteetti();

    /**
     * Asettaa metro-palvelupisteen kapasiteetin parametrina annettuun arvoon.
     *
     * @param metronKapasiteetti int-arvo, joka asetetaan metro-palvelupisteen kapasiteetiksi.
     */
    void setMetronKapasiteetti(int metronKapasiteetti);

    /**
     * Palauttaa aseman-kapasiteetin.
     * Jos asemassa on tätä lukua enemmän asiakkaita, entrance-palvelupiste ei vastaanota asiakkaita.
     *
     * @return aseman kapasiteetti (int)
     */
    int getAsemanKapasiteetti();

    /**
     * Asettaa aseman kapasiteetin parametrina annettuun arvoon.
     * Jos asemassa on tätä lukua enemmän asiakkaita, entrance-palvelupiste ei vastaanota asiakkaita.
     *
     * @param asemanKapasiteetti aseman kapasiteetti (int)
     */
    void setAsemanKapasiteetti(int asemanKapasiteetti);

    /**
     * Palauttaa asemassa olevat asiakkaat (entrance-palvelupisteen läpi menneet asiakkaat - metro-palvelupisteen käsittelemät asiakkaat)
     *
     * @return asemassa olevien asiakkaiden lukumäärä (int)
     */
    int getAsiakkaatAsemassa();

    /**
     * Palauttaa metro-palvelupisteen käsittelemät asiakkaat.
     *
     * @return palvellut asiakkaat (int)
     */
    int getPalvellutAsaiakkaat();

    /**
     * Palauttaa simulaattorin tämänhetkisen viiveen
     *
     * @return simulaattorin viive (long)
     */
    long getViive();

    /**
     * setteri
     *
     * @param kaynnissa {@link #kaynnissa}
     */
    void setKaynnissa(boolean kaynnissa);

    /**
     * getteri (huonosti nimetty)
     *
     * @return kaynnissa {@link #kaynnissa}
     */
    boolean onkoKaynnissa();

    /**
     * getteri. Prosenttiarvo, kuinka monta asiakasta hyppää lipunmyynnin ohi.
     *
     * @return % asiakkaista, jotka hyppäävät lipunmyynnin ohi.
     */
    int getMobiililippujakauma();

    /**
     * setteri. Prosenttiarvo, kuinka monta asiakasta hyppää lipunmyynnin ohi.
     *
     * @param % asiakkaista, jotka hyppäävät lipunmyynnin ohi.
     */
    void setMobiililippujakauma(int mobiililippujakauma);

    /**
     * Asettaa sisääkäynti-palvelupisteen käsittelyajan normaalijakauman odotusarvon ja varianssin.
     *
     * @param mean     Odotusarvo
     * @param variance Varianssi
     */
    void setEntranceJakauma(int mean, int variance);

    /**
     * Asettaa lipunmyynti-palvelupisteen käsittelyajan normaalijakauman odotusarvon ja varianssin.
     *
     * @param mean     Odotusarvo
     * @param variance Varianssi
     */
    void setSalesJakauma(int mean, int variance);

    /**
     * Asettaa lipuntarkastus-palvelupisteen käsittelyajan normaalijakauman odotusarvon ja varianssin.
     *
     * @param mean     Odotusarvo
     * @param variance Varianssi
     */
    void setCheckJakauma(int mean, int variance);

    /**
     * Asettaa metro-palvelupisteen käsittelyajan normaalijakauman odotusarvon ja varianssin.
     *
     * @param mean     Odotusarvo
     * @param variance Varianssi
     */
    void setMetroJakauma(int mean, int variance);

    /**
     * Arvot tallennetaan kontrolleriin ja niitä käytetään, kun luodaan uusi moottori.
     * OmaMoottorin konstruktori käyttää näitä luodakseen saapumisgeneraattorin (joka luo uusia asiakkaita normaalijakauman mukaisesti)
     *
     * @param mean     Normaalijakauman odotusarvo
     * @param variance Normaalijakauman varianssi
     */
    void setArrivalJakauma(int mean, int variance);

    /**
     * Tallentaa kontrolleriin palvelupisteidne käsittelyajan normaalijakauman odotusarvon ja varianssin.
     * Jakaumat asetataan simulaattorille käynnistyksen yhteydessä.
     *
     * @param tt       Tapahtumaa vastaava palvelupiste. (entrance, ticketsales, ticketcheck, metro)
     * @param mean     Normaalijakauman odotusarvo
     * @param variance Normaalijakauman varianssi
     */
    void setPPJakauma(TapahtumanTyyppi tt, int mean, int variance);

    /**
     * Palauttaa palvelupisteen tämänhetkisen tilan, eli käsitteleekö se asiakkaita juuri nyt?
     *
     * @param palvelupiste Tapahtumaa vastaava palvelupiste. (entrance, ticketsales, ticketcheck, metro)
     * @return onko palvelupiste varattu? true / false
     */
    boolean onkoPPVarattu(TapahtumanTyyppi palvelupiste);

    /**
     * Palauttaa palvelupisteen jonon pituuden tällä hetkellä.
     *
     * @param palvelupiste Tapahtumaa vastaava palvelupiste. (entrance, ticketsales, ticketcheck, metro)
     * @return
     */
    int getPPjononpituus(TapahtumanTyyppi palvelupiste);

    /**
     * Palauttaa keskimääräisen jononkeston palvelupisteessä.
     *
     * @param palvelupiste Tapahtumaa vastaava palvelupiste. (entrance, ticketsales, ticketcheck, metro)
     * @return
     */
    double getPPkeskijonoaika(TapahtumanTyyppi palvelupiste);

    /**
     * Palauttaa palvelupisteen käsittelemien asiakkaiden lukumäärän.
     *
     * @param palvelupiste Tapahtumaa vastaava palvelupiste. (entrance, ticketsales, ticketcheck, metro)
     * @return
     */
    int getPPpalvellutAsiakkaat(TapahtumanTyyppi palvelupiste);

    /**
     * Palauttaa palvelupisteen käsittelemien asiakkaiden keskimääräisen käsittelyajan. (palvelun keston keskiaika)
     *
     * @param palvelupiste Tapahtumaa vastaava palvelupiste. (entrance, ticketsales, ticketcheck, metro)
     * @return
     */
    double getPPkeskiarvoaika(TapahtumanTyyppi palvelupiste);

    /**
     * Palauttaa palvelupisteen generaattorin odotus ja varianssiarvot
     *
     * @param tt TapahtumanTyyppi, joka vastaa palvelupistettä
     * @return int[2] taulukon, jossa i[0] = odotusarvo ja i[1] = varianssi
     */
    int[] getPPJakauma(TapahtumanTyyppi tt);

    void setUi(IVisualisointi iv);

    /**
     * Tallentaa simulaattorin asetukset ja loppuarvot ensiksi olioksi ja sitten tietokantaan.
     * Kuitenkin jos simulaattori on keskeytetty ennenaikaisesti, ei tee mitään.
     *
     * @param mtr OmaMoottori-olio, jonka parametrit ja palvelupisteet tallennetaan.
     */
    void tallenaEntity(OmaMoottori mtr);

    void asetachart(graphviewcontroller i, int x);

    void initchart(graphviewcontroller i);

    /**
     * Asettaa Taulukko ikkunassa sijaitsevan listviewein
     *
     * @param i referenssi graphviewcontrolleriin
     */
    void dChart(graphviewcontroller i);
}
