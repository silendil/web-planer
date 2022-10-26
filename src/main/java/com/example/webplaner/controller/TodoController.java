package com.example.webplaner.controller;

import com.example.webplaner.dto.TodoDto;
import com.example.webplaner.exception.ResourceNotFoundException;
import com.example.webplaner.exception.UnauthorizedAccessException;
import com.example.webplaner.service.TodoService;
import com.example.webplaner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;
    private UserService userService;

    @GetMapping("/todo/{id}")
    public String getTodo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("todo", todoService.findById(id)
                .orElseThrow(ResourceNotFoundException::new));
        return "todo";
    }

    @GetMapping("")
    public String index(Model model) {
        List<TodoDto> todoDtoList = todoService
                .findByUserId(userService.getCurrentUserId()
                        .orElseThrow(UnauthorizedAccessException::new));
        model.addAttribute("todoList", todoDtoList);
        return "index";
    }

    @PostMapping("todo")
    public String createTodo(@ModelAttribute("todo") TodoDto todoDto) {
        todoService.createTodo(todoDto,
                userService.getCurrentUser().orElseThrow(ResourceNotFoundException::new));
        return "redirect:/";
    }

    @GetMapping("todo")
    public String todo(Model model) {
        model.addAttribute("todo", new TodoDto());
        return "todo";
    }

    @GetMapping("/todo/delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteById(id);
        return "redirect:/";
    }

}
