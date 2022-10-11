package application.controller;

import application.MainApp;
import application.eduni.distributions.Normal;
import application.simu.framework.IMoottori;
import application.simu.framework.Kello;
import application.simu.framework.Tapahtuma;
import application.simu.model.OmaMoottori;
import application.simu.model.Palvelupiste;
import application.simu.model.TapahtumanTyyppi;
import application.view.IVisualisointi;
import application.view.graphviewcontroller;
import dao.ISimulaattoriDAO;
import dao.SimulaattoriDAO;
import entity.ServicePoint;
import entity.Simulaattori;
import entity.Station;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import java.util.List;


/**
 * MVC-mallin mukainen controller, jota käyttöliittymä käyttää mallin kanssa kommunikointiin.
 *
 * @author Eetu Soronen, Emil Ålgars
 * @version 1
 */

public class Kontrolleri implements IKontrolleri {

    boolean kaynnissa = false;
    private IMoottori moottori;
    private IVisualisointi ui;
    private int simukesto = 1000;
    private int metronKapasiteetti = 40;
    private int asemanKapasiteetti = 200;
    private Palvelupiste[] palvelupisteet;


    //palvelupisteiden jakaumien mean ja var -arvot
    private int entranceMean = 4;
    private int entranceVariance = 8;
    private int salesMean = 20;
    private int salesVariance = 10;
    private int checkMean = 7;
    private int checkVariance = 3;
    private int metroMean = 360;
    private int metroVariance = 60;


    private int arrivalMean = 5;
    private int arrivalVariance = 3;

    private boolean simuStopped = false;

    public Kontrolleri() {
        MainApp.setKontrol(this);
    }

    // Moottorin ohjausta:
    @Override
    public void kaynnistaSimulointi() {

        if (((Thread) moottori).getState() != Thread.State.NEW) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Uutta moottoria ei luotu!");
            alert.setContentText("Muistitko nollata moottorin?");
            alert.show();
            return;
        }

        moottori = getMoottori();
        Kello.getInstance().setAika(0);
        palvelupisteet = moottori.getPalvelupisteet();

        asetaMoottorinParametrit();


