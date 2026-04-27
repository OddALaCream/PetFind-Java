package CLASES;

import java.sql.Date;

public class Medicamento {
    private int idMedicamento;
    private int idReceta;
    private String nombre;
    private String dosis;
    private int duracion;
    private int frecuencia;

    // Constructores
    public Medicamento() {}

    public Medicamento(int idMedicamento, int idReceta, String nombre, String dosis, int duracion, int frecuencia) {
        this.idMedicamento = idMedicamento;
        this.idReceta = idReceta;
        this.nombre = nombre;
        this.dosis = dosis;
        this.duracion = duracion;
        this.frecuencia = frecuencia;
    }

    public Medicamento(String nombre, String dosis, int duracion, int frecuencia) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.duracion = duracion;
        this.frecuencia = frecuencia;
    }

    // Getters y Setters
    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}

// ==================== CLASE RECORDATORIO ====================
