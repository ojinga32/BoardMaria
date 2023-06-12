package com.board.entity.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.board.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity,String>{
	public MemberEntity findByAuthkey(String authkey);
	public Optional<MemberEntity> findByUsernameAndTelno(String username, String telno);
}
