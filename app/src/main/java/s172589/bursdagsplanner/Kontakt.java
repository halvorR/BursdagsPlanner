package s172589.bursdagsplanner;

import java.io.Serializable;

/**
 * Created by Roger on 15.09.2015.
 */
public class Kontakt implements Serializable {
    String navn;
    int tlf;
    String dato;

    public Kontakt(String dato, String navn, int tlf) {
        this.dato = dato;
        this.navn = navn;
        this.tlf = tlf;
    }

    public Kontakt(String navn, int tlf) {
        this.navn = navn;
        this.tlf = tlf;
    }

    public Kontakt() {
    }

    public String getNavn() {
        return navn;
    }

    public int getTlf() {
        return tlf;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setTlf(int tlf) { this.tlf = tlf; }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

}
