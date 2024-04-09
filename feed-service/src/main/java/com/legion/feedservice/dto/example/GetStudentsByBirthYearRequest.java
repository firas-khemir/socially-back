package com.legion.feedservice.dto.example;

import java.util.List;

public class GetStudentsByBirthYearRequest {

	private List<Integer> birthYearList;

	public List<Integer> getBirthYearList() {
		return birthYearList;
	}

	public void setBirthYearList(List<Integer> birthYearList) {
		this.birthYearList = birthYearList;
	}

}
