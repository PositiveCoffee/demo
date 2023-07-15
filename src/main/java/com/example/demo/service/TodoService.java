package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {

    @Autowired
    TodoRepository repository;

    public List<TodoEntity> create(final TodoEntity todoEntity){

        // Validations
        validate(todoEntity);

        repository.save(todoEntity);

        log.info("Entity id : {} is saved.",todoEntity.getId());

        return repository.findByUserId(todoEntity.getUserId());
    }

    //리펙토링
    private void validate(final TodoEntity todoEntity){
        if(todoEntity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(todoEntity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }

    public List<TodoEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity){
        // 1.저장할 엔티티가 유효한지 확인한다.
        validate(entity);

        // 2.넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        // 3.반환된 TodoEntity가 존재하면 값을 새 entity의 값으로 덮어씌운다.
        original.ifPresent(todo -> {
            todo.updateTitle(entity.getTitle());
            todo.updateDone(entity.isDone());
            // 4.데이터베이스에 새 값을 저장한다.
            repository.save(todo);
        });

        // 5.모든 Todo리스트를 반환한다.
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity){
        // 1.저장할 엔티티가 유효한지 확인한다.
        validate(entity);

        try{
            // 2.엔티티를 삭제한다.
            repository.delete(entity);
        }catch (Exception e){
            // 3.Exception 발생시 id와 Exception 로깅
            log.warn("error deleting entity ",entity.getId(),e);

            // 4.컨트롤러로 Exception을 날린다.
            throw new RuntimeException("error deleting entity "+entity.getId());
        }

        return retrieve(entity.getUserId());
    }

}
