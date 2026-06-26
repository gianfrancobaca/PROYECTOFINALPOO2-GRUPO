package modulo2_gestionClientes.models;

import java.util.Date;

public class TicketSoporte {

    private int idTicket;
    private String asunto;
    private String descripcion;
    private String estado;
    private Date fechaCreacion;
    private Cliente cliente;

    public TicketSoporte() {
    }

    public TicketSoporte(int idTicket, String asunto, String descripcion, String estado, Date fechaCreacion, Cliente cliente) {
        this.idTicket = idTicket;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "TicketSoporte{" +
                "idTicket=" + idTicket +
                ", asunto='" + asunto + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "Sin cliente") +
                '}';
    }
}
