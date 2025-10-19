Task 1: Task Execution REST API:-

This is a Spring Boot application that provides a REST API for creating, managing, and executing shell command "tasks". It uses a MongoDB database to store all task objects. 


Technologies Used:-
1) Java (Spring Boot)
2) MongoDB (Spring Data)
3) Maven
4) Postman (for testing)


Start MongoDB:-

Before running the application, you must have a mongod instance running.

The application is configured to run on port 8082 and connect to a MongoDB database named taskdb on localhost:27017. 

Run the application :-

From IDE: Right-click the Task1Application.java file and select "Run". 


Application is running since I see the log "Started Task1Application..." in my console.

<img width="1919" height="1076" alt="proj_! ss1" src="https://github.com/user-attachments/assets/f022dfb3-bb33-46b7-b97d-38ca45171d5a" />



Data Models :-

The application uses two main Java classes to represent the data in MongoDB. 

Task.java: This is the blueprint for a single task. It defines that every task must have an ID, name, owner, command, and a list of its past executions. 

TaskExecution.java: This is the blueprint for a single run of a task. It stores the start time, end time, and the command's output. 
These models use getters and setters so the application can read and change their properties. For example, Spring uses getters to build the JSON response and setters to update an object's name before saving it. 

API Endpoints (Usage & Screenshots)
All endpoints are tested using Postman. 


1. Create Task
Endpoint: POST /tasks
Description: Creates a new task.  The id is set to null so MongoDB generates a new one. The command is checked by a validator.

Request Body (JSON):

JSON
{

    "name": "Print Hello",
    
    "owner": "Satvik Shukla",
    
    "command": "echo Hello world"
}

Response (201 Created):


<img width="1919" height="1079" alt="create task" src="https://github.com/user-attachments/assets/67788657-b9b1-46d5-8c7b-95cdd8781832" />



2. Get Task by ID

Endpoint: GET /tasks/{id} 

Description: Fetches a single task by its unique ID.

Response (200 OK):


<img width="1919" height="1079" alt="get taskby id" src="https://github.com/user-attachments/assets/3b3afca9-9b6c-4331-8163-d3bccc1cf8b2" />


3. Execute a Task

Endpoint: PUT /tasks/68f4a25993152f55866299c0/execute 


Description: Runs the command string associated with the task and saves a new TaskExecution object (with the start time, end time, and output) to the task's history.

Response (200 OK):


<img width="1919" height="1079" alt="taskexecution" src="https://github.com/user-attachments/assets/5ca2d990-1101-4ee8-8db9-28dcb27a62b0" />


4. Find Tasks by Name

Endpoint: GET /tasks/find?name=print

Description: Searches for any task whose name contains the query string (case-insensitive, using findByNameContainingIgnoreCase).

Response (200 OK): 


<img width="1919" height="1079" alt="get task by name" src="https://github.com/user-attachments/assets/4c0f9085-873e-4c2d-889b-02e01e304ec7" />


5. Get All Tasks

Endpoint: GET /tasks 

Description: Returns a list of all tasks in the database.

Response (200 OK): 
<img width="1919" height="1079" alt="get all task" src="https://github.com/user-attachments/assets/b18bc245-eab0-4588-bc80-0338fadf2cf6" />


6. Update a Task

Endpoint: PUT /tasks/68f4a25993152f55866299c0

Description: Updates an existing task's details (name, owner, or command).

Request Body (JSON):

JSON

{

    "name": "Print Host name",
    "owner": "Satvik Shukla",
    "command": "echo host name"
    
}
Response (200 OK):


<img width="1919" height="1078" alt="update task" src="https://github.com/user-attachments/assets/88e01289-2664-4151-b292-35535e607aca" />


7. Delete a Task

Endpoint: DELETE /tasks/68f4a25993152f55866299c0

Description: Deletes a task by its ID.

Response (204 No Content): 
<img width="1919" height="1078" alt="delete task" src="https://github.com/user-attachments/assets/07c3c61c-063d-40ab-81f5-bc6958cf3cfe" />


8. Verify Deletion (404)
Endpoint: GET /tasks/68f4a25993152f55866299c0

Description: Proves the task is gone by trying to fetch it again.



Response (404 Not Found):


<img width="1919" height="1068" alt="verify delete" src="https://github.com/user-attachments/assets/2efd4cf0-2b29-48f2-8030-cd7b2f929e15" />




9) validation fail for unsafe commands


command - "rm-rf"



<img width="1919" height="1077" alt="validation fail of unsafe command" src="https://github.com/user-attachments/assets/6c5ff84f-a292-45e4-a718-4554dec062af" />
    



10) visualation of task in mongodb compass
    <img width="1915" height="1078" alt="image" src="https://github.com/user-attachments/assets/95bb9ca2-c569-4a29-837b-01fbb3871fe8" />



