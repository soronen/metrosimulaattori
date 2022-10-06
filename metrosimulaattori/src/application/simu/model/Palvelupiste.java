package application.simu.model;

import java.util.LinkedList;

import application.eduni.distributions.ContinuousGenerator;
import application.simu.framework.Kello;
import application.simu.framework.Tapahtuma;
import application.simu.framework.Tapahtumalista;
import application.simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus

	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;



	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	private int maxSize;

	//JonoStartegia strategia; //optio: asiakkaiden järjestys



	private boolean varattu = false;

	private double asiakasKeskiaika = 0;
	private double aloitusaika;
	private double keskiarvoaika;
	private int palvelunro = 0;


	public TapahtumanTyyppi getSkeduloitavanTapahtumanTyyppi() {
		return skeduloitavanTapahtumanTyyppi;
	}

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;

	}

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi , int maxSize){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.maxSize = maxSize;
	}


	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);

	}

	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		palvelunro++;
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());

		varattu = true;
		double palveluaika = generator.sample();

		asiakasKeskiaika += ((Kello.getInstance().getAika())-jono.peek().getPpisteSaapumisaika());

		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));

		keskiarvoaika += (Kello.getInstance().getAika() - aloitusaika);
		aloitusaika = Kello.getInstance().getAika();
	}

	public TapahtumanTyyppi getTapahtumanTyyppi() {
		return skeduloitavanTapahtumanTyyppi;
	}

	public boolean onVarattu(){
		return varattu;
	}


	public boolean onJonossa(){
		return jono.size() != 0;
	}
	public int getMaxSize() {
		return maxSize;
	}

	public double getKeskiarvoaika() {
		return keskiarvoaika/palvelunro;
	}

	public double getKeskijonoaika(){
		return asiakasKeskiaika / palvelunro;
	}

	public int getJonopituus(){
		return jono.size();
	}

	public void addtoKeskijonoaika(double c){
		palvelunro++;
		asiakasKeskiaika += (Kello.getInstance().getAika() - c);
	}
	public int getPalvelunro(){
		return palvelunro;
	}

	public void setVarattu(boolean varattu) {
		this.varattu = varattu;
	}

	public void setJakauma(ContinuousGenerator generator) {
		this.generator = generator;
	}
}