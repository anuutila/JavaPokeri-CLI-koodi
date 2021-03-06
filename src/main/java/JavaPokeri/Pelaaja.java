package JavaPokeri;

import java.io.Serializable;
import java.util.*;

import static java.lang.Math.addExact;
import static java.lang.Math.multiplyExact;

class Pelaaja implements Serializable {
    private String nimi;
    private int saldo;
    private int suurinSaldo;
    private int panos;
    private int kilpapelinSaldo;
    private ArrayList<Integer> kilpapelinTulokset;
    private ArrayList<Kortti> kasi;
    private Object[] parasKasi;
    private Boolean[] saavutustenTilat;
    private Boolean[] vetopokeriParannustenTilat;
    private int viimeisinVoitto;
    private int suurinVoitto;
    private int viimeisinKadenArvo;
    private int yhteisvoitot;
    private int yhteishaviot;
    private int voitetutJaot;
    private int havitytJaot;


    Pelaaja(String nimi) {
        this.nimi = nimi;
        saldo = 20;
        suurinSaldo = 20;
        panos = 0;
        kilpapelinSaldo = 0;
        kilpapelinTulokset = alustaKilpapelinTulokset();
        kasi = alustaKasi();
        parasKasi = alustaParasKasi();
        saavutustenTilat = alustaSaavutustenTilat();
        vetopokeriParannustenTilat = alustaVetopokeriParannustenTilat();
        viimeisinVoitto = 0;
        suurinVoitto = 0;
        viimeisinKadenArvo = -1;
        yhteisvoitot = 0;
        yhteishaviot = 0;
        voitetutJaot = 0;
        havitytJaot = 0;
    }


    Pelaaja(ArrayList<Kortti> kortit) {kasi = kortit;}

    String getNimi() {return nimi;}

    int getSaldo() {
        return saldo;
    }

    void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    int getSuurinSaldo() { return suurinSaldo; }

    void setSuurinSaldo(int suurinSaldo) { this.suurinSaldo = suurinSaldo; }

    int getPanos() {
        return panos;
    }

    void setPanos(int panos) {
        this.panos = panos;
    }

    int getKilpapelinSaldo() {
        return kilpapelinSaldo;
    }

    void setKilpapelinSaldo(int kilpapelinSaldo) {
        this.kilpapelinSaldo = kilpapelinSaldo;
    }

    ArrayList<Integer> getKilpapelinTulokset() {
        return kilpapelinTulokset;
    }

    Object[] getParasKasi() { return parasKasi; }

    Boolean[] getSaavutustenTilat() {
        return saavutustenTilat;
    }

    Boolean[] getVetopokeriParannustenTilat() {return vetopokeriParannustenTilat; }

    void setVetopokeriParannuksenTilat(Boolean[] vetopokeriParannukset) {
        this.vetopokeriParannustenTilat = vetopokeriParannukset;
    }

    int getSuurinVoitto() { return suurinVoitto; }

    int getYhteisvoitot() {
        return yhteisvoitot;
    }

    int getYhteishaviot() {
        return yhteishaviot;
    }

    int getVoitetutJaot() {
        return voitetutJaot;
    }

    int getHavitytJaot() {
        return havitytJaot;
    }

    /** Metodi alustaa pelaajan k??den yhdell?? kortilla */
    private ArrayList<Kortti> alustaKasi() {
        ArrayList<Kortti> kasi = new ArrayList<Kortti>(List.of(
                new Kortti(Maa.JOKERI, Arvo.JOKERI)));
        return kasi;
    }

    /** Metodi alustaa pelaajan vetopokerin kilpapelin tulokset sis??lt??v??n listan yhdell?? 0:lla. */
    private ArrayList<Integer> alustaKilpapelinTulokset() {
        ArrayList<Integer> tulokset = new ArrayList<>(List.of(0));
        tulokset.add(0);
        return tulokset;
    }

    /** Metodi alustaa pelaajan kaikki vetopokerin saavutukset tilaan false, mik?? tarkoittaa sit??,
     ett?? saavutuksia ei viel?? ole avattu */
    private Boolean[] alustaSaavutustenTilat() {
        Boolean[] saavutustenTilat = new Boolean[40];
        for (int i=0; i<40; i++) {
            saavutustenTilat[i] = false;
        }
        return saavutustenTilat;
    }

