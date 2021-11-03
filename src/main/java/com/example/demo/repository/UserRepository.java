package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query( "SELECT u FROM User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);

	List<Role> getUserRolesById(Long id);

	@Query( "SELECT u FROM User u WHERE u.email= :email and u.password= :password")
	public User FindUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@Query("select u from User u where concat(u.name,u.email) like %?1%")
	public List<User> Search (String keyword);
}
