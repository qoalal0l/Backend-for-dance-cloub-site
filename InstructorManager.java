package ua.dp.renessans.model.manager;

import java.util.List;

import ua.dp.renessans.model.entity.Instructor;

public interface InstructorManager {
	void addInstructor(Instructor instructor) throws ManagerException;

	void deleteInstructor(int instructorId) throws ManagerException;

	void updateInstructorFullName(int instructorId, Instructor newInstructor) throws ManagerException;

	void updateInstructorDescription(int instructorId, Instructor newInstructor) throws ManagerException;

	void updateInstructorLargeImage(int instructorId, Instructor newInstructor) throws ManagerException;

	void updateInstructorSmallImage(int instructorId, Instructor newInstructor) throws ManagerException;

	Instructor getInstructorById(int id) throws ManagerException;

	List<Instructor> getAllInstructors() throws ManagerException;
}
