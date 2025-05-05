package sumativaocho;

import java.util.ArrayList;
import java.util.Scanner;

public class SumativaOcho {
    public static void main(String[] args) {
        // variables principales, para iniciar teclado y presentar los asientos. valor 0 es disponible y 1 ocupado.
        Scanner teclado = new Scanner(System.in);

        int[] filaA = {0, 0, 0, 0, 0};
        int[] filaB = {0, 0, 0, 0, 0};
        int[] filaC = {0, 0, 0, 0, 0};

        double precioBase = 10000; // precio base de entrada $10000

        // listas para ventas, descuento y reserva.
        ArrayList<String> ventas = new ArrayList<>(50);
        ArrayList<String> descuentosAplicados = new ArrayList<>(50);
        ArrayList<String> reservas = new ArrayList<>(50);

        String[] idClientes = new String[50]; // array fijo para registrar ID de cada venta
        int totalVentas = 0; // contador general de ventas realizadas.

        boolean continuar = true; // mientras el cliente no seleccione salir el ciclo se repite.

        // menu principal
        while (continuar) {
            System.out.println("\n********** MENÚ **********");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver ventas");
            System.out.println("3. Ver reservas");
            System.out.println("4. Modificar venta");
            System.out.println("5. Eliminar venta");
            System.out.println("6. Reservar entrada");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            if (!teclado.hasNextInt()) {
                System.out.println("Ingrese un número válido.");
                teclado.nextLine();
                continue;
            }

            int opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    totalVentas = comprarEntrada(teclado, filaA, filaB, filaC, ventas, descuentosAplicados, idClientes, totalVentas, precioBase);
                    break;
                case 2:
                    mostrarVentas(ventas, idClientes, descuentosAplicados, totalVentas);
                    break;
                case 3:
                    totalVentas = mostrarReservas(teclado, reservas, filaA, filaB, filaC, ventas, descuentosAplicados, idClientes, totalVentas, precioBase);
                    break;
                case 4:
                    totalVentas = modificarVenta(teclado, ventas, filaA, filaB, filaC, precioBase, idClientes, descuentosAplicados, totalVentas);
                    break;
                case 5:
                    totalVentas = eliminarVenta(teclado, ventas, filaA, filaB, filaC, idClientes, descuentosAplicados, totalVentas);
                    break;
                case 6:
                    reservarEntrada(teclado, filaA, filaB, filaC, reservas);
                    break;
                case 7:
                    continuar = false;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        teclado.close();
    }

    public static int comprarEntrada(Scanner teclado, int[] filaA, int[] filaB, int[] filaC, ArrayList<String> ventas, ArrayList<String> descuentos, String[] idClientes, int totalVentas, double precioBase) {
        mostrarPlano(filaA, filaB, filaC);
        System.out.print("Zona (A/B/C): ");
        String zona = teclado.nextLine().toUpperCase();
        int[] fila;

        switch (zona) {
            case "A": fila = filaA; break;
            case "B": fila = filaB; break;
            case "C": fila = filaC; break;
            default:
                System.out.println("Zona inválida."); return totalVentas;
        }

        System.out.print("Asiento (1-5): ");
        if (!teclado.hasNextInt()) {
            System.out.println("Número inválido."); teclado.nextLine(); return totalVentas;
        }

        int asiento = teclado.nextInt();
        teclado.nextLine();
        int indice = asiento - 1;

        if (indice < 0 || indice >= fila.length || fila[indice] == 1) {
            System.out.println("Asiento inválido u ocupado."); return totalVentas;
        }

        fila[indice] = 1;

        System.out.print("Ingrese ID cliente (ej. RUT): ");
        String id = teclado.nextLine();

        System.out.print("Ingrese edad: ");
        if (!teclado.hasNextInt()) {
            System.out.println("Edad inválida."); teclado.nextLine(); return totalVentas;
        }
        int edad = teclado.nextInt();
        teclado.nextLine();

        double descuento = (edad >= 60) ? 0.15 : (edad < 23) ? 0.10 : 0;
        double total = precioBase - (precioBase * descuento);
        int descInt = (int) (descuento * 100);

        if (edad < 23) {
            System.out.println("\n¡Felicitaciones! Eres estudiante, ganaste un paquete de cabritas.");
        } else if (edad >= 60) {
            System.out.println("\n¡Felicitaciones! Eres de la tercera edad, ganaste un agua mineral con unas galletas.");
        } else {
            System.out.println("\nNo tienes descuento.");
        }

        String venta = zona + "-" + asiento + ", Edad " + edad + ", Total $" + Math.round(total);
        ventas.add(venta);
        descuentos.add(descInt + "%");
        idClientes[totalVentas] = id;
        totalVentas++;

        System.out.println("\nCompra realizada: " + venta + ", ID Cliente: " + id + ", Descuento: " + descInt + "%");
        return totalVentas;
    }
    //metodo mostrarPlano(), imprime filas y asientos disponibles.
    public static void mostrarPlano(int[] filaA, int[] filaB, int[] filaC) {
        System.out.print("Zona A: "); for (int i : filaA) System.out.print("[" + i + "]");
        System.out.print("\nZona B: "); for (int i : filaB) System.out.print("[" + i + "]");
        System.out.print("\nZona C: "); for (int i : filaC) System.out.print("[" + i + "]");
        System.out.println();
    }
    // metodo mostrarVentas() muestra ventas registradas, asiento, edad y descuento.
    public static void mostrarVentas(ArrayList<String> ventas, String[] idClientes, ArrayList<String> descuentos, int totalVentas) {
        System.out.println("\n--- VENTAS ---");
        for (int i = 0; i < ventas.size(); i++) {
            System.out.println((i + 1) + ". " + ventas.get(i) + " | ID Cliente: " + idClientes[i] + " | Desc: " + descuentos.get(i));
        }
    }
    // Metodo similar a comprar entrada, pero sin pago. marca el asiento como ocupado y guarda reserva.
    public static void reservarEntrada(Scanner teclado, int[] filaA, int[] filaB, int[] filaC, ArrayList<String> reservas) {
        mostrarPlano(filaA, filaB, filaC);
        System.out.print("Zona (A/B/C): ");
        String zona = teclado.nextLine().toUpperCase();
        int[] fila;

        switch (zona) {
            case "A": fila = filaA; break;
            case "B": fila = filaB; break;
            case "C": fila = filaC; break;
            default:
                System.out.println("Zona inválida."); return;
        }

        System.out.print("Asiento (1-5): ");
        if (!teclado.hasNextInt()) {
            System.out.println("Número inválido."); teclado.nextLine(); return;
        }

        int asiento = teclado.nextInt();
        teclado.nextLine();
        int indice = asiento - 1;

        if (indice < 0 || indice >= fila.length || fila[indice] == 1) {
            System.out.println("Asiento inválido u ocupado."); return;
        }

        fila[indice] = 1;
        reservas.add(zona + "-" + asiento);
        System.out.println("\nReserva realizada exitosamente.");
    }
        // muestra la lista de reservas y pregunta si desea comprar una reserva.
    public static int mostrarReservas(Scanner teclado, ArrayList<String> reservas, int[] filaA, int[] filaB, int[] filaC, ArrayList<String> ventas, ArrayList<String> descuentos, String[] idClientes, int totalVentas, double precioBase) {
        if (reservas.isEmpty()) {
            System.out.println("\nNo hay reservas.");
            return totalVentas;
        }

        System.out.println("\n--- RESERVAS ---");
        for (int i = 0; i < reservas.size(); i++) {
            System.out.println((i + 1) + ". " + reservas.get(i));
        }

        System.out.print("¿Desea comprar una de estas reservas? (s/n): ");
        String respuesta = teclado.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Seleccione el número de reserva: ");
            if (!teclado.hasNextInt()) {
                System.out.println("Entrada inválida."); return totalVentas;
            }

            int index = teclado.nextInt() - 1;
            teclado.nextLine();
            if (index < 0 || index >= reservas.size()) {
                System.out.println("Reserva no encontrada."); return totalVentas;
            }

            String reserva = reservas.remove(index);
            String[] datos = reserva.split("-");
            String zona = datos[0];
            int asiento = Integer.parseInt(datos[1]);

            System.out.print("Ingrese ID cliente: ");
            String id = teclado.nextLine();
            System.out.print("Ingrese edad: ");
            int edad = teclado.nextInt();
            teclado.nextLine();

            double descuento = (edad >= 60) ? 0.15 : (edad < 23) ? 0.10 : 0;
            double total = precioBase - (precioBase * descuento);
            int descInt = (int) (descuento * 100);

            if (edad < 23) {
                System.out.println("\n¡Felicitaciones! Eres estudiante, ganaste un paquete de cabritas.");
            } else if (edad >= 60) {
                System.out.println("\n¡Felicitaciones! Eres de la tercera edad, ganaste un agua mineral con unas galletas.");
            } else {
                System.out.println("\nNo tienes descuento.");
            }

            String venta = zona + "-" + asiento + ", Edad " + edad + ", Total $" + Math.round(total);
            ventas.add(venta);
            descuentos.add(descInt + "%");
            idClientes[totalVentas] = id;
            totalVentas++;

            System.out.println("\nCompra realizada desde reserva: " + venta);
        }

        return totalVentas;
    }
        // metodo modificarVentas() muestra venta, libera asiento, elimina venta y descuento e ID de cliente.
    public static int modificarVenta(Scanner teclado, ArrayList<String> ventas, int[] filaA, int[] filaB, int[] filaC, double precioBase, String[] idClientes, ArrayList<String> descuentos, int totalVentas) {
        mostrarVentas(ventas, idClientes, descuentos, totalVentas);
        System.out.print("Seleccione el número de venta a modificar: ");
        if (!teclado.hasNextInt()) {
            System.out.println("Entrada inválida.");
            teclado.nextLine(); return totalVentas;
        }
        int index = teclado.nextInt() - 1;
        teclado.nextLine();

        if (index < 0 || index >= ventas.size()) {
            System.out.println("Venta no encontrada."); return totalVentas;
        }

        liberarAsiento(ventas.get(index), filaA, filaB, filaC);
        ventas.remove(index);
        descuentos.remove(index);
        for (int i = index; i < totalVentas - 1; i++) {
            idClientes[i] = idClientes[i + 1];
        }
        idClientes[totalVentas - 1] = null;
        totalVentas--;

        System.out.println("Reingrese datos:");
        return comprarEntrada(teclado, filaA, filaB, filaC, ventas, descuentos, idClientes, totalVentas, precioBase);
    }
    // al igual que modificarVenta() pero elimina sin volver a comprar. reduce totalVenta.
    public static int eliminarVenta(Scanner teclado, ArrayList<String> ventas, int[] filaA, int[] filaB, int[] filaC, String[] idClientes, ArrayList<String> descuentos, int totalVentas) {
        mostrarVentas(ventas, idClientes, descuentos, totalVentas);
        System.out.print("Seleccione el número de venta a eliminar: ");
        if (!teclado.hasNextInt()) {
            System.out.println("Entrada inválida.");
            teclado.nextLine(); return totalVentas;
        }
        int index = teclado.nextInt() - 1;
        teclado.nextLine();

        if (index < 0 || index >= ventas.size()) {
            System.out.println("Venta no encontrada."); return totalVentas;
        }

        liberarAsiento(ventas.get(index), filaA, filaB, filaC);
        ventas.remove(index);
        descuentos.remove(index);
        for (int i = index; i < totalVentas - 1; i++) {
            idClientes[i] = idClientes[i + 1];
        }
        idClientes[totalVentas - 1] = null;
        totalVentas--;

        System.out.println("Venta eliminada.");
        return totalVentas;
    }
        // extrae zona y asiento desde la cadena de venta, cambia el valor del asiento a disponible.
    public static void liberarAsiento(String venta, int[] filaA, int[] filaB, int[] filaC) {
        String[] partes = venta.split(",");
        String[] datos = partes[0].split("-");
        String zona = datos[0];
        int asiento = Integer.parseInt(datos[1].trim()) - 1;

        switch (zona) {
            case "A": filaA[asiento] = 0; break;
            case "B": filaB[asiento] = 0; break;
            case "C": filaC[asiento] = 0; break;
        }
    }
}

