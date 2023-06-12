package com.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name="file")
@Table(name="tbl_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

	@Id
	//키 생성 전략 --> 오라클의 경우 시퀀스는 DB에 수동으로 생성해야 함
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="FILE_SEQ")
	@SequenceGenerator(name="FILE_SEQ", sequenceName="TBL_FILE_SEQ", initialValue=1, allocationSize=1)
	private Long fileseqno;

	@Column(name="seqno", nullable=false)
	private Long seqno;
	
	@Column(name="email", length=20, nullable=false)
	private String email;
	
	@Column(name="org_filename", length=200, nullable=false)
	private String org_filename;
	
	@Column(name="stored_filename", length=200, nullable=false)
	private String stored_filename;
	
	@Column(name="filesize", nullable=false)
	private Long filesize;
	
	@Column(name="checkfile", length=2, nullable=false)
	private String checkfile;
	
}
