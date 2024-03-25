package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.business.dtos.ProductDTO;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.services.ProductUpdaterService;
import com.gov.iti.business.utils.ByteArrayToBase64TypeAdapter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobClient;
import java.io.ByteArrayInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Base64;

@WebServlet(urlPatterns = "/addProduct")
public class ProductAdderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ProductAdderServlet.doPost");
        BufferedReader reader = req.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        // Parse JSON into ProductDTO object
        ProductDTO productDTO = parseJsonToProduct(jsonBody.toString());

//        System.out.println(jsonBody.toString());

        Product product = new Product();
        product.setProductName(productDTO.getProductName().toUpperCase());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());

        System.out.println("Product: " + product.toString());

        String imageBase64Encoded = productDTO.getProductImage();
        imageBase64Encoded = imageBase64Encoded.split(",")[1];
        System.out.println("Image: " + imageBase64Encoded);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ecommerce");
        ProductUpdaterService productUpdaterService = new ProductUpdaterService(emf);

        productUpdaterService.saveImageToAzure(product, imageBase64Encoded);
        productUpdaterService.addProduct(product);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private ProductDTO parseJsonToProduct(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(byte[].class, new ByteArrayToBase64TypeAdapter())
                .create();
        return gson.fromJson(json, ProductDTO.class);
    }
}
