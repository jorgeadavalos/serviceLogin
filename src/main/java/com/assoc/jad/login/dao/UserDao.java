package com.assoc.jad.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.assoc.jad.login.model.Users;

public interface UserDao extends JpaRepository<Users, Integer> {
	
	List<Users> findByEmail(String email);
	List<Users> findByIdGreaterThan(int id);
	Users findById(int id);
	List<Users> findByLoginid(String loginid);
	List<Users> findByLoginidAndService(String loginid,String service);
	List<Users> findByEmailAndService(String email, String service);
	
	@Query("from Users where email=?1 order by lastname")
	List<Users> findByAlangOrder(String email);

}
