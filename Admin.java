package ua.dp.renessans.model.entity;

import java.sql.Timestamp;

public class Admin {

	private final int id;
	private final String login;
	private final Timestamp registered;

	public Admin(int id, String login, Timestamp registered, int registrator) {
		super();
		this.id = id;
		this.login = login;
		this.registered = registered;
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public Timestamp getRegistered() {
		return registered;
	}

}
