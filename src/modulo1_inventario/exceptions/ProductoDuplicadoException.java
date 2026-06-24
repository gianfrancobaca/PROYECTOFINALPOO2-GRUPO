package modulo1_inventario.exceptions;

public class ProductoDuplicadoException extends InventarioException {
    private final String codigo;

    public ProductoDuplicadoException(String codigo) {
        super("Ya existe un producto con el código: " + codigo);
        this.codigo = codigo;
    }

    public String getCodigo() { return codigo; }
}

