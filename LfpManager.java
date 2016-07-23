package ua.dp.renessans.model.manager;

import java.util.List;

import ua.dp.renessans.model.entity.Lfp;

public interface LfpManager {
	void addLfp(Lfp lfp) throws ManagerException;

	void deleteLfp(int id) throws ManagerException;

	void updateLfpText(int id, Lfp newLfp) throws ManagerException;

	Lfp getLfpById(int id) throws ManagerException;

	int getLfpsCount() throws ManagerException;

	List<Lfp> getAllLfps() throws ManagerException;

	List<Lfp> getLfpsInRange(int from, int to) throws ManagerException;
}
