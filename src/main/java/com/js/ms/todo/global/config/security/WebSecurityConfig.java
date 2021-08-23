package com.js.ms.todo.global.config.security;

import com.js.ms.todo.global.config.security.oauth2.handler.MyAuthenticationSuccessHandler;
import com.js.ms.todo.global.config.security.jwt.JWTAuthorizationFilter;
import com.js.ms.todo.global.config.security.oauth2.type.KakaoOAuth2User;
import com.js.ms.todo.global.config.security.oauth2.type.NaverOAuth2User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;
import java.util.List;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and();
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/member/**").permitAll()
                .antMatchers("/mail/**").permitAll()
                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager())  // 인증이 완료된 사용자가 리소스에 접근하려고 할때 해당 요청을 허용할것인지 판단하는 클래스(==인가)
                .and()
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                // oauth2 login 에 대한 설정
                .oauth2Login()
                .successHandler(new MyAuthenticationSuccessHandler())
                // OAuth2 로그인 성공 후 사용자 정보를 가져올 때의 설정
                .userInfoEndpoint()
                    .customUserType(KakaoOAuth2User.class, "kakao")
                    .customUserType(NaverOAuth2User.class, "naver");
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        WebExpressionVoter webExpressionVoter =  new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        List<AccessDecisionVoter<? extends Object>> voterList = Collections.singletonList(webExpressionVoter);
        return new MyAffirmativeBased(voterList);

    }


}