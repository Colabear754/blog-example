package com.example.blog.service.impl;

import com.example.blog.domain.BoardVO;
import com.example.blog.mapper.BoardMapper;
import com.example.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper mapper;

    @Override
    public List<BoardVO> getDocuments(Map<String, Object> params) {
        return mapper.getDocuments(params);
    }

    @Override
    public BoardVO getDocument(int seq) {
        return mapper.getDocument(seq);
    }

    @Override
    public int getDocumentCount(int category_id) {
        return mapper.getDocumentCount(category_id);
    }

    @Override
    public int write(BoardVO board) {
        return mapper.write(board);
    }

    @Override
    public int update(BoardVO board) {
        return mapper.update(board);
    }

    @Override
    public int delete(int seq) {
        return mapper.delete(seq);
    }

    @Override
    public boolean isLike(Map<String, String> params) {
        return mapper.isLike(params);
    }

    @Override
    public int like(Map<String, String> params) {
        return mapper.like(params);
    }

    @Override
    public int cancelLike(Map<String, String> params) {
        return mapper.cancelLike(params);
    }
}
