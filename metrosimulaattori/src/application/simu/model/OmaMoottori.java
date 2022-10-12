package application.simu.model;

import application.controller.IKontrolleri;
import application.eduni.distributions.Normal;
import application.simu.framework.Kello;
import application.simu.framework.Moottori;
import application.simu.framework.Saapumisprosessi;
import application.simu.framework.Tapahtuma;

import java.util.Random;

public class OmaMoottori extends Moottori{

	private int customersWithin = 0;
	private int servedCustomers = 0;
	private int stationCapacity = 0;
	private int metroCapacity = 0;
	private final Saapumisprosessi saapumisprosessi;

	private double keskiarvoaika;
	private int mobiililippujakauma = 50;
	private int arrivalMean;
	private int arrivalVariance;


	public OmaMoottori(IKontrolleri kontrolleri, int arrivalMean, int arrivalVariance){ // UUSI

		super(kontrolleri); //UUSI

		palvelupisteet = new Palvelupiste[4];
		palvelupisteet[0]=new Palvelupiste(new Normal(1000,1000), tapahtumalista, TapahtumanTyyppi.ENTRANCE, stationCapacity);
		palvelupisteet[1]=new Palvelupiste(new Normal(1000,1000), tapahtumalista, TapahtumanTyyppi.TICKETSALES);
		palvelupisteet[2]=new Palvelupiste(new Normal(1000,1000), tapahtumalista, TapahtumanTyyppi.TICKETCHECK);
		palvelupisteet[3]=new Palvelupiste(new Normal(1000,1000), tapahtumalista, TapahtumanTyyppi.METRO);

		saapumisprosessi = new Saapumisprosessi(new Normal(arrivalMean,arrivalVariance), tapahtumalista, TapahtumanTyyppi.ARRIVAL);
		this.arrivalMean = arrivalMean;
		this.arrivalVariance = arrivalVariance;
	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		kontrolleri.paivitaUI(t);
		System.out.println("Arrival jakauma: " + arrivalMean+ " " + arrivalVariance);

		Asiakas a;

		switch (t.getTyyppi()){
			// menee toistaiseksi suoraan ticketcheck-pisteeseen
			case ARRIVAL:
				palvelupisteet[0].lisaaJonoon(new Asiakas());
				saapumisprosessi.generoiSeuraava();
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
			case ENTRANCE:
				if (customersWithin < stationCapacity) {
					a = palvelupisteet[0].otaJonosta();
					customersWithin++;
					a.setPpisteSaapumisaika(Kello.getInstance().getAika());

					// luku 1-100
					Random r = new Random();
					int chance = r.nextInt(100)+1;

					if(chance <= mobiililippujakauma) {
						System.out.println("Asiakas " + a.getId() + " lisättiin palvelupisteen " + 2 + " jonoon");
						a.valiraportti();
						palvelupisteet[2].lisaaJonoon(a);
					} else {
						System.out.println("Asiakas " + a.getId() + " lisättiin palvelupisteen " + 1 + " jonoon");
						a.valiraportti();
						palvelupisteet[1].lisaaJonoon(a);
					}
				} else {
					palvelupisteet[0].setVarattu(false);
				}

				break;
			case METRO:
				for (int i = 0; i < metroCapacity ; i++) {
					if (palvelupisteet[3].onJonossa()) {
						a = palvelupisteet[3].otaJonosta();
						a.setPoistumisaika(Kello.getInstance().getAika());
						a.raportti();
						palvelupisteet[3].kokonaisaikaJonotettu(a.getPpisteSaapumisaika());
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

		kontrolleri.tallenaEntity(this);

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

	public void setMobiililippujakauma(int mobiililippujakauma) {
		this.mobiililippujakauma = mobiililippujakauma;
	}

	@Override
	public int getMobiililippujakauma() {
		return mobiililippujakauma;
	}
}