package com.example.blog.service.impl;

import com.example.blog.domain.MemberVO;
import com.example.blog.mapper.MemberMapper;
import com.example.blog.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper mapper;

    @Override
    public boolean sign_in(MemberVO member) {
        return mapper.sign_in(member);
    }

    @Override
    public boolean isDuplicated(String id) {
        return mapper.isDuplicated(id);
    }

    @Override
    public int sign_up(MemberVO member) {
        return mapper.sign_up(member);
    }

    @Override
    public int updateMember(MemberVO member) {
        return mapper.updateMember(member);
    }

    @Override
    public int withdrawal(Map<String, String> params) {
        return mapper.withdrawal(params);
    }
}
