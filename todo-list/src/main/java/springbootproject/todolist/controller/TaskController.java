package springbootproject.todolist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springbootproject.todolist.entity.Task;
import springbootproject.todolist.entity.User;
import springbootproject.todolist.service.TaskService;
import springbootproject.todolist.service.UserService;

@Controller
@RequestMapping("/api")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	String filterType="All";
	
	Function<List<Task>, List<Task>> filterTaskFunction= (taskList)->{
		return taskList
		.stream()
		.filter((task)->{
			if(!filterType.equals("All"))
			{
				return task.getStatus().equals(filterType);
			}
			return true;
		})
		.collect(Collectors.toList());
	};
	
	List<Task> taskList;
	
	@GetMapping("/tasks")
	public String showTasks(@RequestParam(name="filterCond", defaultValue = "All") String filterType, Model theModel, HttpSession session)
	{		
		this.filterType=filterType;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Set<String> roles = authentication.getAuthorities().stream()
		     .map(r -> r.getAuthority()).collect(Collectors.toSet());
		
		taskList=new ArrayList<>();
		
		if(roles.contains("ROLE_ADMIN"))
		{
			List<User> userList=userService.getAllUsers();
			
			for(User user:userList)
			{
				List<Task> tempTaskList=filterTaskFunction.apply(taskService.getUserTasks(user.getId()));
				
				taskList.addAll(tempTaskList);
			}
		}
		else if(roles.contains("ROLE_MANAGER"))
		{
			List<User> userList=userService.getAllUsers();
			
			for(User user:userList)
			{
				if(user.getRoles().size()<=2)
				{
					List<Task> tempTaskList=filterTaskFunction.apply(taskService.getUserTasks(user.getId()));
					taskList.addAll(tempTaskList);
				}
			}
		}
		else
		{
			User theUser=(User)session.getAttribute("user");
			
			taskList= filterTaskFunction.apply(taskService.getUserTasks(theUser.getId()));
		}
		
		theModel.addAttribute("tasks",taskList);
		
		return "list-tasks";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel)
	{
		//create the model attribute to bind the form data
		Task theTask=new Task();
		
		theModel.addAttribute("task",theTask);
		
		return "task-form";
	}
	
	@PostMapping("/add")
	public String addTask(@ModelAttribute("task") Task theTask, HttpSession session)
	{
		User theUser=(User)session.getAttribute("user");
		//System.out.println("========================="+theTask);
		
		//save the task
		if(theTask.getId()==null)
		{
			theTask.setUserId(theUser.getId());
			theTask.setCreatedBy(theUser.getUsername());
			taskService.addTask(theTask);
		}
		else
		{
			Optional<Task> optionalTask=taskService.getByTaskId(theTask.getId());
			
			if(optionalTask.isPresent())
			{
				theTask.setUserId(optionalTask.get().getUserId());
				theTask.setCreatedBy(optionalTask.get().getCreatedBy());
			}
		}

		//System.out.println("========================="+theTask);
		taskService.addTask(theTask);
		
		
		//use a redirect to prevent duplicate submissions
		return "redirect:/api/tasks";
	}
	
	@GetMapping("/delete")
	public String deleteTask(@RequestParam("taskId") int taskId) {
				
		taskService.deleteTask(taskId);
		
		return "redirect:/api/tasks";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("taskId") int taskId, Model theModel, HttpSession session)
	{		
		Optional<Task> theTask=taskService.getByTaskId(taskId);
		
		if(theTask.isPresent())
			theModel.addAttribute("task",theTask.get());
		
		return "task-form";
	}
	
	@GetMapping("/search")
	public String searchTask(@ModelAttribute("theSearchTask") String taskString, Model theModel)
	{
		
		List<Task> searchTaskList=new ArrayList<>();
		for(Task task:taskList)
		{
			if(task.getTaskName().toLowerCase().contains(taskString.toLowerCase()))
			{
				searchTaskList.add(task);
			}
		}
		
		theModel.addAttribute("tasks",searchTaskList);
		return "list-tasks";
	}
}
