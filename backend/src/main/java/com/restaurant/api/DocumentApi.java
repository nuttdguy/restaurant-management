package com.restaurant.api;

import com.restaurant.domain.model.Photo;
import com.restaurant.domain.model.RoleType;
import com.restaurant.service.DocumentService;
import com.restaurant.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class DocumentApi {

    private final DocumentService documentService;

    @PostMapping("/upload/license")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> uploadLicense(@RequestPart("license") MultipartFile licenseDocument,
                                                @RequestPart("restaurantId") String restaurantId) throws IOException {
        log.trace("DocumentApi - uploadLicense");
        log.trace("{} ", licenseDocument);
        return ResponseEntity.ok().body(documentService.saveLicense(licenseDocument, UUID.fromString(restaurantId)));
    }

    @PostMapping(value = "/upload/image")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> uploadImage(@RequestPart("image") MultipartFile imageFile,
                                              @RequestPart("restaurantId") String restaurantId,
                                              HttpServletRequest request) {
        log.trace("DocumentApi - uploadImage");



        try {
            return ResponseEntity.ok(documentService.savePhoto(imageFile, UUID.fromString(restaurantId)));
        } catch (IOException ex) {
            log.trace(ex.getLocalizedMessage());
        }
        return ResponseEntity.badRequest().body("File could not be saved");
    }

//    @GetMapping("/download/image/{imageId}")
//    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
//    public ResponseEntity<Object> downloadPhoto(@PathVariable("imageId") Long imageId) {
//        log.trace("DocumentApi - downloadPhoto");
//
//        Set<Photo> photos = documentService.getImageById(imageId);
//        return ResponseEntity.ok().body(photos);
//    }


    @GetMapping("/download/images/id/{restaurantId}")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getPhotosByRestaurantId(@PathVariable("restaurantId") UUID restaurantId) {
        log.trace("DocumentApi - getImageByRestaurantId");

        Set<Photo> photos = documentService.getImageByRestaurantId(restaurantId);
        return ResponseEntity.ok().body(photos);
    }

    @GetMapping("/download/images/name/{imageName}")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getPhotoByName(@PathVariable("imageName") String imageName) {
        log.trace("DocumentApi - getImageByRestaurantName");

        Photo photo = documentService.getByPhotoName(imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(photo.getType()))
                .body(FileUtil.decompressData(photo.getFile()));
    }


}
