package com.example.jar.controller;

import com.example.jar.model.domain.Board;
import com.example.jar.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 게시판 관련 화면 흐름을 제어하는 컨트롤러입니다.
 * 게시글 목록, 조회, 수정, 작성 폼 등을 처리합니다.
 */
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 목록 페이지를 보여줍니다.
     * 
     * @param model   뷰에 데이터를 전달하기 위한 객체
     * @param page    현재 페이지 번호 (0부터 시작, 기본값 0)
     * @param keyword 검색어 (제목 또는 내용, 기본값 없음)
     * @return 게시글 목록 뷰 이름 (board_list)
     */
    @GetMapping("/board_list")
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword) {

        // 한 페이지당 3개의 게시글을 id 기준 내림차순(최신순)으로 정렬하여 가져옵니다.
        Pageable pageable = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
        Page<Board> list;

        // 검색어가 없으면 전체 목록을, 있으면 검색 결과를 조회합니다.
        if (keyword == null || keyword.isEmpty()) {
            list = boardService.findAll(pageable);
        } else {
            list = boardService.searchByKeyword(keyword, pageable);
        }

        // 뷰에서 사용할 데이터들을 모델에 담습니다.
        model.addAttribute("boards", list);
        model.addAttribute("currentPage", list.getNumber());
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "board_list";
    }

    /**
     * 기존 UI 진입점을 유지하기 위해 동일한 게시글 목록을 다른 URL(/article_list)로도 제공합니다.
     * 동작 방식은 /board_list와 동일합니다.
     */
    @GetMapping("/article_list")
    public String articleList(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "") String keyword) {

        Pageable pageable = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
        Page<Board> list;

        if (keyword == null || keyword.isEmpty()) {
            list = boardService.findAll(pageable);
        } else {
            list = boardService.searchByKeyword(keyword, pageable);
        }

        model.addAttribute("boards", list);
        model.addAttribute("currentPage", list.getNumber());
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "article_list2";
    }

    /**
     * 게시글 상세 페이지를 조회합니다.
     * 조회 시 조회수가 1 증가합니다.
     * 
     * @param id    조회할 게시글 ID
     * @param model 뷰에 데이터를 전달하기 위한 객체
     * @return 게시글 상세 뷰 이름 (board_view)
     */
    @GetMapping("/board_view/{id}")
    public String view(@PathVariable long id, Model model) {
        // 게시글 조회 및 조회수 증가 처리
        Board board = boardService.incrementCount(id);
        model.addAttribute("board", board);
        return "board_view";
    }

    /**
     * 게시글 수정 폼을 보여줍니다.
     * 로그인한 사용자만 접근 가능하며, 작성자 본인인지 확인합니다.
     * 
     * @param id      수정할 게시글 ID
     * @param model   뷰에 데이터를 전달하기 위한 객체
     * @param session 현재 사용자 세션 객체
     * @return 수정 폼 뷰 이름 (board_edit) 또는 로그인/목록 페이지 리다이렉트
     */
    @GetMapping("/board_edit/{id}")
    public String editForm(@PathVariable long id, Model model, jakarta.servlet.http.HttpSession session) {
        // 로그인 여부 확인
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/member_login";
        }
        
        Board board = boardService.findById(id);
        
        // 작성자 본인 확인 (이메일 비교)
        if (board != null && !email.equals(board.getAuthor())) {
            return "redirect:/article_list";
        }
        
        model.addAttribute("board", board);
        // 수정할 데이터를 담을 DTO 객체 생성
        model.addAttribute("updateRequest", new com.example.jar.model.service.UpdateBoardRequest(board.getTitle(), board.getContent()));
        return "board_edit";
    }

    /**
     * 게시글 수정을 처리합니다.
     * 
     * @param id      수정할 게시글 ID
     * @param request 수정할 제목과 내용이 담긴 객체
     * @param session 현재 사용자 세션 객체
     * @return 게시글 목록 페이지 리다이렉트
     */
    @PostMapping("/api/boards/update/{id}")
    public String update(@PathVariable long id,
                         @ModelAttribute com.example.jar.model.service.UpdateBoardRequest request,
                         jakarta.servlet.http.HttpSession session) {
        // 로그인 여부 확인
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/member_login";
        }
        
        Board board = boardService.findById(id);
        
        // 작성자 본인 확인
        if (board != null && !email.equals(board.getAuthor())) {
            return "redirect:/article_list";
        }
        
        // 게시글 업데이트 수행
        boardService.update(id, request);
        return "redirect:/article_list";
    }
}
