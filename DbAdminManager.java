package ua.dp.renessans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.NamingException;

import org.mindrot.jbcrypt.BCrypt;

import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.manager.AdminManager;
import ua.dp.renessans.model.manager.ManagerException;

public class DbAdminManager implements AdminManager {

	private final static String QUERY_MYSQL_ADD_ADMIN = "INSERT INTO admin (login, password_hash, date_registered) VALUES (?, ?, ?)";
	private final static String QUERY_MYSQL_GET_ADMIN_BY_ID = "SELECT * FROM admin WHERE id = ?";
	private final static String QUERY_MYSQL_GET_ADMIN_BY_NAME = "SELECT * FROM admin WHERE login = ?";
	private final static String QUERY_MYSQL_VALIDATE_CREDENTIALS = "SELECT password_hash FROM admin WHERE login = ?";
	private final static String QUERY_MYSQL_CHANGE_PASSWORD = "UPDATE admin SET password_hash=? WHERE id=?";

	@Override
	public void addAdmin(Admin admin, String passwordHash) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_ADD_ADMIN);) {
			st.setString(1, admin.getLogin());
			st.setString(2, passwordHash);
			st.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public boolean validateCredentials(String login, String password) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_VALIDATE_CREDENTIALS);) {
			st.setString(1, login);
			ResultSet rs = st.executeQuery();
			if (!rs.next()) {
				return false;
			}
			String hash = rs.getString("password_hash");
			return BCrypt.checkpw(password, hash);
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Admin getAdminById(int id) throws ManagerException {
		Admin admin = null;
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_ADMIN_BY_ID);) {
			st.setInt(1, id);
			rs = st.executeQuery();
			if (!rs.next()) {
				return null;
			}
			admin = new Admin(rs.getInt("id"), rs.getString("login"), rs.getTimestamp("date_registered"), -1);
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ManagerException(e);
			}
		}

		return admin;

	}

	@Override
	public void changePassword(int adminId, String newPasswordHash) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_CHANGE_PASSWORD);) {
			st.setString(1, newPasswordHash);
			st.setInt(2, adminId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Admin getAdminByName(String name) throws ManagerException {
		Admin admin = null;
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_ADMIN_BY_NAME);) {
			st.setString(1, name);
			rs = st.executeQuery();
			if (!rs.next()) {
				return null;
			}
			admin = new Admin(rs.getInt("id"), rs.getString("login"), rs.getTimestamp("date_registered"), -1);
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ManagerException(e);
			}
		}

		return admin;

	}
}
