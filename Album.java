package ua.dp.renessans.model.entity;

import java.sql.Timestamp;

public class Album {
	private final int id;
	private final String name;
	private final String description;
	private final String coverName;
	private final Timestamp added;
	private final int adderId;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getCoverName() {
		return coverName;
	}

	public Timestamp getAdded() {
		return added;
	}

	public int getAdderId() {
		return adderId;
	}

	public Album(int id, String name, String description, String coverName, Timestamp added, int adderId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.coverName = coverName;
		this.added = added;
		this.adderId = adderId;
	}

}
