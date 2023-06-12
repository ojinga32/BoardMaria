package com.board.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="reply")
@Table(name="tbl_reply")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter	
@ToString
public class ReplyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="REPLY_SEQ")
	@SequenceGenerator(name = "REPLY_SEQ", sequenceName="tbl_reply_seq", initialValue=1, allocationSize=1)
	private Long replyseqno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="email", nullable=false)
	private MemberEntity email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="seqno", nullable=false)
	private BoardEntity seqno;	
	
	@Column(name="replywriter", length=50, nullable=false)
	private String replywriter;
	
	@Column(name="replycontent", length=200, nullable=false)
	private String replycontent;
	
	@Column(name="replyregdate", nullable=false)
	private LocalDateTime replyregdate;
	
}
