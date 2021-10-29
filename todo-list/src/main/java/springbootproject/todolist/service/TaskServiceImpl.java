package springbootproject.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springbootproject.todolist.dao.TaskDao;
import springbootproject.todolist.entity.Task;
import springbootproject.todolist.entity.User;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;
	
	@Override
	@Transactional
	public void addTask(Task newtask) {
		taskDao.addTask(newtask);
	}

	@Override
	@Transactional
	public void deleteTask(int taskId) {
		taskDao.deleteTask(taskId);
	}

	@Override
	@Transactional
	public List<Task> getUserTasks(int userId) {
		return taskDao.getUserTasks(userId);
	}

	@Override
	@Transactional
	public Optional<Task> getByTaskId(int taskId) {
		return taskDao.getByTaskId(taskId);
	}

}
