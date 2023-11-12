package com.graphql.example.entities;

public class StudentDTO {
	private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private Long courseId;  // O cualquier otro identificador relacionado con Course

    // Constructores, getters y setters

    public StudentDTO() {
    }

    public StudentDTO(Long id, String name, String lastName, Integer age, Long courseId) {
        this.setId(id);
        this.setName(name);
        this.setLastName(lastName);
        this.setAge(age);
        this.setCourseId(courseId);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
}
