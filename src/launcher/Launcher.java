package launcher;

import controlador.Controlador;
import modelo.Usuario;
import vista.Vista;

public class Launcher {
    public static void main(String[] args) {
        Usuario usuario = new Usuario("", "", "", 18, "", "");
        Vista vista = new Vista();
        new Controlador(usuario, vista);
    }
}
