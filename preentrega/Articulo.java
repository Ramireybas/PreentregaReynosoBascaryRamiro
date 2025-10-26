
public abstract class Articulo implements Validable {
    private static int ultimoId = 0; // Contador estático para ID único
    private int id;
    private String descripcion;
    private double precio;

    public Articulo(String descripcion, double precio) {
        this.id = ++ultimoId;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    // Setters (validando que el precio no sea negativo)
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            System.out.println("Error: El precio no puede ser negativo.");
        }
    }

    // Método abstracto para polimorfismo
    public abstract String getTipo();

    // Implementación del método de la interfaz Validable
    @Override
    public boolean esValido() {
        return this.descripcion != null && !this.descripcion.trim().isEmpty() && this.precio >= 0;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Descripción: " + descripcion + ", Precio: $" + String.format("%.2f", precio) + ", Tipo: " + getTipo();
    }
}