/**
 * This is an example of an asynchronous HTTP request
 */
import java.io.*;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;

public class Async{
    public static void main(String arg[]){
        // The HTTPClient is created with the URL to be requested 
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://carlosolarte.github.io/java-dist/"))
            .build();

        // sendAsync returns a CompletableFuture<T>
        // Hence, this call does not block
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()) ;
        System.out.println("After the call");

        response
            .thenApply(HttpResponse::body) // When ready, extract the body
            .thenAccept(System.out::println) // for the resulting string, print it
            .join(); // Returns when completed 
    }
}
