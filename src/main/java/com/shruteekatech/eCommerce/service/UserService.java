package com.shruteekatech.eCommerce.service;

import com.shruteekatech.eCommerce.utills.Login;
import com.shruteekatech.eCommerce.dto.UserDto;

import java.util.List;

public interface UserService {

    public UserDto createUser(UserDto userDto);

    public UserDto updateUser(UserDto userDto,Long userId);

    public void deleteUser(Long userId);

    public UserDto findUserById(Long userId);

    public List<UserDto> findAllUser();

   // findBy methods implementations

    public List<UserDto> findByName(String name);

    public UserDto findByEmail(String email);

    public UserDto findByPhone(String phone);

    // for login findByEmailAndPassword(String email,String password) userd
    public boolean loginCheck(Login login);

    // for searching with any words
    public List<UserDto> findByNameContaining(String infix);

    // for searching with stating words
    public List<UserDto> findByNameStartingWith(String prefix);

    //for sorting of active user
    public List<UserDto> findByIsActiveTrue();

    // for sorting of deactivated user
    public List<UserDto> findByIsActiveFalse();

    public List<UserDto> findByGenderContaining(String words);


}
