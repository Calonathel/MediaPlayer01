import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	
	/* ------------------------------------------------- */
	// Constructors
	public WavFile() {
		super();
	}
	
	public WavFile(String path) throws NotPlayableException {
		super(path);
		readAndSetDurationFromFile(super.getPathname());
	}

	/* ------------------------------------------------- */
	// compute duration based on numberOfFrames and frameRate
	public static long computeDuration(long numberOfFrames, float frameRate) {
		
		long wavDuration = ((numberOfFrames  * 1000000) / (long) frameRate);
		return wavDuration;
	}
	
	// read and set duration from file
	public void readAndSetDurationFromFile(String inputStr) throws NotPlayableException {
		try {
			// read parameters out of file
			WavParamReader.readParams(inputStr);
			
			// store computed duration
			duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());
		} catch (Exception e) {
			throw new NotPlayableException(inputStr, "e");
		}

	}
	
	// store attributes of the song (author-title-album-duration) in an array
	public String[] fields() {
		// populate with tags
		store[0] = getAuthor();
		store[1] = getTitle();
		store[2] = "";
		store[3] = getFormattedDuration();	
		return store;
		}
	
	/* ------------------------------------------------- */
	// toString: "author - title - duration
	public String toString() {
		String output = (super.toString() + " - " + getFormattedDuration());		
		return output;
	}
	
}
