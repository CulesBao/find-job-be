package com.findjobbe.findjobbe.utils;

import com.findjobbe.findjobbe.config.EnvConfiguration;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityConstants {
    private static final Dotenv env = new EnvConfiguration().dotenv();

    public static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public static final String SECRET_KEY = env.get("SECRET_KEY");

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static final String LOGIN_REQUEST_URI = "/login";

    public static final String REGISTRATION_REQUEST_URI = "/register";

    private SecurityConstants() {

        throw new UnsupportedOperationException();
    }

    /**
     * @return authenticated username from Security Context
     */
    public static String getAuthenticatedId() {

//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        final
//        return userDetails.getUsername();
        return null;
    }

}
