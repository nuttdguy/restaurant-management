package com.restaurant.api;

import com.restaurant.domain.model.Image;
import com.restaurant.service.DocumentService;
import com.restaurant.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class DocumentApi {

    private final DocumentService documentService;

    @PostMapping("/license/upload")
    public ResponseEntity<Object> uploadLicense(@RequestParam("license") MultipartFile licenseDocument,
                                                @RequestParam("restaurantId") UUID restaurantId) throws IOException {
        log.trace("DocumentApi - uploadLicense");
        log.trace("{} ", licenseDocument);
        return ResponseEntity.ok().body(documentService.saveLicense(licenseDocument, restaurantId));
    }

    @PostMapping("/image/upload")
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile imageFile,
                                              @RequestParam("restaurantId") UUID restaurantId) {
        log.trace("DocumentApi - uploadImage");
        try {
            return ResponseEntity.ok(documentService.saveImage(imageFile, restaurantId));
        } catch (IOException ex) {
            log.trace(ex.getLocalizedMessage());
        }
        return ResponseEntity.badRequest().body("File could not be saved");
    }

    @GetMapping("/images/{restaurantId}")
    public ResponseEntity<Object> getImageByRestaurantId(@PathVariable("restaurantId") UUID restaurantId) {
        log.trace("DocumentApi - getImageByRestaurantId");

        return ResponseEntity.ok(documentService.getImageByRestaurantId(restaurantId));
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Object> getImageByRestaurantName(@PathVariable("imageName") String imageName) {
        log.trace("DocumentApi - getImageByRestaurantName");

        Image image = documentService.getImageByName(imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(FileUtil.decompressData(image.getImageBytes()));
    }


}
