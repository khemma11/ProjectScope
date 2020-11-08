package am.itspace.projectscope.controller;

import am.itspace.projectscope.model.User;
import am.itspace.projectscope.model.UserType;
import am.itspace.projectscope.repository.UserRepository;
import am.itspace.projectscope.security.CurrentUser;
import am.itspace.projectscope.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Value("${file.upload.dir}")
    private String uploadDir;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String main() {
        return "home";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws IOException {
        Optional<User> byUsername = userService.findByEmail(user.getEmail());
        if (byUsername.isPresent()) {
            return "redirect:/";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String profilePic = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File image = new File(uploadDir, profilePic);
        file.transferTo(image);
        user.setProfilePicture(profilePic);
        userService.save(user);
        return "home";
    }

    @GetMapping("/successLogin")
    public String successLogin(Model model,@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        User user = currentUser.getUser();
        if (user.getUsertype() == UserType.TEAM_LEADER) {
            return "redirect:/teamLider";
        } else {
            model.addAttribute("user", user);
            return "team_member";
        }
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }


    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@RequestParam("name") String imageName) throws IOException {

        InputStream in = new FileInputStream(uploadDir + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }

}
