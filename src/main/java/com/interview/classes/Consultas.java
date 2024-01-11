package com.interview.classes;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author jadag
 */
public class Consultas {

    public HttpResponse<String> obtenerDatos(String url) throws IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url)) // Replace with your API URL
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (URISyntaxException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public HttpResponse<String> actualizarProducto(String url, String strJson){
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(strJson))
            .build();

        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = responseFuture.get(); // Espera y obtiene la respuesta
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: \n" + response.body());
            JOptionPane.showMessageDialog(null, "Actualización existosa");
            return response;
        } catch (InterruptedException e) {
            System.out.println("Request was interrupted");
            JOptionPane.showMessageDialog(null, "Request was interrupted");
            Thread.currentThread().interrupt();
            return null;
        } catch (ExecutionException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Exception occurred: " + e.getMessage());
            return null;
        }
    }
}
