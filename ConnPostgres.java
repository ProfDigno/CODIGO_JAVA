/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BASEDATO;

//import ClaseUTIL.FunMensaje;
//import ClaseUTIL.SQLejecucion;
//import ClaseUTIL.SQLprepar;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.*;
import javax.swing.JOptionPane;
import org.postgresql.Driver;

/**
 * Esta clase se conecta directamente y exclusivamente con postgres pero depende
 * de los datos proporcionado por la base de dato SQLite por eso es importante
 * que los datos de conexion en el SQLite esteen corectos
 *
 * @author Pc
 */
public class ConnPostgres {

    private static Connection connPostgres = null;
    public static String PsDriver;
    public static String PsConexion;
    public static String PsLocalhost;
    public static String PsPort;
    public static String PsNomBD;
    public static String PsUsuario;
    public static String PsContrasena;
    VariablesBD var = new VariablesBD();
    ConnSQLITEdato csd = new ConnSQLITEdato();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    EvenConexion evconn = new EvenConexion();
    public void cargarVariablesBDsqlite(Connection connConfig) {
//        EvenConexion evconn = new EvenConexion(connConfig);
        String sql = "select * from conexion "
                + "where estado='true' ";
        try {
            ResultSet rs = evconn.getResulsetSQL(connConfig,sql, "cargarVariablesBDsqlite");
            if (rs.next()) {
                var.setPsNombreConexion(rs.getString(2));
                var.setPsLocalhost(rs.getString(3));
                var.setPsPort(rs.getString(4));
                var.setPsUsuario(rs.getString(5));
                var.setPsContrasena(rs.getString(6));
                var.setPsNomBD(rs.getString(7));
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, "cargarVariablesBDsqlite");
        }
    }

    void cargarVariables() {
        PsDriver = "org.postgresql.Driver";
        PsConexion = "jdbc:postgresql";
        PsLocalhost = var.getPsLocalhost();
        PsPort = var.getPsPort();
        PsNomBD = var.getPsNomBD();
        PsUsuario = var.getPsUsuario();
        PsContrasena = var.getPsContrasena();
        System.out.println("++++++++++++++++Carga de variable de sqlite para la conexion postgres=" + "\n" + PsDriver + "\n" + PsUsuario + "\n" + PsContrasena);
    }

    void cargarVariablesDirecto() {
        PsDriver = "org.postgresql.Driver";
        PsConexion = "jdbc:postgresql";
        PsLocalhost = "172.16.249.5";
        PsPort = "5432";
        PsNomBD = "BDviveSER_4temp";
        PsUsuario = "postgres";
        PsContrasena = "c0n3ct4";
        System.out.println("++++++++++++++++Carga de variable de sqlite para la conexion postgres=" + "\n" + PsDriver + "\n" + PsUsuario + "\n" + PsContrasena);
    }

    public void ConnectDBpostgres(Connection connConfig, boolean cargarsqlite, boolean msj) {
        if (cargarsqlite) {
            cargarVariablesBDsqlite(connConfig);
        }
        cargarVariables();
        try {
            String connectString = "" + PsConexion + "://" + PsLocalhost + ":" + PsPort + "/" + PsNomBD + "";
            Class.forName(PsDriver);
            Connection connLocal = DriverManager.getConnection(connectString, PsUsuario, PsContrasena);
            if (connLocal != null) {
                System.out.println("++++++++++++++++Conection a posgrest suceso" + "\n" + PsDriver + "\n" + connectString + "\n" + PsUsuario + "\n" + PsContrasena);
                if (msj) {
                    JOptionPane.showMessageDialog(null, "++Conection a posgrest suceso++" + "\n" + PsDriver + "\n" + connectString + "\n" + PsUsuario);
                }
            }
            setConnPostgres(connLocal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error en la conexion con base de datos"
                    + "\nLocal Host: " + PsLocalhost
                    + "\nPuerto: " + PsPort
                    + "\nUsuario: " + PsUsuario
                    + "\nError: " + e.getMessage(), "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, "EL SISTEMA SE VA CERRAR POR FALLA EN LA CONEXION", "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void cerrar_conexion() {
        try {
            getConnPosgres().close();
            System.out.println("CONEXION CERRADA");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CERRAR\n" + e);
        }
    }

    public void cerrar_todas_conexiones() {
        //SELECT pg_terminate_backend(pid) FROM pg_stat_activity
        String sql = "select count(*) as cantidad from pg_stat_activity;";
//        evconn.SQL_execute_libre(getConnPosgres(), sql);
    }

    /**
     * @return the connPostgres
     */
    public static Connection getConnPosgres() {
        System.out.println("CONECTADO");
        return connPostgres;
        
    }

    /**
     * @param aConnPostgres the connPostgres to set
     */
    public static void setConnPostgres(Connection aConnPostgres) {
        connPostgres = aConnPostgres;
    }

}
