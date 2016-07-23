package ua.dp.renessans.model.entity;

import java.sql.Timestamp;

public class Lfp {

	private final int id;
	private final String text;
	private final Timestamp added;
	private final int adderId;
	private final Timestamp lastModified;
	private final int modifierId;

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
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

	public Lfp(int id, String text, Timestamp added, int adderId, Timestamp last_modified, int modifierId) {
		super();
		this.id = id;
		this.text = text;
		this.added = added;
		this.adderId = adderId;
		this.lastModified = last_modified;
		this.modifierId = modifierId;
	}

}
