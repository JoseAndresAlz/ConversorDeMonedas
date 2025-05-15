
import com.google.gson.Gson;
import java.util.*;

public class ConversorMonedasApp {

    private static final List<Moneda> MONEDAS = List.of(
            new Moneda(1, "USD", "Dolar estadounidense"),
            new Moneda(2, "EUR", "Euro"),
            new Moneda(3, "ARS", "Peso argentino"),
            new Moneda(4, "CLP", "Peso chileno"),
            new Moneda(5, "MXN", "Peso mexicano"),
            new Moneda(6, "BRL", "Real brasileño")
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApiClient apiClient = new ApiClient();
        Gson gson = new Gson();

        System.out.println("=== CONVERSOR DE MONEDAS ===");

        while (true) {
            mostrarMenuMonedas();

            System.out.print("Selecciona el numero de la moneda de ORIGEN: ");
            String monedaOrigen = leerMoneda(scanner);

            System.out.print("Selecciona el numero de la moneda de DESTINO: ");
            String monedaDestino = leerMoneda(scanner);

            System.out.print("Ingresa el monto a convertir: ");
            double monto;
            try {
                monto = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Monto inválido. Intenta nuevamente.");
                continue;
            }

            try {
                String json = apiClient.getRatesFor(monedaOrigen);
                ExchangeRateResponse response = gson.fromJson(json, ExchangeRateResponse.class);

                if (!response.conversion_rates.containsKey(monedaDestino)) {
                    System.out.println("⚠️ Moneda destino no soportada por la API.");
                    continue;
                }

                double tasa = response.conversion_rates.get(monedaDestino);
                double resultado = monto * tasa;

                String nombreOrigen = obtenerNombreMoneda(monedaOrigen);
                String nombreDestino = obtenerNombreMoneda(monedaDestino);

                System.out.printf("✅ %.2f %s (%s) equivale a %.2f %s (%s)\n",
                        monto, monedaOrigen, nombreOrigen,
                        resultado, monedaDestino, nombreDestino);

            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }

            System.out.print("\n¿Deseas hacer otra conversion? (s/n): ");
            String opcion = scanner.nextLine().toLowerCase();
            if (opcion.equals("n") || opcion.equals("no")) {
                System.out.println("👋 ¡Gracias por usar el conversor!");
                break;
            }
        }
    }

    private static void mostrarMenuMonedas() {
        System.out.println("\nMonedas disponibles:");
        for (Moneda moneda : MONEDAS) {
            System.out.printf("%d - %s (%s)\n", moneda.numero, moneda.codigo, moneda.nombre);
        }
    }

    private static String leerMoneda(Scanner scanner) {
        int eleccion;
        try {
            eleccion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Entrada inválida. Debes ingresar un número del menú.");
            return leerMoneda(scanner);
        }

        for (Moneda moneda : MONEDAS) {
            if (moneda.numero == eleccion) {
                return moneda.codigo;
            }
        }

        System.out.println("⚠️ Opción no válida. Intenta de nuevo.");
        return leerMoneda(scanner);
    }

    // Clase interna para representar cada moneda
    static class Moneda {

        int numero;
        String codigo;
        String nombre;

        Moneda(int numero, String codigo, String nombre) {
            this.numero = numero;
            this.codigo = codigo;
            this.nombre = nombre;
        }
    }
    
    private static String obtenerNombreMoneda(String codigo) {
    for (Moneda moneda : MONEDAS) {
        if (moneda.codigo.equals(codigo)) {
            return moneda.nombre;
        }
    }
    return "Desconocida";
}

    
}

