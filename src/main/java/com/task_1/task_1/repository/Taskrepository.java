package com.task_1.task_1.repository;
import com.task_1.task_1.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface Taskrepository extends MongoRepository<Task,String> {
    
    List<Task> findByNameContainingIgnoreCase(String name);
}
