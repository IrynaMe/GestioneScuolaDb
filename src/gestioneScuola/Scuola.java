package gestioneScuola;


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

        // stampa menu con inserimento da console. gestito null in gc.stampaMenu
        while (true) {
            VotoMenu votoMenu = gc.stampaMenu();

            switch (votoMenu) {
                case AGGIUNGI_PERSONA:
                    Ruolo ruolo = gc.scegliRuolo();
                    if (ruolo.equals(Ruolo.DOCENTE)) {
                        aggiungiPersona(Ruolo.DOCENTE);
                    } else if (ruolo.equals(Ruolo.ALLIEVO)) {
                        aggiungiPersona(Ruolo.ALLIEVO);
                    } else if (ruolo.equals(Ruolo.AMMINISTRATIVO)) {
                        aggiungiPersona(Ruolo.AMMINISTRATIVO);
                    } else {
                        System.out.println("Tabella di inserimento non definita");
                    }
                    break;
            /*    case CAMBIA_STATO_PERSONA:
                    Ruolo ruolo1 = gc.scegliRuolo();
                    // cambiaStatoPersona(Persona p);
                    break;
                case MODIFICA_PERSONA:
                    Ruolo ruolo2 = gc.scegliRuolo();
                    modifica();
                    break;*/
                case STAMPA_LISTA_PERSONE:
                    Ruolo ruolo3 = gc.scegliRuolo();
                   stampaListaPersone(ruolo3);
                    break;
              /*  case AGGIUNGI_MATERIA:
                    //...
                    break;
                case CAMBIA_STATO_MATERIA:
                    //...
                    break;
                case STAMPA_LISTA_MATERIE:
                    //...
                    break;
                case AGGIUNGI_PROVA_ALLIEVO:
                    //...
                    break;
                case STAMPA_PROVE_ALLIEVO:
                    //...
                    break;
                case AGGIUNGI_CLASSE:
                    //...
                    break;
                case CAMBIA_STATO_CLASSE:
                    //...
                    break;*/
                case ESCI:
                    System.out.println("Arrivederci!");
                    // chiudo connessione db
                    miodb.disconnect();
                    return;
                default:
                    System.out.println("Scelta non valida, riprova");
            }
        }
    }


    //return querySql to
    public void aggiungiPersona(Ruolo ruolo) {
        String querySql = null;
        String nomeTabella = null;
        //definisco tabella da inserire query. Inserimento ruolo errato è gestito nel gestisciSceltaMenu
        if (ruolo.equals(Ruolo.ALLIEVO)) {
            nomeTabella = "allievo";
        } else if (ruolo.equals(Ruolo.DOCENTE)) {
            nomeTabella = "docente";
        } else if (ruolo.equals(Ruolo.AMMINISTRATIVO)) {
            nomeTabella = "amministrativo";
        }


        //prendo i dati con gestione console
        String cf = gc.dammiCodiceFiscale("Inserisci codice fiscale di "+ nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "CF inserito con successo", 3);

        String nome = gc.dammiStringa("Inserisci nome di " +nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Nome inserito con successo", 3, 2, 20);

        String cognome = gc.dammiStringa("Inserisci cognome di "+nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Cognome inserito con successo", 3, 2, 20);
        String sesso = gc.dammiSesso("Inserisci sesso: un carattere: f/m per "+nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Sesso inserito con successo", 3);

        String statoNascita = gc.dammiStringa("Inserisci stato di nascita di "+nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Stato di nascita inserita con successo", 3, 2, 30);

        String provinciaNascita = gc.dammiStringa("Inserisci provincia di nascita di "+nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "Provincia di nascita inserita con successo", 3, 2, 30);

        String comuneNascita = gc.dammiStringa("Inserisci comune di nascita di " +nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",

                "Comune di nascita inserita con successo", 3, 2, 30);
        LocalDate dataNascita = gc.dammiData("Inserisci data di nascita in formato dd-mm-yyyy di "+nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
                "La data inseriti con successo", 3, 1920, 2018);


        String email = gc.dammiMail("Inserisci email di "+nomeTabella, "Input non valido, riprova", "I dati non sono inseriti",
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

    public void cambiaStatoPersona(Ruolo ruolo) {
        String querySql = null;
        String nomeTabella = null;

    }

    public void stampaListaPersone(Ruolo ruolo) {
        String nomeTabella = null;
        String sqlQuery = null;
        int index=0;

        //definisco tabella da inserire query. Inserimento ruolo errato è gestito nel gestisciSceltaMenu
        if (ruolo.equals(Ruolo.ALLIEVO)) {
            nomeTabella = "allievo";
        } else if (ruolo.equals(Ruolo.DOCENTE)) {
            nomeTabella = "docente";
        } else if (ruolo.equals(Ruolo.AMMINISTRATIVO)) {
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
                int abilitato=resultSet.getInt("abilitato");

                Persona persona = new Persona(cf, nome, cognome, sesso, statoNascita,
                        provinciaNascita, comuneNascita, dataNascita, email);
                persona.setRuolo(ruolo);
                persone.add(persona);
            }
        } catch (SQLException e) {
            System.out.println("Problema di lettura dal db: "+e);
        }
        if(resultSet!=null){
            //stampo persone dall arraylist
            System.out.println("Lista delle persone definite come "+nomeTabella);
            for (Persona persona : persone) {
                System.out.println(++index+" -> "+persona);
            }
        }else{
            System.out.println("Non ci sono persone definite come "+nomeTabella);
        }

    }


    public void modifica() {

    }


}
