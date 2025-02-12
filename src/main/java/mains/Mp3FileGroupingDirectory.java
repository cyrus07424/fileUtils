package mains;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;

/**
 * MP3ファイル一覧のアルパム名からディレクトリを統合.
 *
 * @author cyrus
 */
public class Mp3FileGroupingDirectory {

	/**
	 * 対象のディレクトリ.<br>
	 * このディレクトリ直下のディレクトリに対して処理を実行する.
	 */
	private static final File TARGET_DIRECTROY = new File("CHANGE ME");

	/**
	 * 使用するフィールドの一覧(優先度が高い順).
	 */
	private static final FieldKey[] FIELDKEY_ARRAY = new FieldKey[] { FieldKey.ALBUM, FieldKey.ALBUM_ARTIST,
			FieldKey.ALBUM_ARTISTS, FieldKey.ARTIST, FieldKey.ARTISTS };

	/**
	 * デバッグモード(ディレクトリ移動を行わない).
	 */
	private static final boolean DEBUG_MODE = true;

	/**
	 * FIXME 使用する文字セット.
	 */
	private static final String USE_CHARSET = "SHIFT_JIS";

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("■start.");
		try {
			// 対象のディレクトリ直下の全てのファイルに対して実行
			for (File file : TARGET_DIRECTROY.listFiles()) {
				try {
					// ファイルの場合
					if (file.isFile()) {
						// オーディオファイルとして読み込み
						AudioFile audioFile = AudioFileIO.read(file);

						// タグを取得
						Tag tag = audioFile.getTag();
						if (DEBUG_MODE) {
							// 全てのフィールドに対して実行
							Iterator<TagField> tagFieldIterator = tag.getFields();
							while (tagFieldIterator.hasNext()) {
								// FIXME フィールドの値を取得
								TagField tagField = tagFieldIterator.next();
								System.out.println(
										tagField.getId() + ": " + new String(tagField.getRawContent()));
								System.out.println(
										tagField.getId() + ": "
												+ new String(tagField.getRawContent(), StandardCharsets.ISO_8859_1));
								System.out.println(
										tagField.getId() + ": " + new String(tagField.getRawContent(), "EUC-JP"));
								System.out.println(
										tagField.getId() + ": " + new String(tagField.getRawContent(), USE_CHARSET));
							}
						}

						// タグが存在する場合
						if (tag != null) {
							// 全ての使用するフィールドに対して実行
							for (FieldKey fieldKey : FIELDKEY_ARRAY) {
								// フィールドの値を取得
								String fieldValue = getFieldFromTag(tag, fieldKey);
								System.out.println(fieldKey.name() + ": " + fieldValue);

								// フィールドの値が存在する場合
								if (StringUtils.isNotBlank(fieldValue)) {
									if (!DEBUG_MODE) {
										// 子ディレクトリを作成
										File childDirectory = new File(TARGET_DIRECTROY,
												sanitizeDirectoryName(fieldValue));

										// ファイルを子ディレクトリへ移動
										FileUtils.moveFileToDirectory(file, childDirectory, true);
									}

									// 終了
									break;
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}

	/**
	 * タグからフィールドの値を取得.
	 * 
	 * @param tag
	 * @param fieldKey
	 * @return
	 */
	private static String getFieldFromTag(Tag tag, FieldKey fieldKey) {
		try {
			return StringUtils.strip(tag.getFirst(fieldKey));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ディレクトリ名をサニタイズ.
	 * 
	 * @param directoryName
	 * @return
	 */
	private static String sanitizeDirectoryName(String directoryName) {
		// FIXME
		directoryName = StringUtils.replaceChars(directoryName, '?', '？');
		directoryName = StringUtils.replaceChars(directoryName, ':', '：');
		return directoryName;
	}
}