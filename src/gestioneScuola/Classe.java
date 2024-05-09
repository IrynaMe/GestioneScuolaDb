package gestioneScuola;

public class Classe {
    private int livello;
    private String sezione;

    public Classe(int livello, String sezione) {
        this.livello = livello;
        this.sezione = sezione;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }
}//
