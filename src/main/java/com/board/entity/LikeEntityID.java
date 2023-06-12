package com.board.entity;

import java.io.Serializable;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode //변수값들을 serialize화 하기 위해 필요한 작업을 해 줌  
public class LikeEntityID implements Serializable{

	private Long seqno;
	private String email;
	
}
