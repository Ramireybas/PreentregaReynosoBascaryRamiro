public class ArticuloElectronico extends Articulo {
    private String marca;

    public ArticuloElectronico(String descripcion, double precio, String marca) {
        super(descripcion, precio);
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String getTipo() {
        return "Electrónico";
    }

    @Override
    public String toString() {
        return super.toString() + ", Marca: " + marca;
    }
}