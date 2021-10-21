package com.example.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

	 private static final List<Student> STUDENTS = Arrays.asList(
		      new Student(1, "James Bond"),
		      new Student(2, "Maria Jones"),
		      new Student(3, "Anna Smith")
		    );
	 @GetMapping
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
	 public List<Student> getAllStudents(){
		 return STUDENTS;
	 }
	 
	 @PostMapping
	 @PreAuthorize("hasAuthority('student:write')")
	 public void registerNewStudent(@RequestBody Student student) {
		 System.out.println(student);
	 }
	
	 @DeleteMapping(path = "{idStudent}")
	 @PreAuthorize("hasAuthority('student:write')")
	 public void deleteStudent(@PathVariable("idStudent") Integer idStudent) {
		 System.out.println(idStudent);
	 }
	 
	 @PutMapping(path = "{idStudent}")
	 @PreAuthorize("hasAuthority('student:write')")
	 public void updateStudent(@PathVariable("idStudent") Integer idStudent, @RequestBody Student student) {
		 System.out.println(String.format("%s %s", idStudent, student));
	 }
}