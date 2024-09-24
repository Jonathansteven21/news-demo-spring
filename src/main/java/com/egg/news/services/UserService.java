package com.egg.news.services;

import com.egg.news.entities.Image;
import com.egg.news.entities.User;
import com.egg.news.enums.Role;
import com.egg.news.exceptions.UserInputException;
import com.egg.news.repositories.UserRepository;
import com.egg.news.utils.UserValidationUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ImageService imageService;

    @Autowired
    public UserService(UserRepository userRepository, ImageService imageService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @Transactional
    public void register(MultipartFile file, String name, String email, String password, String password2) throws UserInputException {
        UserValidationUtil.validateUser(name, email, password, password2);

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(Role.USER);

        Image image = imageService.saveImage(file);

        user.setImage(image);

        userRepository.save(user);
    }

    @Transactional
    public void update(MultipartFile file, String idUser, String name, String email, String password, String password2) throws UserInputException {
        UserValidationUtil.validateUser(name, email, password, password2);


        Optional<User> optionalUser = userRepository.findById(idUser);
        User user = optionalUser.orElseThrow(() -> new UserInputException("User ID not found"));


        user.setName(name);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(Role.USER);

        String idImage = user.getImage() != null ? user.getImage().getId() : null;
        Image image = imageService.updateImage(file, idImage);

        user.setImage(image);

        userRepository.save(user);
    }

    public User getOne(String id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user1 = userRepository.searchByEmail(email);

        List<GrantedAuthority> permissions = new ArrayList<>();

        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user1.getRole().toString());

        permissions.add(p);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("usersession", user1);

        return new org.springframework.security.core.userdetails.User(user1.getEmail(), user1.getPassword(), permissions);
    }
}
