import java.util.Map;

import studiplayer.basic.BasicPlayer;
import studiplayer.basic.TagReader;

public class TaggedFile extends AudioFile {

	/* ------------------------------------------------- */
	// Constructors
	public TaggedFile() {
		super();
	}
	
	public TaggedFile(String input) {
		super(input);
		readAndStoreTags(getFilename());
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
	
	// read and store tags of file
	public void readAndStoreTags(String pathname) {
		
		// map the tags
		Map<String, Object> tag_map = TagReader.readTags(pathname);
		
		// loop through the tags
		for (String key : tag_map.keySet()) {
			// check title, author, album and duration and if not empty or null - store appropriately
			//title (check for null String and empty String)
			if (key.equals("title") && (!(tag_map.get(key).equals(null)) || !(tag_map.get(key).equals("")))) {
				title = (String) tag_map.get(key);
				title = title.trim();
			}
			// author (check for null String and empty String)
			if (key.equals("author") && (!(tag_map.get(key).equals(null)) || !(tag_map.get(key).equals("")))) {
				author = (String) tag_map.get(key);
				author = author.trim();
			}
			// album (check for null String and empty String)
			if (key.equals("album") && (!(tag_map.get(key).equals(null)) || !(tag_map.get(key).equals("")))) {
				album = (String) tag_map.get(key);
				album = album.trim();
			}
			// duration (check for null String and value 0
			if (key.equals("album") && (!(tag_map.get(key).equals(null)) || !(tag_map.get(key).equals("0")))) {
				duration = (long) tag_map.get(key);
			}
		}
		
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
