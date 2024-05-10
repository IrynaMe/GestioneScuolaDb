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


    public void gestisciSceltaMenu() {
        // connessione con db
        miodb.conn();
        // stampa menu
        while (true) {
            //VotoMenu votoMenu = gc.stampaMenu();
            Entita entita = gc.scegliEntita();
            if (entita != null) {
                switch (entita) {
                    case ALLIEVO:
                        MenuInterfaccia input = gc.stampaMenu(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.CERCA_ALLIEVO)) cercaPersonaPerCf(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.AGGIUNGI_ALLIEVO)) aggiungiPersona(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.STAMPA_LISTA_ALLIEVI)) stampaListaPersone(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.CAMBIA_STATO_ALLIEVO)) cambiaStatoPersona(Entita.ALLIEVO);
                        if (input.equals(MenuAllievo.ESCI)) {
                            System.out.println("Stai per uscire dal menu Allievo");
                            break;
                        }
                        break;
                    case DOCENTE:
                        MenuInterfaccia input1 = gc.stampaMenu(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.CERCA_DOCENTE)) cercaPersonaPerCf(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.AGGIUNGI_DOCENTE)) aggiungiPersona(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.STAMPA_LISTA_DOCENTI)) stampaListaPersone(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.CAMBIA_STATO_DOCENTE)) cambiaStatoPersona(Entita.DOCENTE);
                        if (input1.equals(MenuDocente.ESCI)) {
                            System.out.println("Stai per uscire dal menu Docente");
                            break;
                        }
                        break;
                    case AMMINISTRATIVO:
                        MenuInterfaccia input2 = gc.stampaMenu(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.CERCA_AMMINISTRATIVO)) cercaPersonaPerCf(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.AGGIUNGI_AMMINISTRATIVO))
                            aggiungiPersona(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.STAMPA_LISTA_AMMINISTRATIVO))
                            stampaListaPersone(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.CAMBIA_STATO_AMMINISTRATIVO))
                            cambiaStatoPersona(Entita.AMMINISTRATIVO);
                        if (input2.equals(MenuAmministrativo.ESCI)) {
                            System.out.println("Stai per uscire dal menu Docente");
                            break;
                        }
                        break;
                    case PROVA:
                        MenuInterfaccia input3 = gc.stampaMenu(Entita.PROVA);
                        if (input3.equals(MenuProva.CERCA_PROVA)) cercaProvaPerDataOra(Entita.PROVA);
                        if (input3.equals(MenuProva.AGGIUNGI_PROVA_ALLIEVO)) aggiungiProva(Entita.PROVA);
                        if (input3.equals(MenuProva.CAMBIA_STATO_PROVA)) cambiaStatoProva(Entita.PROVA);
                        if (input3.equals(MenuProva.ESCI)) {
                            System.out.println("Stai per uscire dal menu Prova");
                            break;
                        }
                        break;
                    case MATERIA:

                        MenuInterfaccia input4 = gc.stampaMenu(Entita.MATERIA);
                        if (input4.equals(MenuMateria.CERCA_MATERIA)) cercaMateriaPerCodice(Entita.MATERIA);
                        if (input4.equals(MenuMateria.CAMBIA_STATO_MATERIA)) cambiaStatoMateria(Entita.MATERIA);
                        if (input4.equals(MenuMateria.ESCI)) {
                            System.out.println("Stai per uscire dal menu Materia");
                            break;
                        }
                        break;
                    case CLASSE:
                        MenuInterfaccia input5= gc.stampaMenu(Entita.CLASSE);
                        if (input5.equals(MenuClasse.CERCA_CLASSE)) cercaClassePerLivelloSezione(Entita.CLASSE);
                        if (input5.equals(MenuClasse.CAMBIA_STATO_CLASSE)) cambiaStatoClasse(Entita.CLASSE);
                        if (input5.equals(MenuClasse.ESCI)) {
                            System.out.println("Stai per uscire dal menu Classe");
                            break;
                        }
                        break;
                    case NON_DEFINITO:
                        System.out.println("Arrivederci!");
                        miodb.disconnect();
                        return;
                    default:
                        System.out.println("Scelta non valida, riprova");
                }
            } else {
                System.out.println("Arrivederci!");
                miodb.disconnect();
            }
        }
    }


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

            sqlQuery = "SELECT * FROM " + nomeTabella + " WHERE cf = '" + cf + "'";

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

    public Materia cercaMateriaPerCodice(Entita entita){

        String sqlQuery = null;
        Materia materia = null;
        String codiceMateria;
        if (entita.equals(Entita.MATERIA)) {

            codiceMateria = gc.dammiStringa("Inserisci codice della materia ", "Input non valido, riprova", "I dati non sono inseriti",
                    "Stato di nascita inserita con successo", 3, 1, 10);
            //creo SqlQuery
            sqlQuery = "SELECT * FROM materia WHERE codice = '" + codiceMateria + "'";
            ResultSet resultSet = miodb.readInDb(sqlQuery);
            try {
                while (resultSet.next()) {
                    String codice = resultSet.getString("codice");
                    String nome = resultSet.getString("nome");
                    int abilitato = resultSet.getInt("abilitato");
                 materia=new Materia(codice,nome);
                 materia.setAbilitato(abilitato);
                }
            } catch (SQLException e) {
                System.out.println("Problema di lettura dal db: " + e);
            }
            if (resultSet != null) {
                System.out.println("Trovato: " + materia.toString());
            } else {
                System.out.println("Non ci sono materie con  il codice inserito");
            }
        }else{
            System.out.println("Tabella non definita");
        }
        return materia;
    }

    public Classe cercaClassePerLivelloSezione(Entita entita){
        String sqlQuery = null;

        Classe classe = null;
        String sezione=null;
        Integer livello=null;
        if (entita.equals(Entita.MATERIA)) {

            livello = gc.dammiIntero("Inserisci livello da 1 a 4", "Input non valido, riprova", "I dati non sono inseriti",
                    "Stato di nascita inserita con successo", 3, 1, 4);
            sezione = gc.dammiStringa("Inserisci sezione ", "Input non valido, riprova", "I dati non sono inseriti",
                    "Stato di nascita inserita con successo", 3, 1, 2);
            //creo SqlQuery
            sqlQuery = "SELECT * FROM classe WHERE livello = '" + livello + "' AND sezione = '" + sezione + "'";

            ResultSet resultSet = miodb.readInDb(sqlQuery);
            try {
                while (resultSet.next()) {
                    Integer livelloTab = resultSet.getInt("livello");
                    String sezioneTab = resultSet.getString("sezione");
                    int abilitato = resultSet.getInt("abilitato");
                    classe=new Classe(livelloTab,sezioneTab);
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
        }else{
            System.out.println("Tabella non definita");
        }
        return classe;
    }
    public void cambiaStatoClasse(Entita entita) {
        Classe classe=cercaClassePerLivelloSezione(entita);
        if (classe != null) {
            int nuovoStato = (classe.getAbilitato() == 0) ? 1 : 0;
            String nomeTabella = getNomeTabella(entita);
            String sqlQuery = "UPDATE classe SET abilitato = " + nuovoStato + " WHERE livello = '" + classe.getLivello() + "' AND sezione = '" + classe.getSezione() +"'";

            miodb.writeInDb(sqlQuery);
            System.out.println("Stato di classeè cambiato per: "+nuovoStato);
        } else {
            System.out.println("Stato di classe non è cambiato");
        }
    }
    public void cambiaStatoMateria(Entita entita) {
        Materia materia = cercaMateriaPerCodice(entita);
        if (materia != null) {
            int nuovoStato = (materia.getAbilitato() == 0) ? 1 : 0;
            String sqlQuery = "UPDATE materia SET abilitato = " + nuovoStato + " WHERE codice = '" + materia.getCodice() + "'";
            miodb.writeInDb(sqlQuery);
            System.out.println("Stato di materia è cambiato per: "+nuovoStato);
        } else {
            System.out.println("Stato non è cambiato");
        }
    }
    private String getNomeTabella(Entita entita) {
        switch (entita) {
            case ALLIEVO:
                return "allievo";
            case DOCENTE:
                return "docente";
            case AMMINISTRATIVO:
                return "amministrativo";
            // case MATERIA: return "materia";
            case PROVA:
                return "prova";
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
            System.out.println("Stato di "+persona.getEntita()+ " è cambiato per: "+nuovoStato);
        } else {
            System.out.println("Stato di persona non è cambiato");
        }
    }


public Prova cercaProvaPerDataOra(Entita entita){
    String sqlQuery = null;
    LocalDateTime dataOraProva = null;
    LocalDate dataProva=null;
    LocalTime oraProva=null;
    LocalDateTime dataOraTab = null;
    Prova prova = null;
    if (entita.equals(Entita.PROVA)) {

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
                Integer voto=resultSet.getInt("voto");
                int abilitato = resultSet.getInt("abilitato");
                prova=new Prova(dataOraTab,cfAllievo,cfDocente,nomeMateria,voto);
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
    }else{
        System.out.println("Tabella non definita");
    }
    return prova;

}

   public void cambiaStatoProva(Entita entita) {
       Prova prova = cercaProvaPerDataOra(entita);
       if (prova != null) {
           int nuovoStato = (prova.getAbilitato() == 0) ? 1 : 0;
           String nomeTabella = getNomeTabella(entita);
           String sqlQuery = "UPDATE prova SET abilitato = " + nuovoStato + " WHERE data_ora = '" + prova.getDataOra() + "'";
           miodb.writeInDb(sqlQuery);
           System.out.println("Stato di prova è cambiato per: "+nuovoStato);
       } else {
           System.out.println("Stato di persona non è cambiato");
       }
    }




    public void stampaListaPersone(Entita entita) {
        String nomeTabella = null;
        String sqlQuery = null;
        int index = 0;

        //definisco tabella da inserire query. Inserimento ruolo errato è gestito nel gestisciSceltaMenu
        if (entita.equals(Entita.ALLIEVO) || entita.equals(Entita.AMMINISTRATIVO) || entita.equals(Entita.DOCENTE)) {
            //definisco tabella da inserire query.
            nomeTabella = getNomeTabella(entita);

            //creo SqlQuery per prendere tutti abilitati
            sqlQuery = "SELECT * FROM " + nomeTabella + " WHERE abilitato = 1";
            ResultSet resultSet = miodb.readInDb(sqlQuery);
            try {
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
                    persone.add(persona);
                }
            } catch (SQLException e) {
                System.out.println("Problema di lettura dal db: " + e);
            }
            if (resultSet != null) {
                //stampo persone dall arraylist
                System.out.println("Lista delle persone definite come " + nomeTabella);
                for (Persona persona : persone) {
                    System.out.println(++index + " -> " + persona);
                }
            } else {
                System.out.println("Non ci sono persone definite come " + nomeTabella);
            }


        } else {
            System.out.println("Tabella di inserimento non defifnita");
        }

    }


    public void aggiungiProva(Entita entita) {

        String querySql = null;

        //definisco tabella da inserire query. Inserimento ruolo errato è gestito nel gestisciSceltaMenu
        if (entita.equals(Entita.PROVA)) {

            //prendo i dati con gestione console
            String cfAllievo = gc.dammiCodiceFiscale("Inserisci codice fiscale dell'allievo ", "Input non valido, riprova", "I dati non sono inseriti",
                    "CF inserito con successo", 3);
            String cfDocente = gc.dammiCodiceFiscale("Inserisci codice fiscale del docente", "Input non valido, riprova", "I dati non sono inseriti",
                    "CF inserito con successo", 3);

            String nomeMateria = gc.dammiStringa("Inserisci nome di materia ", "Input non valido, riprova", "I dati non sono inseriti",
                    "Nome inserito con successo", 3, 2, 20);

            Integer voto=gc.dammiIntero("Inserisci voto da 1 a 10 ", "Input non valido, riprova", "I dati non sono inseriti",
                    "Nome inserito con successo", 3,1,10);
            // LocalDate dataProva = gc.dammiData("Inserisci data di prova in formato dd-mm-yyyy  ", "Input non valido, riprova", "I dati non sono inseriti","La data inseriti con successo", 3, 1920, 2018);
            // LocalTime oraProva=gc.dammiOra("Inserisci ora di prova in formato: HH:mm", "Input non valido, riprova", "I dati non sono inseriti","La data inseriti con successo", 3,"08","23");
            //LocalDateTime dataOraProva = dataProva.atTime(oraProva);

            //Per evitare duplicate entry inserisco LocalDate time now
            LocalDateTime dataOraProva = LocalDateTime.now();


            //creo oggetto
            Prova prova = new Prova(dataOraProva, cfAllievo, cfDocente, nomeMateria,voto);

            //creo querySql da passare in writeInDb
            String sqlQuery = "INSERT INTO prova (data_ora, cf_allievo, cf_docente, nome_materia) " +
                    "VALUES ('" + prova.getDataOra() + "', '" + prova.getCfAllievo() + "', '" + prova.getCfDocente() + "', '" + prova.getNomeMateria() + "')";

            //scrivo in db
            miodb.writeInDb(querySql);

        } else {
            System.out.println("Entità errata, tabella di inserimento non definita");
        }
    }


}//
