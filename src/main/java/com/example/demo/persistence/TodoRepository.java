package com.example.demo.persistence;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,String> {

    @Query(value = "SELECT * FROM Todo t WHERE t.user_Id = ?1",nativeQuery = true)
    List<TodoEntity> findByUserId(String userId);
}
