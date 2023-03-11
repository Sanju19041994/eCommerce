package com.shruteekatech.eCommerce.serviceImplTest;

import com.shruteekatech.eCommerce.entity.User;
import com.shruteekatech.eCommerce.repository.UserRepo;
import com.shruteekatech.eCommerce.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = (UserServiceImplTest.class))
public class UserServiceImplTest {


    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createUserTest(){
//        User user = new User(1L,"Sanju","skc@gmail.com","8989849712","skc@123","Aurangabad","Male","Nothing to say",true,"2023-02-14",null);
    }




}
