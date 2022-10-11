package application.simu.model;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella

/**
 * Nämö enum-arvot toimivat erottavat palvelupisteet ja saapumisen toisistaan
 *
 * @author Eetu Soronen
 * @version 1
 */
public enum TapahtumanTyyppi {
	ARRIVAL,
	ENTRANCE,
	TICKETSALES,
	TICKETCHECK,
	METRO,
	MOBILETICKET
}
