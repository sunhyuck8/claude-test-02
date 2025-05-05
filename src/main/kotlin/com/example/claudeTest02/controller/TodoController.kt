package com.example.claudeTest02.controller

import com.example.claudeTest02.dto.TodoCreateRequest
import com.example.claudeTest02.dto.TodoResponse
import com.example.claudeTest02.dto.TodoUpdateRequest
import com.example.claudeTest02.service.TodoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun getAllTodos(): ResponseEntity<List<TodoResponse>> {
        val todos = todoService.getAllTodos()
        return ResponseEntity.ok(todos)
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<TodoResponse> {
        return try {
            val todo = todoService.getTodoById(id)
            ResponseEntity.ok(todo)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createTodo(@Valid @RequestBody request: TodoCreateRequest): ResponseEntity<TodoResponse> {
        val createdTodo = todoService.createTodo(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo)
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @Valid @RequestBody request: TodoUpdateRequest
    ): ResponseEntity<TodoResponse> {
        return try {
            val updatedTodo = todoService.updateTodo(id, request)
            ResponseEntity.ok(updatedTodo)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            todoService.deleteTodo(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}