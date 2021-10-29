package springbootproject.todolist.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springbootproject.todolist.createuser.CreateUser;
import springbootproject.todolist.dao.RoleDao;
import springbootproject.todolist.dao.UserDao;
import springbootproject.todolist.entity.Role;
import springbootproject.todolist.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	@Transactional
	public void save(CreateUser createUser) {
		
		User theUser=new User();
		
		theUser.setUsername(createUser.getUserName());
		theUser.setPassword(passwordEncoder.encode(createUser.getPassword()));
		theUser.setFirstName(createUser.getFirstName());
		theUser.setLastName(createUser.getLastName());
		theUser.setEmail(createUser.getEmail());
		
		theUser.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		
		userDao.save(theUser);
	}
	
	/*
	@Override
	@Transactional
	public void save(User user) {
		userDao.save(user);
	}*/
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user=userDao.findByUserName(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream() //Stream<Role>
				.map(role->new SimpleGrantedAuthority(role.getName())) //Stream<Authorities>
				.collect(Collectors.toList()); //List<Authorities>
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	

}
