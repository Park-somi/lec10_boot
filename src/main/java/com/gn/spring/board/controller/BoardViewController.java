package com.gn.spring.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.gn.spring.board.domain.BoardDto;
import com.gn.spring.board.service.BoardService;

@Controller
public class BoardViewController {
	
	private final BoardService boardService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardViewController.class);
	
	// 생성자 주입 방법
	@Autowired // 매개변수가 하나일 경우 생략가능
	public BoardViewController(BoardService boardService) { 
		this.boardService = boardService;
	}
	
	// ModelAttribute : 양방향 통신가능
	@GetMapping("/board")
	public String selectBoardList(Model model,
			@PageableDefault(page=0, size = 10, sort="regDate", direction=Sort.Direction.DESC) Pageable pageable,
			BoardDto searchDto) { 
		// PageableDefault() : 페이징 관련 어노테이션
		// size : 한 페이지 내 게시글 개수
		// direction : sort 방향
		Page<BoardDto> resultList = boardService.selectBoardList(searchDto, pageable);
		LOGGER.debug(resultList.toString());
		model.addAttribute("resultList",resultList);
		model.addAttribute("searchDto",searchDto);
		return "board/list";
	}
	
	@GetMapping("/board/create")
	public String createBoardPage() {
		return "board/create";
	}
	
	@GetMapping("/board/{board_no}")
	public String selectBoardOne(Model model,
			@PathVariable("board_no") Long board_no) {
		BoardDto dto = boardService.selectBoardOne(board_no);
		model.addAttribute("dto",dto);
		return "board/detail";
	}
	
}
