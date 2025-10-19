package com.task_1.task_1.controller;
import com.task_1.task_1.model.Task;
import com.task_1.task_1.service.Taskservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//for  returning in  JSON
@RestController
//setting the base url for all end point 
//all url will start with "http://localhost:8082/tasks"
@RequestMapping("/tasks")

public class TaskController {

    @Autowired
    private Taskservice taskservice;

    // two things 1)get all task and 2)get task by id
    @GetMapping
    public ResponseEntity<List<Task>>getAllTasks(){

        List<Task> tasks=taskservice.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // endpoint 2 is getting tasks by id

    @GetMapping("/{id}")
    public ResponseEntity <Task> gettaskById(@PathVariable String id){
        //return 404 when task not found

        Task task=taskservice.gettaskById(id);
        //return status code of 202 when task is found
        return ResponseEntity.ok(task);

    }
    // endpoint 3 create task
    @PostMapping
    public ResponseEntity <Task> createTask(@RequestBody Task task){

        Task savedTask=taskservice.createTask(task);
        //return the saved task with 201 service code
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);

    }

    //endpoint 4 update task


    @PutMapping("/{id}")
    public ResponseEntity<Task> UpdateTask(@PathVariable String id, @RequestBody Task taskDetails) {
        // 1. Call the new updateTask service method
        Task updatedTask = taskservice.UpdateTask(id, taskDetails);
        // 2. Return the updated task with a 200 OK status
        return ResponseEntity.ok(updatedTask);
    }
    //endpoint 5 to delete task
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteTask(@PathVariable String id){
        taskservice.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    //endpoint 6 find task by name
    @GetMapping("/find")
    public ResponseEntity<List<Task>> findTaskByName(@RequestParam String name) {
        List<Task> tasks = taskservice. findTaskByName(name);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Endpoint 7: Execute a Task
     */
    @PutMapping("/{id}/execute")
    public ResponseEntity<Task> executeTask(@PathVariable String id) {
        Task updatedTask = taskservice.executeTask(id);
        return ResponseEntity.ok(updatedTask);
    }








    
}