        if (!kaynnissa && ((Thread) moottori).getState() == Thread.State.NEW) {

            kaynnissa = true;
            ((Thread) moottori).start();

        }
    }

    @Override
    public void asetaMoottorinParametrit() {
        setMetronKapasiteetti(metronKapasiteetti);
        setAsemanKapasiteetti(asemanKapasiteetti);

        setEntranceJakauma(entranceMean, entranceVariance);
        setSalesJakauma(salesMean, salesVariance);
        setCheckJakauma(checkMean, checkVariance);
        setMetroJakauma(metroMean, metroVariance);


    }

    /**
     * Kutsuu setKaynnissa(false) -metodia.
     * Jos moottori != null asettaa simlointiajan nollaksi ja poistaa simulaattorin.
     */
    @Override
    public void resetSimulator() {
        setKaynnissa(false);
        if (moottori != null) {
            moottori.setSimulointiaika(0);
            moottori = null;
        }

    }

    /**
     * pysäyttää!!1! ⛔🚫🛑🚫  simulaattorin kesken asetmmalla simulointiajan nollaan.
     * asettaa moottorin null arvoksi pienen viiveen kuluttua tästä, kun muutkin säikeet ovat saaneet kuulla uutiset
     * jotta konsoliin ei tulisi virheilmoituksia
     */
    public void stopSimulation() {
        setKaynnissa(false);
        simuStopped = true;
        moottori.setSimulointiaika(0);
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100);
                    moottori = null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Palauttaa moottorin. Jos moottori = null, luo sen.
     *
     * @return OmaMoottori-olio, jonka saapmuisen jakauma on asetettu (arrivalMean ja arrivalVariance);
     */
    @Override
    public IMoottori getMoottori() {
        if (moottori == null) {
            moottori = new OmaMoottori(this, arrivalMean, arrivalVariance); // luodaan uusi moottorisäie jokaista simulointia varten
        }
        return moottori;
    }

    /**
     * Palauttaa moottorin palvelupisteet
     *
     * @return Palvelupiste[]-taulukko, joka sisältää moottorin palvelupisteet. [0] = Entrance, [1] = Sales, [2] = Check, [3] = Metro
     */
    @Override
    public Palvelupiste[] getPalvelupisteet() {
        return moottori.getPalvelupisteet();
    }

    /**
     * Nopeuttaa simulaattoria laskemalla simulaattorin viivettä 10%:lla.
     */
    @Override
    public void nopeuta() {
        moottori.setViive((long) (moottori.getViive() * 0.9));
    }

    /**
     * Korottaa mottorin viivettä 10%:lla ja lisää tulokseen vielä ykkösen.
     * Ykkönen lisätään, jotta viive nousee nollastakin.
     */
    @Override
    public void hidasta() {
        moottori.setViive((long) (moottori.getViive() * 1.10 + 1));
    }

    /**
     * OmaMoottorin kutsuma metodi, joka kutsuu UI:n päivitäUI()-metodia jokaisen tapahtuman käsittelyn yhteydessä.
     * Vastaa käyttöliittymän päivittämisestä.
     *
     * @param t Tapahtuma-olio, joka sisältää tiedon tapahtuneesta tapahtumasta.
     */
    @Override
    public void paivitaUI(Tapahtuma t) {
        ui.paivitaUI(t);
    }

    /**
     * Asettaa simulaattorin keston moottorissa ja tallentaa sen kontrollerin simukesto-muuttujaan.
     *
     * @param simukesto asetetaan simulaattorin kestoksi
     */
    @Override
    public void setsimulaattorinKesto(int simukesto) {
        this.simukesto = simukesto;
        moottori.setSimulointiaika(simukesto);
    }

    /**
     * Asettaa simulaattorin viiveen parametrina annettuun arvoon.
     *
     * @param simuviive long arvo, joka asetetaan simulaattorin viiveeksi.
     */
    @Override
    public void setSimulaattorinViive(int simuviive) {
        moottori.setViive(simuviive);

    }

    /**
     * Palauttaa metro-palvelupisteen kapasiteetin.
     *
     * @return Metro-palvelupisteen kapasiteetti int-arvona.
     */
    @Override
    public int getMetronKapasiteetti() {
        return moottori.getMetroCapacity();
    }

    /**
     * Asettaa metro-palvelupisteen kapasiteetin parametrina annettuun arvoon.
     *
     * @param metronKapasiteetti int-arvo, joka asetetaan metro-palvelupisteen kapasiteetiksi.
     */
    @Override
    public void setMetronKapasiteetti(int metronKapasiteetti) {
        this.metronKapasiteetti = metronKapasiteetti;
        moottori.setMetroCapacity(metronKapasiteetti);
    }

    /**
     * Palauttaa aseman-kapasiteetin.
     * Jos asemassa on tätä lukua enemmän asiakkaita, entrance-palvelupiste ei vastaanota asiakkaita.
     *
     * @return aseman kapasiteetti (int)
     */
    @Override
    public int getAsemanKapasiteetti() {
        return moottori.getStationCapacity();
    }

    /**
     * Asettaa aseman kapasiteetin parametrina annettuun arvoon.
     * Jos asemassa on tätä lukua enemmän asiakkaita, entrance-palvelupiste ei vastaanota asiakkaita.
     *
     * @param asemanKapasiteetti aseman kapasiteetti (int)
     */
    @Override
    public void setAsemanKapasiteetti(int asemanKapasiteetti) {
        this.asemanKapasiteetti = asemanKapasiteetti;
        moottori.setStationCapacity(asemanKapasiteetti);
    }

    /**
     * Palauttaa asemassa olevat asiakkaat (entrance-palvelupisteen läpi menneet asiakkaat - metro-palvelupisteen käsittelemät asiakkaat)
     *
     * @return asemassa olevien asiakkaiden lukumäärä (int)
     */
    @Override
    public int getAsiakkaatAsemassa() {
        return moottori.getCustomersWithin();
    }

    /**
     * Palauttaa metro-palvelupisteen käsittelemät asiakkaat.
     *
     * @return palvellut asiakkaat (int)
     */
    @Override
    public int getPalvellutAsaiakkaat() {
        return moottori.getServedCustomers();
    }

    @Override
    public long getViive() {
        return moottori.getViive();
    }

    @Override
    public void setKaynnissa(boolean kaynnissa) {
        this.kaynnissa = kaynnissa;
    }

    @Override
    public boolean onkoKaynnissa() {
        return kaynnissa;
    }

    @Override
    public int getMobiililippujakauma() {
        return moottori.getMobiililippujakauma();
    }

    @Override
    public void setMobiililippujakauma(int mobiililippujakauma) {
        moottori.setMobiililippujakauma(mobiililippujakauma);
    }

    @Override
    public void setEntranceJakauma(int mean, int variance) {
        entranceMean = mean;
        entranceVariance = variance;
        palvelupisteet[0].setJakauma(new Normal(entranceMean, entranceVariance));
    }

    @Override
    public void setSalesJakauma(int mean, int variance) {
        salesMean = mean;
        salesVariance = variance;

        palvelupisteet[1].setJakauma(new Normal(salesMean, salesVariance));
    }

    @Override
    public void setCheckJakauma(int mean, int variance) {
        checkMean = mean;
        checkVariance = variance;
        palvelupisteet[2].setJakauma(new Normal(checkMean, checkVariance));
    }

    @Override
    public void setMetroJakauma(int mean, int variance) {
        metroMean = mean;
        metroVariance = variance;
        palvelupisteet[3].setJakauma(new Normal(metroMean, metroVariance));
    }


    public void setArrivalJakauma(int mean, int variance) {
        arrivalMean = mean;
        arrivalVariance = variance;
    }

    public void setPPJakauma(TapahtumanTyyppi tt, int mean, int variance) {
        switch (tt) {
            case ENTRANCE:
                entranceMean = mean;
                entranceVariance = variance;
                break;
            case TICKETSALES:
                salesMean = mean;
                salesVariance = variance;
                break;
            case TICKETCHECK:
                checkMean = mean;
                checkVariance = variance;
                break;
            case METRO:
                metroMean = mean;
                metroVariance = variance;
                break;
        }
    }

    public boolean onkoPPVarattu(TapahtumanTyyppi palvelupiste) {
        switch (palvelupiste) {
            case ENTRANCE:
                return palvelupisteet[0].onVarattu();
            case TICKETSALES:
                return palvelupisteet[1].onVarattu();
            case TICKETCHECK:
                return palvelupisteet[2].onVarattu();
            case METRO:
                return palvelupisteet[3].onVarattu();
        }
        return false;
    }

    public int getPPjononpituus(TapahtumanTyyppi palvelupiste) {
        int index = 0;
        switch (palvelupiste) {
            case ENTRANCE:
                index = 0;
                break;
            case TICKETSALES:
                index = 1;
                break;
            case TICKETCHECK:
                index = 2;
                break;
            case METRO:
                index = 3;
                break;
        }
        return palvelupisteet[index].getJonopituus();
    }

    public double getPPkeskijonoaika(TapahtumanTyyppi palvelupiste) {
        int index = 0;
        switch (palvelupiste) {
            case ENTRANCE:
                index = 0;
                break;
            case TICKETSALES:
                index = 1;
                break;
            case TICKETCHECK:
                index = 2;
                break;
            case METRO:
                index = 3;
                break;
        }
        return palvelupisteet[index].getKeskijonoaika();
    }

    public int getPPpalvellutAsiakkaat(TapahtumanTyyppi palvelupiste) {
        int index = 0;
        switch (palvelupiste) {
            case ENTRANCE:
                index = 0;
                break;
            case TICKETSALES:
                index = 1;
                break;
            case TICKETCHECK:
                index = 2;
                break;
            case METRO:
                index = 3;
                System.out.println("metron palvelunro = " + palvelupisteet[3].getPalvelunro());
                break;
        }
        return palvelupisteet[index].getPalvelunro();
    }

    public double getPPkeskiarvoaika(TapahtumanTyyppi palvelupiste) {
        int index = 0;
        switch (palvelupiste) {
            case ENTRANCE:
                index = 0;
                break;
            case TICKETSALES:
                index = 1;
                break;
            case TICKETCHECK:
                index = 2;
                break;
            case METRO:
                index = 3;
                break;
        }
        return palvelupisteet[index].getKeskiarvoaika();
    }


    /**
     * Palauttaa palvelupisteen generaattorin odotus ja varianssiarvot
     *
     * @param tt TapahtumanTyyppi, joka vastaa palvelupistettä
     * @return int[2] taulukon, jossa i[0] = odotusarvo ja i[1] = varianssi
     */
    public int[] getPPJakauma(TapahtumanTyyppi tt) {
        switch (tt) {
            case ENTRANCE:
                return new int[]{entranceMean, entranceVariance};
            case TICKETSALES:
                return new int[]{salesMean, salesVariance};
            case TICKETCHECK:
                return new int[]{checkMean, checkVariance};
            case METRO:
                return new int[]{metroMean, metroVariance};
        }
        return null;
    }


    public void setUi(IVisualisointi iv) {

        System.out.println(iv + " vaihdettu");

        this.ui = iv;
    }


    /**
     * Tallentaa simulaattorin asetukset ja loppuarvot ensiksi olioksi ja sitten tietokantaan.
     * Kuitenkin jos simulaattori on keskeytetty ennenaikaisesti, ei tee mitään.
     *
     * @param mtr OmaMoottori-olio, jonka parametrit ja palvelupisteet tallennetaan.
     */
    @Override
    public void tallenaEntity(OmaMoottori mtr) {
        if (simuStopped == true) {
            simuStopped = false;
            return;
        }

        Palvelupiste[] ppt = mtr.getPalvelupisteet();
        ServicePoint[] spoints = new ServicePoint[4];

        Station station = new Station(
                getMobiililippujakauma(), arrivalMean, arrivalVariance,
                mtr.getCustomersWithin(), mtr.getServedCustomers(),
                mtr.getStationCapacity());


        // luo ServicePoint-olion jokaisesta palvelupisteestä
        for (int i = 0; i < 4; i++) {
            TapahtumanTyyppi t = TapahtumanTyyppi.ENTRANCE;

            switch (i) {
                case 0:
                    t = TapahtumanTyyppi.ENTRANCE;
                    break;
                case 1:
                    t = TapahtumanTyyppi.TICKETSALES;
                    break;
                case 2:
                    t = TapahtumanTyyppi.TICKETCHECK;
                    break;
                case 3:
                    t = TapahtumanTyyppi.METRO;
                    break;
            }
            spoints[i] = new ServicePoint(
                    t,
                    ppt[i].getPalvelunro(),
                    ppt[i].getJonopituus(),
                    ppt[i].getKeskiarvoaika(),

                    ppt[i].getKeskijonoaika(),
                    getPPJakauma(t)[0],
                    getPPJakauma(t)[1],
                    ppt[i].getMaxSize());
        }

        Simulaattori sim = new Simulaattori(simukesto, spoints[0], spoints[1], spoints[2], spoints[3], station);
        SimulaattoriDAO dao = new SimulaattoriDAO();
        dao.lisaaSimulaattori(sim);

    }


    @Override
    public void asetachart(graphviewcontroller i, int x) {



        i.getbarChart().getData().clear();

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        ListView lv = i.getListView();

        lv.getSelectionModel().getSelectedIndex();

        SimulaattoriDAO sdao = new SimulaattoriDAO();

        List<Simulaattori> lista = sdao.listaaSimulaattorit();

        int id = 1;
        if (!(i.getListView().getSelectionModel().getSelectedIndex() < 0)){
            String ids = (String) i.getListView().getItems().get(i.getListView().getSelectionModel().getSelectedIndex());
            try {
                id = Integer.parseInt(ids.replaceAll("[^0-9]", ""));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            String ids = (String) i.getListView().getItems().get(0);
            try {
                id = Integer.parseInt(ids.replaceAll("[^0-9]", ""));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



        Simulaattori sim = sdao.haeSimulaattori(id);



        if (lista == null || lista.size() == 0) {

            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Sql tietokannassa ei simulaation tuloksia! D:");
            a.show();
            return;

        }

        ServicePoint sp = new ServicePoint();

        switch (x) {
            case 1:

                sp = sim.getEntrance();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), sp.getJononKeskikesto()));

                sp = sim.getMetro();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), sp.getJononKeskikesto()));

                sp = sim.getTicketcheck();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), sp.getJononKeskikesto()));

                sp = sim.getTicketsales();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), sp.getJononKeskikesto()));


                break;
            case 2:


                sp = sim.getEntrance();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), Double.valueOf(sp.getPalvellutAsiakkaat())));

                sp = sim.getMetro();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), Double.valueOf(sp.getPalvellutAsiakkaat())));

                sp = sim.getTicketcheck();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), Double.valueOf(sp.getPalvellutAsiakkaat())));

                sp = sim.getTicketsales();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), Double.valueOf(sp.getPalvellutAsiakkaat())));


                break;
            case 3:

                sp = sim.getEntrance();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), sp.getPalvelunKeskiaika()));

                sp = sim.getMetro();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), sp.getPalvelunKeskiaika()));

                sp = sim.getTicketcheck();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), sp.getPalvelunKeskiaika()));

                sp = sim.getTicketsales();
                series.getData().add(new XYChart.Data<>(sp.getPalvelupiste().name(), sp.getPalvelunKeskiaika()));

                break;
        }

        i.getbarChart().getData().add(series);


    }

    public void initchart(graphviewcontroller i) {
        SimulaattoriDAO sdao = new SimulaattoriDAO();

        List<Simulaattori> simlist = sdao.listaaSimulaattorit();

        if (simlist == null || simlist.size() == 0) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Sql tietokannassa ei simulaation tuloksia! >:D");
            a.show();
            return;

        }
        i.getListView().getItems().clear();
        for (Simulaattori sim : simlist) {

            i.getListView().getItems().add("Simulaatio " + sim.getId());

        }

        asetachart(i, 1);

    }

    public void dChart(graphviewcontroller i){
        ISimulaattoriDAO sdao = new SimulaattoriDAO();
        int id = i.getListView().getSelectionModel().getSelectedIndex();

        if (id < 0){
            return;
        }

        String ids = (String) i.getListView().getItems().get(i.getListView().getSelectionModel().getSelectedIndex());
        try {
            id = Integer.parseInt(ids.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sdao.poistaSimulaattori(id);

        initchart(i);
    }
}
