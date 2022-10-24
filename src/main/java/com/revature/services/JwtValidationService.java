package com.revature.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtValidationService {

    private Algorithm algorithm = Algorithm.HMAC256("This is the super duper secret string for the updoot social media site");

    public String createJwtWithUsername(String username){
        return JWT.create().withClaim("username",username).sign(algorithm);
    }
    public boolean validateJwt(String jwt){
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            verifier.verify(jwt);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
