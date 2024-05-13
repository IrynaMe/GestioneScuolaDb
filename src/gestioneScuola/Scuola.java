package gestioneScuola;


import gestioneScuola.menu.*;
import librerie.gestioneConsole.GestioneConsole;
import librerie.gestioneDb.ManageDb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Scuola {
    GestioneConsole gc = new GestioneConsole();
    ManageDb miodb = new ManageDb();

    private String nomeScuola;
    // private List<Persona> allievi;
    // private List<Persona> insegnanti;
    //private List<Persona> amministrativi;
    private List<Persona> persone;


    public Scuola(String nomeScuola) {
        this.nomeScuola = nomeScuola;
        //   this.allievi = new ArrayList<>();
        //  this.insegnanti = new ArrayList<>();
        //  this.amministrativi = new ArrayList<>();
        this.persone = new ArrayList<>();
    }

    //METODI PER STAMPARE E GESTIRE MENU
    //Menu principale: return Entita da passare in stampaMenu per definire menu da stampare
    public Entita scegliEntita() {
        Entita[] arrEntita = Entita.values();
        Entita entitaScelta = null;

        do {
            System.out.println("*** Menu principale ****");
            System.out.println("Scegli tipologia di operazioni");
            for (int i = 0; i < arrEntita.length; i++) {
                System.out.println((i + 1) + " -> " + arrEntita[i].getDescription());
            }

            //  System.out.print("Inserisci la scelta: ");
            try {
                int scelta = gc.dammiIntero("Inserisci la scelta: ", "Input non valido, riprova", "Inserimento non e andato con successo", "*** Sei entrato in submenu ***", 3, 1, arrEntita.length);

                if (scelta >= 1 && scelta <= arrEntita.length) {
                    entitaScelta = arrEntita[scelta - 1];
                } else {
                    System.out.println("Scelta errata, riprova");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido, inserisci un numero corretto");
            }

        } while (entitaScelta == null);


        return entitaScelta;
    }


    //SubMenu:riceve Entita da scegliEntita
    public MenuInterfaccia stampaMenu(Entita entita) {
        MenuInterfaccia[] menuOptions = null;
        MenuInterfaccia votoMenuSchelto = null;
        Integer input = -1;
        //scelgo menu da usare partendo dalla entita prescelta
        if (entita.equals(Entita.ALLIEVO)) {
            menuOptions = MenuAllievo.values();
        } else if (entita.equals(Entita.AMMINISTRATIVO)) {
            menuOptions = MenuAmministrativo.values();
        } else if (entita.equals(Entita.DOCENTE)) {
            menuOptions = MenuDocente.values();
        } else if (entita.equals(Entita.PROVA)) {
            menuOptions = MenuProva.values();
        } else if (entita.equals(Entita.MATERIA)) {
            menuOptions = MenuMateria.values();
        }
        //stampo 1 dei menu dependendo dall aentita
        do {
            System.out.println("*** Specifica l'operazione ***");
            for (int i = 0; i < menuOptions.length; i++) {
                System.out.println((i + 1) + " -> " + menuOptions[i].getDescription());
            }
            System.out.println("************************");
            try {
                input = gc.dammiIntero("Inserisci la scelta: ", "Inserimento errato, riprova", "Inserimento non è andato con successo", "*** Eseguimento di operazione ***", 5, 1, menuOptions.length);
            } catch (NumberFormatException e) {
                System.out.println("Input non valido, riprova");
                continue;
            }
            if (input != null && input >= 1 && input <= menuOptions.length) {
                votoMenuSchelto = menuOptions[input - 1];
            } else {
                System.out.println("Scelta non valida, riprova");
            }
        } while (!entita.equals(Entita.NON_DEFINITO) && input == null);

        return votoMenuSchelto;
    }

    public void gestisciSceltaMenu() {
        // connessione con db
        miodb.conn();
        // stampa menu
        while (true) {
            //VotoMenu votoMenu = gc.stampaMenu();
            Entita entita = scegliEntita();
            if (entita != null) {
                switch (entita) {
                    case ALLIEVO:
                        MenuInterfaccia input = stampaMenu(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.CERCA_ALLIEVO)) cercaPersonaPerCf(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.AGGIUNGI_ALLIEVO)) aggiungiPersona(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.STAMPA_LISTA_ALLIEVI)) stampaListaPersone(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.CAMBIA_STATO_ALLIEVO)) cambiaStatoPersona(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.ESCI)) {
                            System.out.println("Stai per tornare in menu principale");
                            break;
                        }
                        break;
                    case DOCENTE:
                        MenuInterfaccia input1 = stampaMenu(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.CERCA_DOCENTE)) cercaPersonaPerCf(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.AGGIUNGI_DOCENTE)) aggiungiPersona(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.STAMPA_LISTA_DOCENTI)) stampaListaPersone(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.CAMBIA_STATO_DOCENTE)) cambiaStatoPersona(Entita.DOCENTE);
                        if (input1.equals((MenuDocente.DOCENTE_IN_CLASSE))) aggiungiDocenteInClasse();
                        if (input1.equals(MenuDocente.ESCI)) {
                            System.out.println("Stai per tornare in menu principale");
                            break;
                        }
                        break;
                    case AMMINISTRATIVO:
                        MenuInterfaccia input2 = stampaMenu(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.CERCA_AMMINISTRATIVO))
                            cercaPersonaPerCf(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.AGGIUNGI_AMMINISTRATIVO))
                            aggiungiPersona(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.STAMPA_LISTA_AMMINISTRATIVO))
                            stampaListaPersone(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.CAMBIA_STATO_AMMINISTRATIVO))
                            cambiaStatoPersona(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.ESCI)) {
                            System.out.println("Stai per tornare in menu principale");
                            break;
                        }
                        break;
                    case PROVA:
                        MenuInterfaccia input3 = stampaMenu(Entita.PROVA);
                        if (input3.equals(MenuProva.CERCA_PROVA)) cercaProvaPerDataOra();
                        if (input3.equals(MenuProva.AGGIUNGI_PROVA_ALLIEVO)) aggiungiProva();
                        if (input3.equals(MenuProva.CAMBIA_STATO_PROVA)) cambiaStatoProva();
                        if (input3.equals(MenuProva.ESCI)) {
                            System.out.println("Stai per tornare in menu principale");
                            break;
                        }
                        break;
                    case MATERIA:

                        MenuInterfaccia input4 = stampaMenu(Entita.MATERIA);
                        if (input4.equals(MenuMateria.CERCA_MATERIA)) cercaMateriaPerCodice();
                        if (input4.equals(MenuMateria.CAMBIA_STATO_MATERIA)) cambiaStatoMateria();
                        if (input4.equals(MenuMateria.ESCI)) {
                            System.out.println("Stai per tornare in menu principale");
                            break;
                        }
                        break;
                    case CLASSE:
                        MenuInterfaccia input5 = stampaMenu(Entita.CLASSE);
                        if (input5.equals(MenuClasse.CERCA_CLASSE)) cercaClassePerLivelloSezione();
                        if (input5.equals(MenuClasse.CAMBIA_STATO_CLASSE)) cambiaStatoClasse();
                        if (input5.equals(MenuClasse.ESCI)) {
                            System.out.println("Stai per tornare in menu principale");
                            break;
                        }
                        break;
                    case NON_DEFINITO:
                        System.out.println("Arrivederci!");
                        miodb.disconnect();
                        gc.chiudiScanner();
                        return;
                    default:
                        System.out.println("Scelta non valida, riprova");
                }
            } else {
                System.out.println("Arrivederci!");
                miodb.disconnect();
                gc.chiudiScanner();
            }
        }
    }

    //FINE METODI PER STAMPARE E GESTIRE MENU

    //return querySql to
    public void aggiungiPersona(Entita entita) {
        String querySql = null;
        String nomeTabella = null;
        //definisco tabella da inserire query. Inserimento ruolo errato è gestito nel gestisciSceltaMenu
        if (entita.equals(Entita.ALLIEVO)) {
            nomeTabella = "allievo";
        } else if (entita.equals(Entita.DOCENTE)) {
            nomeTabella = "docente";
        } else if (entita.equals(Entita.AMMINISTRATIVO)) {
            nomeTabella = "amministrativo";
        } else {
            System.out.println("Entità errata, tabella di inserimento non definita");
        }


        //prendo i dati con gestione console
        String cf = gc.dammiCodiceFiscale("Inserisci codice fiscale di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "CF inserito con successo", 3);

        String nome = gc.dammiStringa("Inserisci nome di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Nome inserito con successo", 3, 2, 20);

        String cognome = gc.dammiStringa("Inserisci cognome di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Cognome inserito con successo", 3, 2, 20);
        String sesso = gc.dammiSesso("Inserisci sesso: un carattere: f/m per " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Sesso inserito con successo", 3);

        String statoNascita = gc.dammiStringa("Inserisci stato di nascita di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Stato di nascita inserita con successo", 3, 2, 30);

        String provinciaNascita = gc.dammiStringa("Inserisci provincia di nascita di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Provincia di nascita inserita con successo", 3, 2, 30);

        String comuneNascita = gc.dammiStringa("Inserisci comune di nascita di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",

                "Comune di nascita inserita con successo", 3, 2, 30);
        LocalDate dataNascita = gc.dammiData("Inserisci data di nascita in formato dd-mm-yyyy di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "La data inseriti con successo", 3, 1920, 2018);


        String email = gc.dammiMail("Inserisci email di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "I dati inseriti con successo", 3);
        //creo oggetto
        Persona persona = new Persona(cf, nome, cognome, sesso, statoNascita, provinciaNascita, comuneNascita, dataNascita, email);


        //creo querySql da passare in writeInDb
        querySql = "INSERT INTO " + nomeTabella + " (`cf`, `nome`, `cognome`, `sesso`, `stato_nascita`, `provincia_nascita`, `comune_nascita`, `data_nascita`, `email`)" +
                " VALUES ('" + persona.getCf() + "', '" + persona.getNome() + "', '" + persona.getCognome() + "', '" + persona.getSesso() + "', '" +
                persona.getStatoNascita() + "', '" + persona.getProvinciaNascita() + "', '" + persona.getComuneNascita() + "', '" +
                persona.getDataNascita() + "', '" + persona.getEmail() + "')";


        //scrivo in db
        miodb.writeInDb(querySql);
    }

    public Persona cercaPersonaPerCf(Entita entita) {
        String nomeTabella = null;
        String sqlQuery = null;
        String cf = null;
        Persona persona = null;

        if (entita.equals(Entita.ALLIEVO) || entita.equals(Entita.AMMINISTRATIVO) || entita.equals(Entita.DOCENTE)) {
            //definisco tabella da inserire query.
            nomeTabella = getNomeTabella(entita);
            cf = gc.dammiCodiceFiscale("Inserisci codice fiscale di " + nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                    "CF inserito con successo", 3);

            sqlQuery = "SELECT * FROM " + nomeTabella + " WHERE cf = '" + cf + "' AND abilitato=1";

            ResultSet resultSet = miodb.readInDb(sqlQuery);
            try {
                while (resultSet.next()) {
                    String cfPersona = resultSet.getString("cf");
                    String nome = resultSet.getString("nome");
                    String cognome = resultSet.getString("cognome");
                    String sesso = resultSet.getString("sesso");
                    String statoNascita = resultSet.getString("stato_nascita");
                    String provinciaNascita = resultSet.getString("provincia_nascita");
                    String comuneNascita = resultSet.getString("comune_nascita");
                    LocalDate dataNascita = resultSet.getDate("data_nascita").toLocalDate();
                    String email = resultSet.getString("email");
                    int abilitato = resultSet.getInt("abilitato");

                    persona = new Persona(cf, nome, cognome, sesso, statoNascita,
                            provinciaNascita, comuneNascita, dataNascita, email);
                    persona.setEntita(entita);
                    persona.setAbilitato(abilitato);


                }
            } catch (SQLException e) {
                System.out.println("Problema di lettura dal db: " + e);
            }

            if (resultSet != null) {
                System.out.println(nomeTabella + " trovato: " + persona.toString());

            } else {
                System.out.println("Non ci sono persone con cf inserito");
            }


        } else {
            System.out.println("Tabella di inserimento non definita");
        }


        return persona;
    }

    public Materia cercaMateriaPerCodice() {
        String sqlQuery = null;
        Materia materia = null;
        String codiceMateria = gc.dammiStringa("Inserisci codice della materia ", "Input non valido, riprova", "I dati non sono inseriti",
                "Codice della materia inserito con successo", 3, 1, 10).toUpperCase();
        // Creo SqlQuery
        sqlQuery = "SELECT * FROM materia WHERE codice = '" + codiceMateria + "'";
        ResultSet resultSet = miodb.readInDb(sqlQuery);
        try {
            if (resultSet.next()) {
                String codice = resultSet.getString("codice");
                String nome = resultSet.getString("nome");
                int abilitato = resultSet.getInt("abilitato");
                materia = new Materia(codice, nome);
                materia.setAbilitato(abilitato);
                System.out.println("Trovato: " + materia.toString());
            } else {
                System.out.println("Non ci sono materie con il codice inserito");
            }
        } catch (SQLException e) {
            System.out.println("Problema di lettura dal db: " + e);
        }

        return materia;
    }


    public Classe cercaClassePerLivelloSezione() {
        String sqlQuery = null;

        Classe classe = null;
        String sezione = null;
        Integer livello = null;


        livello = gc.dammiIntero("Inserisci livello da 1 a 4", "Input non valido, riprova", "I dati non sono inseriti",
                "Livello inserito con successo", 3, 1, 4);
        sezione = gc.dammiStringa("Inserisci sezione ", "Input non valido, riprova", "I dati non sono inseriti",
                "Sezione inserita con successo", 3, 1, 2);
        //creo SqlQuery
        sqlQuery = "SELECT * FROM classe WHERE livello = '" + livello + "' AND sezione = '" + sezione + "' AND abilitato=1";

        ResultSet resultSet = miodb.readInDb(sqlQuery);
        try {
            while (resultSet.next()) {
                Integer livelloTab = resultSet.getInt("livello");
                String sezioneTab = resultSet.getString("sezione");
                int abilitato = resultSet.getInt("abilitato");
                classe = new Classe(livelloTab, sezioneTab);
                classe.setAbilitato(abilitato);
            }
        } catch (SQLException e) {
            System.out.println("Problema di lettura dal db: " + e);
        }
        if (resultSet != null) {
            System.out.println("Trovato: " + classe.toString());
        } else {
            System.out.println("Non ci sono classe con livello e sezione inseriti");
        }

        return classe;
    }

    public void aggiungiDocenteInClasse() {
        String nomeTabellaInserimento = "docente_in_classe";
        String cf = null;
        String annoScolastico = null;
        String sqlQueryInserimento = null;
        Classe classe = cercaClassePerLivelloSezione();
        if (classe == null) {
            System.out.println("Classe non trovato");
        } else {
            Persona persona = cercaPersonaPerCf(Entita.DOCENTE);
            if (persona == null) {
                System.out.println("Docente non trovato");
            } else {
                Materia materia = cercaMateriaPerCodice();
                if (materia == null) {
                    System.out.println("Materia non trovata");
                } else {
                    //if classe != null && persona != null && materia != null
                    annoScolastico = gc.dammiRangeAnni("Inserisci anno scolastico in formato: YYYY/YYYY", "Inserimento non valido, riprova", "Iserimento non è andato con successo", "Iserimento è andato con successo", 3);

                    sqlQueryInserimento = "INSERT INTO docente_classe (anno_scolastico, cf_docente, livello_classe, sezione_classe, nome_materia) \n" +
                            "VALUES \n" +
                            "('" + annoScolastico + "', '" + persona.getCf() + "', " + classe.getLivello() + ", '" + classe.getSezione() + "', '" + materia.getNomeMateria() + "')";
                    miodb.writeInDb(sqlQueryInserimento);
                }
            }
        }
    }

    public void cambiaStatoClasse() {
        Classe classe = cercaClassePerLivelloSezione();
        if (classe != null) {
            int nuovoStato = (classe.getAbilitato() == 0) ? 1 : 0;
            String sqlQuery = "UPDATE classe SET abilitato = " + nuovoStato + " WHERE livello = '" + classe.getLivello() + "' AND sezione = '" + classe.getSezione() + "'";

            miodb.writeInDb(sqlQuery);
            System.out.println("Stato di classeè cambiato per: " + nuovoStato);
        } else {
            System.out.println("Stato di classe non è cambiato");
        }
    }

    public void cambiaStatoMateria() {
        Materia materia = cercaMateriaPerCodice();
        if (materia != null) {
            int nuovoStato = (materia.getAbilitato() == 0) ? 1 : 0;
            String sqlQuery = "UPDATE materia SET abilitato = " + nuovoStato + " WHERE codice = '" + materia.getCodice() + "'";
            miodb.writeInDb(sqlQuery);
            System.out.println("Stato di materia è cambiato per: " + nuovoStato);
        } else {
            System.out.println("Stato non è cambiato");
        }
    }

    //per allievo, docente, amministrativo
    private String getNomeTabella(Entita entita) {
        switch (entita) {
            case ALLIEVO:
                return "allievo";
            case DOCENTE:
                return "docente";
            case AMMINISTRATIVO:
                return "amministrativo";
            // case MATERIA: return "materia";
            // case PROVA:return "prova";
            default:
                return null;
        }
    }

    public void cambiaStatoPersona(Entita entita) {
        Persona persona = cercaPersonaPerCf(entita);
        if (persona != null) {
            int nuovoStato = (persona.getAbilitato() == 0) ? 1 : 0;
            String nomeTabella = getNomeTabella(entita);
            String sqlQuery = "UPDATE " + nomeTabella + " SET abilitato = " + nuovoStato + " WHERE cf = '" + persona.getCf() + "'";
            miodb.writeInDb(sqlQuery);
            System.out.println("Stato di " + persona.getEntita() + " è cambiato per: " + nuovoStato);
        } else {
            System.out.println("Stato di persona non è cambiato");
        }
    }


    public Prova cercaProvaPerDataOra() {
        String sqlQuery = null;
        LocalDateTime dataOraProva = null;
        LocalDate dataProva = null;
        LocalTime oraProva = null;
        LocalDateTime dataOraTab = null;
        Prova prova = null;

        dataProva = gc.dammiData("Inserisci data di prova in formato dd-mm-yyyy  ", "Input non valido, riprova", "I dati non sono inseriti", "La data inseriti con successo", 3, 1920, 2018);
        oraProva = gc.dammiOra("Inserisci ora di prova in formato: HH:mm:ss", "Input non valido, riprova", "I dati non sono inseriti", "La data inseriti con successo", 3);
        dataOraProva = dataProva.atTime(oraProva);
        sqlQuery = "SELECT * FROM prova WHERE data_ora = '" + dataOraProva + "'";

        ResultSet resultSet = miodb.readInDb(sqlQuery);
        try {
            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("data_ora");
                if (timestamp != null) {
                    dataOraTab = timestamp.toLocalDateTime();
                }
                String cfAllievo = resultSet.getString("cf_allievo");
                String cfDocente = resultSet.getString("cf_docente");
                String nomeMateria = resultSet.getString("nome_materia");
                Integer voto = resultSet.getInt("voto");
                int abilitato = resultSet.getInt("abilitato");
                prova = new Prova(dataOraTab, cfAllievo, cfDocente, nomeMateria, voto);
                prova.setAbilitato(abilitato);
            }
        } catch (SQLException e) {
            System.out.println("Problema di lettura dal db: " + e);
        }
        if (resultSet != null) {
            System.out.println("Trovato: " + prova.toString());
        } else {
            System.out.println("Non ci sono prove con data e ora inserite");
        }

        return prova;

    }

    public void cambiaStatoProva() {
        Prova prova = cercaProvaPerDataOra();
        if (prova != null) {
            int nuovoStato = (prova.getAbilitato() == 0) ? 1 : 0;
            String sqlQuery = "UPDATE prova SET abilitato = " + nuovoStato + " WHERE data_ora = '" + prova.getDataOra() + "'";
            miodb.writeInDb(sqlQuery);
            System.out.println("Stato di prova è cambiato per: " + nuovoStato);
        } else {
            System.out.println("Stato di persona non è cambiato");
        }
    }

    public void stampaListaPersone(Entita entita) {
        String nomeTabella = null;
        String sqlQuery = null;
        int index = 0;
        ResultSet resultSet = null;
        //definisco tabella da inserire query. Inserimento ruolo errato è gestito nel gestisciSceltaMenu
        if (entita.equals(Entita.ALLIEVO)
                || entita.equals(Entita.AMMINISTRATIVO)
                || entita.equals(Entita.DOCENTE)) {
            //definisco tabella da inserire query
            nomeTabella = getNomeTabella(entita);

            //creo SqlQuery per prendere tutti abilitati
            sqlQuery = "SELECT * FROM " + nomeTabella + " WHERE abilitato = 1";

            try {
                resultSet = miodb.readInDb(sqlQuery);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        String cf = resultSet.getString("cf");
                        String nome = resultSet.getString("nome");
                        String cognome = resultSet.getString("cognome");
                        String sesso = resultSet.getString("sesso");
                        String statoNascita = resultSet.getString("stato_nascita");
                        String provinciaNascita = resultSet.getString("provincia_nascita");
                        String comuneNascita = resultSet.getString("comune_nascita");
                        LocalDate dataNascita = resultSet.getDate("data_nascita").toLocalDate();
                        String email = resultSet.getString("email");
                        int abilitato = resultSet.getInt("abilitato");

                        Persona persona = new Persona(cf, nome, cognome, sesso, statoNascita,
                                provinciaNascita, comuneNascita, dataNascita, email);
                        persona.setEntita(entita);
                        persona.setAbilitato(abilitato);
                        persone.add(persona);
                    }

                    //stampo persone dall arraylist
                    System.out.println("Lista delle persone definite come " + nomeTabella);
                    for (Persona persona : persone) {
                        System.out.println(++index + " -> " + persona);
                    }
                    //if resultset==null
                } else {
                    System.out.println("Non ci sono persone definite come " + nomeTabella);
                }
            } catch (SQLException e) {
                System.out.println("Problema di lettura dal db: " + e);
            }
            //se entita non e una dei persona
        } else {
            System.out.println("Tabella di inserimento non defifnita");
        }

    }

    public boolean controlloAllievoDocenteClasseMateria(Persona allievo, Persona docente, Materia materia) {
        //controlli
        ResultSet resSetAllievoClasse = null;
        ResultSet resSetDocenteClasse = null;
        String sqlDocenteClasse = null;
        String sqlAllievoClasse = null;
        String annoAllievo = null;
        Integer livelloAllievo = null;
        String sezioneAllievo = null;
        String annoDocente = null;
        Integer livelloDocente = null;
        String sezioneDocente = null;
        String materiaDocente = null;
        int count = 0;
        //controllo per allievo, docente materia sono in stesso classe
        //controllo allievo_classe
        sqlAllievoClasse = "SELECT anno_scolastico,livello_classe, sezione_classe from allievo_in_classe where cf_allievo='" + allievo.getCf() + "'";
        try {
            resSetAllievoClasse = miodb.readInDb(sqlAllievoClasse);
            while (resSetAllievoClasse.next()) {
                annoAllievo = resSetAllievoClasse.getString("anno_scolastico");
                livelloAllievo = resSetAllievoClasse.getInt("livello_classe");
                sezioneAllievo = resSetAllievoClasse.getString("sezione_classe");
            }
        } catch (SQLException e) {
            System.out.println("Problema di lettura dati allievo dal db: " + e);
        }
        //controllo docente_in_classe
        sqlDocenteClasse = "Select anno_scolastico, livello_classe, sezione_classe, nome_materia from docente_classe where cf_docente='" + docente.getCf() + "'";
        try {
            resSetDocenteClasse = miodb.readInDb(sqlDocenteClasse);
            while (resSetDocenteClasse.next()) {
                annoDocente = resSetDocenteClasse.getString("anno_scolastico");
                livelloDocente = resSetDocenteClasse.getInt("livello_classe");
                sezioneDocente = resSetDocenteClasse.getString("sezione_classe");
                materiaDocente = resSetDocenteClasse.getString("nome_materia");
            }
        } catch (SQLException e) {
            System.out.println("Problema di lettura dati docente dal db: " + e);
        }
        //paragono i dati
        if (annoAllievo.equalsIgnoreCase(annoDocente)) {
            count++;

        } else {
            System.out.println("Non c'e corrispondenza dell'anno scolastico allievo e docente");
        }
        if (livelloDocente.equals(livelloAllievo)) {
            count++;
        } else {
            System.out.println("Non c'e corrispondenza dell livello classe allievo e docente");
        }
        if (sezioneDocente.equalsIgnoreCase(sezioneAllievo)) {
            count++;
        } else {
            System.out.println("Non c'e corrispondenza sezione classe allievo e docente");
        }
        if ((materia.getNomeMateria().equalsIgnoreCase(materiaDocente))&&materia.getAbilitato()==1) {
            count++;
        } else {
            System.out.println("Non c'e corrispondenza della materia allievo e docente oppure materia è disabilitata");
        }
        if (count == 4) return true;

        return false;
    }

    public void aggiungiProva() {
        boolean isStessoClasseMateria = false;
        Persona allievo = cercaPersonaPerCf(Entita.ALLIEVO);
        if (allievo != null) {
            Persona docente = cercaPersonaPerCf(Entita.DOCENTE);
            if (docente != null) {
                Materia materia = cercaMateriaPerCodice();
                if (materia != null) {
                    // isStessoClasseMateria = controlloAllievoDocenteClasseMateria(allievo, docente, materia);
                    // if (isStessoClasseMateria == true) {
                    Integer voto = gc.dammiIntero("Inserisci voto da 1 a 10 ", "Input non valido, riprova", "I dati non sono inseriti",
                            "Voto inserito con successo", 3, 1, 10);
                    //Per evitare duplicate entry inserisco LocalDate time now
                    LocalDateTime dataOraProva = LocalDateTime.now();

                    //creo oggetto
                    Prova prova = new Prova(dataOraProva, allievo.getCf(), docente.getCf(), materia.getNomeMateria(), voto);

                    //creo querySql da passare in writeInDb
                    String sqlQuery = "INSERT INTO prova (data_ora, cf_allievo, cf_docente, nome_materia, voto) " +
                            "VALUES ('" + prova.getDataOra() + "', '" + prova.getCfAllievo() + "', '" + prova.getCfDocente() + "', '" + prova.getNomeMateria() + "', '" + voto + "')";

                    //scrivo in db
                    miodb.writeInDb(sqlQuery);
                    // }else{
                    //      System.out.println("Docente non insegna materia per questo allievo");
                    //  }
                } else {
                    System.out.println("Materia non trovata");
                }
            } else {
                System.out.println("Docente non trovato");
            }
        } else {
            System.out.println("Allievo non trovato");
        }


    }


}//
