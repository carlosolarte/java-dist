/**
 * This is an example of an asynchronous HTTP request
 * Requested data is a JSON format
 */
import java.net.http.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpResponse.*;
import java.util.concurrent.CompletableFuture;

public class Covid{
    public static void main(String arg[]){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .header("Accept", "application/json")
            .uri(URI.create("https://restcountries.com/v3.1/all?fields=name"))
            .build();

        // sendAsync returns a CompletableFuture<T>
        // Hence, this call does not block
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString()) ;

        response
            .thenApply(HttpResponse::body) // When ready, extract the body
            .thenAccept(System.out::println) // for the resulting string, print it
            .join(); // Returns when completed 
    }
}
