/**
 * This is an example of an asynchronous HTTP request
 * Requested data is in JSON format
 * Library https://github.com/google/gson is used
 * Compile and execute with -cp .:/gson-2.9.0.jar
 */
import java.net.http.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.net.http.HttpResponse.*;
import java.util.concurrent.CompletableFuture;
import java.lang.reflect.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.*;

class CountryData{
    private String name;
    private String capital;

    public String toString(){
        return this.name + " - " + this.capital;
    }
}

public class Country{
    public static void main(String arg[]){
        // The HTTPClient is created with the URL to be requested 
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .header("Accept", "application/json")
            .uri(URI.create("https://restcountries.com/v2/all?fields=name,capital"))
            .build();

        // sendAsync returns a CompletableFuture<T>
        // Hence, this call does not block
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString()) ;
        String json = response.thenApply(HttpResponse::body).join();

        Type collectionType = new TypeToken<Collection<CountryData>>(){}.getType();
        Gson gson = new Gson();
        Collection<CountryData> countries = gson.fromJson(json, collectionType);

        System.out.println(countries);
    }
}
