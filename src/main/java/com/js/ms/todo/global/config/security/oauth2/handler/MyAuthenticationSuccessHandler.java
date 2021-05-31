package com.js.ms.todo.global.config.security.oauth2.handler;


import com.js.ms.todo.global.config.security.jwt.JWTGenerator;
import com.js.ms.todo.global.config.security.oauth2.type.MyOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        MyOAuth2User oAuth2User = (MyOAuth2User) authentication.getPrincipal();

        String jwt = JWTGenerator.generate(oAuth2User.dbPK, oAuth2User.email, oAuth2User.role);

        response.setStatus(200);
        response.sendRedirect("http://localhost:8081/oauth-callback?token="+jwt+"&userInfo="+oAuth2User.email);

    }
}
