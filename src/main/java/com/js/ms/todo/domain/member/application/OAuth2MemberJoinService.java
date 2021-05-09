package com.js.ms.todo.domain.member.application;


import com.js.ms.todo.domain.member.domain.Member;
import com.js.ms.todo.domain.member.domain.MemberRepository;
import com.js.ms.todo.global.config.Response.Response;
import com.js.ms.todo.global.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2MemberJoinService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Member join(String email, String oauth2Id, String name, String providerType, String accessToken, String refreshToken) {

        Member member = memberRepository.findByEmail(email);
        if(!ObjectUtils.isEmpty(member)) {
            return member;
        }


        Optional<Member> optionalMember = memberRepository
                .findByOauthIdAndProviderName(oauth2Id, providerType);

        if (optionalMember.isPresent()) {
            optionalMember.get().updateOauth2Info(accessToken, refreshToken);
            return optionalMember.get();
        }

        Member newJoinedMeber = new Member(email, oauth2Id, name, providerType, accessToken, refreshToken);

        memberRepository.save(newJoinedMeber);
        this.eventPublisher.publishEvent(new com.js.ms.todo.domain.member.application.MemberJoinedEvent(newJoinedMeber));

        return newJoinedMeber;
    }
}
