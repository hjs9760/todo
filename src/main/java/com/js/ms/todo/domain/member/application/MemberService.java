package com.js.ms.todo.domain.member.application;

import com.js.ms.todo.domain.member.domain.Member;
import com.js.ms.todo.domain.member.domain.MemberRepository;
import com.js.ms.todo.domain.member.presentation.dto.SignInForm;
import com.js.ms.todo.domain.member.presentation.dto.UserInfo;
import com.js.ms.todo.global.config.Response.Response;
import com.js.ms.todo.global.config.exception.ErrorCode;
import com.js.ms.todo.global.config.security.jwt.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Response save(Member member) {
        if(!ObjectUtils.isEmpty(memberRepository.findByEmail(member.getEmail()))) return Response.of(ErrorCode.MEMBER_SIGNIN_EMAIL_DUPLICATE);

        if(!ObjectUtils.isEmpty(memberRepository.save(member))) {
            eventPublisher.publishEvent(new MemberJoinedEvent(member));
            return Response.of("200", "메일 인증후 서비스 이용이 가능합니다.");
        } else {
            return Response.of(ErrorCode.MEMBER_SIGNUP_FAIL);
        }
    }

    public boolean checkId(String userId) {
        return memberRepository.existsByUserId(userId);
    }


    public Response signInAndInitializeJWT(SignInForm signInForm) {
        Member member = memberRepository.findByUserId(signInForm.getUserId());

        if(ObjectUtils.isEmpty(member)) return Response.of(ErrorCode.MEMBER_SIGNIN_ID_FAIL);

        if(!member.isEmailCheck()) {
            //todo : 인증 코드 재전송
            return Response.of(ErrorCode.MEMBER_SIGNUP_EMAIL_FAIL);
        } else if(passwordEncoder.matches(signInForm.getPw(), member.getPw())) {
            return Response.of(ErrorCode.MEMBER_AUTHENTICATION_FAIL);
        } else {
            String jwt = JWTGenerator.generate(member.getId(), member.getEmail(), member.getRole());

            UserInfo userInfo = UserInfo.builder()
                                .userId(member.getUserId())
                                .birth(member.getBirth())
                                .name(member.getName())
                                .profileImage(member.getProfileImage())
                                .gender(member.getGender())
                                .role(member.getRole())
                                .email(member.getEmail())
                                .build();

            return Response.of("200", userInfo, null, jwt);
        }

    }
}
