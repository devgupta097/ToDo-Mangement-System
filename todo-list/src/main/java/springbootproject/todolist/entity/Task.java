package springbootproject.todolist.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="task_name")
	private String taskName;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="target_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate targetDate;
	
	@Column(name="status")
	private String status="Pending";
	
	@Column(name="user_id")
	private int userId;
	
	/*@ManyToOne(cascade= {CascadeType.DETACH,
			CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="user_id")
	private User user;*/
	
	public Task() {}
	
	public Task(String taskName, LocalDate targetDate) {
		this.taskName = taskName;
		this.targetDate = targetDate;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", taskName=" + taskName + ", createdBy=" + createdBy + ", targetDate=" + targetDate
				+ ", status=" + status + ", userId=" + userId + "]";
	}
	
}
