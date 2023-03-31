package mains;

import java.io.File;
import java.util.regex.Pattern;

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
	private static File TARGET_DIRECTROY = new File("CHANGEME");

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
		try {
			// 対象のディレクトリ直下の全てのファイルに対して実行
			for (File tempFile1 : TARGET_DIRECTROY.listFiles()) {
				// ディレクトリの場合
				if (tempFile1.isDirectory()) {
					// ディレクトリ名に接尾辞のパターンが含まれている場合
					if (SUFFIX_PATTERN.matcher(tempFile1.getName()).find()) {
						System.out.println("■処理対象:" + tempFile1.getName());

						// 接尾辞を除外した新しいディレクトリ名を作成
						String newDirectoryName = RegExUtils.removeFirst(tempFile1.getName(), SUFFIX_PATTERN);
						File newDirectoryNameFile = new File(tempFile1.getParentFile(), newDirectoryName);

						// 新しいディレクトリ名にリネーム
						if (!tempFile1.renameTo(newDirectoryNameFile)) {
							// リネームに失敗した場合
							System.err.println("リネーム失敗:" + newDirectoryName);

							// リネーム対象のディレクトリ内の全てのファイルに対して実行
							for (File tempFile2 : tempFile1.listFiles()) {
								System.out.println("移動:" + tempFile2.getName());

								// 新しいディレクトリ名内へ移動
								if (!tempFile2.renameTo(new File(newDirectoryNameFile, tempFile2.getName()))) {
									// リネームに失敗した場合
									System.err.println("移動失敗:" + tempFile2.getName());
								}
							}
						}
					} else {
						System.out.println("■処理対象外:" + tempFile1.getName());
					}

					// ディレクトリが空の場合
					if (tempFile1.listFiles().length == 0) {
						// ディレクトリを削除
						if (!tempFile1.delete()) {
							System.err.println("ディレクトリの削除に失敗:" + tempFile1.getName());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}