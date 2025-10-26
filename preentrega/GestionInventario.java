import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class GestionInventario {
    private List<Articulo> articulos;
    private Scanner scanner;

    public GestionInventario() {
        this.articulos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                ejecutarOpcion(opcion);
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpiar el buffer
                opcion = -1; // Volver al menú
            }
        } while (opcion != 5);
        System.out.println("Saliendo del sistema...");
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n================================");
        System.out.println("=== SISTEMA DE GESTIÓN - ARTÍCULOS ===");
        System.out.println("================================");
        System.out.println("1) Crear artículo");
        System.out.println("2) Listar artículos");
        System.out.println("3) Modificar artículo");
        System.out.println("4) Eliminar artículo");
        System.out.println("5) Salir");
        System.out.print("Elija una opción: ");
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                crearArticulo();
                break;
            case 2:
                listarArticulos();
                break;
            case 3:
                modificarArticulo();
                break;
            case 4:
                eliminarArticulo();
                break;
            case 5:
                // La salida se maneja en el bucle 'do-while'
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }
    }

    private void crearArticulo() {
        System.out.println("¿Qué tipo de artículo desea crear?");
        System.out.println("1) Artículo Electrónico");
        System.out.println("2) Artículo de Ropa");
        System.out.print("Seleccione (1 o 2): ");
        int tipo;
        try {
            tipo = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Seleccionando opción por defecto (Artículo Electrónico).");
            scanner.nextLine(); // Limpiar buffer
            tipo = 1;
        }

        System.out.print("Ingrese la descripción del artículo: ");
        String descripcion = scanner.nextLine();
        double precio = 0.0;

        try {
            System.out.print("Ingrese el precio del artículo: ");
            precio = scanner.nextDouble();
            scanner.nextLine(); // Consumir salto de línea
        } catch (InputMismatchException e) {
            System.out.println("Precio inválido. Debe ser un número.");
            scanner.nextLine(); // Limpiar buffer
            return; // Volver al menú
        }

        Articulo nuevoArticulo = null;
        switch (tipo) {
            case 1:
                System.out.print("Ingrese la marca del artículo electrónico: ");
                String marca = scanner.nextLine();
                nuevoArticulo = new ArticuloElectronico(descripcion, precio, marca);
                break;
            case 2:
                System.out.print("Ingrese la talla del artículo de ropa: ");
                String talla = scanner.nextLine();
                nuevoArticulo = new ArticuloRopa(descripcion, precio, talla);
                break;
            default:
                System.out.println("Opción de tipo no válida. Creando Artículo Electrónico por defecto.");
                System.out.print("Ingrese la marca del artículo electrónico: ");
                String marcaDef = scanner.nextLine();
                nuevoArticulo = new ArticuloElectronico(descripcion, precio, marcaDef);
        }

        if (nuevoArticulo.esValido()) {
            articulos.add(nuevoArticulo);
            System.out.println("Artículo creado exitosamente:\n" + nuevoArticulo);
        } else {
            System.out.println("Error: El artículo no es válido y no se agregó.");
        }
    }

    private void listarArticulos() {
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
        } else {
            System.out.println("\n--- Lista de Artículos ---");
            for (Articulo a : articulos) {
                System.out.println(a);
            }
        }
    }

    private void modificarArticulo() {
        System.out.print("Ingrese el ID del artículo a modificar: ");
        int id;
        try {
            id = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine(); // Limpiar buffer
            return;
        }

        Articulo articulo = buscarArticuloPorId(id);

        if (articulo != null) {
            System.out.println("Artículo encontrado:\n" + articulo);
            System.out.print("¿Desea modificarlo? (s/n): ");
            String respuesta = scanner.nextLine().toLowerCase();
            if (respuesta.equals("s")) {
                System.out.print("Nueva descripción (deje vacío para no cambiar): ");
                String nuevaDescripcion = scanner.nextLine();
                if (!nuevaDescripcion.trim().isEmpty()) {
                    articulo.setDescripcion(nuevaDescripcion);
                }

                System.out.print("Nuevo precio (deje -1 para no cambiar): ");
                try {
                    double nuevoPrecio = scanner.nextDouble();
                    if (nuevoPrecio != -1) {
                        articulo.setPrecio(nuevoPrecio);
                    }
                    scanner.nextLine(); // Consumir salto de línea
                } catch (InputMismatchException e) {
                    System.out.println("Precio inválido, no se actualizará.");
                    scanner.nextLine(); // Limpiar buffer
                }

                // Modificar atributos específicos si es necesario (ej: marca, talla)
                if (articulo instanceof ArticuloElectronico) {
                    ArticuloElectronico ae = (ArticuloElectronico) articulo;
                    System.out.print("Nueva marca (deje vacío para no cambiar): ");
                    String nuevaMarca = scanner.nextLine();
                    if (!nuevaMarca.trim().isEmpty()) {
                        ae.setMarca(nuevaMarca);
                    }
                } else if (articulo instanceof ArticuloRopa) {
                    ArticuloRopa ar = (ArticuloRopa) articulo;
                    System.out.print("Nueva talla (deje vacío para no cambiar): ");
                    String nuevaTalla = scanner.nextLine();
                    if (!nuevaTalla.trim().isEmpty()) {
                        ar.setTalla(nuevaTalla);
                    }
                }

                System.out.println("Artículo modificado exitosamente:\n" + articulo);
            }
        } else {
            System.out.println("Artículo con ID " + id + " no encontrado.");
        }
    }

    private void eliminarArticulo() {
        System.out.print("Ingrese el ID del artículo a eliminar: ");
        int id;
        try {
            id = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine(); // Limpiar buffer
            return;
        }

        Articulo articulo = buscarArticuloPorId(id);

        if (articulo != null) {
            System.out.println("Artículo encontrado:\n" + articulo);
            System.out.print("¿Está seguro que desea eliminarlo? (s/n): ");
            String respuesta = scanner.nextLine().toLowerCase();
            if (respuesta.equals("s")) {
                articulos.remove(articulo);
                System.out.println("Artículo eliminado exitosamente.");
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } else {
            System.out.println("Artículo con ID " + id + " no encontrado.");
        }
    }

    private Articulo buscarArticuloPorId(int id) {
        for (Articulo a : articulos) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }
}
