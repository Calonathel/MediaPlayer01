import java.io.File;

public class AudioFileFactory {

	// create a WavFile or TaggedFile based on path-endings
	public static AudioFile getInstance (String pathname) {
		// create empty AudioFile
		AudioFile audiofile = null;
		
		// check for errors (file readable?)
		if (new File(pathname).canRead()) {
			// distinguish between Wav- or Tagged-File
			// not case-sensitive!
			if (pathname.toLowerCase().endsWith("wav")) {
				// WavFile
				audiofile = new WavFile(pathname);
			} else if (pathname.toLowerCase().endsWith("mp3") || pathname.toLowerCase().endsWith("ogg")) {
				// TaggedFile
				audiofile = new TaggedFile(pathname);
			} else {
				// catch errors
				throw new RuntimeException("Unknown suffix for AudioFile: \"" + pathname + "\"");
			}
		} else {
			// file not readable
			throw new RuntimeException ("File can't be read: " + pathname);
		}
		// return created AudioFile
		return audiofile;
	}
	
}
