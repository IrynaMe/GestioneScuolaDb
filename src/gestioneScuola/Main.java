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




/*        String ipAddress;
        boolean isConnected=false;
        try {
            // prendo IP address corrente
            InetAddress inetAddress = InetAddress.getLocalHost();
            ipAddress = inetAddress.getHostAddress();
            System.out.println("Local IP Address: " + ipAddress);

            if (ipAddress.equals("192.168.1.224")) {
                isConnected = miodb.connect("localhost", 8889, "gestione_scuola", "server_scuola", "server_scuola123");
            } else if (ipAddress.equals("192.168.100.53")) {
                isConnected = miodb.connect("localhost", 3306, "gestione_scuola", "server_scuola", "server_scuola123");
            } else {
                // Default
                isConnected = miodb.connect("localhost", 3306, "gestione_scuola", "server_scuola", "server_scuola123");
            }
        } catch (
                UnknownHostException e) {
            e.printStackTrace();
        }
        if(isConnected){
            //get allievo dal db
            String getAllievoSql="Select * from allievo";
            ResultSet res=miodb.readInDb(getAllievoSql);
            if(res!=null){
                try {
                while (res.next()){
                    String nomeAllievo=res.getString("nome");
                    String cf=res.getString("cf");
                    LocalDate dataNascita=res.getDate("data_nascita").toLocalDate();
                    //....altre colonne
                    //  Allievo allievo=new Allievo()

                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }*/


           /* String query="INSERT INTO materia (nome_materia) VALUES \n" +
                    "('Geografia'),\n" +
                    "('Italiano'),\n" +
                    "('Matematica'),\n" +
                    "('Scienze');";*/
// miodb.writeInDb(query);
//   }


// Scuola scuola = new Scuola("Leonardo da Vinci");
// GestioneConsole gc = new GestioneConsole();
//  gc.stampaMenuAllievi();
//  }
