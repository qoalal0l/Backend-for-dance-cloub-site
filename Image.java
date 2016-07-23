package ua.dp.renessans.model.entity;

import java.sql.Timestamp;

public class Image {
	private final int id;
	private final int albumId;
	private final String name;
	private final String description;
	private final Timestamp added;
	private final int adderId;

	public int getId() {
		return id;
	}

	public int getAlbumId() {
		return albumId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Timestamp getAdded() {
		return added;
	}

	public int getAdderId() {
		return adderId;
	}

	public Image(int id, int albumId, String name, String description, Timestamp added, int adderId) {
		super();
		this.id = id;
		this.albumId = albumId;
		this.name = name;
		this.description = description;
		this.added = added;
		this.adderId = adderId;
	}

}
