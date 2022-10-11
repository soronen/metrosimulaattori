package application.simu.model;

import java.util.LinkedList;

import application.eduni.distributions.ContinuousGenerator;
import application.simu.framework.Kello;
import application.simu.framework.Tapahtuma;
import application.simu.framework.Tapahtumalista;
import application.simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava

/**
 * Palvelupisteitä simulaattorissamme on neljä, jotka ovat Entrance, TicketSales, TicketCheck ja Metro.
 * Ne ovat toteutettu tässä luokassa.
 * Palvelupisteet ottavat asiakkaita jonosta ja käsittelevet niitä tietyin aikavälein.
 *
 * @author Eetu Soronen, Emil Ålgars
 * @version 1
 */
public class Palvelupiste {

	/**
	 * Lista sisältää kaikki Asiakas-oliot, joista palvelupiste ottaa aina pisimpään listalla olleen käsiteltäväksi kun se on vapaa.
	 */
	private LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus

	/**
	 * Määrittää palvelupisteen käsittelyajan jakauman.
	 */
	private ContinuousGenerator generator;


	/**
	 * Tapahtumalista sisältää TapahtumanTyypin ja sen alkamis- ja loppuajan.
	 */
	private Tapahtumalista tapahtumalista;


	/**
	 * Jokaisella palvelupisteellä on yksi tapahtumantyyppi,
	 * joka erottaa palvelupisteet toisistaan ja määrää mminkälaisia tapahtumia ne käsittelevät.
	 * Tapahtumantyyppi on enum, joka on määritelty TapahtumanTyyppi-luokassa.
	 */
	private TapahtumanTyyppi seuraavaTapahtumanTyyppi;

	/**
	 * Palvelupisteellä voi olla maksimikapasiteetti, että kuinka monta asiakasta ne voivat käsitellä samanaikaisesti.
	 * Esim. metro oletusarvoisesti käsittelee monta asiakasta kerrallaan, mutta sen käsittelyaika on erittäin pitkä.
	 */
	private int maxSize;

	/**
	 * Onko palvelupiste varattu, eli voiko se vastaanottaa asiakkaita palveltavaksi.
	 */
	private boolean varattu = false;

	/**
	 * Jokaisen asiakkaan jonon keston summa.
	 */
	private double kokonaisaikaJonotettu = 0;

	/**
	 * Palvelun aloitusaika.
	 */
	private double aloitusaika;

	/**
	 * Kokonaisaika, mitä jokainen asiaiakas on viettänyt palvelupisteen sisällä käsiteltävänä.
	 */
	private double kokonaisaikaPalvelupisteessa;

	/**
	 * Monta asiakasta on käynyt palvelupisteen läpi.
	 */
	private int palvelunro = 0;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.seuraavaTapahtumanTyyppi = tyyppi;

	}

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi , int maxSize){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.seuraavaTapahtumanTyyppi = tyyppi;
		this.maxSize = maxSize;
	}


	/**
	 * Lisää asiakkaan palvelupisteen jonoon.
	 * @param a Lisättävä Asiakas-olio.
	 */
	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);

	}

	/**
	 * Ottaa ensimmäisen asiakkaan palvelupisteen jonosta ja palauttaa sen.
	 * Jono on linked-list, eli jono toimii first come, first serve -periaatteella.
	 * @return Jonon ensimmäinen Asiakas-olio.
	 */
	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		palvelunro++;
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());

		varattu = true;
		double palveluaika = generator.sample();

		kokonaisaikaJonotettu += ((Kello.getInstance().getAika())-jono.peek().getPpisteSaapumisaika());

		tapahtumalista.lisaa(new Tapahtuma(seuraavaTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));

		kokonaisaikaPalvelupisteessa += (Kello.getInstance().getAika() - aloitusaika);
		aloitusaika = Kello.getInstance().getAika();

	}

	/**
	 * Jokaisella palvelupisteellä on yksi tapahtumantyyppi,
	 * joka erottaa palvelupisteet toisistaan ja määrää mminkälaisia tapahtumia ne käsittelevät.
	 * @return TapahtumanTyyppi-olio
	 */
	public TapahtumanTyyppi getTapahtumanTyyppi() {
		return seuraavaTapahtumanTyyppi;
	}


	/**
	 * Käsitteleekö palvelupiste asiakasta tällä hetkellä?
	 * @return kyllä/ei
	 */
	public boolean onVarattu(){
		return varattu;
	}


	/**
	 * Onko jonossa asiakkaita vai onko se tyhjä?
	 * @return kyllä/ei
	 */
	public boolean onJonossa(){
		return jono.size() != 0;
	}

	/**
	 * Palauttaa palvelupisteen maksimikapasiteetin
	 * @return {@link #maxSize}
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * palauttaa palvelupisteen keskiarvopalveluajan (kokonaispalveluaika / palvelujen määrä)
	 * @return {@link #kokonaisaikaPalvelupisteessa} / {@link #palvelunro};
	 */
	public double getKeskiarvoaika() {
		return kokonaisaikaPalvelupisteessa /palvelunro;
	}

	/**
	 * palauttaa palvelupisteen keskiarvopalveluajan (kokonaisjonotusaika / palvelujen määrä)
	 * @return {@link #kokonaisaikaJonotettu} / {@link #palvelunro};
	 */
	public double getKeskijonoaika(){
		return kokonaisaikaJonotettu / palvelunro;
	}

	/**
	 * Palauttaa jonon pituuden
	 * @return {@link #jono}.size()
	 */
	public int getJonopituus(){
		return jono.size();
	}

	/**
	 * Metro-palvelupisteen (eli palvelupisteen, jolla on määritetty {@link #maxSize} käyttämä funktio,
	 * joka nostaa {@link #palvelunro} arvoa yhdellä, ja lisää juuri palvellun asiakkaan jonon keston {@link #kokonaisaikaJonotettu} arvoon.
	 *
	 * @param {@link #kokonaisaikaJonotettu} lisättävä arvo.
	 */
	public void kokonaisaikaJonotettu(double timeToBeAdded){
		palvelunro++;
		kokonaisaikaJonotettu += (Kello.getInstance().getAika() - timeToBeAdded);
	}

	/**
	 * Palauttaa palvelupisteen palveltujen asiakkaiden määrän.
	 * @return {@link #palvelunro}
	 */
	public int getPalvelunro(){
		return palvelunro;
	}

	/**
	 * Asettaa palvelupisteen varatuksi.
	 * @param varattu {@link #varattu}
	 */
	public void setVarattu(boolean varattu) {
		this.varattu = varattu;
	}

	/**
	 * Asettaa palvelupisteen käsittelyaikajakauman, joka on ContinuousGenerator-olio
	 * @param generator {@link #generator}
	 */
	public void setJakauma(ContinuousGenerator generator) {
		this.generator = generator;
	}
}