package com.shruteekatech.eCommerce.utills;

import com.shruteekatech.eCommerce.dto.UserDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class JwtResponse {

    private String jwtToken;

    private UserDto user;

}
