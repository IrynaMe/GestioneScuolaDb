package gestioneScuola.menu;

public enum MenuMateria implements MenuInterfaccia {
   // AGGIUNGI_MATERIA("Aggiungi materia"),
    //   CAMBIA_STATO_MATERIA("Cambia stato materia: abilita/disabilita"),
    //STAMPA_LISTA_MATERIE("Stampa lista materie abilitati"),
    ESCI("Esci");


    private final String description;
    MenuMateria(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
