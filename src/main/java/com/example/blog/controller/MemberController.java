package com.example.blog.controller;

import com.example.blog.crypto.Crypto;
import com.example.blog.domain.MemberVO;
import com.example.blog.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.util.Map;

@Controller
@RequestMapping("/members/*")
public class MemberController {
    @Autowired
    MemberMapper memberDao;
    Crypto crypto;

    @RequestMapping("/is-duplicated")
    public String is_duplicated(@RequestParam String id, Model model) {
        // id 중복확인
        model.addAttribute("result", memberDao.isDuplicated(id));

        return "members/is-duplicated";
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public String sign_up(@ModelAttribute MemberVO member, Model model) throws GeneralSecurityException {
        // 회원가입
        int result = -1;    // 1: 가입성공, 0: 이미 존재하는 회원, -1: 가입실패

        result = memberDao.sign_up(member);

        model.addAttribute("result", result);

        return "members/sign-up";
    }

    @RequestMapping(value = "/updateMember", method = RequestMethod.POST)
    public String updateMember(@ModelAttribute MemberVO member, @RequestParam String newPassword, Model model) throws GeneralSecurityException {
        // 회원정보 수정
        int result = -1;    //  1: 수정성공, 0: 해당 정보 없음, -1: 수정실패
        // 회원 정보 암호화
        newPassword = crypto.encrypt(newPassword);

        if (memberDao.sign_in(member)) {    // 비밀번호 체크
            if (newPassword != null && !newPassword.isBlank()) {    // 새로운 비밀번호를 입력했으면
                member.setPassword(newPassword);    // 회원 객체의 비밀번호를 새로운 비밀번호로 변경
            }
            result = memberDao.updateMember(member);    // 회원정보 수정
        }
        
        model.addAttribute("result", result);

        return "members/updateMembers";
    }

    @RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
    public String withdrawal(@RequestParam Map<String, String> params, Model model) throws GeneralSecurityException {
        // 회원 탈퇴
        int result = -1;    //  1: 탈퇴성공, 0: 해당 정보 없음, -1: 탈퇴실패
        params.put("password", crypto.encrypt(params.get("password")));  // 비밀번호 암호화

        result = memberDao.withdrawal(params);

        model.addAttribute("result", result);

        return "members/withdrawal";
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public String sign_in(@ModelAttribute MemberVO member, HttpSession session, Model model) throws GeneralSecurityException {
        // 로그인
        boolean result = memberDao.sign_in(member); // 로그인 성공 여부

        if (result) {   // 로그인 성공 시
            session.setAttribute("_uid", member.getId());   // 세션에 ID 설정
        }

        model.addAttribute("result", result);

        return "members/sign-in";
    }
}
