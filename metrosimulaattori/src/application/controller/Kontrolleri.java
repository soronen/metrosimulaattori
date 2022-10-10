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
import dao.ServicePointDAO;
import dao.SimulaattoriDAO;
import datasource.MariaDbJpaConn;
import entity.ServicePoint;
import entity.Simulaattori;
import entity.Station;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import javax.sql.DataSource;
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
    private int simuviive = 100;
    private int metronKapasiteetti = 40;
    private int asemanKapasiteetti = 200;
    private Palvelupiste palvelupisteet[];


    //palvelupisteiden jakaumien mean ja var -arvot
    private int entranceMean = 4;
    private int	entranceVariance = 8;
    private int salesMean = 20;
    private int salesVariance = 10;
    private int checkMean = 7;
    private int checkVariance = 3;
    private int metroMean = 360;
    private int metroVariance = 60;


    private int arrivalMean = 5;
    private int arrivalVariance = 3;




    public Kontrolleri(IVisualisointi ui) {
        this.ui = ui;
        MainApp.setKontrol(this);
    }

    public Kontrolleri(){
        MainApp.setKontrol(this);
    }

    // Moottorin ohjausta:
    @Override
    public void kaynnistaSimulointi() {
        moottori = getMoottori();
        Kello.getInstance().setAika(0);
        palvelupisteet = moottori.getPalvelupisteet();

        asetaMoottorinParametrit();

        if (!kaynnissa) {
            kaynnissa = true;
            ((Thread)moottori).start();
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



    @Override
    public void resetSimulator() {
        moottori.setSimulointiaika(0);
        moottori = null;
        setKaynnissa(false);
    }

    @Override
    public IMoottori getMoottori() {
        if (moottori == null) {
            moottori = new OmaMoottori(this, arrivalMean, arrivalVariance); // luodaan uusi moottorisäie jokaista simulointia varten
        }
        return moottori;
    }

    @Override
    public Palvelupiste[] getPalvelupisteet() {
        return moottori.getPalvelupisteet();
    }

    @Override
    public void nopeuta() {
        moottori.setViive((long)(moottori.getViive()*0.9));
    }

    @Override
    public void hidasta() {
        moottori.setViive((long)(moottori.getViive()*1.10));
    }

    @Override
    public void muutaNopeutta(long viive) {
        moottori.setViive(viive);
    }

    @Override
    public void naytaLoppuaika(double aika) {

    }
    @Override
    public void visualisoiAsiakas() {

    }
    @Override
    public void paivitaUI(Tapahtuma t) {



        ui.paivitaUI(t);
    }

    @Override
    public void setsimulaattorinKesto(int simukesto) {
        this.simukesto = simukesto;
        moottori.setSimulointiaika(simukesto);
    }

    @Override
    public void setSimulaattorinViive(int simuviive) {
        this.simuviive = simuviive;
        moottori.setViive(simuviive);

    }

    @Override
    public int getMetronKapasiteetti() {
        return moottori.getMetroCapacity();
    }

    @Override
    public void setMetronKapasiteetti(int metronKapasiteetti) {
        this.metronKapasiteetti = metronKapasiteetti;
        moottori.setMetroCapacity(metronKapasiteetti);
    }

    @Override
    public int getAsemanKapasiteetti() {
        return moottori.getStationCapacity();
    }

    @Override
    public void setAsemanKapasiteetti(int asemanKapasiteetti) {
        this.asemanKapasiteetti = asemanKapasiteetti;
        moottori.setStationCapacity(asemanKapasiteetti);
    }

    @Override
    public int getAsiakkaatAsemassa() {
        return moottori.getCustomersWithin();
    }
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
        palvelupisteet[0].setJakauma(new Normal(entranceMean,entranceVariance));
    }
    @Override
    public void setSalesJakauma(int mean, int variance) {
        salesMean = mean;
        salesVariance = variance;

        palvelupisteet[1].setJakauma(new Normal(salesMean,salesVariance));
    }
    @Override
    public void setCheckJakauma(int mean, int variance) {
        checkMean = mean;
        checkVariance = variance;
        palvelupisteet[2].setJakauma(new Normal(checkMean,checkVariance));
    }
    @Override
    public void setMetroJakauma(int mean, int variance) {
        metroMean = mean;
        metroVariance = variance;
        palvelupisteet[3].setJakauma(new Normal(metroMean,metroVariance));
    }


    public void setArrivalJakauma (int mean, int variance) {
        arrivalMean = mean;
        arrivalVariance = variance;
    }

    public void setPPJakauma(TapahtumanTyyppi tt, int mean, int variance) {
        switch(tt) {
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
     * @param tt TapahtumanTyyppi, joka vastaa palvelupistettä
     * @return int[2] taulukon, jossa i[0] = odotusarvo ja i[1] = varianssi
     */
    public int[] getPPJakauma(TapahtumanTyyppi tt) {
        switch(tt) {
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



    public void setUi(IVisualisointi iv){

        System.out.println(iv + " vaihdettu");

        this.ui = iv;
    }


    /**
     * Tallentaa simulaattorin asetukset ja loppuarvot ensiksi olioksi ja sitten tietokantaan.
     *
     * @param mtr OmaMoottori-olio, jonka parametrit ja palvelupisteet tallennetaan.
     */
    @Override
    public void tallenaEntity(OmaMoottori mtr) {
        Palvelupiste[] ppt = mtr.getPalvelupisteet();
        ServicePoint[] spoints = new ServicePoint[4];

        Station station = new Station(
                getMobiililippujakauma(), arrivalMean, arrivalVariance,
                mtr.getCustomersWithin(), mtr.getServedCustomers(),
                mtr.getStationCapacity());


        // luo ServicePoint-olion jokaisesta palvelupisteestä
        for (int i = 0; i<4; i++) {
        TapahtumanTyyppi t = TapahtumanTyyppi.ENTRANCE;

            switch (i) {
                case 0:
                    t=TapahtumanTyyppi.ENTRANCE;
                    break;
                case 1:
                    t=TapahtumanTyyppi.TICKETSALES;
                    break;
                case 2:
                    t=TapahtumanTyyppi.TICKETCHECK;
                    break;
                case 3:
                    t=TapahtumanTyyppi.METRO;
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
        SimulaattoriDAO.lisaaSimulaattori(sim);

    }



    @Override
    public void asetachart(graphviewcontroller i, int x) {

        i.getbarChart().getData().clear();

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        ListView lv = i.getListView();

        lv.getSelectionModel().getSelectedIndex();

        SimulaattoriDAO sdao = new SimulaattoriDAO();



        Simulaattori sim = sdao.haeSimulaattori((lv.getSelectionModel().getSelectedIndex()+1));

        if (lv.getSelectionModel().getSelectedIndex() < 0){
            sim = sdao.haeSimulaattori(1);
            System.out.println("override");
        }


        if (sim == null){

            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Sql tietokannassa ei simulaation tuloksia! D:");
            a.show();
            return;

        }

        ServicePoint sp = new ServicePoint();

        switch (x){
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

    public void initchart(graphviewcontroller i){
        SimulaattoriDAO sdao = new SimulaattoriDAO();

        List<Simulaattori> simlist = sdao.listaaSimulaattorit();

        if (simlist == null || simlist.size() == 0){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Sql tietokannassa ei simulaation tuloksia! >:D");
            a.show();
            return;

        }

        for (Simulaattori sim : simlist){

            i.getListView().getItems().add(sim.getId());

        }

        asetachart(i, 1);

    }

}
