package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {

	public int compare(AudioFile af1, AudioFile af2) {
		// catch null-pointer on either file
		if ((af1 == null) || (af2 == null)) {
			throw new NullPointerException("At least one file is empty");
		}
		
		// compare titles
		return af1.getTitle().compareTo(af2.getTitle());
	}

}
