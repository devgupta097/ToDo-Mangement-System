package springbootproject.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springbootproject.todolist.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager entityManager; 
	
	@Override
	public User findByUserName(String username) {
		// get the current hibernate session
		Session currentSession=entityManager.unwrap(Session.class);
		
		Query<User> theQuery=currentSession.createQuery("from User where username=:uname", User.class);
		theQuery.setParameter("uname", username);
		
		User theUser=null;
		try
		{
			theUser=theQuery.getSingleResult();
		}
		catch(Exception e)
		{
			theUser=null;
		}
		
		return theUser;
	}

	@Override
	public void save(User user) {
		
		Session currentSession=entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(user);
		
	}

	@Override
	public List<User> getAllUsers() {
		
		Session currentSession= entityManager.unwrap(Session.class);
		
		Query theQuery= currentSession.createQuery("from User",User.class);
		
		List<User> userList= theQuery.getResultList();
		return userList;
	}
	
	

}
