/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.Date;

/**
 *
 * @author Digno
 */
public class FrmCliente extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
    EvenJtable evejt = new EvenJtable();
    Connection conn = ConnPostgres.getConnPosgres();
    cliente clie = new cliente();
    clienteBO cBO = new clienteBO();
    clienteDAO cdao = new clienteDAO();
    zona_delivery zona=new zona_delivery();
    zona_deliveryDAO zdao = new zona_deliveryDAO();
    

    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("CLIENTE");
        evetbl.centrar_formulario(this);
        reestableser();
        cdao.actualizar_tabla_cliente(conn, tblpro_cliente);
    }

    boolean validar_guardar() {
        txtfecha_inicio.setText(evefec.getString_formato_fecha());
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtruc, "DEBE CARGAR UN RUC")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttelefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtdireccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtzona, "DEBE CARGAR UNA ZONA")) {
            return false;
        }
        if (txtfecha_nacimiento.getText().trim().length() == 0) {
            txtfecha_nacimiento.setText(evefec.getString_formato_fecha());
            clie.setC7fecha_cumple(evefec.getString_formato_fecha());
            return false;
        }
        return true;
    }

    String tipo_cliente() {
        String tipo = "cliente";
        if (jRtipo_cliente.isSelected()) {
            tipo = "cliente";
        }
        if (jRtipo_funcionario.isSelected()) {
            tipo = "funcionario";
        }
        return tipo;
    }

    void boton_guardar() {
        if (validar_guardar()) {
            clie.setC2fecha_inicio("now");
            clie.setC3nombre(txtnombre.getText());
            clie.setC4direccion(txtdireccion.getText());
            clie.setC5telefono(txttelefono.getText());
            clie.setC6ruc(txtruc.getText());
            clie.setC7fecha_cumple(txtfecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
//            clie.setC9fk_idzona_delivery(1);
            cBO.insertar_cliente(clie, tblpro_cliente);
            reestableser();
        }
    }

    void boton_editar() {
        if (validar_guardar()) {
            clie.setC1idcliente(Integer.parseInt(txtidcliente.getText()));
            clie.setC2fecha_inicio(txtfecha_inicio.getText());
            clie.setC3nombre(txtnombre.getText());
            clie.setC4direccion(txtdireccion.getText());
            clie.setC5telefono(txttelefono.getText());
            clie.setC6ruc(txtruc.getText());
            clie.setC7fecha_cumple(txtfecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
//            clie.setC9fk_idzona_delivery(1);
            cBO.update_cliente(clie, tblpro_cliente);
        }
    }

    void seleccionar_tabla() {
        int id = evejt.getInt_select_id(tblpro_cliente);
        cdao.cargar_cliente(clie, id);
        txtidcliente.setText(String.valueOf(clie.getC1idcliente()));
        txtfecha_inicio.setText(clie.getC2fecha_inicio());
        txtnombre.setText(clie.getC3nombre());
        txtdireccion.setText(clie.getC4direccion());
        txttelefono.setText(clie.getC5telefono());
        txtruc.setText(clie.getC6ruc());
        txtfecha_nacimiento.setText(clie.getC7fecha_cumple());
        if (clie.getC8tipo().equals("cliente")) {
            jRtipo_cliente.setSelected(true);
        }
        if (clie.getC8tipo().equals("funcionario")) {
            jRtipo_funcionario.setSelected(true);
        }
        txtzona.setText(clie.getC10zona());
        txtdelivery.setText(clie.getC11delivery());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    void reestableser() {
        jLzona.setVisible(false);
        jRtipo_cliente.setSelected(true);
        txtidcliente.setText(null);
        txtnombre.setText(null);
        txtfecha_inicio.setText(null);
        txtdireccion.setText(null);
        txttelefono.setText(null);
        txtruc.setText(null);
        txtfecha_nacimiento.setText(null);
        txtzona.setText(null);
        txtdelivery.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtnombre.grabFocus();
    }
    void cargar_zona(){
        int idzona = eveconn.getInt_seleccionar_JLista(conn, txtzona, jLzona, zona.getTabla(),zona.getNombretabla(),zona.getIdtabla());
            clie.setC9fk_idzona_delivery(idzona);
            zdao.cargar_zona_delivery(zona,idzona);
            txtdelivery.setText(String.valueOf(zona.getDelivery()));
    }
    void boton_nuevo() {
        reestableser();
    }

    public FrmCliente() {
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gru_tipo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jRtipo_cliente = new javax.swing.JRadioButton();
        jRtipo_funcionario = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdireccion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtruc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttelefono = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtidcliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtfecha_inicio = new javax.swing.JTextField();
        lblfecnac = new javax.swing.JLabel();
        txtfecha_nacimiento = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLzona = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        txtzona = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtdelivery = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpro_cliente = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        txtbuscar_telefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtbuscar_ruc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Tipo:");

        gru_tipo.add(jRtipo_cliente);
        jRtipo_cliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRtipo_cliente.setSelected(true);
        jRtipo_cliente.setText("CLIENTE");
        jRtipo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_clienteActionPerformed(evt);
            }
        });

        gru_tipo.add(jRtipo_funcionario);
        jRtipo_funcionario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRtipo_funcionario.setText("FUNCIONARIO");
        jRtipo_funcionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_funcionarioActionPerformed(evt);
            }
        });

        txtdireccion.setColumns(20);
        txtdireccion.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtdireccion.setRows(5);
        txtdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdireccionKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtdireccion);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Direccion:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Ruc:");

        txtruc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrucKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Telefono:");

        txttelefono.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txttelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoKeyPressed(evt);
            }
        });

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID:");

        txtidcliente.setEditable(false);
        txtidcliente.setBackground(new java.awt.Color(204, 204, 204));
        txtidcliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Fecha Inicio:");

        txtfecha_inicio.setEditable(false);
        txtfecha_inicio.setBackground(new java.awt.Color(204, 204, 204));
        txtfecha_inicio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lblfecnac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblfecnac.setText("Fec. Nac.:");

        txtfecha_nacimiento.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtfecha_nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("año-mes-dia");

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLzona.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jLzona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLzonaMouseReleased(evt);
            }
        });
        jLzona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLzonaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(jLzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 40, 330, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("ZONA:");
        jLayeredPane1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 14, -1, -1));

        txtzona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtzona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtzonaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtzonaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 13, 327, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("DELIVERY:");
        jLayeredPane1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 14, -1, -1));

        txtdelivery.setEditable(false);
        txtdelivery.setBackground(new java.awt.Color(204, 204, 204));
        txtdelivery.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLayeredPane1.add(txtdelivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 11, 147, -1));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, -1, -1));

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane1.add(btndeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnombre)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 33, Short.MAX_VALUE))))
                    .addComponent(jLayeredPane1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRtipo_cliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRtipo_funcionario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblfecnac)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jRtipo_cliente)
                    .addComponent(jRtipo_funcionario)
                    .addComponent(lblfecnac)
                    .addComponent(txtfecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblpro_cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblpro_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpro_clienteMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblpro_cliente);

        jLabel6.setText("NOMBRE:");

        txtbuscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyReleased(evt);
            }
        });

        txtbuscar_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_telefonoKeyReleased(evt);
            }
        });

        jLabel9.setText("TELEFONO:");

        txtbuscar_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_rucKeyReleased(evt);
            }
        });

        jLabel10.setText("RUC:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        cdao.ancho_tabla_cliente(tblpro_cliente);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblpro_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpro_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblpro_clienteMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void jRtipo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_clienteActionPerformed
        // TODO add your handling code here:
//        jCdelivery.setSelected(true);
    }//GEN-LAST:event_jRtipo_clienteActionPerformed

    private void jRtipo_funcionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_funcionarioActionPerformed
        // TODO add your handling code here:
