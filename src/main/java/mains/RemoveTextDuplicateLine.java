package mains;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
 * テキストファイル内の重複行を削除.
 *
 * @author cyrus
 */
public class RemoveTextDuplicateLine {

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
			// 対象のディレクトリ直下の全てのファイルに対して実行
			for (File tempFile1 : TARGET_DIRECTROY.listFiles()) {
				// ファイルの場合
				if (tempFile1.isFile()) {
					try {
						// 全ての行を読み込み
						Set<String> lines = new LinkedHashSet<>(FileUtils.readLines(tempFile1, StandardCharsets.UTF_8));

						// 全ての行を書き込み
						FileUtils.writeLines(tempFile1, lines);
					} catch (Exception e) {
						e.printStackTrace();
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