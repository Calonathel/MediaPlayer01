import java.io.File;
import java.io.FileWriter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.IOException;
import java.util.Date;

@SuppressWarnings("serial")
public class PlayList extends LinkedList<AudioFile> {

	/* ------------------------------------------------- */
	// Globals
	private int currentIndex;
	private boolean randomOrderAttribute;
	
	/* ------------------------------------------------- */
	// Constructors
	public PlayList() {
		currentIndex = 0;
		randomOrderAttribute = false; // standard is squential playback
	}
	
	public PlayList(String pathname) {
		// call empty constructor
		this();
		// load M3U file
		loadFromM3U(pathname);
	}
	
	/* ------------------------------------------------- */
	// Getters
	public int getCurrent() {
		return currentIndex;
	}
	
	// return the AudioFile at currentIndex
	public AudioFile getCurrentAudioFile() {
		// check errors;
		// check if the playlist is empty or deletion of elements changed the list number, if so - return null
		if ((currentIndex < 0) || (currentIndex >= this.size())) {
			return null;
		} else {
		// else: return AudioFile at currentIndex	
			return this.get(currentIndex);
		}
	}
	
	/* ------------------------------------------------- */
	// Setters
	public void setCurrent(int current) {
		currentIndex = current;
	}
	
	// set random order for the playlist true or false
	public void setRandomOrder(boolean randomOrder) {
		if (randomOrder) {
			randomOrderAttribute = true;
			Collections.shuffle(this);
		} else {
			randomOrderAttribute = false;
		}
	}
	
	/* ------------------------------------------------- */
	// Functions
	
	// change the index depending on current index
	public void changeCurrent() {
		// check if index is invalid, if so set to 0
		if (this.getCurrentAudioFile().equals(null)) {
			currentIndex = 0;
		} else {
		// check the ring structure
		// check if it is not last element, if so add continuously   ||   else set to 0
			if (currentIndex != (this.size() - 1)) {
				currentIndex++;
			} else {
				currentIndex = 0;
				// if randomOrder is true, shuffle the playlist
				if (randomOrderAttribute) {
					setRandomOrder(true);
				}
			}
		}
	}

	
	// save playlist as m3u text file
	public void saveAsM3U (String pathname) {
		// instantiate a date object (for extra exercise)
		Date date = new Date();
		// create empty filewriter object
		FileWriter writer = null;
		// write lines to file using line.separator
		String linesep = System.getProperty("line.separator");		// String linesep = "\n"
		
		try {
			// create a FileWriter
			writer = new FileWriter(pathname);
			// what to write to file
			// loop through elements of the created playlist objects and write it to file 'pathname'
			for (int i = 0; i < this.size(); i++) {
				writer.write(this.get(i).getPathname() + linesep);
				// also add author and timestamp
				writer.write("# Phil Z " + date.toString() + linesep);
			}
		} catch (IOException e) {
			// catch excpetion e
			throw new RuntimeException("Unable to write to file " + pathname + ":" + e.getMessage());
		} finally {
			try {
				// close file handle in any case!!
				writer.close();
				} catch (Exception e) {
					// just swallow any exception caused by the 'finally' block
				}
			}
		}
	
	// load playlist from m3u text file
	public void loadFromM3U (String pathname) {
		// create empty scanner object
		Scanner scanner = null;
		String line;
		
		try {
			// create a Scanner
			scanner = new Scanner(new File(pathname));
			// clear already stored playlist
			this.clear();
			// get paths from the file, store them in 'line' and then add them to AudioFileFactory
			while (scanner.hasNextLine()) {
				line = scanner.nextLine().trim();
				// check if lines are empty or if it is a comment (starts with '#')
				if (!(line.isEmpty()) && !(line.startsWith("#"))) {
					try {
						// add to AudioFileFactory
						this.add(AudioFileFactory.getInstance(line));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		} catch (IOException e) {
			// catch exception
			throw new RuntimeException(e);
		} finally {
			// close file handle in any case!
			try {
				scanner.close();
			} catch (Exception e) {
				// just swallow any exception caused by the 'finally' block
			}
		}
	}
}
	

