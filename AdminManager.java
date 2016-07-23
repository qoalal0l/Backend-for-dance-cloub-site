package ua.dp.renessans.model.manager;

import ua.dp.renessans.model.entity.Admin;

public interface AdminManager {
	void addAdmin(Admin admin, String passwordHash) throws ManagerException;

	boolean validateCredentials(String login, String password) throws ManagerException;

	Admin getAdminById(int id) throws ManagerException;

	Admin getAdminByName(String name) throws ManagerException;

	void changePassword(int adminId, String newPasswordHash) throws ManagerException;
}
