package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {
	
	public int compare(AudioFile af1, AudioFile af2) {
		// catch null-point on either file
		if ((af1 == null) || (af2 == null)) {
			throw new NullPointerException("At least one file is empty");
		}

		// check if the handed files are not TaggedFiles - if so abort and return given numbers
		// if both instances are wrong
		if (!(af1 instanceof TaggedFile) && !(af2 instanceof TaggedFile)) {
			return 0;
		}
		// if the first instance is wrong
		if (!(af1 instanceof TaggedFile) && (af2 instanceof TaggedFile)) {
			return -1;
		}		
		// if the second instance is wrong
		if ((af1 instanceof TaggedFile) && !(af2 instanceof TaggedFile)) {
			return 1;
		}
		
		// set af1 as tf1 and af2 as tf2 (AudioFile to TaggedFile)
		TaggedFile tf1 = (TaggedFile) af1;
		TaggedFile tf2 = (TaggedFile) af2;
		
		// compare
		return tf1.getAlbum().compareTo(tf2.getAlbum());
	}
}
