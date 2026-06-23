package modulo2_gestionClientes.models;

public class DetallePedido {
    private int idDetalle;
    private int idPedido;
    private String producto;
    private int cantidad;
    private double precioUnitario;

    public DetallePedido() {}

    public DetallePedido(int idDetalle, int idPedido, String producto, int cantidad, double precioUnitario) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }