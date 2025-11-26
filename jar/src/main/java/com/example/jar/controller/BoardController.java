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

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board_list")
    public String list(Model model,
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

        return "board_list";
    }

    // Serve the Board list on the existing article_list route to preserve UI entry point
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

    @GetMapping("/board_view/{id}")
    public String view(@PathVariable long id, Model model) {
        Board board = boardService.incrementCount(id);
        model.addAttribute("board", board);
        return "board_view";
    }

    @GetMapping("/board_edit/{id}")
    public String editForm(@PathVariable long id, Model model, jakarta.servlet.http.HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/member_login";
        }
        Board board = boardService.findById(id);
        if (board != null && !email.equals(board.getAuthor())) {
            return "redirect:/article_list";
        }
        model.addAttribute("board", board);
        model.addAttribute("updateRequest", new com.example.jar.model.service.UpdateBoardRequest(board.getTitle(), board.getContent()));
        return "board_edit";
    }

    @PostMapping("/api/boards/update/{id}")
    public String update(@PathVariable long id,
                         @ModelAttribute com.example.jar.model.service.UpdateBoardRequest request,
                         jakarta.servlet.http.HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/member_login";
        }
        Board board = boardService.findById(id);
        if (board != null && !email.equals(board.getAuthor())) {
            return "redirect:/article_list";
        }
        boardService.update(id, request);
        return "redirect:/article_list";
    }
}
