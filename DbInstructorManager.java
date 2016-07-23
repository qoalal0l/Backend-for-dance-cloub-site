package ua.dp.renessans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import ua.dp.renessans.model.entity.Instructor;
import ua.dp.renessans.model.manager.InstructorManager;
import ua.dp.renessans.model.manager.ManagerException;

public class DbInstructorManager implements InstructorManager {

	private final static String QUERY_MYSQL_GET_ALL_INSTRUCTORS = "SELECT * FROM instructor";
	private final static String QUERY_MYSQL_GET_INSTRUCTOR_BY_ID = "SELECT * FROM instructor WHERE id = ?";
	private final static String QUERY_MYSQL_ADD_INSTRUCTOR = "INSERT INTO instructor (full_name, description, links, small_img, large_img, date_added, adder_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final static String QUERY_MYSQL_DELETE_INSTRUCTOR = "DELETE FROM instructor WHERE id = ?";
	private final static String QUERY_MYSQL_UPDATE_INSTRUCTOR_DESCRIPTION = "UPDATE instructor SET description = ?, date_last_modified = ?, modifier_id = ? WHERE id = ?";
	private final static String QUERY_MYSQL_UPDATE_INSTRUCTOR_FULL_NAME = "UPDATE instructor SET full_name = ?, date_last_modified = ?, modifier_id = ? WHERE id = ?";
	private final static String QUERY_MYSQL_UPDATE_INSTRUCTOR_LARGE_IMAGE = "UPDATE instructor SET large_img = ?, date_last_modified = ?, modifier_id = ? WHERE id = ?";
	private final static String QUERY_MYSQL_UPDATE_INSTRUCTOR_SMALL_IMAGE = "UPDATE instructor SET small_img = ?, date_last_modified = ?, modifier_id = ? WHERE id = ?";

	@Override
	public void addInstructor(Instructor instructor) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_ADD_INSTRUCTOR);) {
			st.setString(1, instructor.getFullName());
			st.setString(2, instructor.getDescription());
			st.setString(3, instructor.getLinks());
			st.setString(4, instructor.getSmallImg());
			st.setString(5, instructor.getLargeImg());
			st.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			st.setInt(7, instructor.getAdderId());
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void deleteInstructor(int id) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_DELETE_INSTRUCTOR);) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public List<Instructor> getAllInstructors() throws ManagerException {
		List<Instructor> instructor = new ArrayList<>();
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_ALL_INSTRUCTORS);
				ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				instructor.add(new Instructor(rs.getInt("id"), rs.getString("full_name"), rs.getString("description"),
						rs.getString("links"), rs.getString("small_img"), rs.getString("large_img"),
						rs.getTimestamp("date_added"), rs.getInt("adder_id"), rs.getTimestamp("date_last_modified"),
						rs.getInt("modifier_id")));
			}
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

		return instructor;

	}

	@Override
	public void updateInstructorDescription(int newsId, Instructor newInstructor) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_INSTRUCTOR_DESCRIPTION);) {
			st.setString(1, newInstructor.getDescription());
			st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			st.setInt(3, newInstructor.getModifierId());
			st.setInt(4, newsId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public void updateInstructorFullName(int newsId, Instructor newInstructor) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_INSTRUCTOR_FULL_NAME);) {
			st.setString(1, newInstructor.getFullName());
			st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			st.setInt(3, newInstructor.getModifierId());
			st.setInt(4, newsId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void updateInstructorLargeImage(int newsId, Instructor newInstructor) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_INSTRUCTOR_LARGE_IMAGE);) {
			st.setString(1, newInstructor.getLargeImg());
			st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			st.setInt(3, newInstructor.getModifierId());
			st.setInt(4, newsId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void updateInstructorSmallImage(int newsId, Instructor newInstructor) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_INSTRUCTOR_SMALL_IMAGE);) {
			st.setString(1, newInstructor.getSmallImg());
			st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			st.setInt(3, newInstructor.getModifierId());
			st.setInt(4, newsId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Instructor getInstructorById(int id) throws ManagerException {
		Instructor instructor = null;
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_INSTRUCTOR_BY_ID);) {
			st.setInt(1, id);
			rs = st.executeQuery();
			rs.next();
			instructor = new Instructor(rs.getInt("id"), rs.getString("full_name"), rs.getString("description"),
					rs.getString("links"), rs.getString("small_img"), rs.getString("large_img"),
					rs.getTimestamp("date_added"), rs.getInt("adder_id"), rs.getTimestamp("date_last_modified"),
					rs.getInt("modifier_id"));
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ManagerException(e);
			}
		}

		return instructor;
	}

}
