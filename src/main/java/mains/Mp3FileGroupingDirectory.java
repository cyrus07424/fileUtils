package mains;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

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
			for (File file : TARGET_DIRECTROY.listFiles()) {
				try {
					// ファイルの場合
					if (file.isFile()) {
						// オーディオファイルとして読み込み
						AudioFile audioFile = AudioFileIO.read(file);

						try {
							// タグを取得
							Tag tag = audioFile.getTag();

							// タグが存在する場合
							if (tag != null) {
								try {
									// アルバム名を取得
									String album = StringUtils.strip(tag.getFirst(FieldKey.ALBUM));
									System.out.println("album: " + album);

									// アルバム名が存在する場合
									if (StringUtils.isNotBlank(album)) {
										// 子ディレクトリを作成
										File childDirectory = new File(TARGET_DIRECTROY, sanitizeDirectoryName(album));

										// ファイルを子ディレクトリへ移動
										FileUtils.moveFileToDirectory(file, childDirectory, true);
									}
								} catch (Exception e1) {
									e1.printStackTrace();
									try {
										// アルバムのアーティストを取得
										String albumArtist = StringUtils
												.strip(audioFile.getTag().getFirst(FieldKey.ALBUM_ARTIST));
										System.out.println("albumArtist: " + albumArtist);

										// アルバムのアーティストが存在する場合
										if (StringUtils.isNotBlank(albumArtist)) {
											// 子ディレクトリを作成
											File childDirectory = new File(TARGET_DIRECTROY,
													sanitizeDirectoryName(albumArtist));

											// ファイルを子ディレクトリへ移動
											FileUtils.moveFileToDirectory(file, childDirectory, true);
										}
									} catch (Exception e2) {
										e2.printStackTrace();
										try {
											// アルバムのアーティストを取得
											String albumArtists = StringUtils
													.strip(audioFile.getTag().getFirst(FieldKey.ALBUM_ARTISTS));
											System.out.println("albumArtists: " + albumArtists);

											// アルバムのアーティストが存在する場合
											if (StringUtils.isNotBlank(albumArtists)) {
												// 子ディレクトリを作成
												File childDirectory = new File(TARGET_DIRECTROY,
														sanitizeDirectoryName(albumArtists));

												// ファイルを子ディレクトリへ移動
												FileUtils.moveFileToDirectory(file, childDirectory, true);
											}
										} catch (Exception e3) {
											e3.printStackTrace();
										}
									}
								}
							}
						} catch (Exception e3) {
							e3.printStackTrace();
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
	 * ディレクトリ名をサニタイズ.
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