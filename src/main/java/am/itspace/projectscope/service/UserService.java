package am.itspace.projectscope.service;

import am.itspace.projectscope.model.User;
import am.itspace.projectscope.model.UserType;
import am.itspace.projectscope.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Value("{file.upload.dir}")
    private String imageUrl;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllByUserType(UserType userType) {

        List<User> users = userRepository.findAllByUserType(userType);
        if (userType == UserType.TEAM_MEMBER) {
            return users;
        }
        return null;
    }

    public void uploadUserImage(Long userId, MultipartFile file) throws IOException {
        String profilePic = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File image = new File(imageUrl, profilePic);
        file.transferTo(image);
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setProfilePicture(profilePic);
            userRepository.save(user);
        }
    }
}
