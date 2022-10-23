package application.simu.model;

import application.simu.framework.Kello;
import application.simu.framework.Trace;

/**
 * Asiakkaat luodaan asiakageneraattorissa ja ne liikkuvat palvelupisteiden läpi
 * pitäen aikaa omista saapumisis ja poistumisajoistaan
 *
 * @author Eetu Soronen, Emil Ålgars
 * @version 1
 */
public class Asiakas {

	/**
	 * Asiakkaan saapumisaika (luomisaika)
	 */
	private double saapumisaika;
	/**
	 * Asiakkaan poistumisaika (simulaattorista)
	 */
	private double poistumisaika;
	/**
	 * Asiakkaan id, jolla asikkaat erotetaan toisistaan.
	 */
	private int id;

	/**
	 * Tämä luku kopioidaan asiakkaan luomisen yhteydessä sen id:ksi.
	 */
	private static int i = 1;

	/**
	 * Asiakkaan saapumisaika palvelupisteeseen
	 */
	private double ppisteSaapumisaika;

	/**
	 * Yhteensä poistuneet asiakkaat simulaattorista.
	 */
	private static int poistuneetAsiakkaat = 0;

	/**
	 * Aika, jonka kaikki asiakkaat yhteensä ovat viettäneet simulaattorissa.
	 */
	private static double sum = 0;

	/**
	 * Konstruktori luo uuden asiakkaan, asettaa sen ppisteSaapumisaika ja saapumisaika -muuttujat kellon sen hetkiseksi ajaksi.
	 * Lisäksi asettaa asiakkaan id:n ja kasvattaa i:ta yhdellä.
	 */
	public Asiakas(){
		id = i++;
		ppisteSaapumisaika = Kello.getInstance().getAika();
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapumisaika);
	}

	/**
	 * Palauttaa asiakkaan poistumisajan simulaattorista
	 * @return {@link #poistumisaika}
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/**
	 * Asettaa asiakkaan poistumisajan, ja korottaa {@link #poistuneetAsiakkaat} yhdellä.
	 * Lisäksi lisää asiakkaan elinajan {@link #sum} -muuttujaan.
	 * @param poistumisaika
	 */
	public void setPoistumisaika(double poistumisaika) {
		poistuneetAsiakkaat++;
		this.poistumisaika = poistumisaika;
		sum += (poistumisaika-saapumisaika);
	}

	/**
	 * Palauttaa asiakkaan saapumisajan simulaattoriin.
	 * @return {@link #saapumisaika}
	 */
	public double getSaapumisaika() {
		return saapumisaika;
	}

	/**
	 * Asettaa asiakkaan saapumisajan simulaattoriin.
	 * @param saapumisaika {@link #saapumisaika}
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/**
	 * Palauttaa asiakkaan id:n.
	 * @return {@link #id}
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asiakkaan loppuraportti, joka tulostetaan simulaattorin lopussa konsoliin.
	 */
	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ getKeskiarvo());
	}

	/**
	 * Väliraportti tulostetaan konsoliin sen edetessä uuteen palvelupisteeseen.
	 */
	public void valiraportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " luotu aikaan " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakkaan ajan delta "+ (Kello.getInstance().getAika()-saapumisaika) + "\n");
	}

	/**
	 * Palauttaa yhden asiakkaan keskiarvoisesti vietetyn ajan simulaattorissa.
	 * @return {@link #sum} / {@link #poistuneetAsiakkaat}
	 */
	public double getKeskiarvo() {
		double keskiarvo = sum/poistuneetAsiakkaat;
		return keskiarvo;
	}

	/**
	 * Palauttaa asiakkaan saapumisajan palvelupisteeseen.
	 * @return {@link #ppisteSaapumisaika}
	 */
	public double getPpisteSaapumisaika() {
		return ppisteSaapumisaika;
	}

	/**
	 * Asettaa asiakkaan saapumisajan palvelupisteeseen.
	 * @param ppisteSaapumisaika {@link #ppisteSaapumisaika}
	 */
	public void setPpisteSaapumisaika(double ppisteSaapumisaika) {
		this.ppisteSaapumisaika = ppisteSaapumisaika;
	}
}