//        jCdelivery.setSelected(false);
    }//GEN-LAST:event_jRtipo_funcionarioActionPerformed

    private void txtrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrucKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtruc, txttelefono);
        evejtf.saltar_campo_enter(evt, txtruc, txttelefono);
    }//GEN-LAST:event_txtrucKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter2(evt, txttelefono, txtdireccion);
        evejtf.saltar_campo_enter(evt, txttelefono, txtfecha_nacimiento);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtnombre, txtruc);
        evejtf.saltar_campo_enter(evt, txtnombre, txtruc);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtfecha_nacimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyReleased
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtfecha_nacimiento);
    }//GEN-LAST:event_txtfecha_nacimientoKeyReleased

    private void txtfecha_nacimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyTyped
        // TODO add your handling code here:
//        fo.soloFechaText(evt);
    }//GEN-LAST:event_txtfecha_nacimientoKeyTyped

    private void txtzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzonaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtzona, jLzona,zona.getTabla(),zona.getNombretabla(),zona.getNombretabla());
    }//GEN-LAST:event_txtzonaKeyReleased

    private void jLzonaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLzonaMouseReleased
        // TODO add your handling code here:
        cargar_zona();
    }//GEN-LAST:event_jLzonaMouseReleased

    private void txtzonaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzonaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jLzona);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtzona.setBackground(Color.WHITE);
            txtdireccion.setBackground(Color.YELLOW);
            txtdireccion.grabFocus();
        }
    }//GEN-LAST:event_txtzonaKeyPressed

    private void jLzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLzonaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_zona();
        }
    }//GEN-LAST:event_jLzonaKeyReleased

    private void txtfecha_nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtfecha_nacimiento, txtzona);
    }//GEN-LAST:event_txtfecha_nacimientoKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_nombre, 1);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtbuscar_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_telefonoKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_telefono, 2);
    }//GEN-LAST:event_txtbuscar_telefonoKeyReleased

    private void txtbuscar_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_rucKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_ruc, 3);
    }//GEN-LAST:event_txtbuscar_rucKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.ButtonGroup gru_tipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jLzona;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRtipo_cliente;
    private javax.swing.JRadioButton jRtipo_funcionario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblfecnac;
    private javax.swing.JTable tblpro_cliente;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_ruc;
    private javax.swing.JTextField txtbuscar_telefono;
    public static javax.swing.JTextField txtdelivery;
    private javax.swing.JTextArea txtdireccion;
    private javax.swing.JTextField txtfecha_inicio;
    private javax.swing.JTextField txtfecha_nacimiento;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txttelefono;
    public static javax.swing.JTextField txtzona;
    // End of variables declaration//GEN-END:variables
}
