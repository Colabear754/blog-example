package com.example.blog.service;

import com.example.blog.domain.BoardVO;

import java.util.List;
import java.util.Map;

public interface BoardService {
    public List<BoardVO> getDocuments(Map<String, Object> params);
    public BoardVO getDocument(int seq);
    public int getDocumentCount(int category_id);
    public int write(BoardVO board);
    public int update(BoardVO board);
    public int delete(int seq);
    public boolean isLike(Map<String, Object> params);
    public int like(Map<String, Object> params);
    public int cancelLike(Map<String, Object> params);
    public int getLikeCount(int seq);
    public int increaseViewCnt(int seq);
    public int updateLikeCnt(int seq);
}
