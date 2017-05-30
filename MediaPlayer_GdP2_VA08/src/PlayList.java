import java.util.Collections;
import java.util.LinkedList;

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
}
