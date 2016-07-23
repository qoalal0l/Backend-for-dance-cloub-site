package ua.dp.renessans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import ua.dp.renessans.model.entity.Lfp;
import ua.dp.renessans.model.manager.LfpManager;
import ua.dp.renessans.model.manager.ManagerException;

public class DbLfpManager implements LfpManager {

	private final static String QUERY_MYSQL_GET_ALL_LFPS = "SELECT * FROM lfp";
	private final static String QUERY_MYSQL_GET_LFP_BY_ID = "SELECT * FROM lfp WHERE id = ?";
	private final static String QUERY_MYSQL_GET_LFPS_COUNT = "SELECT count(*) FROM lfp";
	private final static String QUERY_MYSQL_ADD_LFP = "INSERT INTO lfp (text, date_added, adder_id) VALUES (?, ?, ?)";
	private final static String QUERY_MYSQL_DELETE_LFP = "DELETE FROM lfp WHERE id = ?";
	private final static String QUERY_MYSQL_GET_LFPS_IN_RANGE = "SELECT * FROM lfp LIMIT ?, ?";
	private final static String QUERY_MYSQL_UPDATE_LFP_TEXT = "UPDATE lfp SET text = ?, date_last_modified = ?, modifier_id = ? WHERE id = ?";

	@Override
	public void addLfp(Lfp lfp) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_ADD_LFP);) {
			st.setString(1, lfp.getText());
			st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			st.setInt(3, lfp.getAdderId());
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void deleteLfp(int id) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_DELETE_LFP);) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int getLfpsCount() throws ManagerException {
		int count = 0;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_LFPS_COUNT);
				ResultSet rs = st.executeQuery();) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

		return count;
	}

	@Override
	public List<Lfp> getAllLfps() throws ManagerException {
		List<Lfp> lfps = new ArrayList<>();
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_ALL_LFPS);
				ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				lfps.add(new Lfp(rs.getInt("id"), rs.getString("text"), rs.getTimestamp("date_added"),
						rs.getInt("adder_id"), rs.getTimestamp("date_last_modified"), rs.getInt("modifier_id")));
			}
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

		return lfps;

	}

	@Override
	public List<Lfp> getLfpsInRange(int fromIndex, int toIndex) throws ManagerException {
		List<Lfp> lfps = new ArrayList<>();
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_LFPS_IN_RANGE);) {
			st.setInt(1, fromIndex);
			st.setInt(2, toIndex);
			rs = st.executeQuery();
			while (rs.next()) {
				lfps.add(new Lfp(rs.getInt("id"), rs.getString("text"), rs.getTimestamp("date_added"),
						rs.getInt("adder_id"), rs.getTimestamp("date_last_modified"), rs.getInt("modifier_id")));
			}
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ManagerException(e);
			}
		}

		return lfps;
	}

	@Override
	public void updateLfpText(int newsId, Lfp lfp) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_LFP_TEXT);) {
			st.setString(1, lfp.getText());
			st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			st.setInt(3, lfp.getModifierId());
			st.setInt(4, newsId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public Lfp getLfpById(int id) throws ManagerException {
		Lfp lfp = null;
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_LFP_BY_ID);) {
			st.setInt(1, id);
			rs = st.executeQuery();
			rs.next();
			lfp = new Lfp(rs.getInt("id"), rs.getString("text"), rs.getTimestamp("date_added"), rs.getInt("adder_id"),
					rs.getTimestamp("date_last_modified"), rs.getInt("modifier_id"));
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ManagerException(e);
			}
		}

		return lfp;
	}
}
