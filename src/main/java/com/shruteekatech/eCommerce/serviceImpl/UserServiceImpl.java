package com.shruteekatech.eCommerce.serviceImpl;

import com.shruteekatech.eCommerce.utills.Login;
import com.shruteekatech.eCommerce.dto.UserDto;
import com.shruteekatech.eCommerce.entity.User;
import com.shruteekatech.eCommerce.exception.ResourceNotFoundException;
import com.shruteekatech.eCommerce.repository.UserRepo;
import com.shruteekatech.eCommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Start : createUser() started from UserServiceImpl");
        User user = this.modelMapper.map(userDto, User.class);
        user.setActive(true);
        // encoding password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User save = this.userRepo.save(user);
        UserDto savedUser = this.modelMapper.map(save, UserDto.class);
        logger.info("Complete : createUser() completed from UserServiceImpl");
        return savedUser;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        logger.info("Start : updateUser() started from UserServiceImpl");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id"));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAddress(userDto.getAddress());
        user.setAbout(userDto.getAbout());
        user.setActive(true);

        User update = this.userRepo.save(user);
        UserDto updatedUser = this.modelMapper.map(update, UserDto.class);
        logger.info("Complete : updateUser() completed from UserServiceImpl");
        return updatedUser;
    }

    @Override
    public void deleteUser(Long userId) {
        logger.info("Start : deleteUser() started from UserServiceImpl");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id"));
        this.userRepo.delete(user);
        logger.info("Complete : deleteUser() completed from UserServiceImpl");
    }

    @Override
    public UserDto findUserById(Long userId) {
        logger.info("Start : findUserById() started from UserServiceImpl");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id"));
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        logger.info("Complete : deleteUser() completed from UserServiceImpl");
        return userDto;
    }

    @Override
    public List<UserDto> findAllUser() {
        logger.info("Start : findAllUser() started from UserServiceImpl");
        List<User> userList = this.userRepo.findAll();
        List<UserDto> allUserDtos = userList.stream().map((users) -> this.modelMapper
                            .map(users, UserDto.class)).collect(Collectors.toList());
        logger.info("Complete : findAllUser() completed from UserServiceImpl");
        return allUserDtos;
    }

    // findBy methods implementations
    @Override
    public List<UserDto> findByName(String name) {
        logger.info("Start : findByName() started from UserServiceImpl");
        List<User> byName = this.userRepo.findByName(name).orElseThrow(()->new ResourceNotFoundException("User","Name"));
        List<UserDto> userDtoList = byName.stream().map((list) -> this.modelMapper.map(list, UserDto.class)).collect(Collectors.toList());
        logger.info("Complete : findByName() completed from UserServiceImpl");
        return userDtoList;
    }

    @Override
    public UserDto findByEmail(String email) {
        logger.info("Start : findByEmail() started from UserServiceImpl");
        User byEmail = this.userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","Email or UserName"));
        UserDto userDto = this.modelMapper.map(byEmail, UserDto.class);
        logger.info("Complete : findByEmail() completed from UserServiceImpl");
        return userDto;
    }

    @Override
    public UserDto findByPhone(String phone) {
        logger.info("Start : findByPhone() started from UserServiceImpl");
        User byPhone = this.userRepo.findByPhone(phone).orElseThrow(()->new ResourceNotFoundException("User","Phone Number"));
        UserDto userDto = this.modelMapper.map(byPhone, UserDto.class);
        logger.info("Complete : findByPhone() completed from UserServiceImpl");
        return userDto;
    }

    @Override
    public boolean loginCheck(Login login){
        logger.info("Start : loginCheck() started from UserServiceImpl");
        String email = login.getEmail();
        String password = login.getPassword();
        User byEmailAndPassword = this.userRepo.findByEmailAndPassword(email, password).orElseThrow(()->new ResourceNotFoundException("User","Email / Password"));
        if(byEmailAndPassword != null)
        {
            logger.info("Complete : loginCheck() completed from UserServiceImpl");
            return true;
        }
        return false;
    }

    @Override
    public List<UserDto> findByNameContaining(String infix) {
        logger.info("Start : findByNameContaning() started from UserServiceImpl");
        List<User> nameContaning = this.userRepo.findByNameContaining(infix).orElseThrow(()->new ResourceNotFoundException("User","Words"));
        List<UserDto> userDtos = nameContaning.stream().map((list) -> this.modelMapper.map(list, UserDto.class)).collect(Collectors.toList());
        logger.info("Complete : findByNameContaning() completed from UserServiceImpl");
        return userDtos;
    }

    @Override
    public List<UserDto> findByNameStartingWith(String prefix) {
        logger.info("Start : findByNameStrtingWith() started from UserServiceImpl");
        List<User> nameStartingWith = this.userRepo.findByNameStartingWith(prefix).orElseThrow(()->new ResourceNotFoundException("User","Starting words"));
        List<UserDto> userDtoList = nameStartingWith.stream().map((list) -> this.modelMapper.map(list, UserDto.class)).collect(Collectors.toList());
        logger.info("Complete : findByNameStrtingWith() completed from UserServiceImpl");
        return userDtoList;
    }

    @Override
    public List<UserDto> findByIsActiveTrue() {
        logger.info("Start : findByIsActiveTrue() started from UserServiceImpl");
        List<User> activeUser = this.userRepo.findByIsActiveTrue().orElseThrow(()->new ResourceNotFoundException("User","Active status"));
        List<UserDto> list = activeUser.stream().map((lists) -> this.modelMapper.map(lists, UserDto.class)).collect(Collectors.toList());
        logger.info("Complete : findByIsActiveTrue() completed from UserServiceImpl");
        return list;
    }

    @Override
    public List<UserDto> findByIsActiveFalse() {
        logger.info("Start : findByIsActiveFalse() started from UserServiceImpl");
        List<User> deActive = this.userRepo.findByIsActiveFalse().orElseThrow(()->new ResourceNotFoundException("User","Active status"));
        List<UserDto> userDtos = deActive.stream().map((lists) -> this.modelMapper.map(lists, UserDto.class)).collect(Collectors.toList());
        logger.info("Complete : findByIsActiveFalse() completed from UserServiceImpl");
        return userDtos;
    }

    @Override
    public List<UserDto> findByGenderContaining(String words) {
        logger.info("Start : findByGenderContaning() started from UserServiceImpl");
        List<User> genderContaning = this.userRepo.findByGenderContaining(words).orElseThrow(()->new ResourceNotFoundException("User","Gender"));
        List<UserDto> userDtoList = genderContaning.stream().map((lists) -> this.modelMapper.map(lists, UserDto.class)).collect(Collectors.toList());
        logger.info("Complete : findByGenderContaning() completed from UserServiceImpl");
        return userDtoList;
    }



}
