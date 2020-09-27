package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRepresentation {

    private String email;
    private String password;
    private Role role;
}
