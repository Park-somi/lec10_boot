package com.gn.spring.board.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board") // 실제 데이터베이스 테이블명
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성되는 메소드의 접근 수준을 제어
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Board {
	
	@Id
	@Column(name="board_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardNo; // PK는 Long 타입으로 쓰기
	// 엔티티의 필드명은 _ 사용 불가능 
	// -> repository 에서 'findByboardTitleContaining'과 같이 메소드명에 필드명이 들어가므로
	// => 메소드명에는 _ 사용 불가능!
	
	@Column(name="board_title")
	private String boardTitle;
	
	@Column(name="board_content")
	private String boardContent;
	
	@Column(name="board_writer")
	private Long boardWriter;
	
	@Column(name="reg_date")
	@CreationTimestamp
	private LocalDateTime regDate;
	
	@Column(name="mod_date")
	@UpdateTimestamp
	private LocalDateTime modDate;
	
	@Column(name="ori_thumbnail")
	private String oriThumbnail;
	
	@Column(name="new_thumbnail")
	private String newThumbnail;
}
