package librerie.gestioneDb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class   ManageDb {
    private final String fileProperties = "src" + File.separator + "resources" + File.separator + "config.properties";
    private Connection myConn;

    Statement statement;
    ResultSet resultSet;


    public Connection conn() {
        Properties properties= null;
        myConn = null;
        try {
            //prendo i dati per String connection da resources/config.properties
            FileReader reader = new FileReader(fileProperties);
            properties = new Properties();
            properties.load(reader);
            String connection = properties.getProperty("url") + "://" +
                    properties.getProperty("host") + ":" +
                    properties.getProperty("port") + "/" +
                    properties.getProperty("database") + "?user=" +
                    properties.getProperty("user") + "&password=" +
                    properties.getProperty("password") + "&useSSL=false&serverTimezone=Europe/Rome";

            myConn = DriverManager.getConnection(connection);
            if (myConn != null) {
                System.out.println("Connesso a db con successo!");
            } else {
                System.out.println("NON connesso a db");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problema in lettura file: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Non posso fare la connessione alla db: " + e.getMessage());
        }
        return myConn;
    }


    public boolean writeInDb(String sSqlQuery) {
        boolean isExecuted = false;
        try {
            statement = myConn.createStatement();
            isExecuted = statement.execute(sSqlQuery);//boolean
            System.out.println("Inserito in database con successo");
        } catch (SQLException e) {
            System.out.println("Inserimento in db non è andato con successo: " + e);
        }
        return isExecuted;
    }


    public ResultSet readInDb(String sSqlQuery) {
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery(sSqlQuery);
            if (resultSet == null) System.out.println("Non ci sono records con i parametri cercati");
        } catch (SQLException e) {
            System.out.println("Tentativo di leggere in db non è andato con successo" + e);
        }
        return resultSet;
    }

    public boolean disconnect() {
        try {
            myConn.close();
            System.out.println("Connessione con db è chiusa");
            return true;
        } catch (SQLException e) {
            System.out.println("Non posso chiudere connessione db" + e);
        }
        return false;
    }

    public Connection getMyConn() {
        return myConn;
    }
}//


    /*   public boolean connect(String sIpServer, int iPort, String sNomeDb, String sNomeUtente, String sPassword) {
          //mySql connection String
           String sConnectString = "jdbc:mysql://" + sIpServer + ":" + iPort + "/" + sNomeDb +
                   "?user=" + sNomeUtente + "&password=" + sPassword;
           try {
               myConn=DriverManager.getConnection(sConnectString);
               System.out.println("Connessione effettuato con successo");
               return true;
           } catch (SQLException e) {
               System.out.println("Connessione non effettuato" +e);
              // throw new RuntimeException(e);
           }
       return false;
       }
   */
