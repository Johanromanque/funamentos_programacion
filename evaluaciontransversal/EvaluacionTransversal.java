package evaluaciontransversal;

import java.util.Scanner;
import java.util.ArrayList;

public class EvaluacionTransversal {
    static Scanner sc = new Scanner(System.in);

    // Listas para almacenar entradas vendidas y asientos ocupados
    static ArrayList<String> entradas = new ArrayList<>();
    static ArrayList<String> asientosOcupados = new ArrayList<>();

    // Menú principal
    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n--- SISTEMA DE VENTA DE ENTRADAS TEATRO MORO ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver entradas vendidas");
            System.out.println("3. Actualizar entrada");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    comprarEntrada();
                    break;
                case 2:
                    mostrarEntradasVendidas();
                    break;
                case 3:
                    actualizarEntrada();
                    break;
                case 4:
                    eliminarEntrada();
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5); // repite hasta que el usuario quiera salir
    }

    // Método para comprar una entrada
    public static void comprarEntrada() {
        long inicio = System.nanoTime(); // mide tiempo de procesamiento

        System.out.print("¿Cuántas entradas desea comprar? ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        ArrayList<Entrada> entradasTemp = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            System.out.println("\n--- Entrada #" + i + " ---");

            System.out.print("Edad del cliente: ");
            int edad = sc.nextInt();
            sc.nextLine();

            System.out.print("Sexo (M/F): ");
            String sexo = sc.nextLine().toUpperCase();

            String estudiante = "N";
            if (!(sexo.equals("F") || edad <= 12 || edad >= 60)) {
                System.out.print("¿Es estudiante? (S/N): ");
                estudiante = sc.nextLine().toUpperCase();
            }

            String tipoCliente = determinarTipoCliente(edad, sexo, estudiante);
            double descuento = calcularDescuento(tipoCliente);

            String asiento = seleccionarAsiento();
            if (asiento == null) {
                System.out.println("Asiento inválido o ya ocupado. Se omite esta entrada.");
                continue;
            }

            double precioBase = 10000;
            double precioFinal = precioBase - (precioBase * descuento);

            entradas.add(tipoCliente + " - Asiento: " + asiento + " - $" + precioFinal);
            asientosOcupados.add(asiento);

            entradasTemp.add(new Entrada(tipoCliente, asiento, precioFinal, descuento));
        }

        if (entradasTemp.isEmpty()) {
            System.out.println("No se realizaron compras.");
            return;
        }

        System.out.println("\n--- BOLETA FINAL ---");
        double total = 0;
        for (int i = 0; i < entradasTemp.size(); i++) {
            Entrada e = entradasTemp.get(i);
            System.out.println("Entrada #" + (i + 1));
            System.out.println("Tipo cliente: " + e.tipoCliente);
            System.out.println("Asiento: " + e.asiento);
            System.out.println("Descuento aplicado: " + (int)(e.descuento * 100) + "%");
            System.out.println("Total a pagar: $" + e.precioFinal);
            System.out.println("-----------------------------");
            total += e.precioFinal;
        }

        System.out.println("TOTAL COMPRA: $" + total);
        System.out.println("Gracias por su compra.");

        // se mide y muestra tiempo de procesamiento
        long fin = System.nanoTime();
        long duracion = fin - inicio;
        System.out.println("Tiempo de procesamiento: " + duracion + " nanosegundos");
    }


    // Determinar tipo de cliente
    public static String determinarTipoCliente(int edad, String sexo, String estudiante) {
        if (edad <= 12) return "Niño";
        else if (edad >= 60) return "Tercera Edad";
        else if (estudiante.equals("S")) return "Estudiante";
        else if (sexo.equals("F")) return "Mujer";
        else return "General";
    }

    // Calcular descuento segun tipo de cliente
    public static double calcularDescuento(String tipo) {
        switch (tipo) {
            case "Niño": return 0.10;
            case "Mujer": return 0.20;
            case "Estudiante": return 0.15;
            case "Tercera Edad": return 0.25;
            default: return 0.0;
        }
    }

    // Selección y validación de asiento
    public static String seleccionarAsiento() {
        System.out.println("Zonas disponibles: VIP, PALCO, PLATEA BAJA, PLATEA ALTA, GALERIA");
        System.out.print("Seleccione una zona: ");
        String zona = sc.nextLine().toUpperCase();

        System.out.print("Ingrese número de asiento (ej: A1, B2): ");
        String asiento = sc.nextLine().toUpperCase();

        String asientoCompleto = zona + "-" + asiento;

        if (asientosOcupados.contains(asientoCompleto)) {
            System.out.println("Asiento ocupado. Intente con otro.");
            return null;
        }

        return asientoCompleto;
    }
    
    static class Entrada {
        String tipoCliente;
        String asiento;
        double precioFinal;
        double descuento;

        Entrada(String tipoCliente, String asiento, double precioFinal, double descuento) {
            this.tipoCliente = tipoCliente;
            this.asiento = asiento;
            this.precioFinal = precioFinal;
            this.descuento = descuento;
        }
    }

    // Mostrar entradas vendidas
    public static void mostrarEntradasVendidas() {
        if (entradas.isEmpty()) {
            System.out.println("No hay entradas vendidas aún.");
        } else {
            System.out.println("\nEntradas vendidas:");
            for (int i = 0; i < entradas.size(); i++) {
                System.out.println((i + 1) + ". " + entradas.get(i));
            }
        }
    }

    // Actualizar entrada (solo permite cambiar el asiento)
    public static void actualizarEntrada() {
        if (entradas.isEmpty()) {
            System.out.println("No hay entradas para actualizar.");
            return;
        }

        mostrarEntradasVendidas();
        System.out.print("Seleccione el número de entrada a actualizar: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index < 1 || index > entradas.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        String entradaAnterior = entradas.get(index - 1);
        String[] partes = entradaAnterior.split(" - Asiento: ");
        String tipo = partes[0];
        String asientoAnterior = partes[1].split(" - ")[0];

        System.out.println("Actualizando asiento...");
        String nuevoAsiento = seleccionarAsiento();
        if (nuevoAsiento == null) {
            System.out.println("Asiento inválido o ya ocupado. No se actualizó.");
            return;
        }

        // Actualizar listas
        asientosOcupados.remove(asientoAnterior);
        asientosOcupados.add(nuevoAsiento);

        double precio = Double.parseDouble(partes[1].split("\\$")[1]);
        entradas.set(index - 1, tipo + " - Asiento: " + nuevoAsiento + " - $" + precio);

        System.out.println("Entrada actualizada exitosamente.");
    }

    // Eliminar entrada
    public static void eliminarEntrada() {
        if (entradas.isEmpty()) {
            System.out.println("No hay entradas para eliminar.");
            return;
        }

        mostrarEntradasVendidas();
        System.out.print("Seleccione el número de entrada a eliminar: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index < 1 || index > entradas.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        String entrada = entradas.get(index - 1);
        String asiento = entrada.split(" - Asiento: ")[1].split(" - ")[0];

        entradas.remove(index - 1);
        asientosOcupados.remove(asiento);

        System.out.println("Entrada eliminada correctamente.");
    }
}
