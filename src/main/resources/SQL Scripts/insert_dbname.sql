#inserting into departments
INSERT INTO Departments (department_id, name, capacity) VALUES
(1, 'Faculty', 50),
(2, 'HR', 10),
(3, 'Administration', 20),
(4, 'Accounts', 15),
(5, 'IT Support', 25);

#inserting into employees
INSERT INTO Employees (employee_id, employee_ref_id,first_name, last_name, email, title, photograph_path,department,password) VALUES
(12345, 12345,'John', 'Doe', 'john.doe@example.com', 'ROLE_USER', 'C:/Users/aishw/IdeaProjects/ESDMiniProject/src/main/resources/Images/John_Doe.jpeg',1,'$2a$10$FOGrA.UlJpVmN0SW30Qd0O2naQauzkla1neJj5RVbhkoMZwd8Xspy'),
(23456,23456, 'Jane', 'Smith', 'jane.smith@example.com', 'ROLE_USER', 'C:/Users/aishw/IdeaProjects/ESDMiniProject/src/main/resources/Images/Jane_Smith.jpeg',1,'$2a$10$1SIhlY1M8Zcd2NAJkwIqp.Cf.05vQtaek8zUovLwE2h5/EBOU56EG'),
(34567, 34567,'Alice', 'Johnson', 'alice.johnson@example.com', 'ROLE_USER', 'C:/Users/aishw/IdeaProjects/ESDMiniProject/src/main/resources/Images/Alice_Johnson.jpeg',1,'$2a$10$dw977FMujLlxUPLH1VAi3eAm5SZy8dtH3zzhNwJEHefPl5ZOi2q3y'),
(45678,45678 ,'Bob', 'Brown', 'bob.brown@example.com', 'ROLE_ADMIN', 'C:/Users/aishw/IdeaProjects/ESDMiniProject/src/main/resources/Images/Bob_Brown.jpeg',3,'$2a$10$M41fO86/nhTDrlbP2FLe.eAy33Wp1sPtZtc3Y7.Jj6j.viwxvLfnK'),
(56789,56789, 'Eve', 'Williams', 'eve.williams@example.com', 'ROLE_ADMIN', 'C:/Users/aishw/IdeaProjects/ESDMiniProject/src/main/resources/Images/Eve_Williams.jpeg',4,'$2a$10$c9bz6Q1BMqevxjdpdT4J/..QW3KOn3UiHAMUI521oUppFfBW18xeG');

#inserting into employee salary
INSERT INTO Employee_salary (id, employee_id, payment_date, amount, description) VALUES
(1, 12345, '2024-11-01', 5000.00, 'Monthly salary for Professor John Doe'),
(2, 23456, '2024-11-01', 5200.00, 'Monthly salary for Professor Jane Smith'),
(3, 34567, '2024-11-01', 4500.00, 'Monthly salary for Professor Alice Johnson'),
(4, 45678, '2024-11-01', 4800.00, 'Monthly salary for Administrator Bob Brown'),
(5, 56789, '2024-11-01', 4000.00, 'Monthly salary for Accountant Eve Williams');

#inserting into courses
INSERT INTO Courses (course_id, course_code, name, description, year, term, faculty_id, credits, capacity) VALUES
(1, 'CSE101', 'Introduction to Computer Science', 'An introductory course in computer science covering basic programming and algorithms.', 2024, 'Fall', 12345, 3, 50),
(2, 'PHY201', 'Physics I', 'Basic concepts in mechanics, motion, energy, and forces.', 2024, 'Fall', 23456, 4, 40),
(3, 'MATH301', 'Calculus III', 'Advanced calculus topics, including multivariable calculus and differential equations.', 2024, 'Spring', 34567, 3, 30);

#inserting into faculty_courses
INSERT INTO Faculty_courses (id, faculty, course_id) VALUES
(1, 12345, 1),
(2, 23456, 2),
(3, 34567, 3);




