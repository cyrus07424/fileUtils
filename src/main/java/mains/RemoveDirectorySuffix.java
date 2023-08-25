package mains;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;

/**
 * ディレクトリ名の接尾辞を削除して統合.
 *
 * @author cyrus
 */
public class RemoveDirectorySuffix {

	/**
	 * 対象のディレクトリ.<br>
	 * このディレクトリ直下のディレクトリに対して処理を実行する.
	 */
	private static File TARGET_DIRECTROY = new File("CHANGE ME");

	/**
	 * ディレクトリ名の接尾辞のパターン.
	 */
	private static Pattern SUFFIX_PATTERN = Pattern.compile("-.*?-media");

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("■start.");
		try {
			// 対象のディレクトリ直下の全てのファイルに対して実行
			for (File tempFile1 : TARGET_DIRECTROY.listFiles()) {
				// ディレクトリの場合
				if (tempFile1.isDirectory()) {
					try {
						// ディレクトリ名に接尾辞のパターンが含まれている場合
						if (SUFFIX_PATTERN.matcher(tempFile1.getName()).find()) {
							System.out.println("■処理対象:" + tempFile1.getName());

							// 接尾辞を除外した新しいディレクトリ名を作成
							String newDirectoryName = RegExUtils.removeFirst(tempFile1.getName(), SUFFIX_PATTERN);
							File newDirectoryNameFile = new File(tempFile1.getParentFile(), newDirectoryName);

							// 処理対象のディレクトリ内の全てのファイルに対して実行
							for (File tempFile2 : tempFile1.listFiles()) {
								System.out.println("移動:" + tempFile2.getName());

								// 新しいディレクトリ内へ移動
								FileUtils.moveFileToDirectory(tempFile2, newDirectoryNameFile, true);
							}
						} else {
							System.out.println("■処理対象外:" + tempFile1.getName());
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						// ディレクトリが空の場合					
						if (FileUtils.isEmptyDirectory(tempFile1)) {
							// ディレクトリを削除
							System.out.println("■削除:" + tempFile1.getName());
							FileUtils.deleteDirectory(tempFile1);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}
}