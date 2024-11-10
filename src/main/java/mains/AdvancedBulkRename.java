package mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

import utils.MetadataExtractorHelper;
import utils.TikaHelper;

/**
 * ファイル名を一括で変更.
 *
 * @author cyrus
 */
public class AdvancedBulkRename {

	/**
	 * デバッグモード.
	 */
	private static final boolean DEBUG_MODE = false;

	/**
	 * 子ディレクトリに対して再帰的に処理を実行するかどうか.
	 */
	private static final boolean USE_RECURSIVE = false;

	/**
	 * 新しいファイル名に親ディレクトリの名前を使用するかどうか.
	 */
	private static final boolean USE_PARENT_DIRECTORY_NAME = false;

	/**
	 * 対象のディレクトリ.<br>
	 * このディレクトリ直下のディレクトリに対して処理を実行する.
	 */
	private static File TARGET_DIRECTROY = new File("CHANGE ME");

	/**
	 * 日付のフォーマット.
	 */
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");

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
		System.out.println("■" + file.getAbsolutePath());

		// ファイル属性を読み込み
		DosFileAttributes dosFileAttributes = Files.readAttributes(file.toPath(), DosFileAttributes.class);

		// システムファイルの場合はスキップ
		if (dosFileAttributes.isSystem()) {
			return;
		}

		// 親ディレクトリの名前を取得
		String parentName = FilenameUtils.getName(file.getParent());
		if (DEBUG_MODE) {
			System.out.println("parentName: " + parentName);
		}

		// 全てのEXIFタグを出力
		if (DEBUG_MODE) {
			MetadataExtractorHelper.dumpAllTag(file);
		}

		// 撮影日時を取得
		Date dateTime = MetadataExtractorHelper.getDateTime(file);
		if (DEBUG_MODE && dateTime != null) {
			System.out.println("dateTime: " + dateTime);
		}

		// 撮影日時が空の場合
		if (dateTime == null) {
			// ファイル属性を読み込み
			BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

			// 作成日時を取得
			FileTime creationTime = basicFileAttributes.creationTime();
			if (DEBUG_MODE) {
				System.out.println("creationTime: " + creationTime);
			}

			// 更新日時を取得
			FileTime lastModifiedTime = basicFileAttributes.lastModifiedTime();
			if (DEBUG_MODE) {
				System.out.println("lastModifiedTime: " + lastModifiedTime);
			}

			// 作成日時と更新日時のうち古い方を採用
			if (creationTime.toMillis() < lastModifiedTime.toMillis()) {
				dateTime = new Date(creationTime.toMillis());
			} else {
				dateTime = new Date(lastModifiedTime.toMillis());
			}

			if (DEBUG_MODE) {
				System.out.println("dateTime: " + dateTime);
			}
		}

		// 新しいベースファイル名を作成
		String newBaseFileName;
		if (USE_PARENT_DIRECTORY_NAME) {
			newBaseFileName = String.format("%s_%s", parentName, SIMPLE_DATE_FORMAT.format(dateTime));
		} else {
			newBaseFileName = String.format("%s", SIMPLE_DATE_FORMAT.format(dateTime));
		}

		// 新しい拡張子を取得
		String newExtention = TikaHelper.getExtention(file);

		// 新しいユニークなファイル名を作成
		String newFileName;
		File newFile;
		int index = 0;
		while (true) {
			newFileName = String.format("%s_%05d.%s", newBaseFileName, index, newExtention);
			newFile = new File(file.getParentFile(), newFileName);

			// 新しいファイル名のファイルが存在する場合
			if (newFile.exists() && !Files.isSameFile(file.toPath(), newFile.toPath())) {
				// インクリメント
				index++;
			} else {
				break;
			}
		}
		if (DEBUG_MODE) {
			System.out.println("newFileName: " + FilenameUtils.getName(newFile.getAbsolutePath()));
		}

		// 新しいファイル名へリネーム
		if (!newFile.exists()) {
			FileUtils.moveFile(file, newFile);
		}
	}
}