package com.revature.services;

import com.auth0.jwt.JWT;
import com.revature.dtos.JwtInfo;
import com.revature.dtos.LoginCredentials;
import com.revature.entities.Account;
import com.revature.repos.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JwtValidationService jwtService;

    public JwtInfo authenticateUser(LoginCredentials loginCredentials){
        Optional<Account> account = accountRepository.findByUsername(loginCredentials.getUsername());
        if(account.isPresent()){
            if(account.get().getPassword().equals(loginCredentials.getPassword())){
                return new JwtInfo(jwtService.createJwtWithUsername(loginCredentials.getUsername()), loginCredentials.getUsername());
            }else{
                throw new RuntimeException("Incorrect password");
            }
        }else{
            throw new RuntimeException("User not found");
        }
    }

    public Optional<Account> getRequestingUser(String jwt){
        if(this.jwtService.validateJwt(jwt)){
            return this.accountRepository.findByUsername(JWT.decode(jwt).getClaim("username").asString());
        }else{
            return Optional.empty();
        }
    }
}
