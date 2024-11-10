package mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.drew.lang.GeoLocation;
import com.drew.metadata.exif.GpsDirectory;

import utils.MetadataExtractorHelper;

/**
 * ファイルからGPS座標を抽出.
 *
 * @author cyrus
 */
public class ExtractGpsCoordinates {

	/**
	 * デバッグモード.
	 */
	private static final boolean DEBUG_MODE = false;

	/**
	 * 子ディレクトリに対して再帰的に処理を実行するかどうか.
	 */
	private static final boolean USE_RECURSIVE = false;

	/**
	 * 対象のディレクトリ.<br>
	 * このディレクトリ直下のディレクトリに対して処理を実行する.
	 */
	private static File TARGET_DIRECTROY = new File("CHANGE ME");

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("■start.");
		try {
			// 対象のディレクトリに対して処理を実行
			mainDirectory(TARGET_DIRECTROY);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}

	/**
	 * ディレクトリに対して処理を実行.
	 *
	 * @param directory
	 */
	private static void mainDirectory(File directory) {
		// ディレクトリ直下の全てのファイルに対して実行
		for (File file : directory.listFiles()) {
			try {
				// ディレクトリの場合
				if (file.isDirectory()) {
					if (USE_RECURSIVE) {
						// 子ディレクトリに対して処理を実行
						mainDirectory(file);
					}
				} else {
					// ファイルに対して処理を実行
					mainFile(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ファイルに対して処理を実行.
	 *
	 * @param file
	 * @throws Exception 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static void mainFile(File file) throws FileNotFoundException, IOException, SAXException, Exception {
		// 全てのEXIFタグを出力
		if (DEBUG_MODE) {
			MetadataExtractorHelper.dumpAllTag(file);
		}

		// EXIF情報からGPS情報を取得		
		GpsDirectory gpsDirectory = MetadataExtractorHelper.getGpsDirectory(file);
		if (gpsDirectory != null) {
			GeoLocation geoLocation = gpsDirectory.getGeoLocation();
			if (geoLocation != null) {
				Double latitude = geoLocation.getLatitude();
				Double longitude = geoLocation.getLongitude();
				System.out.println(
						"latitude: " + latitude + ", longitude: " + longitude + ", absolutePath: "
								+ file.getAbsolutePath());
			}
		}
	}
}