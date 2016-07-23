package ua.dp.renessans.util;

import java.sql.Timestamp;

public class Timestamper {
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
}
