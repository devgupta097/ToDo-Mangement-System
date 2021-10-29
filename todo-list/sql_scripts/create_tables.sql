DROP DATABASE  IF EXISTS `todo_management_directory`;

CREATE DATABASE  IF NOT EXISTS `todo_management_directory`;
USE `todo_management_directory`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: http://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: fun123
--

INSERT INTO `user` (username,password,first_name,last_name,email)
VALUES 
('employee','$2a$10$ZKRMCDYe2WHxTbJnHRvy4OjgBMYtHpC0jQz7HMmgAJTT17Mqbg2lO','john','Doe','john@luv2code.com'),
('manager','$2a$10$Wd3pJo6zL6iclsMmXwxZ2uTXODmagCqj6B6/1FrBQThzoBxS8jvGO','Mary','Public','mary@luv2code.com'),
('admin','$2a$10$2wp.SruiOogz9dtW.hArcuY4yki4SSdNeuLmNPmAOcxB41MZqL4ny','Susan','Adams','susan@luv2code.com');


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `role` (name)
VALUES 
('ROLE_EMPLOYEE'),
('ROLE_MANAGER'),
('ROLE_ADMIN');

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3);

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;

CREATE TABLE `tasks` (
  `user_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(1000) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `target_date` date DEFAULT NULL,
  `status` varchar(45) DEFAULT 'Pending',
  PRIMARY KEY (`id`),
  CONSTRAINT `task_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `task`
--

INSERT INTO `tasks` VALUES 
	(1,1,'Go for shopping','employee',STR_TO_DATE('21, 07, 2023', '%d, %m, %Y'),'Pending'),
	(2,2,'Do workout','manager',STR_TO_DATE('23, 07, 2023', '%d, %m, %Y'),'Pending'),
	(3,3,'Have Dinner at 9 PM','admin',STR_TO_DATE('29, 07, 2023', '%d, %m, %Y'),'Pending');