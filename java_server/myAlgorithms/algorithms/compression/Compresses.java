package algorithms.compression;

import java.io.InputStream;
import java.io.OutputStream;

/**
* Compresses interface.
* All who implements this interface need to write the function compress and decompress.
*
* @author  Bar Magnezi
* @version 1.0
* @since 3.5.2015
*/
public interface Compresses {
	/**
	 * This method compress the data that send in the argument.
	 * @param text The input stream need to contain the data that we want to compress.
	 * @return InputStream that contain the text compress.
	 */
	OutputStream compress(InputStream text);
	/**
	 * This method decompress the data that send in the argument.
	 * @param zipText The input stream need to contain the data compress that we want to decompress.
	 * @return InputStream that contain the text decompress.
	 */
	OutputStream decompress(InputStream zipText);
}
