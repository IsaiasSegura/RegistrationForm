package modelo;

public class Usuario {
    private String nombre;
    private String email;
    private String contrasenia;
    private int edad;
    private String pais;
    private String provincia;

    public Usuario(String nombre, String correoElectronico, String contrasenia, int edad, String pais, String provincia) {
        this.nombre = nombre;
        this.email = correoElectronico;
        this.contrasenia = contrasenia;
        this.edad = edad;
        this.pais = pais;
        this.provincia = provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return email;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.email = correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Usuario{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", correoElectronico='").append(email).append('\'');
        sb.append(", contrasenia='").append(contrasenia).append('\'');
        sb.append(", edad=").append(edad);
        sb.append(", pais='").append(pais).append('\'');
        sb.append(", provincia='").append(provincia).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
