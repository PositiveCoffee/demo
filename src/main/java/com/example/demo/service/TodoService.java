package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TodoService {

    @Autowired
    TodoRepository repository;

    public String testService(){
        // TodoEntity생성
        TodoEntity entity = TodoEntity.builder()
                .title("My first todo item")
                .build();

        // TodoEntity저장
        repository.save(entity);

        // TodoEntity검색
        TodoEntity todoEntity = repository.findById(entity.getId()).get();
        return todoEntity.getTitle();


    }


}
