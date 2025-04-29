package sumativasiete;

import java.util.ArrayList;
import java.util.Scanner;



public class SumativaSiete {
    static int totalEntradasVendidas = 0;
    static int ingresosTotales = 0;
    static int entradasVIPDisponibles = 10;
    static int entradasPlateaDisponibles = 20;
    static int entradasBalconDisponibles = 30;

    static ArrayList<String> ubicaciones = new ArrayList<>();
    static ArrayList<Integer> preciosBase = new ArrayList<>();
    static ArrayList<Integer> descuentosAplicados = new ArrayList<>();
    static ArrayList<Integer> costosFinales = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    venderEntrada(sc);
                    break;
                case 2:
                    verResumenVentas();
                    break;
                case 3:
                    generarBoleta();
                    break;
                case 4:
                    calcularIngresosTotales();
                    break;
                case 5:
                    System.out.println("\nGracias por su compra.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);

        sc.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n--- Teatro Moro - Venta de Entradas ---");
        System.out.println("1. Vender Entrada");
        System.out.println("2. Ver Resumen de Ventas");
        System.out.println("3. Generar Boleta");
        System.out.println("4. Calcular Ingresos Totales");
        System.out.println("5. Salir");
    }

    public static void venderEntrada(Scanner sc) {
        System.out.println("\nSeleccione zona:");
        System.out.println("1. VIP ($100)");
        System.out.println("2. Platea ($70)");
        System.out.println("3. Balcón ($50)");
        int tipoZona = sc.nextInt();

        String ubicacion = "";
        int precioBase = 0;

        // Variables locales
        int descuento = 0;
        int costoFinal = 0;
        int edad;
        String esEstudiante;

        switch (tipoZona) {
            case 1:
                if (entradasVIPDisponibles <= 0) {
                    System.out.println("No hay entradas VIP disponibles.");
                    return;
                }
                ubicacion = "VIP";
                precioBase = 100;
                entradasVIPDisponibles--;
                break;
            case 2:
                if (entradasPlateaDisponibles <= 0) {
                    System.out.println("No hay entradas Platea disponibles.");
                    return;
                }
                ubicacion = "Platea";
                precioBase = 70;
                entradasPlateaDisponibles--;
                break;
            case 3:
                if (entradasBalconDisponibles <= 0) {
                    System.out.println("No hay entradas Balcón disponibles.");
                    return;
                }
                ubicacion = "Balcón";
                precioBase = 50;
                entradasBalconDisponibles--;
                break;
            default:
                System.out.println("Zona inválida.");
                return;
        }

        // Preguntar datos para descuentos
        System.out.print("Ingrese su edad: ");
        edad = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        // Aplicar descuentos
        if (edad >= 60) {
            System.out.println("\nTiene un 15% de descuento por ser Tercera edad.");
            descuento = (int)Math.round(precioBase * 0.15);
        } else if (edad < 23) {
            System.out.println("\nTiene un 10% de descuento por ser Estudiante.");
            descuento = (int)Math.round(precioBase * 0.10);
        } else {
            System.out.println("\nNo tiene descuento");
            descuento = 0;
        }

        costoFinal = precioBase - descuento;

        // Guardar en las listas
        ubicaciones.add(ubicacion);
        preciosBase.add(precioBase);
        descuentosAplicados.add(descuento);
        costosFinales.add(costoFinal);

        totalEntradasVendidas++;
        ingresosTotales += costoFinal;

        System.out.println("\nEntrada vendida exitosamente.");
    }

    public static void verResumenVentas() {
        System.out.println("\n--- Resumen de Ventas ---");
        System.out.println("Total entradas vendidas: " + totalEntradasVendidas);
        System.out.println("Entradas VIP disponibles: " + entradasVIPDisponibles);
        System.out.println("Entradas Platea disponibles: " + entradasPlateaDisponibles);
        System.out.println("Entradas Balcón disponibles: " + entradasBalconDisponibles);
    }

    public static void generarBoleta() {
        System.out.println("\n------------------------------------");
        System.out.println("          Teatro Moro               ");
        System.out.println("------------------------------------");
        for (int i = 0; i < ubicaciones.size(); i++) {
            System.out.println("Entrada #" + (i+1));
            System.out.println("Ubicación: " + ubicaciones.get(i));
            System.out.println("Precio Base: $" + preciosBase.get(i));
            System.out.println("Descuento Aplicado: $" + descuentosAplicados.get(i));
            System.out.println("Costo Final: $" + costosFinales.get(i));
            System.out.println("------------------------------------");
            System.out.println("Gracias por su visita al Teatro Moro");
            System.out.println("------------------------------------");
        }
    }

    public static void calcularIngresosTotales() {
        System.out.println("\n--- Ingresos Totales ---");
        System.out.println("Ingresos acumulados: $" + ingresosTotales);
    }
}
