package springbootproject.todolist.dao;

import java.util.List;
import java.util.Optional;

import springbootproject.todolist.entity.Task;
import springbootproject.todolist.entity.User;

public interface TaskDao {
	
	// add a task
	public void addTask(Task newtask);
		
	// delete a task
	public void deleteTask(int taskId);
		
	// get all tasks for users
	public List<Task> getUserTasks(int userId);
	
	// get by task ID
	public Optional<Task> getByTaskId(int taskId);

}
