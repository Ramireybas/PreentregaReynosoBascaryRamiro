public class ArticuloRopa extends Articulo {
    private String talla;

    public ArticuloRopa(String descripcion, double precio, String talla) {
        super(descripcion, precio);
        this.talla = talla;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    @Override
    public String getTipo() {
        return "Ropa";
    }

    @Override
    public String toString() {
        return super.toString() + ", Talla: " + talla;
    }
}