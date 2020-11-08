package am.itspace.projectscope.rest;

import am.itspace.projectscope.model.User;
import am.itspace.projectscope.model.UserType;
import am.itspace.projectscope.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
public class UserEndpoint {

    @Value("{file.upload.dir}")
    private String imageUrl;


    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/byType/")
    public List<User> getUsersByUserType(@RequestParam UserType userType) {
        return userService.getAllByUserType(userType);
    }

    @PutMapping("/image/{userId}")
    public void uploadImage(@PathVariable("userId") Long userId,
                            @RequestParam("image") MultipartFile file) throws IOException {
        userService.uploadUserImage(userId, file);
    }

    @GetMapping(value = "/get/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) throws IOException {
        InputStream in = new FileInputStream(imageUrl + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }
}
