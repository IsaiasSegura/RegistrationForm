package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Vista extends JFrame {

    private JPanel cardPanel, panel_bienvenida, panel_datos1, panel_datos2, panel_resumen, panel_registroCompletado;
    private CardLayout cardLayout;

    private JTextArea textAreaBienvenida, textAreaResumen;

    private JLabel lblTituloCabecera, lblDatosPersonales, lblNombre, lblEmail, lblClave, lblEdad, lblArchivo;
    private JLabel lblDatos2, lblPaisLabel, lblProvinciaLabel; // Evitar conflicto con cb
    private JLabel lblRegistroCompletadoLabel;

    private JTextField nombreTxt, emailTxt, txtRutaArchivo;
    private JPasswordField passwordTxt;
    private JSpinner edad;
    private JComboBox<String> cbPaises, cbProvincias;
    private JCheckBox checkBox;
    private JButton btnSiguiente, btnSiguiente1, btnAtras1, btnSiguiente2, btnAtras2, btnSiguiente3, btnAtras3, btnSalir, btnExaminar;

    private JLabel lblBandera;
    private JLabel lblLogo;
    private ImageIcon banderaSpain, banderaUSA;

    public JTextField getNombreTxt() { return nombreTxt; }
    public JTextField getEmailTxt() { return emailTxt; }
    public JPasswordField getPasswordTxt() { return passwordTxt; }
    public JSpinner getEdad() { return edad; }
    public JComboBox<String> getCbPaises() { return cbPaises; }
    public JComboBox<String> getCbProvincias() { return cbProvincias; }
    public JCheckBox getCheckBox() { return checkBox; }
    public JButton getBtnSiguiente() { return btnSiguiente; }
    public JButton getBtnSiguiente1() { return btnSiguiente1; }
    public JButton getBtnSiguiente2() { return btnSiguiente2; }
    public JButton getBtnSiguiente3() { return btnSiguiente3; }
    public JButton getBtnExaminar() { return btnExaminar; }
    public JTextField getTxtRutaArchivo() { return txtRutaArchivo; }

    public Vista() {
        frameConfig();
        crearMenu(); // R9
        iniCardLayout();
        vistaBienvenida();
        vistaDatos1();
        cargarBanderas();
        vistaDatos2();
        vistaPanelResumen();
        VistapanelregistroCompletado();
        addPaneles();
    }

    private void frameConfig() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Formulario de registro");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemManual = new JMenuItem("Manual de usuario");

        itemManual.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://tu-manual-web.com"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo abrir el navegador");
            }
        });

        menuAyuda.add(itemManual);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
    }

    private void iniCardLayout() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
    }

    private void vistaBienvenida() {
        panel_bienvenida = new JPanel(new BorderLayout());
        textAreaBienvenida = new JTextArea();
        textAreaBienvenida.setEditable(false);
        textAreaBienvenida.setLineWrap(true);
        textAreaBienvenida.setWrapStyleWord(true);
        textAreaBienvenida.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(textAreaBienvenida);
        textAreaBienvenida.setText("Bienvenido al formulario de registro...");

        btnSiguiente = new JButton("Siguiente");
        panel_bienvenida.add(scrollPane, BorderLayout.CENTER);
        panel_bienvenida.add(btnSiguiente, BorderLayout.SOUTH);
    }

    private void vistaDatos1() {
        panel_datos1 = new JPanel(new BorderLayout());
        lblDatosPersonales = new JLabel("Datos Personales", SwingConstants.CENTER);
        lblDatosPersonales.setFont(new Font("SansSerif", Font.BOLD, 16));

        JPanel panelFields = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFields.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        nombreTxt = new JTextField();
        emailTxt = new JTextField();
        emailTxt.setToolTipText("Email válido (ejemplo@ej.es)");
        passwordTxt = new JPasswordField();
        passwordTxt.setToolTipText("8-16 caracteres, mayúscula, número y especial");

        edad = new JSpinner(new SpinnerNumberModel(20, 18, 99, 1));
        txtRutaArchivo = new JTextField();
        txtRutaArchivo.setEditable(false);
        btnExaminar = new JButton("Examinar");

        lblNombre = new JLabel("Nombre:");
        lblEmail = new JLabel("Email:");
        lblClave = new JLabel("Clave:");
        lblEdad = new JLabel("Edad:");
        lblArchivo = new JLabel("Archivo:");

        panelFields.add(lblNombre); panelFields.add(nombreTxt);
        panelFields.add(lblEmail); panelFields.add(emailTxt);
        panelFields.add(lblClave); panelFields.add(passwordTxt);
        panelFields.add(lblEdad); panelFields.add(edad);
        panelFields.add(lblArchivo); panelFields.add(txtRutaArchivo);
        panelFields.add(new JLabel("")); panelFields.add(btnExaminar);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnAtras1 = new JButton("Atras");
        btnSiguiente1 = new JButton("Siguiente");
        btnPanel.add(btnAtras1); btnPanel.add(btnSiguiente1);

        panel_datos1.add(lblDatosPersonales, BorderLayout.NORTH);
        panel_datos1.add(panelFields, BorderLayout.CENTER);
        panel_datos1.add(btnPanel, BorderLayout.SOUTH);
    }

    private void vistaDatos2() {
        panel_datos2 = new JPanel(new BorderLayout(10, 10));
        lblDatos2 = new JLabel("País y Provincia", SwingConstants.CENTER);

        JPanel panelCentro = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        cbPaises = new JComboBox<>(new String[]{"Spain", "USA"});
        configurarComboConBanderas();
        cbProvincias = new JComboBox<>();

        lblPaisLabel = new JLabel("País:");
        lblProvinciaLabel = new JLabel("Provincia:");

        lblBandera = new JLabel();
        lblBandera.setHorizontalAlignment(SwingConstants.CENTER);
        lblBandera.setPreferredSize(new Dimension(140, 90));

        cbPaises.addActionListener(e -> {
            actualizarCombosPorPais();
        });

        btnAtras2 = new JButton("Atrás");
        btnSiguiente2 = new JButton("Siguiente");

        panelCentro.add(lblPaisLabel); panelCentro.add(cbPaises);
        panelCentro.add(lblProvinciaLabel); panelCentro.add(cbProvincias);
        panelCentro.add(btnAtras2); panelCentro.add(btnSiguiente2);

        panel_datos2.add(lblDatos2, BorderLayout.NORTH);
        panel_datos2.add(panelCentro, BorderLayout.CENTER);
        panel_datos2.add(lblBandera, BorderLayout.EAST);

        actualizarCombosPorPais();
    }


    private void actualizarCombosPorPais() {
        if (cbPaises.getSelectedItem() != null) {
            String pais = cbPaises.getSelectedItem().toString();
            // Determinamos el nombre del archivo (spain o usa)
            String resPath = pais.equalsIgnoreCase("Spain") ? "spain" : "usa";

            // Cargamos las provincias del archivo correspondiente
            cbProvincias.setModel(new DefaultComboBoxModel<>(cargarProvincias(resPath)));

            // Cambiamos la bandera
            lblBandera.setIcon(cargarImagenEscalada("/img/" + resPath + ".png", 140, 90));
        }
    }

    private void vistaPanelResumen() {
        panel_resumen = new JPanel(new BorderLayout());
        textAreaResumen = new JTextArea();
        checkBox = new JCheckBox("Guardar archivo en un fichero");

        JPanel panelBtn = new JPanel(new FlowLayout());
        btnAtras3 = new JButton("Atras");
        btnSiguiente3 = new JButton("Finalizar");
        panelBtn.add(btnAtras3); panelBtn.add(btnSiguiente3);

        panel_resumen.add(new JScrollPane(textAreaResumen), BorderLayout.CENTER);
        panel_resumen.add(checkBox, BorderLayout.NORTH);
        panel_resumen.add(panelBtn, BorderLayout.SOUTH);
    }

    private void VistapanelregistroCompletado() {
        panel_registroCompletado = new JPanel(new BorderLayout());
        lblRegistroCompletadoLabel = new JLabel("Registro Completado", SwingConstants.CENTER);
        btnSalir = new JButton("Salir");
        panel_registroCompletado.add(lblRegistroCompletadoLabel, BorderLayout.CENTER);
        panel_registroCompletado.add(btnSalir, BorderLayout.SOUTH);
    }

    public void actualizarIdioma(ResourceBundle rb) {
        setTitle(rb.getString("ventana.titulo"));
        lblDatosPersonales.setText(rb.getString("seccion.datos1"));
        lblDatos2.setText(rb.getString("seccion.datos2"));
        lblNombre.setText(rb.getString("label.nombre"));
        lblEmail.setText(rb.getString("label.email"));
        lblClave.setText(rb.getString("label.clave"));
        lblEdad.setText(rb.getString("label.edad"));
        lblArchivo.setText(rb.getString("label.archivo"));
        lblPaisLabel.setText(rb.getString("label.pais"));
        lblProvinciaLabel.setText(rb.getString("label.provincia"));

        btnSiguiente.setText(rb.getString("btn.siguiente"));
        btnSiguiente1.setText(rb.getString("btn.siguiente"));
        btnSiguiente2.setText(rb.getString("btn.siguiente"));
        btnSiguiente3.setText(rb.getString("btn.finalizar"));
        btnAtras1.setText(rb.getString("btn.atras"));
        btnAtras2.setText(rb.getString("btn.atras"));
        btnAtras3.setText(rb.getString("btn.atras"));
        btnExaminar.setText(rb.getString("btn.examinar"));
        btnSalir.setText(rb.getString("btn.salir"));

        checkBox.setText(rb.getString("check.exportar"));
        textAreaBienvenida.setText(rb.getString("bienvenida.info"));
        lblRegistroCompletadoLabel.setText(rb.getString("label.final"));
    }

    private void addPaneles() {
        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        contenedorPrincipal.add(crearCabecera(), BorderLayout.NORTH);
        contenedorPrincipal.add(cardPanel, BorderLayout.CENTER);
        cardPanel.add(panel_bienvenida, "BIENVENIDA");
        cardPanel.add(panel_datos1, "DATOS1");
        cardPanel.add(panel_datos2, "DATOS2");
        cardPanel.add(panel_resumen, "RESUMEN");
        cardPanel.add(panel_registroCompletado, "FINAL");
        add(contenedorPrincipal);
    }

    private JPanel crearCabecera() {
        PanelDegradado cabecera = new PanelDegradado(new Color(30, 136, 229), new Color(144, 202, 249));
        cabecera.setPreferredSize(new Dimension(600, 90));
        lblLogo = new JLabel(cargarLogoEscalado("/img/logodb.jpg", 80, 80));
        lblTituloCabecera = new JLabel("Registration Form");
        lblTituloCabecera.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTituloCabecera.setForeground(Color.WHITE);
        cabecera.add(lblLogo); cabecera.add(lblTituloCabecera);
        return cabecera;
    }

    // Handlers para el controlador
    public void accionBtnSiguiente(ActionListener al) { btnSiguiente.addActionListener(al); }
    public void accionBtnSiguiente1(ActionListener al) { btnSiguiente1.addActionListener(al); }
    public void accionBtnAtras1(ActionListener al) { btnAtras1.addActionListener(al); }
    public void accionBtnSiguiente2(ActionListener al) { btnSiguiente2.addActionListener(al); }
    public void accionBtnAtras2(ActionListener al) { btnAtras2.addActionListener(al); }
    public void accionBtnSiguiente3(ActionListener al) { btnSiguiente3.addActionListener(al); }
    public void accionBtnAtras3(ActionListener al) { btnAtras3.addActionListener(al); }
    public void accionBtnSalir(ActionListener al) { btnSalir.addActionListener(al); }

    public void mostrar(String panel) { cardLayout.show(cardPanel, panel); }
    public void setBotonPorDefecto(JButton boton) { getRootPane().setDefaultButton(boton); }
    public void resumen(String datos) { textAreaResumen.setText(datos); }

    private void cargarBanderas() {
        banderaSpain = new ImageIcon(getClass().getResource("/img/spain.png"));
        banderaUSA = new ImageIcon(getClass().getResource("/img/usa.png"));
    }

    private ImageIcon cargarImagenEscalada(String ruta, int ancho, int alto) {
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
            Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);
        } catch (Exception e) { return null; }
    }

    private ImageIcon cargarLogoEscalado(String ruta, int ancho, int alto) {
        return cargarImagenEscalada(ruta, ancho, alto);
    }

    public String[] cargarProvincias(String pais) {
        List<String> lista = new ArrayList<>();
        try (Scanner sc = new Scanner(getClass().getResourceAsStream("/files/" + pais + ".txt"))) {
            while (sc.hasNextLine()) { lista.add(sc.nextLine()); }
        } catch (Exception e) { }
        return lista.toArray(new String[0]);
    }

    private void configurarComboConBanderas() {
        cbPaises.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if ("Spain".equals(value)) label.setIcon(cargarImagenEscalada("/img/spain.png", 24, 16));
                else if ("USA".equals(value)) label.setIcon(cargarImagenEscalada("/img/usa.png", 24, 16));
                return label;
            }
        });
    }

    public void exportar() {
        if (!checkBox.isSelected()) return;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = new File(fc.getSelectedFile(), "user-data.txt");
            try (PrintWriter pw = new PrintWriter(file)) {
                pw.write(textAreaResumen.getText());
                JOptionPane.showMessageDialog(this, "Guardado con éxito");
            } catch (Exception e) { }
        }
    }
}