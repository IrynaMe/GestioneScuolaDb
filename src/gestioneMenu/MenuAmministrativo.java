package gestioneMenu;

public enum MenuAmministrativo {
    ESCI("Esci"),
    AGGIUNGI_AMMINISTRATIVO("Aggiungi amministrativo"),
    CAMBIA_STATO_AMMINISTRATIVO("Cambia stato amministrativo(abilita/disbilita)"),
    MODIFICA_AMMINISTRATIVO("Modifica dati amministrativo"),
    STAMPA_AMMINISTRATIVI("Stampa lista di amministrativi");

    private final String description;

    MenuAmministrativo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
