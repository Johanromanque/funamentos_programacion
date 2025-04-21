package sumativaseis;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class SumativaSeis {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Variables de instancia 
        String[] ubicaciones = new String[100];
        int[] edades = new int[100];
        double[] precios = new double[100];
        String[] clientes = new String[100];

        // Variables estáticas 
        int entradasVendidas = 0;
        double ingresosTotales = 0;
        final int[] reservas = {0};
        int descuentosAplicados = 0; 

        // Variables locales temporales
        String ubicacion, tipoCliente;
        int edad, asiento = 0;
        double precioBase, precioFinal, descuento;

        boolean continuar = true;
        
        // Menu de ventas
        while (continuar) {
            System.out.println("\n=== TEATRO MORO ===");
            System.out.println("1. Reservar entrada");
            System.out.println("2. Comprar entrada");
            System.out.println("3. Modificar una venta");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer
            if (opcion == 1) {
                System.out.println("\n--- RESERVA DE ENTRADA ---");

                System.out.print("\n" + "Ingrese numero de asiento (1-100): ");
                asiento = sc.nextInt();// 1 breakpoint, verificar que el numero de asiento este dentro del (1-100).
                sc.nextLine(); 

                if (asiento < 1 || asiento > 100) {
                    System.out.println("Asiento invalido.");
                } else if (ubicaciones[asiento - 1] != null) {
                    System.out.println("Asiento ya reservado o comprado.");
                } else {
                    ubicaciones[asiento - 1] = "RESERVA"; // 2 breakpoint confirmar registro de reserva del array ubicaciones[].
                    reservas[0]++;

                    System.out.println("\nTienes 20 segundos para confirmar la compra. Escribe 'S' y presiona Enter:\n");
                    
                    // Clase temporizador timer
                    final boolean[] confirmado = {false};
                    final Timer timer = new Timer();
                    final int[] segundosRestantes = {20};

                    int finalAsiento = asiento;
                    TimerTask tarea = new TimerTask() {
                        @Override
                        public void run() {
                            if (segundosRestantes[0] > 0) {
                                System.out.println("Quedan " + segundosRestantes[0] + " segundos...");
                                segundosRestantes[0]--;
                            } else {
                                if (!confirmado[0]) {
                                    ubicaciones[finalAsiento - 1] = null;
                                    reservas[0]--;
                                    System.out.println("\nTiempo agotado. La reserva fue cancelada.");
                                }
                                timer.cancel(); 
                            }
                        }
                    };

                    timer.scheduleAtFixedRate(tarea, 0, 1000);

                    // Escuchar la confirmación del usuario
                    String respuesta = sc.nextLine().toUpperCase();
                    if (respuesta.equals("S")) {
                        confirmado[0] = true;
                        timer.cancel(); 
                        System.out.println("Asiento " + asiento + " reservado exitosamente!");

                        opcion = 2; // Redirige a compra directamente
                    } else {
                        System.out.println("\nReserva no confirmada. Cancelando...");
                        ubicaciones[asiento - 1] = null;
                        reservas[0]--;
                        timer.cancel();
                    }
                }
                
            }
            if (opcion == 2) {
                System.out.println("\n--- COMPRA DE ENTRADA ---");

                System.out.print("Ingrese numero de asiento (1-100): ");
                asiento = sc.nextInt();
                sc.nextLine();

                // Validar que el asiento esté dentro del rango
                if (asiento < 1 || asiento > 100) {
                    System.out.println("Asiento invalido.");
                } else if (ubicaciones[asiento - 1] == null) {
                    System.out.println("\nPrimero debe hacer una reserva para este asiento.");
                } else if (!ubicaciones[asiento - 1].equals("RESERVA")) {
                    System.out.println("El asiento ya fue comprado.");
                } else {
                    System.out.print("Ingrese ubicacion (VIP, PLATEA, GENERAL): ");
                    ubicacion = sc.nextLine().toUpperCase();

                    precioBase = 0;
                    if (ubicacion.equals("VIP")) {
                        precioBase = 15000;
                    } else if (ubicacion.equals("PLATEA")) {
                        precioBase = 12000;
                    } else if (ubicacion.equals("GENERAL")) {
                        precioBase = 10000;
                    } else {
                        System.out.println("Ubicacion invalida.");
                        continue;
                    }

                    System.out.print("Ingrese edad del comprador: ");
                    edad = sc.nextInt();
                    sc.nextLine();

                    descuento = 0;
                    tipoCliente = "Adulto";
                    if (edad >= 60) {
                        descuento = 0.15;
                        tipoCliente = "Tercera Edad";
                        System.out.println("\n-----------------------------------------------------------------");
                        System.out.println("¡Felicidades! Ganaste un tesito por ser parte de la tercera edad.");
                        System.out.println("-----------------------------------------------------------------");
                    } else if (edad < 23) {
                        descuento = 0.10;
                        tipoCliente = "Estudiante";
                        System.out.println("\n---------------------------------------------------");
                        System.out.println("¡Felicidades! Ganaste una bebida por ser estudiante.");
                        System.out.println("---------------------------------------------------");
                    }

                    precioFinal = precioBase - (precioBase * descuento);

                    ubicaciones[asiento - 1] = ubicacion; // cambia "RESERVA" por ubicación final
                    edades[asiento - 1] = edad;
                    precios[asiento - 1] = precioFinal;
                    entradasVendidas++;
                    ingresosTotales += precioFinal;
                    reservas[0]--; // reducir contador de reservas al comprar

                    System.out.println("\nCompra realizada exitosamente.\n");
                    System.out.println("Ubicacion: " + ubicacion);
                    System.out.println("Tipo cliente: " + tipoCliente);
                    System.out.println("Precio final: $" + Math.round(precioFinal));
                }

            } else if (opcion == 3) {
                System.out.println("\n--- MODIFICAR VENTA ---");

                System.out.print("Ingrese numero de asiento (1-100): ");
                asiento = sc.nextInt();
                sc.nextLine();

                if (asiento < 1 || asiento > 100 || ubicaciones[asiento - 1] == null) {
                    System.out.println("No hay venta registrada en ese asiento.");
                } else {
                    System.out.println("Se modificará la compra para el asiento " + asiento);

                    System.out.print("Ingrese nueva edad del cliente: ");
                    edad = sc.nextInt();
                    sc.nextLine();

                    descuento = 0;
                    tipoCliente = "Adulto";
                    if (edad >= 60) {
                        descuento = 0.15;
                        tipoCliente = "Tercera Edad";
                    } else if (edad < 23) {
                        descuento = 0.10;
                        tipoCliente = "Estudiante";
                    }

                    ubicacion = ubicaciones[asiento - 1];
                    if (ubicacion.equals("VIP")) {
                        precioBase = 15000;
                    } else if (ubicacion.equals("PLATEA")) {
                        precioBase = 12000;
                    } else {
                        precioBase = 10000;
                    }

                    precioFinal = precioBase - (precioBase * descuento);

                    // Descontar anterior y sumar nuevo ingreso
                    ingresosTotales -= precios[asiento - 1];
                    ingresosTotales += precioFinal;

                    edades[asiento - 1] = edad;
                    precios[asiento - 1] = precioFinal;

                    System.out.println("Venta modificada correctamente.");
                }

            } else if (opcion == 4) {
                System.out.println("\n--- IMPRIMIR BOLETA ---");

                System.out.print("Ingrese el numero de asiento (1-100): ");
                asiento = sc.nextInt(); // 6 breakpoint: verificacion  que el asiento ingresado sea valido y este comprado.
                sc.nextLine();

                if (asiento < 1 || asiento > 100) {
                    System.out.println("Numero de asiento invalido.");
                } else if (ubicaciones[asiento - 1] == null || ubicaciones[asiento - 1].equals("RESERVA")) {
                    System.out.println("No hay una compra registrada para este asiento.");
                } else {
                    edad = edades[asiento - 1]; // 7 breakpoint: validar datos de edad y precio.
                    precioFinal = precios[asiento - 1];
                    tipoCliente = "Adulto";

                    if (edad >= 60) {
                        tipoCliente = "Tercera Edad";
                    } else if (edad < 23) {
                        tipoCliente = "Estudiante";
                    }

                    System.out.println("\n====================================");
                    System.out.println("          BOLETA TEATRO MORO        ");
                    System.out.println("====================================");
                    System.out.println("Asiento Numero  : " + asiento); // 8 breakpoint: validar que todos los datos de la boleta sean correctos.
                    System.out.println("Ubicacion       : " + ubicaciones[asiento - 1]);
                    System.out.println("Edad del Cliente: " + edad);
                    System.out.println("Tipo de Cliente : " + tipoCliente);
                    System.out.println("Precio Final    : $" + Math.round(precioFinal));
                    System.out.println("====================================");
                }

            } else if (opcion == 5) {
                continuar = false;
                System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
            } else {
                System.out.println("Opcion invalida.");
            }
        }

        sc.close();
    }
}
