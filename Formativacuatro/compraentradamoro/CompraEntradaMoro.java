package compraentradamoro;
import java.util.Scanner;

public class CompraEntradaMoro {

    public static void main(String[] args) {
  Scanner teclado = new Scanner(System.in);
        // Precio base de la entrada
        double precioBase = 10000;
        double descuento = 0;

        // Zonas del teatro (1 = disponible, 0 = ocupado)
        int[] filaA = {0, 0, 0};
        int[] filaB = {0, 0, 0};
        int[] filaC = {0, 0, 0};

        boolean continuar = true;

        for (; continuar; ) {// Menu principal
            System.out.println("\n**************************");
            System.out.println("Menu Principal");
            System.out.println("1 - Comprar entrada");
            System.out.println("2 - Salir");
            System.out.print("Seleccione una opción: ");

            if (!teclado.hasNextInt()) {
                System.out.println("Error: debe ingresar un número. Intente nuevamente.");
                teclado.nextLine();
                continue;
            }

            int opcion = teclado.nextInt();
            teclado.nextLine();  // Limpiar buffer
            System.out.println("**************************");

            if (opcion == 1) {
                // Mostrar plano del teatro
                System.out.println("Plano del Teatro:");
                System.out.print("Zona A: ");
                for (int i = 0; i < filaA.length; i++) {
                    System.out.print("[" + filaA[i] + "]");
                }

                System.out.print("\nZona B: ");
                for (int i = 0; i < filaB.length; i++) {
                    System.out.print("[" + filaB[i] + "]");
                }

                System.out.print("\nZona C: ");
                for (int i = 0; i < filaC.length; i++) {
                    System.out.print("[" + filaC[i] + "]");
                }

                // Selección de zona
                System.out.print("\n\nIngrese la zona (A, B, C): ");
                String zona = teclado.nextLine().toUpperCase();
                int[] filaSeleccionada;

                // Asignar fila según zona
                if (zona.equals("A")) {
                    filaSeleccionada = filaA;
                } else if (zona.equals("B")) {
                    filaSeleccionada = filaB;
                } else if (zona.equals("C")) {
                    filaSeleccionada = filaC;
                } else {
                    System.out.println("Zona inválida.");
                    continue;  // Volver al menú
                }

                // Mostrar asientos disponibles en la zona elegida
                System.out.print("Asientos en zona " + zona + ": ");
                for (int i = 0; i < filaSeleccionada.length; i++) {
                    System.out.print("[" + (i + 1) + ":" + filaSeleccionada[i] + "] ");
                }
                System.out.println();

                // Elegir número de asiento (visible como 1,2,3 pero internamente será 0,1,2)
                System.out.print("Seleccione el número del asiento (1,2,3 / 0:Disponible, 1:Ocupado): ");
                if (!teclado.hasNextInt()) {
                    System.out.println("Error: debe ingresar un número. Intente nuevamente.");
                    teclado.nextLine();
                    continue;
                }

                int numeroAsiento = teclado.nextInt();
                teclado.nextLine();
                int indiceAsiento = numeroAsiento - 1; // Convertimos a índice del arreglo

                if (indiceAsiento < 0 || indiceAsiento >= filaSeleccionada.length) {
                    System.out.println("Número de asiento inválido.");
                    continue;
                }
                if (filaSeleccionada[indiceAsiento] == 1) {
                    System.out.println("Ese asiento ya está ocupado. Intente con otro.");
                    continue;
                }

                // Validar si el asiento está disponible
                if (indiceAsiento >= 0 && indiceAsiento < filaSeleccionada.length) {
                    if (filaSeleccionada[indiceAsiento] == 0) {
                        filaSeleccionada[indiceAsiento] = 1; // Marcamos como ocupado

                        // Solicitar edad
                        System.out.print("Ingrese su edad: ");
                        if (!teclado.hasNextInt()) {
                            System.out.println("Error: edad inválida. Intente nuevamente.");
                            teclado.nextLine();
                            continue;
                        }
                        int edad = teclado.nextInt();
                        teclado.nextLine();

                        if (edad <= 0 || edad > 120) {
                            System.out.println("Edad no válida. Intente nuevamente.");
                            continue;
                        }
                        // Aplicar descuentos según la edad
                        if (edad >= 60) {
                            descuento = 0.15;  // Descuento del 15% para personas mayores de 60
                        } else if (edad < 23) {
                            descuento = 0.10;  // Descuento del 10% para personas menores de 23
                        } else {
                            descuento = 0;
                        }
                        double precioFinal = precioBase;
                        int contador = 0;

                        // Aplicar el descuento y calcular el precio final
                        do {
                            precioFinal = precioBase - (precioBase * descuento);
                            contador++;
                        } while (contador < 1);  // Solo se ejecuta una vez

                        // Convertir descuento a porcentaje entero
                        int descuentoAplicado = (int) Math.round(descuento * 100);  // Descuento en porcentaje (15 para 0.15, 10 para 0.10)

                        // Convertir precio base y final a enteros
                        int precioBaseInt = (int) Math.round(precioBase);  // Redondear el precio base a entero
                        int precioFinalInt = (int) Math.round(precioFinal);  // Redondear el precio final a entero

                        // Mostrar resumen de la compra
                        System.out.println("**************************");
                        System.out.println("Resumen de la compra:");
                        System.out.println("Ubicación del asiento: Zona " + zona + ", Asiento " + numeroAsiento);
                        System.out.println("Precio base: $" + precioBaseInt);
                        System.out.println("Descuento aplicado: " + descuentoAplicado + "%");  // Descuento en valor entero
                        System.out.println("Total a pagar: $" + precioFinalInt); // Mostrar el precio final redondeado

                        // Preguntar si desea otra compra
                        System.out.print("\n¿Desea realizar otra compra? (Si/No): ");
                        String respuesta = teclado.nextLine().toUpperCase();
                        if (!respuesta.equalsIgnoreCase("si")) {
                            continuar = false;
                            System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                        }
                    } else {
                        System.out.println("Ese asiento ya está ocupado. Intente con otro.");
                    }
                }
            } else if (opcion == 2) {
                System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                break;
            } else {
                System.out.println("Opción no válida.");
            }
        }
        teclado.close();
    }
}