package application.simu.model;

import application.controller.IKontrolleri;
import application.eduni.distributions.Normal;
import application.eduni.distributions.Uniform;
import application.simu.framework.Kello;
import application.simu.framework.Moottori;
import application.simu.framework.Saapumisprosessi;
import application.simu.framework.Tapahtuma;
import java.util.concurrent.ThreadLocalRandom;

public class OmaMoottori extends Moottori{

	private int customersWithin = 0;
	private int servedCustomers = 0;
	int stationCapacity = 0;

	private int metroCapacity = 0;

	private final Saapumisprosessi saapumisprosessi;

	double keskiarvoaika;

	public OmaMoottori(IKontrolleri kontrolleri){ // UUSI

		super(kontrolleri); //UUSI

		palvelupisteet = new Palvelupiste[4];

		palvelupisteet[0]=new Palvelupiste(new Uniform(4,8), tapahtumalista, TapahtumanTyyppi.ENTRANCE, stationCapacity);
		palvelupisteet[1]=new Palvelupiste(new Normal(20,10), tapahtumalista, TapahtumanTyyppi.TICKETSALES);
		palvelupisteet[2]=new Palvelupiste(new Normal(7,3), tapahtumalista, TapahtumanTyyppi.TICKETCHECK);
		palvelupisteet[3]=new Palvelupiste(new Normal(360,60), tapahtumalista, TapahtumanTyyppi.METRO);

		saapumisprosessi = new Saapumisprosessi(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.ARRIVAL);
	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		kontrolleri.paivitaUI();

		Asiakas a;
		switch (t.getTyyppi()){
			// menee toistaiseksi suoraan ticketcheck-pisteeseen
			case ARRIVAL:
				palvelupisteet[0].lisaaJonoon(new Asiakas());
				saapumisprosessi.generoiSeuraava();
				customersWithin++;

				kontrolleri.visualisoiAsiakas(); // UUSI
				break;
			case TICKETSALES: a = palvelupisteet[1].otaJonosta();
				a.setPpisteSaapumisaika(Kello.getInstance().getAika());
				System.out.println("Asiakas " + a.getId() + " lisättiin palvelupisteen " + 2 + " jonoon");
				a.valiraportti();
				palvelupisteet[2].lisaaJonoon(a);
				break;
			case MOBILETICKET: a = palvelupisteet[1].otaJonosta();
				a.setPpisteSaapumisaika(Kello.getInstance().getAika());
				System.out.println("Asiakas " + a.getId() + " lisättiin palvelupisteen " + 3 + " jonoon");
				a.valiraportti();
				palvelupisteet[3].lisaaJonoon(a);
				break;
			case TICKETCHECK: a = palvelupisteet[2].otaJonosta();
				a.setPpisteSaapumisaika(Kello.getInstance().getAika());
				System.out.println("Asiakas " + a.getId() + " lisättiin palvelupisteen " + 3 + " jonoon");
				a.valiraportti();
				palvelupisteet[3].lisaaJonoon(a);
				break;
			case ENTRANCE: a = palvelupisteet[0].otaJonosta();
				a.setPpisteSaapumisaika(Kello.getInstance().getAika());
				if (ThreadLocalRandom.current().nextBoolean()){
					System.out.println("Asiakas " + a.getId() + " lisättiin palvelupisteen " + 1 + " jonoon");
					a.valiraportti();
					palvelupisteet[1].lisaaJonoon(a);
				} else {
					System.out.println("Asiakas " + a.getId() + " lisättiin palvelupisteen " + 2 + " jonoon");
					a.valiraportti();
					palvelupisteet[2].lisaaJonoon(a);
				}
				break;
			case METRO:
				for (int i = 0; i < metroCapacity ; i++) {
					if (palvelupisteet[3].onJonossa()) {
						a = palvelupisteet[3].otaJonosta();
						a.setPoistumisaika(Kello.getInstance().getAika());
						a.raportti();
						palvelupisteet[3].addtoKeskijonoaika(a.getPpisteSaapumisaika());
						customersWithin--;
						servedCustomers++;
						keskiarvoaika = a.getKeskiarvo();
					}
				}
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");

		// kakkien asiakkaiden "viipyi" aika / poistuneiden asiakkaide lukumäärällä
		System.out.println("Läpimenoaikojen keskiarvo on " + keskiarvoaika);

		for (int i = 0; i < palvelupisteet.length; i++) {
			System.out.println("\nPalvelupisteen " + i + " keskiarvopalveluaika oli " + palvelupisteet[i].getKeskiarvoaika());
			System.out.println("Palvelupisteen " + i + " keskiverto jono aika oli " + palvelupisteet[i].getKeskijonoaika());
			System.out.println("Palvelupisteen " + i + " jonopituus lopussa: " + palvelupisteet[i].getJonopituus());
		}
		kontrolleri.setKaynnissa(false);
	}

	protected void yritaCTapahtumat(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa() && p.getTapahtumanTyyppi() != TapahtumanTyyppi.ENTRANCE){
				p.aloitaPalvelu();
			} else if (!p.onVarattu() && p.onJonossa() && p.getTapahtumanTyyppi() == TapahtumanTyyppi.ENTRANCE){

				if (customersWithin < stationCapacity){
					System.out.println("Palvelu aloitettu. Palvelupisteen kapasiteetti: " + customersWithin + " : " + stationCapacity);
					customersWithin++;
					p.aloitaPalvelu();
					}

				}
			}
		}


	public Palvelupiste[] getPalvelupisteet() {
		return palvelupisteet;
	}
	public double getLapimenoaika() {
		return keskiarvoaika;
	}
	public int getMetroCapacity() {
		return metroCapacity;
	}
	public void setMetroCapacity(int capacity) {
		metroCapacity = capacity;
	}
	public int getStationCapacity() {
		return stationCapacity;
	}
	public void setStationCapacity(int stationCapacity) {
		this.stationCapacity = stationCapacity;
	}
	public int getCustomersWithin() {
		return customersWithin;
	}

	public int getServedCustomers() {
		return servedCustomers;
	}




}