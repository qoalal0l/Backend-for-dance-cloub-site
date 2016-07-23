package ua.dp.renessans.util;

import java.io.File;

public class FileLocations {
	public final static String ALBUMS = "images/gallery/album/";
	public final static String THUMBINALS = "images/gallery/thumbnail/";
	public final static String COVERS = "images/gallery/cover/";
	public final static String INSTRUCTOR_SMALL = "images/instructor/small/";
	public final static String INSTRUCTOR_LARGE = "images/instructor/large/";
//	public final static String IMAGE_STORAGE = "/home/qoala/1ren/";
	public final static String IMAGE_STORAGE = "D:/Website_Renessans/";
	public final static String NO_ALBUM_COVER = "images/NO_ALBUM_COVER.png";

	public static String getValidFilePath(String path) {
		String[] tokens = path.split("\\.(?=[^\\.]+$)");
		String newPath = path;
		File file = new File(path);
		while (file.exists()) {
			tokens[0] += '&';
			newPath = tokens[0] + '.' + tokens[1];
			file = new File(newPath);
		}

		return newPath;
	}
}
