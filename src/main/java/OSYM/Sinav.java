package OSYM;

public class Sinav {

    private String sinavAdi;
    private String sinavTarihi;

    public Sinav(String sinavAdi, String sinavTarihi) {
        this.sinavAdi = sinavAdi;
        this.sinavTarihi = sinavTarihi;
    }

    public String getSinavAdi() {
        return sinavAdi;
    }

    public String getSinavTarihi() {
        return sinavTarihi;
    }

    @Override
    public String toString() {
        return this.sinavAdi;
    }


}
