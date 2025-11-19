// modelo/beans/Actividad.java
package modelo;

public class Actividad {
    private int id_actividad;
    private String nom_actividad;
    private String enlace;
    
    // Constructores
    public Actividad() {}
    
    public Actividad(int id_actividad, String nom_actividad, String enlace) {
        this.id_actividad = id_actividad;
        this.nom_actividad = nom_actividad;
        this.enlace = enlace;
    }
    
    // Getters y Setters
    public int getId_actividad() { return id_actividad; }
    public void setId_actividad(int id_actividad) { this.id_actividad = id_actividad; }
    
    public String getNom_actividad() { return nom_actividad; }
    public void setNom_actividad(String nom_actividad) { this.nom_actividad = nom_actividad; }
    
    public String getEnlace() { return enlace; }
    public void setEnlace(String enlace) { this.enlace = enlace; }
    
    @Override
    public String toString() {
        return nom_actividad;
    }
}
