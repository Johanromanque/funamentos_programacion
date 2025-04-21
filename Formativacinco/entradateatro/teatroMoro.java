package entradateatro;

import java.util.Scanner;

public class teatroMoro {

    static final Scanner scanner = new Scanner(System.in);
    static final int MAX_ENTRADAS = 100;

    static String[] ubicaciones = new String[MAX_ENTRADAS];
    static int[] edades = new int[MAX_ENTRADAS];
    static double[] precios = new double[MAX_ENTRADAS];
    static int totalEntradas = 0;

    static int vipDisponibles = 10;
    static int plateaDisponibles = 30;
    static int galeriaDisponibles = 60;

    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n==== Teatro Moro ====");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver entradas vendidas");
            System.out.println("3. Buscar entrada");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Debe ingresar un número.");
                scanner.nextLine();
                continue;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> comprarEntrada();
                case 2 -> mostrarEntradas();
                case 3 -> buscarEntrada();
                case 4 -> eliminarEntrada();
                case 5 -> {
                    System.out.println("¡Gracias por su visita!");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }

    static void comprarEntrada() {
        if (totalEntradas >= MAX_ENTRADAS) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        System.out.println("\nEntradas disponibles:");
        System.out.println("VIP: " + vipDisponibles);
        System.out.println("Platea: " + plateaDisponibles);
        System.out.println("General: " + galeriaDisponibles);

        System.out.print("Ingrese tipo de entrada (VIP, Platea, General): ");
        String tipo = scanner.nextLine().toUpperCase();

        int disponibles = switch (tipo) {
            case "VIP" -> vipDisponibles;
            case "PLATEA" -> plateaDisponibles;
            case "GENERAL" -> galeriaDisponibles;
            default -> {
                System.out.println("Tipo inválido.");
                yield -1;
            }
        };

        if (disponibles == -1) return;

        System.out.print("¿Cuántas entradas desea?: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        if (cantidad <= 0 || cantidad > disponibles || totalEntradas + cantidad > MAX_ENTRADAS) {
            System.out.println("Cantidad inválida.");
            return;
        }

        System.out.print("Ingrese su edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();

        if (edad <= 0 || edad > 120) {
            System.out.println("Edad inválida.");
            return;
        }

        double precioBase = switch (tipo) {
            case "VIP" -> 15000;
            case "PLATEA" -> 12000;
            default -> 10000;
        };

        double descuento = 0;
        if (edad >= 60) {
            descuento = 0.15;
            System.out.println("\n-----------------------------------------------------------------");
            System.out.println("¡Felicidades! Ganaste un tesito por ser parte de la tercera edad.");
            System.out.println("-----------------------------------------------------------------");
        } else if (edad < 23) {
            descuento = 0.10;
            System.out.println("\n---------------------------------------- ----------");
            System.out.println("¡Felicidades! Ganaste una bebida por ser estudiante.");
            System.out.println("---------------------------------------------------");
        }

        double precioFinal = precioBase * (1 - descuento);

        for (int i = 0; i < cantidad; i++) {
            ubicaciones[totalEntradas] = tipo;
            edades[totalEntradas] = edad;
            precios[totalEntradas] = precioFinal;
            totalEntradas++;
        }

        switch (tipo) {
            case "VIP" -> vipDisponibles -= cantidad;
            case "PLATEA" -> plateaDisponibles -= cantidad;
            case "GENERAL" -> galeriaDisponibles -= cantidad;
        }
        System.out.println("Descuento aplicado: " + Math.round(descuento * 100) + "%");
        System.out.println("Compra realizada. Total a pagar: $" + Math.round(precioFinal * cantidad));

    }

    static void mostrarEntradas() {
        if (totalEntradas == 0) {
            System.out.println("No hay entradas vendidas.");
            return;
        }

        for (int i = 0; i < totalEntradas; i++) {
            System.out.println("Entrada " + (i + 1) + ": " +
                    "Ubicación: " + ubicaciones[i] +
                    ", Edad: " + edades[i] +
                    ", Precio: $" + Math.round(precios[i]));
        }
    }

    static void buscarEntrada() {
        System.out.println("\nBuscar por:");
        System.out.println("1. Número de entrada");
        System.out.println("2. Ubicación");
        System.out.println("3. Tipo de cliente");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> {
                System.out.print("Ingrese número de entrada: ");
                int num = scanner.nextInt();
                scanner.nextLine();
                if (num < 1 || num > totalEntradas) {
                    System.out.println("Número inválido.");
                    return;
                }
                System.out.println("Entrada " + num + ": " +
                        "Ubicación: " + ubicaciones[num - 1] +
                        ", Edad: " + edades[num - 1] +
                        ", Precio: $" + Math.round(precios[num - 1]));
            }
            case 2 -> {
                System.out.print("Ingrese ubicación (VIP, Platea, General): ");
                String ubi = scanner.nextLine().toUpperCase();
                for (int i = 0; i < totalEntradas; i++) {
                    if (ubicaciones[i].equals(ubi)) {
                        System.out.println("Entrada " + (i + 1) + ": Edad " + edades[i] + ", Precio $" + Math.round(precios[i]));
                    }
                }
            }
            case 3 -> {
                System.out.println("1. Estudiante (menor de 23)");
                System.out.println("2. Tercera edad (60 o más)");
                int tipo = scanner.nextInt();
                scanner.nextLine();
                for (int i = 0; i < totalEntradas; i++) {
                    if ((tipo == 1 && edades[i] < 23) || (tipo == 2 && edades[i] >= 60)) {
                        System.out.println("Entrada " + (i + 1) + ": " + ubicaciones[i] + ", Edad: " + edades[i] + ", Precio: $" + Math.round(precios[i]));
                    }
                }
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    static void eliminarEntrada() {
        if (totalEntradas == 0) {
            System.out.println("No hay entradas para eliminar.");
            return;
        }
           System.out.println("\n===== Entradas disponibles para eliminar =====");
                for (int i = 0; i < totalEntradas; i++) {
                    System.out.println((i + 1) + ". Número: " + (i + 1) +
                            ", Ubicación: " + ubicaciones[i] +
                            ", Edad: " + edades[i] +
                            ", Precio: $" + Math.round(precios[i]));
                }

        System.out.print("Ingrese número de entrada a eliminar: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        if (numero < 1 || numero > totalEntradas) {
            System.out.println("Número inválido.");
            return;
        }

        String tipo = ubicaciones[numero - 1];
        switch (tipo) {
            case "VIP" -> vipDisponibles++;
            case "PLATEA" -> plateaDisponibles++;
            case "GENERAL" -> galeriaDisponibles++;
        }

        for (int i = numero - 1; i < totalEntradas - 1; i++) {
            ubicaciones[i] = ubicaciones[i + 1];
            edades[i] = edades[i + 1];
            precios[i] = precios[i + 1];
        }

        totalEntradas--;
        System.out.println("Entrada eliminada exitosamente.");
    }
}
