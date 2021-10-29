package springbootproject.todolist.dao;

import springbootproject.todolist.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
}
