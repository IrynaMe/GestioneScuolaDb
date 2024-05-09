package gestioneScuola;

import librerie.gestioneDb.ManageDb;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String []args) {
        Scuola scuola=new Scuola("Leonardo Da Vinci");
        scuola.gestisciSceltaMenu();

    }
}
