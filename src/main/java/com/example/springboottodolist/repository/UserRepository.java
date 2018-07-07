package com.example.springboottodolist.repository;

import com.example.springboottodolist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Modifying
  @Query("UPDATE #{#entityName} o SET o.name = ?1 WHERE o.id = ?2")
  void updateUserNameById(String name, Long id);
}
