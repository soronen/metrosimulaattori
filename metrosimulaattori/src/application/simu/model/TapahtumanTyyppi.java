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

	/**
	 * Saapuminen
	 */
	ARRIVAL,

	/**
	 * Sisäänkäynti
	 */
	ENTRANCE,

	/**
	 * Lipunmyynti
	 */
	TICKETSALES,

	/**
	 * Lipuntarkastus
	 */
	TICKETCHECK,

	/**
	 * Metro
	 */
	METRO,

	/**
	 * Mobiililippu, tämä tapahtuma hyppää lipunmyynnin ohi.
	 */
	MOBILETICKET
}
