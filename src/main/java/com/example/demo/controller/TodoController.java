package com.example.demo.controller;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.TodoDto;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto){
        try{
            String temporaryUserId = "temporary-user";

            // 1.TodoEntity로 변환
            TodoEntity entity = TodoDto.toEntity(dto);

            // 2.id를 null로 초기화. 생성 당시에는 id가 없어야하기 때문
            entity.initalizedEntityIdToNull();

            // 3.임시 유저 아이디 설정
            entity.setUserIdTemp(temporaryUserId);

            // 4.서비스를 이용해 엔티티 생성
            List<TodoEntity> entities = todoService.create(entity);

            // 5.자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

            // 6.변환된 TodoDto 리스트를 이용해 ResponseDto 초기화
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                    .data(dtos)
                    .build();

            // 7.ResponseDto 리턴
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temporary-user";

        // 1.서비스 메서드의 retrieve 메서드를 사용해 Todo리스트를 가져온다.
        List<TodoEntity> entities = todoService.retrieve(temporaryUserId);

        // 2.자바스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

        // 6.변환된 Todo리스트를 이용해 ResponseDto를 초기화한다.
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                .data(dtos).build();

        // 7.ResponseDto를 리턴한다
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TodoDto dto){

        String temporaryUserId = "temporary-user";

        // 1.dto를 entity로 변환
        TodoEntity entity = TodoDto.toEntity(dto);

        // 2.id를 TemporaryUserID로 변환
        entity.setUserIdTemp(temporaryUserId);

        // 3.서비스를 이용해 업데이트
        List<TodoEntity> entities = todoService.update(entity);

        // 4.자바 스트름을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

        // 5.변환된 Dto 리스트를 이용해 ResponseDto 초기화
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                .data(dtos).build();

        // 6.ResponseEntity 반환
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto dto){
        try{
            String temporaryUserId = "temporary-user";

            // 1.entity로 변환
            TodoEntity entity = TodoDto.toEntity(dto);

            // 2.임시유저 설정
            entity.setUserIdTemp(temporaryUserId);

            // 3.서비스를 이용해 entity삭제
            List<TodoEntity> entities = todoService.delete(entity);

            // 4.엔티티 리스트를 dto리스트로 변환
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

            // 5.변환된 Dto 리스트를 이용해 ResponseDto 초기화
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                    .data(dtos).build();

            // 6.ResponseDto 리턴
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                    .error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


}
