package gestioneMenu;

public enum MenuDocente {
    ESCI("Esci"),
    AGGIUNGI_DOCENTE("Aggiungi docente"),
    CAMBIA_STATO_DOCENTE("Cambia stato docente(abilita/disbilita)"),
    MODIFICA_DOCENTE("Modifica dati docente"),
    STAMPA_DOCENTI("Stampa lista di docenti");

    private final String description;

    MenuDocente(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
