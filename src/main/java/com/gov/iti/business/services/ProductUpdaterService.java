package com.gov.iti.business.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.utils.AzureStorage;
import com.gov.iti.persistence.daos.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Optional;

public class ProductUpdaterService {
    private final EntityManagerFactory entityManagerFactory;
    private final ProductDao productDao = ProductDao.getInstance();
    String connectionString = AzureStorage.CONNECTION_STRING;
    String containerName = AzureStorage.CONTAINER_NAME;

    public ProductUpdaterService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void updateProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        productDao.update(entityManager, product);

        transaction.commit();
        entityManager.close();
    }

    public Product findProductByName (String productName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return productDao.getProductByName(productName, entityManager);
    }

    public void addProduct (Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        productDao.create(entityManager, product);

        transaction.commit();
        entityManager.close();
        System.out.println("Product added successfully.");
    }

    public void deleteProductByName (String productName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Product productByName = productDao.getProductByName(productName, entityManager);
        System.out.println(productByName.getId());
        productDao.deleteById(entityManager, productByName.getId());
//        entityManager.remove(productByName);
        System.out.println("Product deleted successfully.");

        // Azure Blob Storage Configuration
        String blobName = productName.toUpperCase() + ".jpg";

        // Create a BlobContainerClient
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();

        // Delete image from Azure Blob Storage
        try {
            blobContainerClient.getBlobClient(blobName).delete();
            System.out.println("Image deleted successfully from Azure Blob Storage.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        transaction.commit();
        entityManager.close();
    }

    public void saveImageToAzure(Product product, String base64Image) {
        // Decode Base64 string to byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);


        String blobName = product.getProductName().toUpperCase() + ".jpg";

        // Create a BlobContainerClient
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();

        // Upload image to Azure Blob Storage
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
            blobClient.upload(bis, imageBytes.length);
            System.out.println("Image uploaded successfully to Azure Blob Storage.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
