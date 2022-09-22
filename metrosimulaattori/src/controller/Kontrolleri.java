package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;
import view.ISimulaattorinUI;

import simu.model.TapahtumanTyyppi;

import static simu.model.TapahtumanTyyppi.*;

public class Kontrolleri implements IKontrolleri{   // UUSI
	
	private IMoottori moottori; 
	private ISimulaattorinUI ui;


	//private String[] ppnimet = { "ENTRANCE" , "TICKETSALES" , "CHECK" , "METRO" };


	private Palvelupiste[] palvelupisteet;
	
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	
	// Moottorin ohjausta:
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		ui.getVisualisointi().tyhjennaNaytto();
		((Thread)moottori).start();
		//((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?

		moottori.getPalvelupisteet();


	}
	
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
	}
	
	
	
	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
		
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->ui.setLoppuaika(aika)); 
	}

	
	@Override
	public void visualisoiAsiakas() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisointi().uusiAsiakas();
			}
		});
	}


	// palvelupisteiden getterit
	public int getJonottajienlkm(TapahtumanTyyppi tt) {
		switch (tt) {
			case ENTRANCE:
				return palvelupisteet[0].getJonopituus();
			case TICKETSALES:
				return palvelupisteet[1].getJonopituus();
			case TICKETCHECK:
				return palvelupisteet[2].getJonopituus();
			case METRO:
				return palvelupisteet[3].getJonopituus();
		}
		return 0;
	}
	public double getJononkeskipituus(TapahtumanTyyppi tt) {
		switch (tt) {
			case ENTRANCE:
				return palvelupisteet[0].getKeskijonoaika();
			case TICKETSALES:
				return palvelupisteet[1].getKeskijonoaika();
			case TICKETCHECK:
				return palvelupisteet[2].getKeskijonoaika();
			case METRO:
				return palvelupisteet[3].getKeskijonoaika();
		}
		return 0;
	}

	// aseman (moottorin) getterit
	public double getLapimenoaika() {
		return moottori.getLapimenoaika();
	}
	public int getMetroCapacity() {
		return moottori.getMetroCapacity();
	}
	public int getStationCapacity() {
		return moottori.getStationCapacity();
	}
	public int getCustomersWithin() {
		return moottori.getCustomersWithin();
	}

}
