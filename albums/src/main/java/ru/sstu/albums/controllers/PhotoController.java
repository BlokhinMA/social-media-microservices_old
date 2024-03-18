package ru.sstu.albums.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.sstu.albums.models.Photo;
import ru.sstu.albums.models.PhotoComment;
import ru.sstu.albums.models.PhotoRating;
import ru.sstu.albums.models.PhotoTag;
import ru.sstu.albums.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/photo_entity/{id}")
    public ResponseEntity<?> photoEntity(@PathVariable int id) {
        Photo photo = photoService.showEntity(id);
        return ResponseEntity.ok()
                .header("fileName", photo.getOriginalFileName())
                .contentType(MediaType.valueOf(photo.getContentType()))
                .contentLength(photo.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(photo.getBytes())));
    }

    @GetMapping("/photo/{id}")
    public Photo show(@PathVariable int id, Principal principal) {
        return photoService.show(id, principal);
    }

    @DeleteMapping("/delete_photo")
    public void delete(Photo photo, Principal principal) {
        photoService.delete(photo, principal);
    }

    @PostMapping("/add_photo_tag")
    public void createTag(PhotoTag photoTag, Principal principal) {
        photoService.createTag(photoTag, principal);
    }

    @DeleteMapping("/delete_photo_tag")
    public void deleteTag(PhotoTag photoTag, Principal principal) {
        photoService.deleteTag(photoTag, principal);
    }

    @PostMapping("/add_photo_rating")
    public void createRating(PhotoRating photoRating, Principal principal) {
        photoService.createRating(photoRating, principal);
    }

    @PostMapping("/update_photo_rating")
    public void updateRating(PhotoRating photoRating, Principal principal) {
        photoService.updateRating(photoRating, principal);
    }

    @DeleteMapping("/delete_photo_rating")
    public void deleteRating(PhotoRating photoRating, Principal principal) {
        photoService.deleteRating(photoRating, principal);
    }

    @PostMapping("/add_photo_comment")
    public void createComment(PhotoComment photoComment, Principal principal) {
        photoService.createComment(photoComment, principal);
    }

    @DeleteMapping("/delete_photo_comment")
    public void deleteComment(PhotoComment photoComment, Principal principal) {
        photoService.deleteComment(photoComment, principal);
    }

    @GetMapping("/find_photos")
    public void find(String searchTerm, String keyword) {
        photoService.find(searchTerm, keyword);
    }

}
