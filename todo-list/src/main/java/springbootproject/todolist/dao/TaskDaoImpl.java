package springbootproject.todolist.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springbootproject.todolist.entity.Task;

@Repository
public class TaskDaoImpl implements TaskDao {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void addTask(Task newtask) {
		
		Session session = entityManager.unwrap(Session.class);

		//System.out.println(newtask);
		if(newtask.getId()!=null)
			session.merge(newtask);
		else
			session.saveOrUpdate(newtask);	
		
	}

	@Override
	public void deleteTask(int taskId) {

		Session session = entityManager.unwrap(Session.class);
		
		Query<Task> theQuery = session.createQuery("delete from Task where id =:taskid");
		
		theQuery.setParameter("taskid", taskId);
		
		theQuery.executeUpdate();

	}

	@Override
	public List<Task> getUserTasks(int userId) {
		
		Session session = entityManager.unwrap(Session.class);
		
		Query<Task> theQuery = session.createQuery("from Task where user_id =:userId", Task.class);
		
		theQuery.setParameter("userId", userId);
		
		List<Task> tasks = theQuery.getResultList();
		
		return tasks;
	}

	@Override
	public Optional<Task> getByTaskId(int taskId) {
		
		Session session = entityManager.unwrap(Session.class);
		
		Query<Task> theQuery = session.createQuery("from Task where id =:taskId", Task.class);
		
		theQuery.setParameter("taskId", taskId);
		
		Optional<Task> task = Optional.of(theQuery.getSingleResult());
		
		return task;
	}

}
