package application.simu.framework;

import application.simu.model.Palvelupiste;

public interface IMoottori { // UUSI
		
	// Kontrolleri käyttää tätä rajapintaa
	
	public void setSimulointiaika(double aika);
	public void setViive(long aika);
	public long getViive();


	public abstract Palvelupiste[] getPalvelupisteet();
	public abstract double getLapimenoaika();
	public abstract int getMetroCapacity();
	public abstract int getStationCapacity();
	public abstract int getCustomersWithin();
	public abstract int getServedCustomers();

	public void setMetroCapacity(int capacity);
	public void setStationCapacity(int stationCapacity);

}
