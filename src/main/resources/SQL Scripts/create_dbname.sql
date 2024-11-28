#Creating "Departments" table
create table if not exists Departments (
department_id int primary key,
name varchar(100) not null unique,
capacity int not null
);

#Creating a table "Employees" and selecting department as "Faculty"
create table if not exists Employees (
     employee_id int primary key,
     employee_ref_id int not null unique,
     first_name varchar(50) not null,
     last_name varchar(50) not null,
     email varchar(50) unique not null,
     title varchar(50),
     photograph_path varchar(500), #long file path
     department int not null,
     password varchar(200) not null unique
);

#since employee_id is modified, that must be modified on all the tables
#Creating "Employee_Salary" table
create table if not exists Employee_Salary (
    id int primary key,
    employee_id int unique,
    payment_date date not null,
    amount int not null, #you have to get paid :)
    description varchar(100)
);

#Creating "Courses" table even though it's not mapped in the UML
create table if not exists Courses (
    course_id int primary key ,
    course_code varchar(20) unique not null,
    name varchar(100) not null unique, #courses shouldn't have same names
    description varchar(500),
    year year not null,
    term varchar(50) not null,
    faculty_id int not null,
    credits decimal(2,1) not null, #assuming it is like 4.0
    capacity int not null #some capacity must be there for course to exist
);
#Creating "Faculty_Courses" table
create table if not exists Faculty_Courses (
    id int primary key,
    faculty int not null,
    course_id int not null

);

