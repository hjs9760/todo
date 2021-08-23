package com.js.ms.todo.global.config.security.oauth2;

import com.js.ms.todo.domain.member.application.OAuth2MemberJoinService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyOAuth2Config {

    @Bean
    // 인증 정보 저장을 위한 표준 인터페이스
    public OAuth2AuthorizedClientService authorizedClientService(OAuth2MemberJoinService oAuth2MemberJoinService) {
            // 직접 생성한 구현 클래스 - 인증 정보를 DB에 저장
            return new MyOAuth2AuthorizedClientService(oAuth2MemberJoinService);
    }

    /**
     * OAuth2 제공자들에 등록된 우리 App의 정보들(clientid, secret, redirectUrl)은 궁극적으로 OAuth2 제공자(google, facebook 등)에 저장되고 소유되기 떄문에,
     * 우리 App의 Server에 요청하기 위해서는 이 정보들을 별도로 가지고 있어야 한다.
     *
     */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {

        CustomOAuthProvider[] customOAuthProviders = CustomOAuthProvider.values();

        CommonOAuth2Provider[] commonOAuth2Providers = CommonOAuth2Provider.values();

        List<ClientRegistration> registrations = new ArrayList<>();

        for (CustomOAuthProvider provider : customOAuthProviders) {
            ClientRegistration clientRegistration = provider
                    .getBuilder()
                    .build();

            registrations.add(clientRegistration);
        }

        return new InMemoryClientRegistrationRepository(registrations);
    }

}