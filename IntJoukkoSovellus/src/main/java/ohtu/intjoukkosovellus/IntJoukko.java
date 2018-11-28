
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukujono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLukumaara;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        setUpClass(OLETUSKASVATUS, KAPASITEETTI);
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }

        setUpClass(OLETUSKASVATUS, kapasiteetti);
    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti liian pieni");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kasvatuskoko liian pieni");
        }

        setUpClass(kasvatuskoko, kapasiteetti);
    }

    public void setUpClass(int kasvatuskoko, int kapasiteetti) {
        lukujono = new int[kapasiteetti];
        for (int i = 0; i < lukujono.length; i++) {
            lukujono[i] = 0;
        }
        alkioidenLukumaara = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (alkioidenLukumaara == 0) {
            return lisaaLuku(luku, 0);
        } else if (!kuuluu(luku)) {
            lisaaLuku(luku, alkioidenLukumaara);
            muokkaaTaulukkoa();
            return true;
        }
        return false;
    }

    public boolean lisaaLuku(int luku, int vertausLuku) {
        lukujono[vertausLuku] = luku;
        alkioidenLukumaara++;
        return true;
    }

    public void muokkaaTaulukkoa() {
        if (alkioidenLukumaara % lukujono.length == 0) {
            int[] taulukkoOld = new int[lukujono.length];
            taulukkoOld = lukujono;
            kopioiTaulukko(lukujono, taulukkoOld);
            lukujono = new int[alkioidenLukumaara + kasvatuskoko];
            kopioiTaulukko(taulukkoOld, lukujono);
        }
    }

    public boolean kuuluu(int luku) {
        boolean kuuluuko = false;
        for (int i = 0; i < alkioidenLukumaara; i++) {
            if (luku == lukujono[i]) {
                kuuluuko = true;
            }
        }
        return kuuluuko;
    }

    public boolean poista(int luku) {
        int kohta = etsiLuku(luku);
        if (kohta != -1) {
            poistaLuku(kohta);
            return true;
        }
        return false;
    }

    public int etsiLuku(int luku) {
        int kohta = -1;
        for (int i = 0; i < alkioidenLukumaara; i++) {
            if (luku == lukujono[i]) {
                kohta = i;
                lukujono[kohta] = 0;
                break;
            }
        }
        return kohta;
    }

    public void poistaLuku(int kohta) {
        int apu;
        for (int j = kohta; j < alkioidenLukumaara - 1; j++) {
            apu = lukujono[j];
            lukujono[j] = lukujono[j + 1];
            lukujono[j + 1] = apu;
        }
        alkioidenLukumaara--;
    }

    private void kopioiTaulukko(int[] vanhaTaulukko, int[] uusiTaulukko) {
        for (int i = 0; i < vanhaTaulukko.length; i++) {
            uusiTaulukko[i] = vanhaTaulukko[i];
        }
    }

    public int mahtavuus() {
        return alkioidenLukumaara;
    }


    @Override
    public String toString() {
        if (alkioidenLukumaara == 0) {
            return "{}";
        } else if (alkioidenLukumaara == 1) {
            return "{" + lukujono[0] + "}";
        } else {
            return luoTuotos();
        }
    }

    public String luoTuotos() {
        String tuotos = "{";
        for (int i = 0; i < alkioidenLukumaara - 1; i++) {
            tuotos += lukujono[i];
            tuotos += ", ";
        }
        tuotos += lukujono[alkioidenLukumaara - 1] + "}";
        return tuotos;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLukumaara];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = lukujono[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko uusiJoukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            uusiJoukko.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            uusiJoukko.lisaa(bTaulu[i]);
        }
        return uusiJoukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko uusiJoukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    uusiJoukko.lisaa(bTaulu[j]);
                }
            }
        }
        return uusiJoukko;

    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko uusiJoukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            uusiJoukko.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            uusiJoukko.poista(i);
        }
        return uusiJoukko;
    }
}