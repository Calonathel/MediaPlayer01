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
	
	/* ------------------------------------------------- */
	// Getters
	
	public String getFormattedDuration() {
		return "";
	}
	
	public String getFormattedPosition() {
		return "";
	}
	
}
