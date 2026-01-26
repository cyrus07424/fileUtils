package mains;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
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
	private static final File TARGET_DIRECTORY = new File("CHANGE ME");

	/**
	 * 再帰的に処理を実行するかどうか.
	 */
	private static final boolean RECURSIVE = true;

	/**
	 * アーティスト名の取得で使用するフィールドの一覧(優先度が高い順).
	 */
	private static final FieldKey[] ARTIST_FIELD_KEYS = new FieldKey[] { FieldKey.ALBUM_ARTIST, FieldKey.ALBUM_ARTISTS,
			FieldKey.ARTIST, FieldKey.ARTISTS };

	/**
	 * アルバム名の取得で使用するフィールドの一覧(優先度が高い順).
	 */
	private static final FieldKey[] ALBUM_FIELD_KEYS = new FieldKey[] { FieldKey.ALBUM };

	/**
	 * デフォルトのアーティスト名.
	 */
	private static final String DEFAULT_ARTIST = "Unknown Artist";

	/**
	 * デフォルトのアルバム名.
	 */
	private static final String DEFAULT_ALBUM = "Unknown Album";

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
			processDirectory(TARGET_DIRECTORY);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}

	/**
	 * ディレクトリ配下のMP3ファイルを処理.
	 *
	 * @param targetDirectory
	 */
	private static void processDirectory(File targetDirectory) {
		try {
			Collection<File> fileList = FileUtils.listFiles(targetDirectory, new String[] { "mp3" }, false);
			for (File file : fileList) {
				try {
					// オーディオファイルとして読み込み
					AudioFile audioFile = AudioFileIO.read(file);

					// タグを取得
					Tag tag = audioFile.getTag();
					if (DEBUG_MODE && tag != null) {
						// 全てのフィールドに対して実行
						Iterator<TagField> tagFieldIterator = tag.getFields();
						while (tagFieldIterator.hasNext()) {
							// FIXME フィールドの値を取得
							TagField tagField = tagFieldIterator.next();
							System.out.println(tagField.getId() + ": " + new String(tagField.getRawContent()));
							System.out.println(tagField.getId() + ": "
									+ new String(tagField.getRawContent(), StandardCharsets.ISO_8859_1));
							System.out
									.println(tagField.getId() + ": " + new String(tagField.getRawContent(), "EUC-JP"));
							System.out.println(
									tagField.getId() + ": " + new String(tagField.getRawContent(), USE_CHARSET));
						}
					}

					String artist = extractFirstAvailable(tag, ARTIST_FIELD_KEYS, DEFAULT_ARTIST);
					String album = extractFirstAvailable(tag, ALBUM_FIELD_KEYS, DEFAULT_ALBUM);
					System.out.println("ARTIST: " + artist + " / ALBUM: " + album);

					if (!DEBUG_MODE) {
						File artistDirectory = new File(TARGET_DIRECTORY, sanitizeDirectoryName(artist));
						File destinationDirectory = new File(artistDirectory, sanitizeDirectoryName(album));
						File destinationFile = new File(destinationDirectory, file.getName());

						if (destinationFile.exists()) {
							System.out.println("SKIP (exists): " + destinationFile.getAbsolutePath());
							continue;
						}

						FileUtils.forceMkdir(destinationDirectory);
						FileUtils.moveFile(file, destinationFile);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * タグからフィールドの値を優先度順に取得.
	 * 
	 * @param tag
	 * @param keys
	 * @param defaultValue
	 */
	private static String extractFirstAvailable(Tag tag, FieldKey[] keys, String defaultValue) {
		if (tag == null) {
			return defaultValue;
		}
		for (FieldKey key : keys) {
			String value = getFieldFromTag(tag, key);
			if (StringUtils.isNotBlank(value)) {
				return value;
			}
		}
		return defaultValue;
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