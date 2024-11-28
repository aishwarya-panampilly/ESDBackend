#adding all the foreign keys here
#for table "Employee"

alter table Employees
add constraint  department foreign key (department)
references Departments(department_id);

#for table "Employee_Salary"

alter table Employee_Salary
add constraint employee_id foreign key (employee_id)
references Employees(employee_ref_id)
on update cascade ;

#for table "Faculty_Courses"

alter table Faculty_Courses
add constraint faculty foreign key (faculty)
references Employees(employee_ref_id)
on update cascade,
add constraint course_id foreign key (course_id)
references Courses(course_id)
on update cascade ;

#for table "Courses"

alter table Courses
add constraint faculty_id foreign key(faculty_id)
references Employees(employee_ref_id)
on update cascade ;



