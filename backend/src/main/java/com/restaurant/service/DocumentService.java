package com.restaurant.service;

import com.restaurant.domain.model.Photo;
import com.restaurant.domain.dto.enums.PhotoType;
import com.restaurant.domain.model.Restaurant;
import com.restaurant.domain.model.License;
import com.restaurant.exception.FileFormatException;
import com.restaurant.repository.IPhotoRepo;
import com.restaurant.repository.IRestaurantRepo;
import com.restaurant.repository.ISafetyLicenseRepo;
import com.restaurant.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static com.restaurant.exception.ExceptionMessage.*;
import static java.lang.String.format;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class DocumentService {

    private final IPhotoRepo photoRepo;
    private final ISafetyLicenseRepo safetyLicense;
    private final IRestaurantRepo restaurantRepo;

    public Object saveLicense(MultipartFile licenseDocument, UUID restaurantId) throws IOException {
        log.trace("Document Service - saveLicense");

        String fileName = licenseDocument.getOriginalFilename();
        if (fileName == null ) {
            log.error("File was not attached or was not acceptable");
            throw new FileFormatException(format(NOT_ACCEPTABLE_FORMAT, "File"));
        }


        String[] fileParts = fileName.split("\\.");
        if (fileParts.length > 0 ) {
            String ext = fileParts[fileParts.length - 1].toLowerCase().trim();
            log.trace("file name {} :: ext {}", fileName, ext);

            if (!ext.equals("pdf") && !ext.equals("doc") && !ext.equals("docx")) {
                log.error("File was not attached or was not acceptable");
                throw new FileFormatException(format(REQUIRED_FILE_FORMAT, ".pdf, .doc or .docx file"));
            }
        }


        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(format(NOT_FOUND, restaurantId)));

        log.trace("Saving the license agreement");
        safetyLicense.save(License.builder()
                .name(licenseDocument.getName())
                .type(licenseDocument.getContentType())
                .restaurant(restaurant)
                .file(FileUtil.compressData(licenseDocument.getBytes()))
                .build());

        log.trace("{} was saved successfully", licenseDocument.getOriginalFilename());
        return format("%s was successfully saved", licenseDocument.getName());
    }


    public String savePhoto(MultipartFile photoFile, UUID restaurantId, String photoResourceUri) throws IOException {
        log.trace("Document Service - savePhoto");

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(format(NOT_FOUND, restaurantId)));

        Photo photo = processPhoto(photoFile, PhotoType.PRIMARY, photoResourceUri);
        photo.setRestaurant(restaurant);

        photoRepo.save(photo);
        log.trace("File was saved successfully");
        return "File was successfully saved";
    }

    public Photo processPhoto(MultipartFile photoFile, PhotoType photoType, String photoResourceUri) throws IOException {
        log.trace("DocumentService - processPhoto {}", photoFile);
        String fileURL = UUID.randomUUID().toString();
        String fileType = photoFile.getContentType();

        return Photo.builder()
                .name(photoFile.getOriginalFilename())
                .type(photoFile.getContentType())
                .photoType(photoType)
                .photoUrl(format("%s/%s.%s", photoResourceUri,
                        fileURL, fileType == null ? ".png" : fileType.split("/")[1]))
                .file(FileUtil.compressData(photoFile.getBytes()))
                .build();
    }

    public Photo getImageById(Long imageId) {
        log.trace("Document Service - getImageById {}", imageId);

        return photoRepo.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(
                        format("Did not find any photos for restaurant id %s", imageId)));
    }

    public Photo getByPhotoUrl(String photoUrl) {
        log.trace("Document Service - getByPhotoUrl {}", photoUrl);

        return photoRepo.findByPhotoUrlContains(photoUrl)
                .orElseThrow(() -> new EntityNotFoundException(format("%s not found", photoUrl)));
    }

}
