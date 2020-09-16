package com.pfizer.sacchon.team3.security.dao;

import com.pfizer.sacchon.team3.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser{
    private String email;
    private String password;
    private Role role;

}