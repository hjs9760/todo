package com.js.ms.todo.domain.mail.application;

import com.js.ms.todo.domain.member.domain.Member;
import com.js.ms.todo.domain.member.domain.MemberRepository;
import com.js.ms.todo.global.config.Response.Response;
import com.js.ms.todo.global.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${mail.username}")
    private String mailFrom;
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Transactional
    public Response sendMemberCheckMail(String userId) {
        Member member = memberRepository.findByUserId(userId);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(member.getEmail());
        mailMessage.setFrom(mailFrom);
        mailMessage.setSubject("Todolist, 회원 인증");
        mailMessage.setText("이메일 인증코드 입력을 해주세요. \n인증코드=" + member.getEmailCheckToken());
        javaMailSender.send(mailMessage);

        return Response.of("200", "이메일로 인증코드를 전송 했습니다.");
    }

    public Response checkEmail(String userId, String  emailCheckToken) {
        Member member = memberRepository.findByUserId(userId);

        if(member.getEmailCheckToken().equals(emailCheckToken)) {
            member.convertEmailCheck(true);
            memberRepository.save(member);
            return Response.of("200", "인증에 성공했습니다. \n정상적으로 서비스 이용이 가능합니다.");
        } else {
            return Response.of(ErrorCode.MEMBER_SIGNUP_EMAIL_FAIL);
        }
    }
}
