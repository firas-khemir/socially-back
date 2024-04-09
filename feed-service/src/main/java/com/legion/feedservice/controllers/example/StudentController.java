//package com.legion.feedservice.controllers;
//
//import java.util.List;
//
//import com.legion.feedservice.dto.example.CreateStudentRequest;
//import com.legion.feedservice.dto.example.GetStudentsByBirthYearRequest;
//import com.legion.feedservice.dto.example.UpdateStudentRequest;
//import com.legion.feedservice.entities.example.Student;
//import com.legion.feedservice.services.example.StudentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/student/")
//public class StudentController {
//
//	private final StudentService studentService;
//
//	@PostMapping("/create")
//	public Student createStudent(@Validated @RequestBody CreateStudentRequest createStudentRequest) {
//		return studentService.createStudent(createStudentRequest);
//	}
//
//	@GetMapping("/getStudentById/{id}")
//	public Student getStudentById(@PathVariable long id) {
//		return studentService.getStudentById(id);
//	}
//
//	@GetMapping("/getStudentByName/{name}")
//	public List<Student> getStudentsByName(@PathVariable String name) {
//		return studentService.getStudentsByName(name);
//	}
//
//	@GetMapping("/getAllStudents")
//	public List<Student> getAllStudents() {
//		return studentService.getAllStudents();
//	}
//
//	@PutMapping("/update")
//	public Student updateStudent(@RequestBody UpdateStudentRequest updateStudentRequest) {
//		return studentService.updateStudent(updateStudentRequest);
//	}
//
//	@DeleteMapping("/delete/{id}")
//	public String deleteStudent(@PathVariable long id) {
//		return studentService.deleteStudent(id);
//	}
//
//	@GetMapping("/getStudentByNameAndBirthYear/{name}/{birthYear}")
//	public List<Student> getStudentByNameAndBirthYear(@PathVariable String name,
//			@PathVariable Integer birthYear) {
//		return studentService.getStudentByNameAndBirthYear(name, birthYear);
//	}
//
//	@GetMapping("/getStudentByNameOrBirthYear/{name}/{birthYear}")
//	public List<Student> getStudentByNameOrBirthYear(@PathVariable String name,
//			@PathVariable Integer birthYear) {
//		return studentService.getStudentByNameOrBirthYear(name, birthYear);
//	}
//
//	@GetMapping("/getStudentsByBirthYear")
//	public List<Student> getStudentByNameOrBirthYear(
//			@RequestBody GetStudentsByBirthYearRequest req) {
//		return studentService.getStudentByNameOrBirthYear(req);
//	}
//
//	@GetMapping("/getStudentsWithPagination")
//	public List<Student> getStudentsWithPagination(@RequestParam int pageNo,
//			@RequestParam int pageSize) {
//		return studentService.getStudentsWithPagination(pageNo, pageSize);
//	}
//
//	@GetMapping("/getStudentsWithSorting")
//	public List<Student> getStudentsWithSorting() {
//		return studentService.getStudentsWithSorting();
//	}
//
//	@GetMapping("/getStudentsByNameLike/{name}")
//	public List<Student> getStudentsByNameLike(@PathVariable String name) {
//		return studentService.getStudentsByNameLike(name);
//	}
//
//	@GetMapping("/getStudentsByNameStartsWith/{name}")
//	public List<Student> getStudentsByNameStartsWith(@PathVariable String name) {
//		return studentService.getStudentsByNameStartsWith(name);
//	}
//
//}
