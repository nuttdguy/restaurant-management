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
import java.util.UUID;

import static java.lang.String.format;


@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class DocumentApi {

    private static final String URL_FORMAT_STRING = "http://%s:%s%s%s";
    private final DocumentService documentService;

    @PostMapping("/upload/license")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> uploadLicense(@RequestPart("license") MultipartFile licenseDocument,
                                                @RequestPart("restaurantId") String restaurantId) throws IOException {
        log.trace("DocumentApi - uploadLicense");

        return ResponseEntity.ok().body(documentService.saveLicense(licenseDocument, UUID.fromString(restaurantId)));
    }

    @PostMapping(value = "/upload/image")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> uploadImage(@RequestPart("image") MultipartFile imageFile,
                                              @RequestPart("restaurantId") String restaurantId,
                                              HttpServletRequest httpServletRequest) {
        log.trace("DocumentApi - uploadImage");

        String photoResourceUri = format(URL_FORMAT_STRING,
                httpServletRequest.getServerName(),
                httpServletRequest.getServerPort(),
                httpServletRequest.getContextPath(),
                "/images");

        try {
            return ResponseEntity.ok(documentService.savePhoto(imageFile, UUID.fromString(restaurantId), photoResourceUri));
        } catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
        }
        return ResponseEntity.badRequest().body("File could not be saved");
    }


    @GetMapping("/images/id/{imageId}")
    public ResponseEntity<Object> getPhotoById(@PathVariable("imageId") Long imageId) {
        log.trace("DocumentApi - getPhotoById");

        return ResponseEntity.ok().body(documentService.getImageById(imageId));
    }

    @GetMapping("/images/{photoUrl}")
    public ResponseEntity<Object> getPhotoByUrl(@PathVariable("photoUrl") String photoUrl) {
        log.trace("DocumentApi - getPhotoByUrl");

        Photo photo = documentService.getByPhotoUrl(photoUrl);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(photo.getType()))
                .body(FileUtil.decompressData(photo.getFile()));
    }


}
