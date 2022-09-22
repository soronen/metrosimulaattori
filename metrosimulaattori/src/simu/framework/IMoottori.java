package simu.framework;

import simu.model.Palvelupiste;

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
}
