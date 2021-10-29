package springbootproject.todolist.service;

import java.util.List;
import java.util.Optional;

import springbootproject.todolist.entity.Task;
import springbootproject.todolist.entity.User;

public interface TaskService {
	
	// add a task
	public void addTask(Task theTask);
			
	// delete a task
	public void deleteTask(int taskId);
			
	// get all tasks
	public List<Task> getUserTasks(int userId);
	
	// get by task ID
	public Optional<Task> getByTaskId(int taskId);

}
