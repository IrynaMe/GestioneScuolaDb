package gestioneScuola.menu;

public enum MenuDocente implements MenuInterfaccia {
    AGGIUNGI_DOCENTE("Aggiungi docente"),
    //MODIFICA_DOCENTE("Modifica docente"),
    CERCA_DOCENTE("Cerca docente per cf"),
    CAMBIA_STATO_DOCENTE("Cambia stato docente: abilita/disabilita"),
    STAMPA_LISTA_DOCENTI("Stampa lista docenti"),
    ESCI("Esci");


    private final String description;
    MenuDocente(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
