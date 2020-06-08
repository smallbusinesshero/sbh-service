package com.diconiumwvv.storesservice;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductImageUploadCommand;
import io.sphere.sdk.products.queries.ProductByKeyGet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UploadService {

    private static final String SHOP_IMAGES_PRODUCT_KEY = "ShopImages";
    @Resource
    private BlockingSphereClient client;

    public String uploadToProductAndGetUrl(MultipartFile shopOwnerImage) throws IOException {
        Product product = getProductByKey(SHOP_IMAGES_PRODUCT_KEY);
        final String fileName = UUID.randomUUID().toString().substring(0, 12);
        Product updatedProduct = uploadImageToProduct(shopOwnerImage, product, fileName);
        final Image uploadedImage = getUploadedImage(fileName, updatedProduct);
        return uploadedImage.getUrl();
    }

    public Product uploadImageToProduct(MultipartFile image, Product product, String fileName) throws IOException {
        final ProductImageUploadCommand uploadCommand = getImageUploadCommand(image, product, fileName);
        return client.executeBlocking(uploadCommand);
    }

    private Image getUploadedImage(String fileName, Product updatedProduct) {
        return updatedProduct.getMasterData().getStaged().getMasterVariant()
        .getImages().stream()
        .filter(image -> image.getUrl().contains(fileName))
        .findFirst().get();
    }

    private ProductImageUploadCommand getImageUploadCommand(MultipartFile shopOwnerImage, Product product, String fileName) throws IOException {
        return ProductImageUploadCommand
                .ofMasterVariant(getFile(shopOwnerImage), product.getId())
                .withFilename(fileName)
                .withStaged(true);
    }

    private File getFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private Product getProductByKey(String productKey) {
        final ProductByKeyGet request = ProductByKeyGet.of(productKey);
        return client.executeBlocking(request);
    }
}

