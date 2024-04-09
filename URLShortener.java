import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Scanner;

public class URLShortener {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the long URL:");
            String longUrl = scanner.nextLine();

            String shortUrl = shortenURL(longUrl);
            System.out.println("Shortened URL: " + shortUrl);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static String shortenURL(String longUrl) throws IOException, URISyntaxException {
        String apiUrl = "https://tinyurl.com/api-create.php";

        // Construct the request URL with parameters using URI
        URI uri = new URI(apiUrl + "?url=" + URLEncoder.encode(longUrl, "UTF-8"));

        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString().trim(); // TinyURL service returns the shortened URL directly
        } finally {
            conn.disconnect();
        }
    }
}
