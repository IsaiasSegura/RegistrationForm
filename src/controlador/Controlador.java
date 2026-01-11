package controlador;

import modelo.Usuario;
import vista.Vista;
import vista.LoginWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controlador {

    private Usuario usuario;
    private Vista vista;
    private LoginWindow login;

    public Controlador(Usuario usuario, Vista vista) {
        this.usuario = usuario;
        this.vista = vista;

        this.login = new LoginWindow(vista);

        configurarEventosLogin();
        configurarNavegacionFormulario();
        configurarAtajosTeclado();

        vista.setVisible(false);
        login.setVisible(true);
    }

    private void configurarEventosLogin() {
        login.getBtnLogin().addActionListener(e -> {
            String user = login.getUsuario();
            String pass = login.getPassword();

            if (user.equals("admin") && pass.equals("admin")) {
                JOptionPane.showMessageDialog(login, "Login exitoso!");
                login.dispose();      // Cerramos la ventana de acceso
                vista.setVisible(true); // Mostramos la aplicación principal

                vista.mostrar("BIENVENIDA");
                vista.setBotonPorDefecto(vista.getBtnSiguiente());
            } else {
                JOptionPane.showMessageDialog(login, "Login incorrecto!", "Error", JOptionPane.ERROR_MESSAGE);
                login.limpiarCampos(); // Limpia cajas de texto y pone el foco en usuario
            }
        });

        login.getBtnCancelar().addActionListener(e -> {
            System.exit(0); // Cierra todo el programa
        });

        login.getCbIdioma().addActionListener(e -> {
            String seleccion = (String) login.getCbIdioma().getSelectedItem();
            java.util.Locale local;

            if (seleccion.equalsIgnoreCase("English") || seleccion.equalsIgnoreCase("Inglés")) {
                local = new java.util.Locale("en");
            } else {
                local = new java.util.Locale("es");
            }

            try {
                java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("idiomas.messages", local);

                login.actualizarIdioma(rb);
                vista.actualizarIdioma(rb);

                System.out.println("Idioma cambiado a: " + seleccion);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(login,
                        "Error al cargar el archivo de idiomas: " + ex.getMessage(),
                        "Error de Recursos", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void configurarNavegacionFormulario() {
        vista.accionBtnSiguiente(e -> {
            vista.mostrar("DATOS1");
            vista.setBotonPorDefecto(vista.getBtnSiguiente1());
        });

        vista.accionBtnAtras1(e -> {
            vista.mostrar("BIENVENIDA");
            vista.setBotonPorDefecto(vista.getBtnSiguiente());
        });

        vista.accionBtnSiguiente1(e -> {
            String nombre = vista.getNombreTxt().getText().trim();
            String email = vista.getEmailTxt().getText().trim();
            String password = new String(vista.getPasswordTxt().getPassword());
            int edad = (int) vista.getEdad().getValue();

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validarEmail(email)) {
                JOptionPane.showMessageDialog(vista, "Formato de correo electrónico incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validarPassword(password)) {
                JOptionPane.showMessageDialog(vista, "La contraseña no cumple los requisitos de seguridad", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuario.setNombre(nombre);
            usuario.setCorreoElectronico(email);
            usuario.setContrasenia(password);
            usuario.setEdad(edad);

            vista.mostrar("DATOS2");
            vista.setBotonPorDefecto(vista.getBtnSiguiente2());
        });

        vista.accionBtnAtras2(e -> {
            vista.mostrar("DATOS1");
            vista.setBotonPorDefecto(vista.getBtnSiguiente1());
        });

        vista.accionBtnSiguiente2(e -> {
            usuario.setPais((String) vista.getCbPaises().getSelectedItem());
            usuario.setProvincia((String) vista.getCbProvincias().getSelectedItem());

            String resumen = "NOMBRE: " + usuario.getNombre() + "\n" +
                    "EMAIL: " + usuario.getCorreoElectronico() + "\n" +
                    "EDAD: " + usuario.getEdad() + "\n" +
                    "PAÍS: " + usuario.getPais() + "\n" +
                    "PROVINCIA: " + usuario.getProvincia();

            vista.resumen(resumen);
            vista.mostrar("RESUMEN");
            vista.setBotonPorDefecto(vista.getBtnSiguiente3());
        });

        vista.accionBtnAtras3(e -> {
            vista.mostrar("DATOS2");
            vista.setBotonPorDefecto(vista.getBtnSiguiente2());
        });

        vista.accionBtnSiguiente3(e -> {
            mostrarVentanaCarga();
        });

        vista.accionBtnSalir(e -> System.exit(0));

        vista.getBtnExaminar().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
                vista.getTxtRutaArchivo().setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    private void mostrarVentanaCarga() {
        // Crear un diálogo de carga no cancelable
        JDialog dialogoCarga = new JDialog(vista, "Procesando", true);
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true); // Estilo de carga infinita

        dialogoCarga.setLayout(new java.awt.BorderLayout(10, 10));
        dialogoCarga.add(new JLabel("  Guardando registro, espere...", JLabel.CENTER), java.awt.BorderLayout.NORTH);
        dialogoCarga.add(progressBar, java.awt.BorderLayout.CENTER);
        dialogoCarga.setSize(300, 100);
        dialogoCarga.setLocationRelativeTo(vista);

        new Thread(() -> {
            try {
                Thread.sleep(2500); // Simulamos una carga de 2.5 segundos
                SwingUtilities.invokeLater(() -> {
                    dialogoCarga.dispose();
                    vista.exportar();
                    vista.mostrar("FINAL");
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();

        dialogoCarga.setVisible(true);
    }

    private void configurarAtajosTeclado() {
        InputMap im = vista.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = vista.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("control E"), "examinar");
        am.put("examinar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.getBtnExaminar().doClick();
            }
        });
    }

    private boolean validarEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean validarPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,16}$");
    }
}