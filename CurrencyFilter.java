import java.util.Map;
import java.util.Set;

public class CurrencyFilter {
    private static final Set<String> monedasDeInteres = Set.of("EUR", "ARS", "CLP", "MXN", "BRL");

    public void mostrarMonedas(Map<String, Double> rates) {
        System.out.println("Tasas de cambio respecto a USD:");
        for (String moneda : monedasDeInteres) {
            if (rates.containsKey(moneda)) {
                System.out.println(moneda + ": " + rates.get(moneda));
            }
        }
    }
}
