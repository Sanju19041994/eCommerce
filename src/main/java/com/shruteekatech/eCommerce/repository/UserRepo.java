package com.shruteekatech.eCommerce.repository;

import com.shruteekatech.eCommerce.dto.UserDto;
import com.shruteekatech.eCommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    public Optional<List<User>> findByName(String name);

    public Optional<User> findByEmail(String email);

    public Optional<User> findByPhone(String phone);

    // for login
    public Optional<User> findByEmailAndPassword(String email,String password);

    // for searching with any words
    public Optional<List<User>> findByNameContaining(String infix);

    // for searching with stating words
    public Optional<List<User>> findByNameStartingWith(String prefix);

    //for sorting of active user
    public Optional<List<User>> findByIsActiveTrue();

    // for sorting of deactivated user
    public Optional<List<User>> findByIsActiveFalse();

    public Optional<List<User>> findByGenderContaining(String words);





}
