package modulo1_inventario.models;

import core.Model;
import core.View;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlertaStock implements Model {

    public enum TipoAlerta { STOCK_MINIMO, STOCK_AGOTADO, SOBRESTOCK }
    public enum EstadoAlerta { ACTIVA, RESUELTA, IGNORADA }

    private int          id;
    private int          productoId;
    private String       productoNombre;
    private TipoAlerta   tipo;
    private int          stockActual;
    private int          stockMinimo;
    private EstadoAlerta estado;
    private LocalDateTime fechaGeneracion;

    private final List<View> observers = new ArrayList<>();

    public AlertaStock() {}

    public AlertaStock(int id, int productoId, String productoNombre,
                       TipoAlerta tipo, int stockActual, int stockMinimo) {
        this.id              = id;
        this.productoId      = productoId;
        this.productoNombre  = productoNombre;
        this.tipo            = tipo;
        this.stockActual     = stockActual;
        this.stockMinimo     = stockMinimo;
        this.estado          = EstadoAlerta.ACTIVA;
        this.fechaGeneracion = LocalDateTime.now();
    }

    @Override public void attach(View v)  { observers.add(v); }
    @Override public void detach(View v)  { observers.remove(v); }
    @Override public void notifyViews()   { for (View v : observers) v.update(this, null); }

    public int           getId()                               { return id; }
    public void          setId(int id)                         { this.id = id; }
    public int           getProductoId()                       { return productoId; }
    public void          setProductoId(int productoId)         { this.productoId = productoId; }
    public String        getProductoNombre()                   { return productoNombre; }
    public void          setProductoNombre(String pn)          { this.productoNombre = pn; }
    public TipoAlerta    getTipo()                             { return tipo; }
    public void          setTipo(TipoAlerta tipo)              { this.tipo = tipo; }
    public int           getStockActual()                      { return stockActual; }
    public void          setStockActual(int stockActual)       { this.stockActual = stockActual; }
    public int           getStockMinimo()                      { return stockMinimo; }
    public void          setStockMinimo(int stockMinimo)       { this.stockMinimo = stockMinimo; }
    public EstadoAlerta  getEstado()                           { return estado; }
    public void          setEstado(EstadoAlerta estado)        { this.estado = estado; }
    public LocalDateTime getFechaGeneracion()                  { return fechaGeneracion; }
    public void          setFechaGeneracion(LocalDateTime f)   { this.fechaGeneracion = f; }

    @Override
    public String toString() {
        return String.format("Alerta[%s | %s | stock=%d | mín=%d | %s]",
                tipo, productoNombre, stockActual, stockMinimo, estado);
    }
}