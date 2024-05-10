package gestioneScuola;

public class Materia {
    private int codice;
    private String nomeMateria;
    private int abilitato;

    public Materia(int codice, String nomeMateria) {
        this.codice = codice;
        this.nomeMateria = nomeMateria;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    public int getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(int abilitato) {
        this.abilitato = abilitato;
    }
}
