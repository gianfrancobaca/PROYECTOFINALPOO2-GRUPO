package modulo2_gestionClientes.models;

import java.util.Date;

public class Reclamo {
    private int idReclamo;
    private String motivo;
    private String estado;
    private Date fecha;
    private Cliente cliente;

    public Reclamo() {
    }

    public Reclamo(int idReclamo, String motivo, String estado, Date fecha, Cliente cliente) {
        this.idReclamo = idReclamo;
        this.motivo = motivo;
        this.estado = estado;
        this.fecha = fecha;
        this.cliente = cliente;
    }
