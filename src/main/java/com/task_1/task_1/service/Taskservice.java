package com.task_1.task_1.service;

import com.task_1.task_1.exception.InvalidCommandException;
import com.task_1.task_1.exception.ResourceNotFoundException;
import com.task_1.task_1.model.Task;
import com.task_1.task_1.model.Taskexecution;
import com.task_1.task_1.repository.Taskrepository;
import com.task_1.task_1.validation.CommandValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional; // This is the "box" class

@Service
public class Taskservice {

    @Autowired
    private Taskrepository taskRepository;

    @Autowired
    private CommandValidator commandValidator;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Task gettaskById(String id){
        //repository finds the task and returns optional box
        //means even if there is no task , repository returns box
        Optional<Task>foundTaskBox=taskRepository.findById(id);
        if (foundTaskBox.isPresent()){
            return foundTaskBox.get();
        }
        else{
            throw new ResourceNotFoundException("Task not found with id: " +id);
        }

    }
    public List<Task> findTaskByName(String name ){
        List<Task> tasks = taskRepository.findByNameContainingIgnoreCase(name);
        if (tasks.isEmpty()){
            throw new ResourceNotFoundException("No tasks found with name containing: " +name);
        }
        return tasks;


    }

    public Task createTask(Task task) {

        if (!commandValidator.isCommandSafe(task.getCommand())) {
            throw new InvalidCommandException("Command contains unsafe/blacklisted keywords.");
        }
        // 2.  MongoDB creates a new one.
        task.setId(null);
        // 3 check if execution list is null
        if (task.getTaskexecutions() == null) {
            // If it is, create a new empty list to avoid errors later
            task.setTaskexecutions(new java.util.ArrayList<>());
        }

        return  taskRepository.save(task);


    }


    public Task UpdateTask(String id, Task updatedtaskData) {
        //finding the existing task
        Task existingTask=gettaskById(id);


        // 1. Check if the command is safe
        if (!commandValidator.isCommandSafe(updatedtaskData.getCommand())) {
            // If not safe, throw an error
            throw new InvalidCommandException("Command contains unsafe/blacklisted keywords.");
        }
        // 3. Update the fields of the existing task with the new details.
        existingTask.setName(updatedtaskData.getName());
        existingTask.setOwner(updatedtaskData.getOwner());
        existingTask.setCommand(updatedtaskData.getCommand());
 
        // 3. Save the task to the database
        return taskRepository.save(existingTask);
    }
    /**
     * This method deletes a task by its ID.
     */
    public void deleteTask(String id) {
        
        // 1. Check if a task with this ID even exists
        boolean taskExists = taskRepository.existsById(id);

        // 2. If it doesn't exist, throw an error
        if (!taskExists) {
            throw new ResourceNotFoundException("Task not found with id: " + id + ", cannot delete.");
        }
        
        // 3. If it does exist, delete it
        taskRepository.deleteById(id);
    }



    /**
     * Executes the shell command for a task.
     */
    public Task executeTask(String id) {
        // This line will now call your un-simplified getTaskById method.
        // If the task isn't found, that method will handle throwing the error.
        Task task = gettaskById(id);
        
        LocalDateTime startTime = LocalDateTime.now();
        String output = "";
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", task.getCommand());
            Process process = processBuilder.start();

            // Capture output
            StringBuilder outputBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }

            // Capture errors
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                outputBuilder.append("ERROR: ").append(line).append("\n");
            }

            int exitCode = process.waitFor();
            output = outputBuilder.toString();
            if (exitCode != 0) {
                 output += "\nProcess finished with exit code: " + exitCode;
            }

        } catch (Exception e) {
            output = "Failed to execute command: " + e.getMessage();
        }

        LocalDateTime endTime = LocalDateTime.now();

        // Create and save the execution record
        Taskexecution execution = new Taskexecution(startTime, endTime, output);
        task.getTaskexecutions().add(execution);
        
        return taskRepository.save(task);
    }
}