package gestioneMenu;

public enum MenuAllievo {
    ESCI("Esci"),
    AGGIUNGI_ALLIEVO("Aggiungi allievo"),
    CAMBIA_STATO_ALLIEVO("Cambia stato allievo(abilita/disbilita)"),
    MODIFICA_ALLIEVO("Modifica dati allievo"),
    STAMPA_ALLIEVI("Stampa lista di allievi");

    private final String description;

    MenuAllievo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}