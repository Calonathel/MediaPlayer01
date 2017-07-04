package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile> {

	public int compare(AudioFile af1, AudioFile af2) {
		// catch null-point on either file
		if ((af1 == null) || (af2 == null)) {
			throw new NullPointerException("At least one file is empty");
		}
		
		// compare authors
		return af1.getAuthor().compareTo(af2.getAuthor());
	}

}
