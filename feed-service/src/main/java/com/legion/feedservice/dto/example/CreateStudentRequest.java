package com.legion.feedservice.dto.example;

import lombok.Data;

import java.util.List;

@Data
public class CreateStudentRequest {

	private String name;

	private Integer birthYear;

	private String country;

	private List<CreateSubjectRequest> subjectList;

	private CreateDepartmentRequest department;

}
