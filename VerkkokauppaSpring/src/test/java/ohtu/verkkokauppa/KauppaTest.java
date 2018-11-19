package ohtu.verkkokauppa;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Kauppa kauppa;
    
    @Test
    public void ostoksenPaaytyttyaPankinMetodiTilisiirtoToimiiOikein() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "mehu", 9));
        Kauppa k = new Kauppa(varasto, pankki, viite);     
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
    
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(9));
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiTilisiirtoToimiiOikeinKahdellaTuotteelĺa() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "mehu", 2));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "pulla", 3));
        Kauppa k = new Kauppa(varasto, pankki, viite);     
    
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
    
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiTilisiirtoToimiiOikeinKahdellaSamallaTuotteelĺa() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "pulla", 3));
        Kauppa k = new Kauppa(varasto, pankki, viite);     
    
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
    
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(6));
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiTilisiirtoToimiiKahdellaTuotteelĺaJoistaYksiLopussa() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(0); 
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "mehu", 2));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "pulla", 3));
        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
    
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(3));
    }

    @Test
    public void aloitaAsiointiToimii() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "pulla", 3));
        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(2);

        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
    
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(6));
    }

    @Test
    public void uusiViitenumeroUudelleMaksulle() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1).thenReturn(2).thenReturn(3);
    
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "pulla", 3));
        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        verify(viite, times(1)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
    
        verify(viite, times(2)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.lisaaKoriin(2);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
    
        verify(viite, times(3)).uusi();
    }

    @Test
    public void poistoKoristaToimii() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1).thenReturn(2).thenReturn(3);
    
        Varasto varasto = mock(Varasto.class);
        Tuote tuote = new Tuote(2, "pulla", 3);
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(2)).thenReturn(tuote);
        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.poistaKorista(2);

        verify(varasto, times(1)).palautaVarastoon(tuote);
    }
}
