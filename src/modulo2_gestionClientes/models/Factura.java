package modulo2_gestionClientes.models;

import java.util.Date;

public class Factura {
    private int idFactura;
    private Date fecha;
    private double total;
    private String estado;
    private Pedido pedido;
    private Cliente cliente;

    public Factura() {}

    public Factura(int idFactura, Date fecha, double total, String estado, Pedido pedido, Cliente cliente) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.pedido = pedido;
        this.cliente = cliente;
    }

