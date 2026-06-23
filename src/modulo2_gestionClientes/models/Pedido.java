package modulo2_gestionClientes.models;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int idPedido;
    private Date fecha;
    private String estado;
    private Cliente cliente;
    private List<DetallePedido> detalles;

    public Pedido() {}

    public Pedido(int idPedido, Date fecha, String estado, Cliente cliente, List<DetallePedido> detalles) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estado = estado;
        this.cliente = cliente;
        this.detalles = detalles;
    }

