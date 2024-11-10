package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 * Tikaヘルパー.
 *
 * @author cyrus
 */
public class TikaHelper {

	/**
	 * ファイルのコンテンツタイプを取得.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getContentType(File file) throws IOException {
		return new Tika().detect(file);
	}

	/**
	 * ファイルのメタデータを取得.
	 *
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws TikaException 
	 */
	@Deprecated
	public static Metadata getMetadata(File file) throws IOException, SAXException, TikaException {
		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		try (InputStream stream = new FileInputStream(file)) {
			parser.parse(stream, handler, metadata);
			return metadata;
		}
	}

	/**
	 * ファイルのコンテンツタイプから拡張子を取得.
	 *
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String getExtention(File file) throws IOException {
		switch (getContentType(file)) {
		case "image/jpeg":
			return "jpg";
		case "image/png":
			return "png";
		case "image/gif":
			return "gif";
		case "image/bmp":
			return "bmp";
		case "image/webp":
			return "webp";
		case "image/avif":
			return "avif";
		case "image/vnd.zbrush.pcx":
			return "pcx";
		case "video/mp4":
			return "mp4";
		case "video/quicktime":
			return "mov";
		case "audio/mpeg":
			return "mp3";
		case "text/html":
			return "html";
		case "application/xhtml+xml":
			return "xhtml";
		case "application/x-font-ttf":
			return "ttf";
		case "application/x-dvi":
			return "avi";
		case "application/msword2":
			return "doc";
		case "application/vnd.ms-fontobject":
			return "eot";
		case "application/zlib":
			return "zip";
		case "text/x-matlab":
			return "m";
		case "text/plain":
			return "txt";
		case "application/octet-stream":
		case "application/x-tika-msoffice":
		case "application/vnd.novadigm.ext":
			return "ext";
		default:
			System.err.println("■contentType: " + getContentType(file));

			// FIXME
			return "ext";
		}
	}
}