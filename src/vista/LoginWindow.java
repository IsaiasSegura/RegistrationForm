package vista;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JDialog {
    private JLabel lblUsuario, lblPassword, lblIdioma;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancelar;
    private JComboBox<String> cbIdioma;

    public LoginWindow(Frame padre) {
        super(padre, "Acceso al Sistema", true);
        configurarVentana();
        inicializarComponentes();
        pack();
        setLocationRelativeTo(padre);
    }

    private void configurarVentana() {
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void inicializarComponentes() {
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField(15);

        lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField(15);

        lblIdioma = new JLabel("Idioma:");
        cbIdioma = new JComboBox<>(new String[]{"Español", "English"});

        panelCampos.add(lblUsuario);
        panelCampos.add(txtUsuario);
        panelCampos.add(lblPassword);
        panelCampos.add(txtPassword);
        panelCampos.add(lblIdioma);
        panelCampos.add(cbIdioma);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogin = new JButton("Login");
        btnCancelar = new JButton("Cancelar");

        this.getRootPane().setDefaultButton(btnLogin);

        panelBotones.add(btnCancelar);
        panelBotones.add(btnLogin);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public String getUsuario() { return txtUsuario.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JComboBox<String> getCbIdioma() { return cbIdioma; }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
        txtUsuario.requestFocus();
    }

    public void actualizarIdioma(java.util.ResourceBundle rb) {
        try {
            setTitle(rb.getString("login.titulo"));
            lblUsuario.setText(rb.getString("login.usuario"));
            lblPassword.setText(rb.getString("login.password"));
            lblIdioma.setText(rb.getString("login.idioma"));
            btnLogin.setText(rb.getString("login.boton"));
            btnCancelar.setText(rb.getString("login.cancelar"));
        } catch (Exception e) {
            System.err.println("Error al actualizar textos en LoginWindow: " + e.getMessage());
        }
    }
}