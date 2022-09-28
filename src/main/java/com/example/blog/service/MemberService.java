package com.example.blog.service;

import com.example.blog.domain.MemberVO;

import java.util.Map;

public interface MemberService {
    public boolean sign_in(Map<String, String> params);
    public boolean isDuplicated(String id);
    public int sign_up(MemberVO member);
    public int updateMember(MemberVO member);
    public int withdrawal(Map<String, String> params);
}
