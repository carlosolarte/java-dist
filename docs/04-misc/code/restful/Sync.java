/**
 * This is an example of a synchronous version for an HTTPRequest
 */

import java.net.http.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpResponse.*;

public class Sync{
    public static void main(String arg[]){
        // The HTTPClient is created with the URL to be requested 
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://carlosolarte.github.io/java-dist/"))
            .build();

        try{
            // Performing the request and waiting for the response 
            // Here the body will be processed as a simple string
            // This call blocks until the response is obtained 
            HttpResponse<String> response =  client.send(request, BodyHandlers.ofString());

            // 200 is the HTTP code for OK
            if (response.statusCode() == 200){
                // Just printing the result
                System.out.println("[BODY]\n" + response.body());
            }
            else{
                // This is the case when the page was not found
                System.out.println("[HTTPStatus] response " + response.statusCode());
            }
        }
        catch (IOException | InterruptedException E){
            E.printStackTrace();
        }
    }
}
