package modulo1_inventario.exceptions;

public class InventarioException extends RuntimeException {
    public InventarioException(String mensaje) {
        super(mensaje);
    }
    public InventarioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}