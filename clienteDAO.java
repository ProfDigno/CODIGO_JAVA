/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.*;
import java.sql.*;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class clienteDAO {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "CLIENTE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CLIENTE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.cliente(\n"
            + "            idcliente, fecha_inicio, nombre, direccion, telefono, ruc, fecha_cumple, \n"
            + "            tipo, fk_idzona_delivery)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private String sql_update = "UPDATE public.cliente\n"
            + "   SET  fecha_inicio=?, nombre=?, direccion=?, telefono=?, \n"
            + "       ruc=?, fecha_cumple=?, tipo=?, fk_idzona_delivery=?\n"
            + " WHERE idcliente=?;";
    private String sql_select = "select idcliente,nombre,telefono,ruc from cliente order by 2 asc";
    private String sql_cargar = "select c.idcliente, c.fecha_inicio, c.nombre, c.direccion, "
            + "c.telefono, c.ruc, c.fecha_cumple,c.tipo, c.fk_idzona_delivery,z.nombre as zona,z.delivery "
            + "from cliente c,zona_delivery z "
            + "where c.fk_idzona_delivery=z.idzona_delivery "
            + "and c.idcliente=";

    public void cargar_cliente(cliente clie,int idcliente) {
        String titulo = "cargar_cliente";
//        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcliente, titulo);
            if (rs.next()) {
                clie.setC1idcliente(idcliente);
                clie.setC2fecha_inicio(rs.getString(2));
                clie.setC3nombre(rs.getString(3));
                clie.setC4direccion(rs.getString(4));
                clie.setC5telefono(rs.getString(5));
                clie.setC6ruc(rs.getString(6));
                clie.setC7fecha_cumple(rs.getString(7));
                clie.setC8tipo(rs.getString(8));
                clie.setC9fk_idzona_delivery(rs.getInt(9));
                clie.setC10zona(rs.getString(10));
                clie.setC11delivery(rs.getString(11));
                clie.setC11deliveryDouble(rs.getDouble(11));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + clie.toString(), titulo);
        }
    }

    public void insertar_cliente(Connection conn, cliente clie) {
        clie.setC1idcliente(eveconn.getInt_ultimoID(conn, clie.getTabla(), clie.getIdtabla()));
        String titulo = "insertar_cliente";
        PreparedStatement pst = null;
        java.sql.Date dateInicio = new java.sql.Date(new java.util.Date().getTime());
        java.sql.Date dateCumple = java.sql.Date.valueOf(clie.getC7fecha_cumple());
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, clie.getC1idcliente());
            pst.setDate(2, dateInicio);
            pst.setString(3, clie.getC3nombre());
            pst.setString(4, clie.getC4direccion());
            pst.setString(5, clie.getC5telefono());
            pst.setString(6, clie.getC6ruc());
            pst.setDate(7, dateCumple);
            pst.setString(8, clie.getC8tipo());
            pst.setInt(9, clie.getC9fk_idzona_delivery());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + clie.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + clie.toString(), titulo);
        }
    }
    public void update_cliente(Connection conn, cliente clie) {
        String titulo = "update_cliente";
        PreparedStatement pst = null;
        java.sql.Date dateInicio = java.sql.Date.valueOf(clie.getC2fecha_inicio());
        java.sql.Date dateCumple = java.sql.Date.valueOf(clie.getC7fecha_cumple());
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setDate(1, dateInicio);
            pst.setString(2, clie.getC3nombre());
            pst.setString(3, clie.getC4direccion());
            pst.setString(4, clie.getC5telefono());
            pst.setString(5, clie.getC6ruc());
            pst.setDate(6, dateCumple);
            pst.setString(7, clie.getC8tipo());
            pst.setInt(8, clie.getC9fk_idzona_delivery());
            pst.setInt(9, clie.getC1idcliente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + clie.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + clie.toString(), titulo);
        }
    }
    public void buscar_tabla_cliente(Connection conn, JTable tbltabla,JTextField txtbuscar,int tipo) {
        String filtro="";
        if(txtbuscar.getText().trim().length()>0){
            String buscar=txtbuscar.getText();
            if(tipo==1){
                filtro=" where nombre ilike'%"+buscar+"%' ";
            }
            if(tipo==2){
                filtro=" where telefono ilike'%"+buscar+"%' ";
            }
            if(tipo==3){
                filtro=" where ruc ilike'%"+buscar+"%' ";
            }
        }
        String sql="select idcliente,nombre,telefono,ruc "
                + "from cliente "
                + " "+filtro
                + " order by 2 asc ";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_cliente(tbltabla);
    }
    public void actualizar_tabla_cliente(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_cliente(tbltabla);
    }

    public void ancho_tabla_cliente(JTable tbltabla) {
        int Ancho[] = {10,60,15,15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
