package com.example.blog.mapper;

import com.example.blog.domain.BoardVO;

import java.util.List;
import java.util.Map;

public interface BoardMapper {
    public List<BoardVO> getDocuments(Map<String, Object> params);
    public BoardVO getDocument(int seq);
    public int getDocumentCount(int category_id);
    public int write(BoardVO board);
    public int update(BoardVO board);
    public int delete(int seq);
    public boolean isLike(Map<String, String> params);
    public int like(Map<String, String> params);
    public int cancelLike(Map<String, String> params);
}
