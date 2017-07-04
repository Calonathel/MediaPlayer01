package studiplayer.audio;
import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile {

	
	/* ------------------------------------------------- */
	// Constructors
	public SampledFile() {
		super();
	}
	
	public SampledFile(String inputStr) throws NotPlayableException {
		super(inputStr);
	}

	/* ------------------------------------------------- */
	// start playing the song
	public void play() throws NotPlayableException {
		try {
			BasicPlayer.play(getPathname());
		} catch (Exception e) {
			throw new NotPlayableException(getPathname(), e);
		}
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
		
		// catch errors
		// negative time value
		if (microtime < 0) {
			throw new RuntimeException("Negative time value provided");
		}
		// negative time value
		if (microtime >= 6000000000L) {
			throw new RuntimeException("Negative time value provided");
		}
		
		// convert microseconds to seconds
		seconds = (long) (microtime/converter);

		// convert seconds to minutes if possible
		while (seconds >= 60) {
			seconds -= 60;
			minutes += 1;
		}
		
		// format the string to mm:ss format and return
		return (String.format("%02d:%02d", minutes, seconds));
	}
		
	/* ------------------------------------------------- */
	// Getters	
	public String getFormattedDuration() {
		return timeFormatter(duration);
	}
		
	public String getFormattedPosition() {
		return timeFormatter(studiplayer.basic.BasicPlayer.getPosition());
	}
}
