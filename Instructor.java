package ua.dp.renessans.model.entity;

import java.sql.Timestamp;

public class Instructor {

	private final int id;
	private final String fullName;
	private final String description;
	private final String links;
	private final String smallImg;
	private final String largeImg;
	private final Timestamp added;
	private final int adderId;
	private final Timestamp lastModified;
	private final int modifierId;

	public int getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public String getDescription() {
		return description;
	}

	public String getLinks() {
		return links;
	}

	public String getSmallImg() {
		return smallImg;
	}

	public String getLargeImg() {
		return largeImg;
	}

	public Timestamp getAdded() {
		return added;
	}

	public int getAdderId() {
		return adderId;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public int getModifierId() {
		return modifierId;
	}

	public Instructor(int id, String fullName, String description, String links, String smallImgPath, String largeImgPath,
			Timestamp added, int adderId, Timestamp lastModified, int modifierId) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.description = description;
		this.links = links;
		this.smallImg = smallImgPath;
		this.largeImg = largeImgPath;
		this.added = added;
		this.adderId = adderId;
		this.lastModified = lastModified;
		this.modifierId = modifierId;
	}

}
