package com.example.blog.controller;

import com.example.blog.domain.BoardVO;
import com.example.blog.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog/*")
public class BoardController {
    private static final int PAGESIZE = 6;
    private static final int BLOCKSIZE = 10;
    @Autowired
    private BoardMapper boardDao;

    @RequestMapping("/document-list")
    public String document_list(@RequestParam Map<String, String> params, Model model) {
        // 블로그 글 목록 조회
        int category_id = params.get("category_id") == null ? -1 : Integer.parseInt(params.get("category_id"));
        String pageNum = params.get("pageNum");
        Map<String, Object> input = new HashMap<>();
        int count = boardDao.getDocumentCount(category_id);

        if (pageNum == null) {
            pageNum = "1";
        }

        int currentPage = Integer.parseInt(pageNum);    // 현재 페이지
        int lastPage = count / PAGESIZE + (count % PAGESIZE > 0 ? 1 : 0);   // 마지막 페이지
        int currentBlock = currentPage / BLOCKSIZE + (currentPage % BLOCKSIZE > 0 ? 1 : 0); // 현재 페이지 번호가 있는 블록(1~10페이지라면 1블록, 11~20페이지라면 2블록...)
        int start = (currentPage - 1) * PAGESIZE + 1;   // 불러오기 시작할 번호
        int end = currentPage * PAGESIZE;   // 불러오기 끝낼 번호

        input.put("category_id", category_id);
        input.put("start", start);
        input.put("end", end);

        List<BoardVO> documentList = boardDao.getDocuments(input);

        model.addAttribute("count", count);
        model.addAttribute("documentList", documentList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("currentBlock", currentBlock);

        return "blog/document-list";
    }

    @RequestMapping("/documnet")
    public String document(@RequestParam String seq, Model model) {
        // 블로그 글 보기
        BoardVO document = boardDao.getDocument(Integer.parseInt(seq));

        model.addAttribute("document", document);

        return "blog/document";
    }
}