    /** Metodi alustaa pelaajan kaikki vetopokerin parannukset tilaan false, mik?? tarkoittaa sit??,
        ett?? parannuksia ei viel?? ole ostettu */
    private Boolean[] alustaVetopokeriParannustenTilat() {
        Boolean[] vetopokeriParannustenTilat = new Boolean[10];
        for (int i=0; i<10; i++) {
            vetopokeriParannustenTilat[i] = false;
        }
        return vetopokeriParannustenTilat;
    }

    /** Metodi alustaa pelaajan parasKasi attribuutin, joka sis??lt???? tiedot pelaajan arvokkaimman koskaan
        saaman pokerik??den korteista ja kyseisen k??den arvosta asteikolla 0-9, jossa 0 on arvokkain k??si. */
    private Object[] alustaParasKasi() {
        // Alustetaan parasKasi-attribuutti huonolla pokerik??dell??.
        ArrayList<Kortti> alustusKasi = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.NELJ??),
                new Kortti(Maa.PATA, Arvo.KUUSI),
                new Kortti(Maa.HERTTA, Arvo.SEITSEM??N) ));

        // Vain k??si?? joiden arvo on v??lill?? 0-9 k??ytet????n muissa metodeissa.
        Object[] paraskasi = {10, alustusKasi};
        return paraskasi;
    }


    /** Metodi tulostaa pelaajan k??dess?? olevat 5 pelikorttia allekkain ja numeroituna. */
    void tulostaKasi(boolean korttienVaihtoTapahtunut) {
        System.out.println();
        if (korttienVaihtoTapahtunut) {
            System.out.println("Uusi K??si:");
        } else {
            System.out.println("K??si:");
        }
        System.out.println("(1)   " + kasi.get(0));
        System.out.println("(2)   " + kasi.get(1));
        System.out.println("(3)   " + kasi.get(2));
        System.out.println("(4)   " + kasi.get(3));
        System.out.println("(5)   " + kasi.get(4));
        System.out.println();
    }


    /** Metodi tulostaa n??kyviin pelaajan arvokkaimman koskaan saaman pokerik??den. */
    void tulostaParasKasi() {
        String[] kasienArvot = pokerikasienArvot();
        ArrayList<Kortti> parasPokerikasi = (ArrayList<Kortti>) parasKasi[1];
        System.out.println();
        System.out.println("Paras k??si: " + kasienArvot[(Integer)parasKasi[0]]);
        System.out.println(parasPokerikasi.get(0));
        System.out.println(parasPokerikasi.get(1));
        System.out.println(parasPokerikasi.get(2));
        System.out.println(parasPokerikasi.get(3));
        System.out.println(parasPokerikasi.get(4));
        System.out.println();
    }


    /** Metodi palauttaa merkkijonotaulun, joka sis??lt???? kaikki pokerik??sien arvot
        j??rjestyksess?? parhaimmasta huonoimpaan. */
    private String[] pokerikasienArvot() {
        String[] arvot = {"Kuningasv??risuora", "Viitoset", "V??risuora", "Neloset",
                "T??ysk??si", "V??ri", "Suora", "Kolmoset", "Kaksi paria", "10-A pari"};
        return arvot;
    }


    /** Metodi vaihtaa pelaajan k??dess?? olevat kortit uusiin metodin saamiin kortteihin. */
    void jaaUusiKasi(ArrayList<Kortti> kasi) {
        this.kasi = kasi;
    }


    /** Metodi lis???? pelaajan vetopokerin kilpapeliss?? saaman lopputuloksen pelaajan omat tulokset
        sis??lt??v????n listaan ja j??rjest???? sen suurimmasta pienimp????m. */
    void lisaaKilpapelinTulos(int tulos) {
        ArrayList<Integer> tuloslista = getKilpapelinTulokset();
        tuloslista.add(tulos);
        Collections.sort(tuloslista, Collections.reverseOrder());
        this.kilpapelinTulokset = tuloslista;
    }


    /** Metodi vaihtaa pelaajan k??dest?? tietyt kortit samasta pakasta nostettuihin uusiin kortteihin. */
    void vaihdaKortteja(String[] vaihdettavat, Korttipakka pakka) throws CustomException{

        ArrayList<Integer> vaihdettavatInt = new ArrayList<>();
        for (String vaihdettava : vaihdettavat) {
            vaihdettavatInt.add(Integer.valueOf(vaihdettava));
        }

        for (int i : vaihdettavatInt) {
            if (i < 1 || i > 5 || vaihdettavatInt.size() > 5) {
                throw new CustomException("Kelvoton sy??te!\n" + "Sy??t?? vaihdettavien korttien indeksit pilkuilla erotettuina. Esim. '2,4,5'");
            }
        }

        for(int vaihdettava : vaihdettavatInt) {
            kasi.set(vaihdettava-1, pakka.nostaUusiKortti());
        }
    }


    /** Metodi tekee yhden jaon tuloksena avattujen saavutusten tulosteista listan,
        ja tulostaa ne jaon tulosten j??lkeen allekkain konsoliin */
    void tarkistaSaavutukset() {

        ArrayList<String> tulosteet1 = tarkistaPokerikadenSaavutukset(viimeisinKadenArvo);
        ArrayList<String> tulosteet2 = tarkistaVoittosummanSaavutukset(viimeisinVoitto);
        ArrayList<String> tulosteet3 = tarkistaSaldonSaavutukset(saldo);
        ArrayList<String> tulosteet4 = new ArrayList<>();

        for (int i=1; i<tulosteet1.size(); i++) {
            tulosteet4.add(tulosteet1.get(i));
        }
        for (int i=1; i<tulosteet2.size(); i++) {
            tulosteet4.add(tulosteet2.get(i));
        }
        for (int i=1; i<tulosteet3.size(); i++) {
            tulosteet4.add(tulosteet3.get(i));
        }
        if (tulosteet4.size() >= 1) {
            System.out.println();
            for (String tuloste: tulosteet4) {
                System.out.println(tuloste);
            }
            System.out.println();
        }
    }


    /** Metodi tarkistaa saako pelaaja kilpapelin lopputulokseen perustuvia saavutuksia ja tulostaa avattujen
        saavutusten tulosteet allekkain konsoliin. */
    void tarkistaKilpapelinSaavutukset(int kilpapelinTulos) {

        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");
        String[] saavutukset = new Saavutukset().getJasenet();
        int[] voitettavatTulokset = {99999, 69420, 31415, 12345, 4999, 2021, 1357, 987, 444, 2};

        for (int i=0; i<10; i++) {
            if (kilpapelinTulos != 0) {
                if (kilpapelinTulos > voitettavatTulokset[i] && !saavutustenTilat[i]) {
                    saavutustenTilat[i] = true;
                    tulosteet.add(0, "Avasit uuden saavutuksen: " + saavutukset[i] +"!");
                }
            }
        }

        if (tulosteet.size() > 0) {
            for (String tuloste: tulosteet) {
                System.out.println(tuloste);
                }
        }
    }


    /** Metodi tarkistaa saako pelaaja pokerik??den arvoon perustuvia saavutuksia ja palauttaa avattujen
     saavutusten tulosteet yhten?? listana. */
    private ArrayList<String> tarkistaPokerikadenSaavutukset(int viimeisinKadenArvo) {

        String[] saavutukset = new Saavutukset().getJasenet();
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");

        for (int i=10; i<20; i++){
            if (viimeisinKadenArvo == i-10 && !saavutustenTilat[i]) {
                saavutustenTilat[i] = true;
                //Tehd????n viesteist?? lista, jotta ne voidaan tulostaa lopuksi pakettina jota ennen ja j??lkeen on kuitenkin tyhj?? rivi
                tulosteet.add("Avasit uuden saavutuksen: " + saavutukset[i] + "!");
            }
        }
        return tulosteet;
    }


    /** Metodi tarkistaa saako pelaaja yhden pokerik??den voittosumman suuruuteen perustuvia saavutuksia ja
        palauttaa avattujen saavutusten tulosteet yhten?? listana. */
    private ArrayList<String> tarkistaVoittosummanSaavutukset(int voitto) {

        int[] voittosummat = {100000, 50000, 10000, 5000, 1000, 750, 500, 200, 100, 20};
        String[] saavutukset = new Saavutukset().getJasenet();
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");
        for (int i=20; i<30; i++){
            if (voitto >= voittosummat[i-20] && !saavutustenTilat[i]) {
                saavutustenTilat[i] = true;
                //Tehd????n viesteist?? lista, jotta ne voidaan tulostaa lopuksi pakettina jota ennen ja j??lkeen on kuitenkin tyhj?? rivi
                tulosteet.add("Avasit uuden saavutuksen: " + saavutukset[i] + "!");
            }
        }
        return tulosteet;
    }


    /** Metodi tarkistaa saako pelaaja saldon suuruuteen perustuvia saavutuksia ja
     palauttaa avattujen saavutusten tulosteet yhten?? listana. */
    ArrayList<String> tarkistaSaldonSaavutukset(int saldo) {

        int[] saldorajat = {1000000, 100000, 10000, 1000, 100};
        String[] saavutukset = new Saavutukset().getJasenet();
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");
        for (int i=30; i<35; i++){
            if (saldo >= saldorajat[i-30] && !saavutustenTilat[i]) {
                saavutustenTilat[i] = true;
                //Tehd????n viesteist?? lista, jotta ne voidaan tulostaa lopuksi pakettina jota ennen ja j??lkeen on kuitenkin tyhj?? rivi
                tulosteet.add("Avasit uuden saavutuksen: " + saavutukset[i] + "!");
            }
        }
        return tulosteet;
    }


    /** Metodi k??ynnist???? voitonmaksutapahtuman jos pelaajan k??si toteuttaa jonkin voittavan pokerik??den kriteerit ja
        selvitt???? tarvitseeko pelaajan parasKasi-attribuuttia p??ivitt????.  */
    void kadenTarkistus(String pelimuoto) throws ArithmeticException{

        ArrayList<Maa> kasiMaat = new ArrayList<>();
        ArrayList<Integer> kasiArvot = new ArrayList<>();

        yhteishaviot = yhteishaviot + panos;

        for (int i=0; i<5; i++) {
            kasiMaat.add(kasi.get(i).getMaa());
        }
        for (int j=0; j<5; j++) {
            kasiArvot.add(kasi.get(j).getNumeroarvo());
        }

        ArrayList<Kortti> paraskasi = (ArrayList<Kortti>) parasKasi[1];
        ArrayList<Integer> paraskasiarvot = new ArrayList<>();
        for (int j=0; j<5; j++) {
            paraskasiarvot.add(paraskasi.get(j).getNumeroarvo());
        }

        int kasiJokerienMaara = Collections.frequency(kasiArvot, 0);
        int parasKasiJokerienMaara = Collections.frequency(paraskasiarvot, 0);


        // Tarkistetaan toteuttaako k??si jonkin voittavan pokerik??den kriteerit

        if (testaaKuningasVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: Kuningasv??risuora!");
            voitonmaksu(250, 0, pelimuoto);

            // Vaihdetaan kyseinen k??si uudeksi parhaaksi k??deksi jos se on arvoltaan parempi
            if ((Integer) parasKasi[0] > 0 || (kasiJokerienMaara > parasKasiJokerienMaara && (Integer) parasKasi[0] == 0)) {
                vaihdaParasKasi(0);
            }

        } else if (testaaViitoset(kasiArvot)) {
            System.out.println("Tulos: Viitoset!");
            voitonmaksu(250, 1, pelimuoto);

            if ((Integer) parasKasi[0] > 1 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 1)) {
                vaihdaParasKasi(1);
            }

        } else if (testaaVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: V??risuora!");
            voitonmaksu(75, 2, pelimuoto);

            if ((Integer) parasKasi[0] > 2 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 2)) {
                vaihdaParasKasi(2);
            }

        } else if (testaaNeloset(kasiArvot)) {
            System.out.println("Tulos: Neloset!");
            voitonmaksu(50, 3, pelimuoto);

            if ((Integer) parasKasi[0] > 3 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 3)) {
                vaihdaParasKasi(3);
            }

        } else if (testaaTayskasi(kasiArvot)) {
            System.out.println("Tulos: T??ysk??si!");
            voitonmaksu(20, 4, pelimuoto);

            if ((Integer) parasKasi[0] > 4 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 4)) {
                vaihdaParasKasi(4);
            }

        } else if (testaaVari(kasiMaat)) {
            System.out.println("Tulos: V??ri!");
            voitonmaksu(15, 5, pelimuoto);

            if ((Integer) parasKasi[0] > 5 || (kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 5)) {
                vaihdaParasKasi(5);
            }

        } else if (testaaSuora(kasiArvot)) {
            System.out.println("Tulos: Suora!");
            voitonmaksu(10, 6, pelimuoto);

            if ((Integer) parasKasi[0] > 6 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 6)) {
                vaihdaParasKasi(6);
            }

        } else if (testaaKolmoset(kasiArvot)) {
            System.out.println("Tulos: Kolmoset!");
            voitonmaksu(5, 7, pelimuoto);

            if ((Integer) parasKasi[0] > 7 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara &&(Integer) parasKasi[0] == 7)) {
                vaihdaParasKasi(7);
            }

        } else if (testaaKaksiParia(kasiArvot)) {
            System.out.println("Tulos: Kaksi paria!");
            voitonmaksu(3, 8, pelimuoto);

            if ((Integer) parasKasi[0] > 8) {
                vaihdaParasKasi(8);
            }

        } else if (testaaPari(kasiArvot)) {
            System.out.println("Tulos: 10-A pari!");
            voitonmaksu(2, 9, pelimuoto);

            if ((Integer) parasKasi[0] > 9) {
                vaihdaParasKasi(9);
            }

        } else {
            System.out.println("Ei voittoa.");
            viimeisinVoitto = 0;
            viimeisinKadenArvo = -1;
            havitytJaot++;
        }
    }


    /** Metodi lis???? voitot pelaajan saldoon ja p??ivitt???? pelaajan attribuutit viimeisinVoitto, yhteisvoitot,
        viimeisinKadenArvo, voitetutJaot, suurinVoitto ja suurinSaldo. */
    private void voitonmaksu(int kerroin, int kadenArvo, String pelimuoto) throws ArithmeticException{

        if (panos == 0) {
            // Py??ristet????n nollapanoksen voitot yl??sp??in l??himp????n kokonaislukuun
            viimeisinVoitto = (kerroin+1)/2 ;
            yhteisvoitot = yhteisvoitot + (kerroin+1)/2;
        } else {
            viimeisinVoitto = multiplyExact(kerroin,panos);
            yhteisvoitot = yhteisvoitot + kerroin*panos;
        }

        viimeisinKadenArvo = kadenArvo;
        voitetutJaot++;

        System.out.println("Voitit " + viimeisinVoitto + " kolikkoa.");

        if (pelimuoto.equals("vapaapeli")) {
            int uusiSaldo = addExact(getSaldo(), viimeisinVoitto);
            setSaldo(uusiSaldo);
        }

        if (pelimuoto.equals("kilpapeli")) {
            int uusiSaldo = addExact(getKilpapelinSaldo(), viimeisinVoitto);
            setKilpapelinSaldo(uusiSaldo);
        }

        if (viimeisinVoitto > suurinVoitto) {
            suurinVoitto = viimeisinVoitto;
        }

        if (saldo > suurinSaldo) {
            suurinSaldo = saldo;
        }

    }


    /** Metodi vaihtaa parasKasi-attribuutissa tallessa olevan pelaajan parhaan k??den uuteen parempaan k??teen */
    private void vaihdaParasKasi(int uusiParhaanKadenArvo) {
        parasKasi[0] = uusiParhaanKadenArvo;
        parasKasi[1] = kasi;
    }



    // Seuraavat metodit testaavat l??ytyyk?? pelaajan 5 kortista voittavaa pokerik??tt??. Mahdolliset pokerik??den
    // arvot testataan j??rjestyksess?? arvokkaimmasta k??dest?? arvottomimpaan.

    private boolean testaaKuningasVarisuora(ArrayList<Integer> kasiArvot, ArrayList<Maa> kasiMaat) {
        if (testaaVari(kasiMaat) && testaaKuningasSuora(kasiArvot)) {
            return true;
        }
        return false;
    }

    private boolean testaaViitoset(ArrayList<Integer> kasiArvot) {
        int jokerienMaara = Collections.frequency(kasiArvot, 0);
        // Jos jokerien ja jonkin tietyn arvoisen kortin esiintymien yhteism????r?? on viisi, k??si on arvoltaan viitoset.
        for (int i=2; i<15; i++) {
            if (jokerienMaara + Collections.frequency(kasiArvot, i) == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean testaaVarisuora(ArrayList<Integer> kasiArvot, ArrayList<Maa> kasiMaat) {
        if (testaaVari(kasiMaat) && testaaSuora(kasiArvot)) {
            return true;
        }
        return false;
    }

    private boolean testaaNeloset(ArrayList<Integer> kasiArvot) {
        int jokerienMaara = Collections.frequency(kasiArvot, 0);
        // Jos jokerien ja jonkin tietyn arvoisen kortin esiintymien yhteism????r?? on nelj??, k??si on arvoltaan neloset.
        for (int i=1; i<15; i++) {
            if (jokerienMaara + Collections.frequency(kasiArvot, i) == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean testaaTayskasi(ArrayList<Integer> kasiArvot) {
        if (!(kasiArvot.contains(0))) {
            if (testaaKolmoset(kasiArvot) && testaaPari2(kasiArvot)) {
                return true;
            }
        } else {
            // Tutkitaan tilanne, jossa jokeri on mukana kolmosissa.
            Collections.sort(kasiArvot);
            ArrayList<Integer> kasiArvotKopio = new ArrayList<>(kasiArvot);
            int i = 0;
            for (int arvo : kasiArvot) {
                if ((Collections.frequency(kasiArvot, arvo) == 2) && kasiArvot.contains(0)) {
                    // Poistetaan kolmosen muodostavat kortit, jotta jokeri ei en???? ole mukana parin tarkistuksessa.
                    kasiArvotKopio.remove(i);
                    kasiArvotKopio.remove(i);
                    kasiArvotKopio.remove(0);
                    if (testaaPari2(kasiArvotKopio)) {
                        return true;
                    }
                    // Jos kyseess?? on pelk??t kolmoset, eik?? t??ysk??si, lopetetaan for-loop.
                    break;
                }
                i++;
            }
        }
        return false;
    }

    private boolean testaaVari(ArrayList<Maa> kasiMaat) {
        List<Maa> maalista = List.of(Maa.PATA, Maa.HERTTA, Maa.RISTI, Maa.RUUTU);
        int jokerienMaara = Collections.frequency(kasiMaat, Maa.JOKERI);

        // Jos jokerien ja jonkin tietyn maan esiintymien yhteism????r?? on viisi, k??si on arvoltaan v??ri.
        for (Maa maa : maalista) {
            if (Collections.frequency(kasiMaat, maa) + jokerienMaara == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean testaaKuningasSuora(ArrayList<Integer> kasiArvot) {
        // Tehd????n kasiArvot listasta kopio jotta v??ltyt????n sivuvaikutuksilta.
        ArrayList<Integer> kasiArvotKopio = new ArrayList<>(kasiArvot);
        // Jos k??si on suora ja sen pienin arvo on 10, k??si on my??s kuningassuora.
        if (testaaSuora(kasiArvotKopio)) {
            int jokerienMaara = Collections.frequency(kasiArvotKopio, 0);
            for (int i=0; i<jokerienMaara; i++) {
                kasiArvotKopio.remove(Integer.valueOf(0));
            }
            if (Collections.min(kasiArvotKopio) >= 10) {
                return true;
            }
        }
        return false;
    }


    private boolean testaaSuora(ArrayList<Integer> kasiArvot) {
        // J??rjestet????n k??den korttien numeroarvot. Jokerin arvo on 0, joten ne menev??t listan alkuun.
        Collections.sort(kasiArvot);
        int perakkaiset = 0;
        for (int i=0; i<4; i++) {
            if (kasiArvot.get(i)+1 == kasiArvot.get(i+1)) {
                perakkaiset++;
            }
            // Otetaan my??s jokerit huomioon. Jokeri muodostaa aina per??kk??isyyden.
              else if (kasiArvot.get(i) + kasiArvot.get(i+1) == kasiArvot.get(i)) {
                perakkaiset++;
            } else if (kasiArvot.get(i) + kasiArvot.get(i+1) == kasiArvot.get(i+1)) {
                perakkaiset++;
            }
        }
        // Tilanteet joissa kaikki kortit ovat arvoiltaan per??kk??isess?? j??rjestyksess??.
        // Kattaa my??s tilanteet joissa on 4 tai 5 jokeria.
        if (perakkaiset == 4) {
            return true;
        }

        // Tarkistetaan viel?? kaikki tilanteet jossa jokereita on 1, 2 tai 3 kappaletta.
        int jokerienMaara = Collections.frequency(kasiArvot, 0);

        // Jos jokereita on yksi, normaaleista nelj??st?? kortista suurimman ja pienimm??n arvon
        // et??isyys toisistaan saa olla enint????n 4, mutta v??hint????n 3. Duplikaattiarvoja ei my??sk????n saa olla.
        int suurinNormaalinKortinArvo = kasiArvot.get(4);
        int pieninNormaalinKortinArvo = kasiArvot.get(1);
        int arvojenEtaisyys = suurinNormaalinKortinArvo-pieninNormaalinKortinArvo;
        if (jokerienMaara == 1 && arvojenEtaisyys < 5 && arvojenEtaisyys > 2 && !SamojaArvoja()) {
            return true;
        }

        // Jos jokereita on kaksi, normaaleista kolmesta kortista suurimman ja pienimm??n arvon
        // et??isyys toisistaan saa olla enint????n 4, mutta v??hint????n 2.
        suurinNormaalinKortinArvo = kasiArvot.get(4);
        pieninNormaalinKortinArvo = kasiArvot.get(2);
        arvojenEtaisyys = suurinNormaalinKortinArvo-pieninNormaalinKortinArvo;
        if (jokerienMaara == 2 && arvojenEtaisyys < 5 && arvojenEtaisyys > 1) {
            return true;
        }

        // Jos jokereita on kolme, kahden normaalin kortin arvojen et??isyys toisistaan saa olla enint????n 4,
        // mutta v??hint????n 1.
        int suuremmanNormaalinKortinArvo = kasiArvot.get(4);
        int pienemmanNormaalinKortinArvo = kasiArvot.get(3);
        arvojenEtaisyys = suuremmanNormaalinKortinArvo-pienemmanNormaalinKortinArvo;
        if (jokerienMaara == 3 && arvojenEtaisyys < 5 && arvojenEtaisyys > 0) {
            return true;
        }

        // Tilanteet, joissa ??ss?? on suoran pienin kortti, t??ytyy tutkia erikseen.
        if (perakkaiset >= 2 && kasiArvot.contains(14) && kasiArvot.get(3) < 6) {
            return true;
        }
        return false;
    }


    private boolean testaaKolmoset(ArrayList<Integer> kasiArvot) {
        int jokerienMaara = Collections.frequency(kasiArvot, 0);
        // Jos jokerien ja jonkin tietyn arvoisen kortin esiintymien yhteism????r?? on kolme, k??si on arvoltaan kolmoset.
        for (int i=1; i<15; i++) {
            if (jokerienMaara + Collections.frequency(kasiArvot, i) == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean testaaKaksiParia(ArrayList<Integer> kasiArvot) {
        int parit = 0;
        for (int i=2; i<15; i++) {
            if (Collections.frequency(kasiArvot, i) == 2) {
                parit++;
            }
        }
        if (parit == 2) {
            return true;
        }
        // Jokeria ei tarvitse erikseen testata, sill?? kolmoset ovat aina parempi kuin kaksi paria.
        return false;
    }

    private boolean testaaPari(ArrayList<Integer> kasiArvot) {
        // Metodi hyv??ksyy pariksi vain 10-A arvoisten korttien muodostamat parit
        for (int i=10; i<15; i++) {
            if (Collections.frequency(kasiArvot, i) == 2) {
                return true;
            }
            // Toteutuu aina kun k??dess?? on jokeri ja jokin kortti v??lilt?? 10-14.
            if (kasiArvot.contains(i) && kasiArvot.contains(0)) {
                return true;
            }
        }
        return false;
    }

    private boolean testaaPari2(ArrayList<Integer> kasiArvot) {
        // Metodi hyv??ksyy pariksi mink?? tahansa parin.
        for (int i=2; i<15; i++) {
            if (Collections.frequency(kasiArvot, i) == 2) {
                return true;
            }
            // Jokeri muodostaa aina parin.
            if (kasiArvot.contains(0)) {
                return true;
            }
        }
        return false;
    }


    /** Metodi palauttaa tiedon siit??,  */
    private boolean SamojaArvoja() {
        ArrayList<Integer> kasiArvotIlmanJokereita = new ArrayList<>();
        for (int j=0; j<5; j++) {
            kasiArvotIlmanJokereita.add(kasi.get(j).getNumeroarvo());
        }
        int jokerienMaara = Collections.frequency(kasiArvotIlmanJokereita, 0);
        for (int i=0; i<jokerienMaara; i++) {
            kasiArvotIlmanJokereita.remove(Integer.valueOf(0));
        }

        // HashSet poistaa listasta duplikaatit.
        Set<Integer> set = new HashSet<Integer>(kasiArvotIlmanJokereita);

        // Jos listassa oli duplikaatteja, setin pituus on pienempi kuin alkuper??isen listan pituus.
        if(set.size() < kasiArvotIlmanJokereita.size()){
            return true;
        }
        return false;
    }




    // K??den tarkistuksen toiminnan testaamisessa k??ytetty metodi.
    // Ei toimi t??ll?? hetkell??!
    /*
    void kadenTarkistusTest(String pelimuoto) {
        ArrayList<Maa> kasiMaat = new ArrayList<>();
        ArrayList<Integer> kasiArvot = new ArrayList<>();

        for (int i=0; i<5; i++) {
            kasiMaat.add(kasi.get(i).getMaa());
        }
        for (int j=0; j<5; j++) {
            kasiArvot.add(kasi.get(j).getNumeroarvo());
        }

        //Tarkistetaan toteuttaako k??si jonkin voittavan k??den kriteerit
        if (testaaKuningasVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: Kuningasv??risuora!");
            int kerroin = 250;
            voitonmaksu(kerroin, 0, pelimuoto);


        } else if (testaaViitoset(kasiArvot)) {
            System.out.println("Tulos: Viitoset!");
            int kerroin = 250;
            voitonmaksu(kerroin, 1, pelimuoto);


        } else if (testaaVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: V??risuora!");
            int kerroin = 75;
            voitonmaksu(kerroin, 2, pelimuoto);

        } else if (testaaNeloset(kasiArvot)) {
            System.out.println("Tulos: Neloset!");
            int kerroin = 50;
            voitonmaksu(kerroin, 3, pelimuoto);

        } else if (testaaTayskasi(kasiArvot)) {
            System.out.println("Tulos: T??ysk??si!");
            int kerroin = 20;
            voitonmaksu(kerroin, 4, pelimuoto);


        } else if (testaaVari(kasiMaat)) {
            System.out.println("Tulos: V??ri!");
            int kerroin = 15;
            voitonmaksu(kerroin, 5, pelimuoto);


        } else if (testaaSuora(kasiArvot)) {
            System.out.println("Tulos: Suora!");
            int kerroin = 10;
            voitonmaksu(kerroin, 6, pelimuoto);


        } else if (testaaKolmoset(kasiArvot)) {
            System.out.println("Tulos: Kolmoset!");
            int kerroin = 5;
            voitonmaksu(kerroin, 7, pelimuoto);


        } else if (testaaKaksiParia(kasiArvot)) {
            System.out.println("Tulos: Kaksi paria!");
            int kerroin = 3;
            voitonmaksu(kerroin, 8, pelimuoto);


        } else if (testaaPari(kasiArvot)) {
            System.out.println("Tulos: 10-A pari!");
            int kerroin = 2;
            voitonmaksu(kerroin, 9, pelimuoto);


        } else {
            System.out.println("Ei voittoa.");
            viimeisinVoitto = 0;
            viimeisinKadenArvo = -1;
            havitytJaot++;
        }
    }*/

}