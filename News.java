package ua.dp.renessans.model.entity;

import java.sql.Timestamp;

public class News {

	private final int id;
	private final String title;
	private final String text;
	private final Timestamp added;
	private final int adderId;
	private final Timestamp lastModified;
	private final int modifierId;

	public News(int id, String title, String text, Timestamp added, int adderId, Timestamp lastModified,
			int modifierId) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.added = added;
		this.adderId = adderId;
		this.lastModified = lastModified;
		this.modifierId = modifierId;
	}

	public News(String title, String text, int adderId, int modifierId) {
		this(-1, title, text, null, adderId, null, modifierId);
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public int getModifierId() {
		return modifierId;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public Timestamp getAdded() {
		return added;
	}

	public int getId() {
		return id;
	}

	public int getAdderId() {
		return adderId;
	}

}
