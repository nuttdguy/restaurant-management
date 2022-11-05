package com.restaurant.service;

import com.restaurant.domain.model.Photo;
import com.restaurant.domain.model.PhotoTag;
import com.restaurant.domain.model.Restaurant;
import com.restaurant.domain.model.License;
import com.restaurant.exception.FileFormatException;
import com.restaurant.repository.IImageRepo;
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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class DocumentService {

    private final IImageRepo imageRepo;
    private final ISafetyLicenseRepo safetyLicense;
    private final IRestaurantRepo restaurantRepo;

    public Object saveLicense(MultipartFile licenseDocument, UUID restaurantId) throws IOException {
        log.trace("Document Service - saveLicense");

        String fileName = licenseDocument.getOriginalFilename();
        if (fileName == null ) {
            throw new FileFormatException("File was not attached or was not acceptable");
        }

        log.trace("file not null, do file extension validation");
        String[] fileParts = fileName.split("\\.");

        if (fileParts.length > 0 ) {
            String ext = fileParts[fileParts.length - 1].toLowerCase().trim();
            log.trace("file name {} :: ext {}", fileName, ext);

            if (!ext.equals("pdf") && !ext.equals("doc") && !ext.equals("docx")) {
                throw new FileFormatException("Unacceptable document format - please try again with .pdf, .doc or .docx file");
            }
        }


        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(format("Restaurant %s not found", restaurantId)));

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


    public String saveImage(MultipartFile imageFile, UUID restaurantId) throws IOException {

        log.trace("Document Service - saveImage");
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found - cannot save image"));

        imageRepo.save(Photo.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .photoTag(PhotoTag.DISH)
                .restaurant(restaurant)
                .file(FileUtil.compressData(imageFile.getBytes())).build());

       log.trace("File was saved successfully");
       return "File was successfully saved";
    }

    public Set<Photo> getImageByRestaurantId(UUID restaurantId) {
        log.trace("Document Service - getImageByRestaurantId {}", restaurantId);
        Set<Photo> photos = imageRepo.findByRestaurantUuid(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        format("Did not find any photos for restaurant id %s", restaurantId)));

        log.trace("decompressing photos");
        return photos.stream().map(photo -> Photo.builder()
                .id(photo.getId())
                .name(photo.getName())
                .type(photo.getType())
                .photoTag(photo.getPhotoTag())
                .restaurant(photo.getRestaurant())
                .file(FileUtil.decompressData(photo.getFile()))
                .build()).collect(Collectors.toSet());
    }


    public Photo getByPhotoName(String imageName) {
        return imageRepo.findByName(imageName)
                .orElseThrow(() -> new EntityNotFoundException(format("%s not found", imageName)));
    }


}
