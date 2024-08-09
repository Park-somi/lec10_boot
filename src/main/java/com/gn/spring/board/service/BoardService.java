package com.gn.spring.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gn.spring.board.domain.Board;
import com.gn.spring.board.domain.BoardDto;
import com.gn.spring.board.repository.BoardRepository;

@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	@Autowired
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public Page<BoardDto> selectBoardList(BoardDto searchDto, Pageable pageable) {
		Page<Board> boardList = null;
//		String boardTitle = searchDto.getBoard_title();
//		if(boardTitle != null && !"".equals(boardTitle)) {
//			// 검색할 제목이 있다면 해당 제목을 포함한 게시글만 조회
//			boardList = boardRepository.findByboardTitleContaining(boardTitle, pageable);
//		} else {
//			// 전체 게시글 조회
//			boardList = boardRepository.findAll(pageable);
//		}
		String searchText = searchDto.getSearch_text();
		if(searchText != null && "".equals(searchText) == false) {
			int searchType = searchDto.getSearch_type();
			switch(searchType) {
				case 1:
					boardList = boardRepository.findByboardTitleContaining(searchText, pageable);
					break;
				case 2:
					boardList = boardRepository.findByboardContentContaining(searchText, pageable);
					break;
				case 3:
					// findByboardTitleOrboardContent 는 가능하지만 Containing 은 붙일 수 없음
					// 즉, Containing 하고자 하는 필드가 2개이상인경우 -> JPQL 기술 사용
					boardList = boardRepository.findByboardTitleOrboardContentContaining(searchText, pageable);
					break;
			}
		} else {
			boardList = boardRepository.findAll(pageable);
		}
		
		// Page<board> -> List<BoardDto> 로 변환
		// 보안 때문에 Entity 를 그대로 사용 X -> 'Dto'로 변환해주어야함 
		List<BoardDto> boardDtoList = new ArrayList<BoardDto>();
		for(Board b : boardList) {
			BoardDto dto = new BoardDto().toDto(b);
			boardDtoList.add(dto);
		}
		
		// totalElements() : 게시글의 총 개수
		return new PageImpl<>(boardDtoList,pageable,boardList.getTotalElements());
//		List<Board> boardList = boardRepository.findAll();
//		List<BoardDto> boardDtoList = new ArrayList<BoardDto>();
//		// Board(Entity)를 BoardDto로 바꿔주기
//		for(Board board : boardList) {
//			BoardDto boardDto = new BoardDto().toDto(board);
//			boardDtoList.add(boardDto);
//		}
//		return boardDtoList;
	}
	
	// INSERT 한 데이터를 다시 사용할 수 있으므로 Board 타입으로 return
	public Board createBoard(BoardDto dto) {
		Board board = dto.toEntity();
		return boardRepository.save(board);
	}

	public BoardDto selectBoardOne(Long board_no) {
		Board board = boardRepository.findByboardNo(board_no);
		BoardDto dto = new BoardDto().toDto(board);
		return dto;
	}
	
}
