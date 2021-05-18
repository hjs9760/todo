package com.js.ms.todo.domain.member.presentation;

import com.js.ms.todo.domain.member.application.MemberService;
import com.js.ms.todo.domain.member.domain.Member;
import com.js.ms.todo.domain.member.presentation.dto.SignInForm;
import com.js.ms.todo.domain.member.presentation.dto.SignUpForm;
import com.js.ms.todo.global.config.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
@RequestMapping(value = "/member", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("checkId")
    public boolean checkId(@RequestParam String userId) {
        return memberService.checkId(userId);
    }

    @PostMapping("/signUp")
    public Response signUp(@Valid @RequestBody SignUpForm signUpForm) {
        Member member = Member.builder()
                .userId(signUpForm.getUserId())
                .pw(passwordEncoder.encode(signUpForm.getPw()))
                .name(signUpForm.getName())
                .birth(signUpForm.getBirth())
                .gender(signUpForm.getGender())
                .email(signUpForm.getEmail())
                .emailCheckToken(SignUpForm.generateEmailCheckToken())
                .joinAt(LocalDateTime.now())
                .build();

        return memberService.save(member);
    }

    @PostMapping("/signIn")
    public Response signInAndInitializeJWT(@Valid @RequestBody SignInForm signInForm) {
        return memberService.signInAndInitializeJWT(signInForm);
    }


}
