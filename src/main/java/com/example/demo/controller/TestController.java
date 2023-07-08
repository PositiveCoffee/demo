package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.TestRequestBodyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String testController(){
        return "Hello World!";
    }

    @GetMapping("/testGetMapping")
    public String testControllerWithPath(){
        return "Hello World! testGetMapping";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable int id){
        return "Hello World! ID : "+id;
    }

    @GetMapping("/testRequestParam")
    public String testControllerWithRequestParam(@RequestParam int id){
        return "Hello World! Param ID : "+id;
    }

    @GetMapping("/testRequestBody")
    public String testControllerWithRequestBody(@RequestBody TestRequestBodyDto requestBodyDto){
        return "Hello World! body ID : "+requestBodyDto.getId() + " Message : "+requestBodyDto.getMessage();
    }

    @GetMapping("/testResponseBody")
    public ResponseDto<?> testControllerResponseBody(){
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDto");
        ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
        return response;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity(){
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDto And you got Ok!");
        ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }
}
