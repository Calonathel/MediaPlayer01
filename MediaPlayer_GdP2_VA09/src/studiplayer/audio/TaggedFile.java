package studiplayer.audio;
import java.util.Map;
import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {
	
	/* ------------------------------------------------- */
	// locals
	protected String album = "";

	/* ------------------------------------------------- */
	// Constructors
	public TaggedFile() {
		super();
	}
	
	public TaggedFile(String input) throws NotPlayableException {
		super(input);
		readAndStoreTags(getPathname());
	}
	
	/* ------------------------------------------------- */
	// read and store tags of file
	public void readAndStoreTags(String inputPath) throws NotPlayableException {
		
		try {
			// map the tags
			Map<String, Object> tag_map = studiplayer.basic.TagReader.readTags(inputPath);
			
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
				if (key.equals("duration") && (!(tag_map.get(key).equals(null)) || !(tag_map.get(key).equals("0")))) {
					duration = (long) tag_map.get(key);
				}
			}	
		} catch (Exception e) {
			throw new NotPlayableException(getPathname(), e);
		}
		
	}
	
	// store attributes of the song (author-title-album-duration) in an array
	public String[] fields() {
		// new array
		String[] store = new String[4];
		// populate with tags
		store[0] = getAuthor();
		store[1] = getTitle();
		store[2] = getAlbum();
		store[3] = getFormattedDuration();	
		return store;
	}
	
	/* ------------------------------------------------- */
	// Getters
	public String getAlbum() {
		return album;
	}
	
	/* ------------------------------------------------- */
	public String toString() {
		if (album.isEmpty()) {
			return (super.toString() + " - " + getFormattedDuration());
		} else {
			return (super.toString() + " - " + album + " - " + getFormattedDuration() );
		}
	}
	
}
