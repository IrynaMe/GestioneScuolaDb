package gestioneScuola;


import gestioneScuola.menu.MenuAllievo;
import gestioneScuola.menu.MenuInterfaccia;
import librerie.gestioneConsole.GestioneConsole;
import librerie.gestioneDb.ManageDb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
            if(entita!=null){
                switch (entita) {
                    case ALLIEVO:
                        MenuInterfaccia input=gc.stampaMenu(Entita.ALLIEVO);
                        if(input.equals(MenuAllievo.AGGIUNGI_ALLIEVO)) aggiungiPersona(Entita.ALLIEVO);
                        if(input.equals(MenuAllievo.STAMPA_LISTA_ALLIEVI)) stampaListaPersone(Entita.ALLIEVO);
                        if(input.equals(MenuAllievo.ESCI)) {
                            System.out.println("Stai per uscire dal menu Allievo");
                            break;
                        }
                        break;
                    case DOCENTE:
                        gc.stampaMenu(Entita.DOCENTE);
                        break;
                    case AMMINISTRATIVO:
                        gc.stampaMenu(Entita.AMMINISTRATIVO);
                        break;
                    case PROVA:
                        gc.stampaMenu(Entita.PROVA);
                        break;
                    case MATERIA:
                        gc.stampaMenu(Entita.MATERIA);
                        break;
                    case NON_DEFINITO:
                        System.out.println("Arrivederci!");
                        return;
                    default:
                        System.out.println("Scelta non valida, riprova");
                }

            }else{
                System.out.println("Arrivederci!");
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
        }else {
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

    public void cambiaStatoPersona(Entita entita) {
        String querySql = null;
        String nomeTabella = null;

    }

    public void stampaListaPersone(Entita entita) {
        String nomeTabella = null;
        String sqlQuery = null;
        int index = 0;

        //definisco tabella da inserire query. Inserimento ruolo errato è gestito nel gestisciSceltaMenu
        if (entita.equals(Entita.ALLIEVO)) {
            nomeTabella = "allievo";
        } else if (entita.equals(Entita.DOCENTE)) {
            nomeTabella = "docente";
        } else if (entita.equals(Entita.AMMINISTRATIVO)) {
            nomeTabella = "amministrativo";
        }
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

    }


    public void modifica() {

    }


}
