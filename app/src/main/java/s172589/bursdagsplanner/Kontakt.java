package s172589.bursdagsplanner;

/**
 * Created by Roger on 15.09.2015.
 */
public class Kontakt {
    int _ID;
    String navn;
    int tlf;

    public Kontakt(int _ID, String navn, int tlf) {
        this._ID = _ID;
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

    public void setTlf(int tlf) {
        this.tlf = tlf;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }
}
