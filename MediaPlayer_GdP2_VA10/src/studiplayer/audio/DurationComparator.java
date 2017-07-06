package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {

	public int compare (AudioFile af1, AudioFile af2) {
		
		// catch empty files
		if ((af1 == null) || (af2 == null)) {
			throw new NullPointerException("At least one file is empty");
		}
		
		// compare files
		// if both files are TaggedFiles
		if ((af1 instanceof TaggedFile) && (af2 instanceof TaggedFile)) {
			TaggedFile tf1 = (TaggedFile) af1;
			TaggedFile tf2 = (TaggedFile) af2;
			return tf1.getFormattedDuration().compareTo(tf2.getFormattedDuration());
		}
		// if only the first file is a TaggedFile
		if ((af1 instanceof TaggedFile) && !(af2 instanceof TaggedFile)) {
			TaggedFile tf1 = (TaggedFile) af1;
			WavFile wf2 = (WavFile) af2;
			return tf1.getFormattedDuration().compareTo(wf2.getFormattedDuration());
		}
		// if only the second file is a TaggedFile
		if (!(af1 instanceof TaggedFile) && (af2 instanceof TaggedFile)) {
			WavFile wf1 = (WavFile) af1;
			TaggedFile tf2 = (TaggedFile) af2;
			return wf1.getFormattedDuration().compareTo(tf2.getFormattedDuration());
		}
		// otherwise set WavFile
		WavFile wf1 = (WavFile) af1;
		WavFile wf2 = (WavFile) af2;
		return wf1.getFormattedDuration().compareTo(wf2.getFormattedDuration());
	}	
}
