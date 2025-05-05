package com.example.claudeTest02.service

import com.example.claudeTest02.dto.TodoCreateRequest
import com.example.claudeTest02.dto.TodoResponse
import com.example.claudeTest02.dto.TodoUpdateRequest
import com.example.claudeTest02.model.Todo
import com.example.claudeTest02.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TodoService @Autowired constructor(private val todoRepository: TodoRepository) {

    @Transactional(readOnly = true)
    fun getAllTodos(): List<TodoResponse> {
        return todoRepository.findAll().map { TodoResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getTodoById(id: Long): TodoResponse {
        val todo = findTodoById(id)
        return TodoResponse.from(todo)
    }

    @Transactional
    fun createTodo(request: TodoCreateRequest): TodoResponse {
        val todo = Todo(
            title = request.title,
            description = request.description
        )
        
        val savedTodo = todoRepository.save(todo)
        return TodoResponse.from(savedTodo)
    }

    @Transactional
    fun updateTodo(id: Long, request: TodoUpdateRequest): TodoResponse {
        val todo = findTodoById(id)
        
        todo.title = request.title
        todo.description = request.description
        todo.isDone = request.isDone
        todo.updatedAt = LocalDateTime.now()
        
        val updatedTodo = todoRepository.save(todo)
        return TodoResponse.from(updatedTodo)
    }

    @Transactional
    fun deleteTodo(id: Long) {
        val todo = findTodoById(id)
        todoRepository.delete(todo)
    }

    private fun findTodoById(id: Long): Todo {
        return todoRepository.findById(id)
            .orElseThrow { NoSuchElementException("Todo not found with id: $id") }
    }
}