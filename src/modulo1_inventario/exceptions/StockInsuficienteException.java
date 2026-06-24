package modulo1_inventario.exceptions;

public class StockInsuficienteException extends InventarioException {
    private final int stockDisponible;
    private final int cantidadSolicitada;

    public StockInsuficienteException(int stockDisponible, int cantidadSolicitada) {
        super("Stock insuficiente. Disponible: " + stockDisponible
                + ", Solicitado: " + cantidadSolicitada);
        this.stockDisponible    = stockDisponible;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public int getStockDisponible()    { return stockDisponible; }
    public int getCantidadSolicitada() { return cantidadSolicitada; }
}