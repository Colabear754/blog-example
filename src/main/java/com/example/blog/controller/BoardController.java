package com.example.blog.controller;

import com.example.blog.domain.BoardVO;
import com.example.blog.mapper.BoardMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        int count = boardDao.getDocumentCount(category_id); // 글 전체 개수

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

    @RequestMapping("/document")
    public String document(@RequestParam String seq, Model model) {
        // 블로그 글 보기
        BoardVO document = boardDao.getDocument(Integer.parseInt(seq));

        model.addAttribute("document", document);

        return "blog/document";
    }

    @RequestMapping(value = "/document/like", method = RequestMethod.POST)
    public String like(@RequestParam String seq, @SessionAttribute(value = "_uid", required = false) String id, HttpServletRequest request, Model model) {
        /*
        * 글 추천 및 취소
        * result 값에 따른 결과
        * -1: 작업 실패
        * 0: 이미 추천된 게시물
        * 1: 추천 성공
        * 2: 이미 추천 취소된 게시물
        * 3: 추천 취소 성공
        */
        int result = -1;
        Map<String, String> input = new HashMap<>();

        input.put("seq", seq);

        if (id == null) {   // 로그인 중이 아닐 때
            input.put("id", request.getRemoteAddr());
        } else {    // 로그인 중일 때
            input.put("id", id);
        }

        if (boardDao.isLike(input)) {
            result = boardDao.cancelLike(input) + 2;
        } else {
            result = boardDao.like(input);
        }

        model.addAttribute("result", result);

        return "blog/document/like";
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String write(@ModelAttribute BoardVO document, HttpServletRequest request, Model model) throws IOException {
        // 글 작성
        int result = -1;
        StringBuilder dir = new StringBuilder(request.getServletContext().getRealPath("\\resources\\blog\\img")); // 썸네일 저장경로
        String filename = null; // 썸네일 파일 명
        MultipartFile uploadFile = document.getUploadFile();    // 실제 파일

        Files.createDirectories(Paths.get(dir.toString()));    // 썸네일을 저장할 디렉토리가 없으면 생성

        if (!uploadFile.isEmpty()) {
            dir.append("\\");
            String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());    // 확장자
            long timeMillis = System.currentTimeMillis();   // 무작위 파일명을 위한 현재시간
            int random = (int) (Math.random() * 90000) + 10000;    // 무작위 파일명을 위한 10000~99999의 무작위 정수
            filename = String.valueOf(random) + timeMillis + "." + extension;   // 무작위 정수와 현재시간을 연결한 새로운 파일명

            uploadFile.transferTo(new File(dir + filename));    // 파일을 서버에 등록
        }

        document.setThumbnail(filename);
        result = boardDao.write(document);

        model.addAttribute("result", result);

        return "redirect:blog/document-list";
    }

    @RequestMapping(value = "/document/update", method = RequestMethod.POST)
    public String update(@ModelAttribute BoardVO document, HttpServletRequest request, Model model) throws IOException {
        // 글 수정
        int result = -1;
        StringBuilder dir = new StringBuilder(request.getServletContext().getRealPath("\\resources\\blog\\img")); // 썸네일 저장경로
        String filename = null; // 썸네일 파일 명
        MultipartFile uploadFile = document.getUploadFile();    // 실제 파일

        Files.createDirectories(Paths.get(dir.toString()));    // 썸네일을 저장할 디렉토리가 없으면 생성

        if (!uploadFile.isEmpty()) {
            dir.append("\\");
            File pre_thumb = new File(dir + document.getThumbnail());   // 기존 썸네일
            String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());    // 확장자
            long timeMillis = System.currentTimeMillis();   // 무작위 파일명을 위한 현재시간
            int random = (int) (Math.random() * 90000) + 10000;    // 무작위 파일명을 위한 10000~99999의 무작위 정수
            filename = String.valueOf(random) + timeMillis + "." + extension;   // 무작위 정수와 현재시간을 연결한 새로운 파일명

            uploadFile.transferTo(new File(dir + filename));    // 파일을 서버에 등록
            pre_thumb.delete(); // 기존 썸네일 파일 삭제
        }

        document.setThumbnail(filename);
        result = boardDao.write(document);

        model.addAttribute("result", result);

        return "redirect:blog/document?seq=" + document.getSeq();
    }

    @RequestMapping("/document/delete")
    public String delete(@RequestParam String seq, Model model) {
        // 글 삭제

        model.addAttribute("result", boardDao.delete(seq));

        return "blog/document/delete";
    }
}
