package com.example.webplaner.model.repository;

import com.example.webplaner.dto.TodoDto;
import com.example.webplaner.model.entity.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    @Query("select new com.example.webplaner.dto.TodoDto(t) " +
            "from Todo t inner join fetch User u on u=t.user where u.id = :userId")
    List<TodoDto> findByUserId(@Param("userId") Long userId);
}
