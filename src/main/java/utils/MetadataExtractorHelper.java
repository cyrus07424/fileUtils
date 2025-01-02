package utils;

import java.io.EOFException;
import java.io.File;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;

/**
 * Metadata Extractorヘルパー.
 *
 * @author cyrus
 */
public class MetadataExtractorHelper {

	/**
	 * ファイルからメタデータを取得.
	 *
	 * @param file
	 * @return
	 */
	@Nullable
	private static Metadata getMetadata(File file) {
		try {
			return ImageMetadataReader.readMetadata(file);
		} catch (ImageProcessingException e) {
			if (StringUtils.equals(e.getMessage(), "File format could not be determined")) {
				// NOP
			} else {
				e.printStackTrace();
			}
		} catch (EOFException e) {
			if (StringUtils.equals(e.getMessage(), "End of data reached.")) {
				// NOP
			} else {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 全てのEXIFタグを出力.
	 *
	 * @param file
	 */
	public static void dumpAllTag(File file) {
		Metadata metadata = getMetadata(file);
		if (metadata != null) {
			// 全てのディレクトリに対して実行
			for (Directory directory : metadata.getDirectories()) {
				// 全てのタグに対して実行
				for (Tag tag : directory.getTags()) {
					System.out.println(
							directory.getName() + ": " + tag.getTagName() + ": "
									+ directory.getObject(tag.getTagType()));
				}
			}
		}
	}

	/**
	 * EXIF情報から回転情報を取得.
	 *
	 * @param file
	 * @return
	 * @throws MetadataException 
	 */
	public static int getOrientation(File file) throws MetadataException {
		Metadata metadata = getMetadata(file);
		if (metadata != null) {
			ExifIFD0Directory ifdDirectory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			if (ifdDirectory != null && ifdDirectory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
				return ifdDirectory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
			}
		}
		// FIXME
		return 1;
	}

	/**
	 * EXIF情報からタイムゾーンを取得.
	 *
	 * @param file
	 * @return
	 * @throws MetadataException 
	 */
	@Nullable
	public static TimeZone getTimeZone(File file) throws MetadataException {
		// TODO
		return TimeZone.getDefault();
	}

	/**
	 * EXIF情報から撮影日時を取得.
	 *
	 * @param file
	 * @return
	 * @throws MetadataException 
	 */
	@Nullable
	public static Date getDateTime(File file) throws MetadataException {
		TimeZone timeZone = getTimeZone(file);
		return getDateTime(file, timeZone);
	}

	/**
	 * EXIF情報から撮影日時を取得.
	 *
	 * @param file
	 * @param timeZone
	 * @return
	 * @throws MetadataException 
	 */
	@Nullable
	public static Date getDateTime(File file, TimeZone timeZone) throws MetadataException {
		Metadata metadata = getMetadata(file);
		if (metadata != null) {
			ExifIFD0Directory ifd0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			if (ifd0Directory != null) {
				if (ifd0Directory.containsTag(ExifIFD0Directory.TAG_DATETIME_ORIGINAL)) {
					return ifd0Directory.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL, timeZone);
				} else if (ifd0Directory.containsTag(ExifIFD0Directory.TAG_DATETIME_DIGITIZED)) {
					return ifd0Directory.getDate(ExifIFD0Directory.TAG_DATETIME_DIGITIZED, timeZone);
				} else if (ifd0Directory.containsTag(ExifIFD0Directory.TAG_DATETIME)) {
					return ifd0Directory.getDate(ExifIFD0Directory.TAG_DATETIME, timeZone);
				}
			}
			ExifSubIFDDirectory subIfdDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			if (subIfdDirectory != null) {
				if (subIfdDirectory.containsTag(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)) {
					return subIfdDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL, timeZone);
				} else if (subIfdDirectory.containsTag(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED)) {
					return subIfdDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED, timeZone);
				} else if (subIfdDirectory.containsTag(ExifSubIFDDirectory.TAG_DATETIME)) {
					return subIfdDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME, timeZone);
				}
			}
		}
		return null;
	}

	/**
	 * EXIF情報からGPS情報を取得.
	 *
	 * @param file
	 * @return
	 * @throws MetadataException 
	 */
	@Nullable
	public static GpsDirectory getGpsDirectory(File file) throws MetadataException {
		Metadata metadata = getMetadata(file);
		if (metadata != null) {
			return metadata.getFirstDirectoryOfType(GpsDirectory.class);
		}
		return null;
	}
}