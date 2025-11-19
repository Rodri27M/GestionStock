
package modelo;

import java.util.List;


public class Usuario {
    private int idusu;
    private String num_docu;
    private String nombre;
    private String apellido;
    private String email;
    private String usuario;
    private String clave;
    private int id_perfil;
    private String nombrePerfil; // Para mostrar en vistas
    private List<Actividad> actividades;
    
    // Constructores
    public Usuario() {}
    
    public Usuario(int idusu, String nombre, String apellido, String email, 
                   String usuario, int id_perfil) {
        this.idusu = idusu;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.usuario = usuario;
        this.id_perfil = id_perfil;
    }
    
    // Getters y Setters
    public int getIdusu() { return idusu; }
    public void setIdusu(int idusu) { this.idusu = idusu; }
    
    public String getNum_docu() { return num_docu; }
    public void setNum_docu(String num_docu) { this.num_docu = num_docu; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    
    public int getId_perfil() { return id_perfil; }
    public void setId_perfil(int id_perfil) { this.id_perfil = id_perfil; }
    
    public String getNombrePerfil() { return nombrePerfil; }
    public void setNombrePerfil(String nombrePerfil) { this.nombrePerfil = nombrePerfil; }
    public List<Actividad> getActividades() { return actividades; }
    public void setActividades(List<Actividad> actividades) { this.actividades = actividades; }
    
    // Método útil para verificar si tiene una actividad
    public boolean tieneActividad(String enlace) {
        if (actividades == null) return false;
        return actividades.stream()
                .anyMatch(a -> enlace.equals(a.getEnlace()));
    }
    // Método útil para vistas
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
