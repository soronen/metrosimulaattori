package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;

	private double ppisteSaapumisaika;





	private static int poistuneetAsiakkaat = 0;
	private static double sum = 0;

	public Asiakas(){
		id = i++;
		ppisteSaapumisaika = Kello.getInstance().getAika();
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		poistuneetAsiakkaat++;
		this.poistumisaika = poistumisaika;
		sum += (poistumisaika-saapumisaika);
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	public int getId() {
		return id;
	}

	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ getKeskiarvo());
	}

	public void valiraportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " luotu aikaan " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakkaan ajan delta "+ (Kello.getInstance().getAika()-saapumisaika) + "\n");
	}

	public double getKeskiarvo() {
		double keskiarvo = sum/poistuneetAsiakkaat;
		return keskiarvo;
	}

	public double getPpisteSaapumisaika() {
		return ppisteSaapumisaika;
	}

	public void setPpisteSaapumisaika(double ppisteSaapumisaika) {
		this.ppisteSaapumisaika = ppisteSaapumisaika;
	}



}