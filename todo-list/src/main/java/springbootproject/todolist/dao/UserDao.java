package springbootproject.todolist.dao;

import java.util.List;

import springbootproject.todolist.entity.User;

public interface UserDao{

	public User findByUserName(String username);
	
	public void save(User user);
	
	public List<User> getAllUsers();
}
