import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClient {
    private static final String API_KEY = "d43f0fafb3f04435d75d7787"; // <- Reemplaza con tu clave real
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public String getRatesFor(String baseCurrency) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = API_URL + baseCurrency;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Error: " + response);

            return response.body().string();
        }
    }
}
