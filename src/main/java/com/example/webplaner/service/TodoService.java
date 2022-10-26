package com.example.webplaner.service;

import com.example.webplaner.dto.TodoDto;
import com.example.webplaner.model.entity.Todo;
import com.example.webplaner.model.entity.User;
import com.example.webplaner.model.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TodoService {

    private TodoRepository todoRepository;

    @Transactional
    public void createTodo(TodoDto todoDto, User user) {
        Todo todo = new Todo();
        todo.setDescription(todoDto.getDescription());
        todo.setTargetDate(todoDto.getTargetDate());
        if (todoDto.getId() != null)
            todo.setId(todoDto.getId());
        todo.setUser(user);
        todoRepository.save(todo);
    }

    public Optional<TodoDto> findById(Long id) {
        return todoRepository.findById(id).map(TodoDto::new);
    }

    public List<TodoDto> findByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
}
