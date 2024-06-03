package com.megnaa.service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.megnaa.entity.Student;
import com.megnaa.repo.StudentRepositry;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
	
private final StudentRepositry studentRepositry;


	
		
public StudentService(StudentRepositry studentRepositry) {

	this.studentRepositry = studentRepositry;
}




public List<Student> getStudent(){
		
		return studentRepositry.findAll();
		
	}


public void addNewStudent(Student student) {
	
	Optional<Student> studentByEmail = studentRepositry.findStudentByEmail(student.getEmail());
	if(studentByEmail.isPresent()) {
		throw new IllegalStateException("email taken");
	}
	studentRepositry.save(student);
}




public void deleteStudent(Long studentId) {
	
	boolean exists = studentRepositry.existsById(studentId);
	
	if(!exists) {
		throw new IllegalStateException("Student with id "+studentId+" does not exists");
	}
	
	studentRepositry.deleteById(studentId);
}



@Transactional
public void updateStudent(Long studentId, String name, String email) {
	
	Student student = studentRepositry.findById(studentId)
			.orElseThrow(()->new IllegalStateException("student with id"+ studentId+"does not exists"));
	
	if(name!=null && name.length()>0&&!Objects.equals(student.getEmail(),email)) {
		
		Optional<Student> studentOptional = studentRepositry.findStudentByEmail(email);
		if(studentOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		student.setEmail(email);
	}
	
}
	

}
