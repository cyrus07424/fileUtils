package mains;

import java.io.File;

/**
 * 空ディレクトリを削除.
 *
 * @author cyrus
 */
public class RemoveEmptyDirectory {

	/**
	 * 対象のディレクトリ.<br>
	 * このディレクトリ直下のディレクトリに対して処理を実行する.
	 */
	private static File TARGET_DIRECTORY = new File("CHANGE ME");

	/**
	 * 再帰的に処理を実行するかどうか.
	 */
	private static final boolean RECURSIVE = true;

	/**
	 * デバッグモード(ディレクトリ削除を行わない).
	 */
	private static final boolean DEBUG_MODE = false;

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("■start.");
		try {
			processDirectory(TARGET_DIRECTORY);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}

	/**
	 * ディレクトリを処理.
	 *
	 * @param targetDirectory
	 */
	private static void processDirectory(File targetDirectory) {
		try {
			System.out.println("process: " + targetDirectory.getAbsolutePath());

			// 再帰的に処理を実行する場合
			if (RECURSIVE) {
				// 全ての子ディレクトリに対して実行
				File[] children = targetDirectory.listFiles(File::isDirectory);
				if (children != null) {
					for (File child : children) {
						// ディレクトリ配下のMP3ファイルを処理
						processDirectory(child);
					}
				}
			}

			if (targetDirectory.listFiles().length == 0) {
				System.out.println("delete!!!!!!");
				if (!DEBUG_MODE) {
					// ディレクトリを削除
					targetDirectory.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}