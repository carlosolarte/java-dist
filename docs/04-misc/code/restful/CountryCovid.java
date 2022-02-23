/**
 * This is an example of an asynchronous HTTP request
 * Requested data is in JSON format
 * Also a parallel operation is used to retrieve several data
 * Jackson (https://github.com/FasterXML/jackson) library is used to manipulate JSON objects
 * Compile and run with -cp  .:./jackson-annotations-2.13.1.jar:./jackson-core-2.13.1.jar:./jackson-databind-2.13.1.jar:./gson-2.9.0.jar
 * Also gson (although only one would be enough)
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

class Country{
    private String name;
    private String capital;
    private String alpha3Code;

    public String toString(){
        return this.name + " (" + this.alpha3Code + ")";
    }

    public String getCode(){
        return this.alpha3Code;
    }
}

public class CountryCovid{
    public static void main(String arg[]){
        // The HTTPClient is created with the URL to be requested 
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .header("Accept", "application/json")
            .uri(URI.create("https://restcountries.com/v2/all?fields=name,capital,alpha3Code"))
            .build();

        // sendAsync returns a CompletableFuture<T>
        // Hence, this call does not block
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString()) ;
        String json = response.thenApply(HttpResponse::body).join();

        Type collectionType = new TypeToken<Collection<Country>>(){}.getType();
        Gson gson = new Gson();
        Collection<Country> countries = gson.fromJson(json, collectionType);

        try{
            for (Country c : countries){
                request = HttpRequest.newBuilder()
                    .header("Accept", "application/json")
                    .uri(URI.create("https://covid-19-data.herokuapp.com/api/cases/" + c.getCode()))
                    .build();
                response = client.sendAsync(request, BodyHandlers.ofString()) ;
                json = response.thenApply(HttpResponse::body).join();
                try{
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(json);
                    //
                    JsonNode locatedNode = rootNode.at("/data/cases/deaths");
                    System.out.println( c.toString() + ": " + locatedNode.asInt());
                }
                catch(Exception E){
                }
            }

        }
        catch(Exception E){
            E.printStackTrace();
        }

    }
}
