package com.example.demo.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name="Todo")
@Slf4j
public class TodoEntity {

        @Id
        @GeneratedValue(generator = "system-uuid")
        @GenericGenerator(name="system-uuid", strategy = "uuid")
        private String id;

        private String userId;
        private String title;
        private boolean done;


    public void initalizedEntityIdToNull() {
      this.id = null;
    }

    public void setUserIdTemp(String temporaryUserId) {

        if(temporaryUserId.isEmpty()){
            log.warn("UserId can not be null");
            throw new RuntimeException("UserId can not be null");
        }
        this.userId = temporaryUserId;
    }

    public void updateTitle(String title) {
        if(title.isEmpty()){
            log.warn("title can not be null");
            throw new RuntimeException("title can not be null");
        }
        this.title = title;
    }


    public void updateDone(boolean done) {
        this.done = done;
    }
}
