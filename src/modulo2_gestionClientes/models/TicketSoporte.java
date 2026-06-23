package modulo2_gestionClientes.models;

import java.util.Date;

public class TicketSoporte {
    private int idTicket;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Date fecha;
    private Reclamo reclamo;

    public TicketSoporte() {}

    public TicketSoporte(int idTicket, String descripcion, String estado, String prioridad, Date fecha, Reclamo reclamo) {
        this.idTicket = idTicket;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fecha = fecha;
        this.reclamo = reclamo;
    }

    public int getIdTicket() { return idTicket; }
    public void setIdTicket(int idTicket) { this.idTicket = idTicket; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Reclamo getReclamo() { return reclamo; }
    public void setReclamo(Reclamo reclamo) { this.reclamo = reclamo; }

    @Override
    public String toString() {
        return "TicketSoporte{" +
                "idTicket=" + idTicket +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
