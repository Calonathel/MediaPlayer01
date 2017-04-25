import studiplayer.basic.BasicPlayer;

public class TaggedFile extends AudioFile {

	/* ------------------------------------------------- */
	// Constructors
	public TaggedFile() {
		super();
	}
	
	public TaggedFile(String input) {
		super(input);
	}
	
	
	
	/* ------------------------------------------------- */
	// start playing the song
	public void play() {
		BasicPlayer.play(getPathname());
	}
	
	// toggle pause
	public void togglePause() {
		BasicPlayer.togglePause();
	}
	
	// stop playing the song
	public void stop() {
		BasicPlayer.stop();
	}
	
	// format time components
	public static String timeFormatter(long microtime) {
		long minutes = 0, seconds = 0;
		long converter = 1000000;
		String output = null;
		
		// convert microseconds to seconds
		seconds = (long) (microtime/converter);
		
		// catch error
		// negative time value
		if (seconds < 0) {
			throw new RuntimeException("Negative time value provided");
		}

		// convert seconds to minutes if possible
		while (seconds >= 60) {
			seconds -= 60;
			minutes += 1;
		}
		
		// catch error
		// time value is more than 59:59
		if ((minutes > 59) || (seconds > 59)) {
			throw new RuntimeException("Time value exceeds allowed format");
		}
	
		// format the string to mm:ss format and return
		output = String.format("%02d:%02d", minutes, seconds);
		return output;	
	}
	
	/* ------------------------------------------------- */
	// Getters
	
	public String getFormattedDuration() {
		return "";
	}
	
	public String getFormattedPosition() {
		return "";
	}
	
	public static void main (String[] args) {
		TaggedFile.timeFormatter(3600002000L);
	}
	
}
