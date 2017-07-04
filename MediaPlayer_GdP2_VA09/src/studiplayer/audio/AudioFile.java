package studiplayer.audio;
import java.io.File;

public abstract class AudioFile {

	/* ------------------------------------------------- */
	// Globals
	private String pathName = null;
	protected String fileName = null;
	protected String author = null;
	protected String title = null;
	protected String[] store = new String[4];
	protected long duration = 0;

	
	/* ------------------------------------------------- */
	// abstract functions
	public abstract void play() throws NotPlayableException;
	public abstract void togglePause();
	public abstract void stop();
	public abstract String getFormattedDuration();
	public abstract String getFormattedPosition();
	public abstract String[] fields();
	
	/* ------------------------------------------------- */
	// Constructors
	public AudioFile() {
	}
	
	@SuppressWarnings("unused")
	public AudioFile(String input) throws NotPlayableException {
		
		// handle NotPlayableException
		if (this == null) {
			throw new NotPlayableException(input, "File not accessible");
		}
		
		parsePathname(input);
		parseFilename(getFilename());
		
		// check if Path is viable
		File path = new File(getPathname());
		if (!(path.canRead())) {
			throw new NotPlayableException(getPathname(), "Path is not readable");
		}
	}
	
	/* ------------------------------------------------- */
	// function to check if Windows OS
	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
	}
	
	// modify Pathname
	public void parsePathname(String inputStr) {
		
		// locals
		String driveLetter = null;
		String sep = File.separator;
		int fileNameIndex = 0;
		
		// replace multiple '//' with '/' and '\\' with '/'
		while ((inputStr.contains("//")) || (inputStr.contains("\\"))) {
			inputStr = inputStr.replace("//", "/");
			inputStr = inputStr.replace("\\", "/");
		}
		
		// check for driveName and store
		if (inputStr.contains(":")) {
			driveLetter = inputStr.substring(0,1);
		}
		
		// check if Linux OS and driveLetter is existent
		if (!isWindows() && (driveLetter != "")) {
			pathName = ("/" + inputStr);					// add '/' at the start
			pathName = pathName.replace(":", "");			// remove ":"
			
		}
		// check for Linux OS -> prepare fileName
		if (!isWindows()) {
			// prepare fileName
			fileNameIndex = pathName.lastIndexOf("/");
			fileName = pathName.substring(fileNameIndex + 1);
		}
		
		// check if Windows - turn the "/" around to "\"
		if (isWindows()) {
			pathName = inputStr;
			pathName = pathName.replaceAll("/", sep+sep);
			
			// prepare fileNamesss
			fileNameIndex = pathName.lastIndexOf(sep);
			fileName = pathName.substring(fileNameIndex + 1);
		}
	}
	
	// modify Filename
	public void parseFilename(String fileStr) {
		
		// locals
		int seperatorIndex = 0;
		
		// search for "-" and split the fileName into author and title and trim (remove spaces)
		if (fileStr.contains(" - ")) {
			seperatorIndex = fileStr.indexOf(" - ");
			if (seperatorIndex < 0) {
				seperatorIndex = 0;
			}
			author = (fileStr.substring(0, seperatorIndex)).trim();
			title = (fileStr.substring(seperatorIndex + 3)).trim();  // "+ 3": 3 chars (" - ")
		// otherwise title is fileInput
		} else {
			author = "";
			title = fileStr;
			title = title.trim();
		}
		
		// remove type of file-appendix and trim (if: prevent out-of-bounds-error)
		if (title.contains(".")) {
			title = title.substring(0, title.lastIndexOf("."));
			title = title.trim();
		}		
	}
	
	/* ------------------------------------------------- */
	// Getters
	public String getPathname() {
		return pathName;
	}
	
	public String getFilename() {
		return fileName;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getTitle() {
		return title;
	}
	
	
	// toString
	public String toString() {
		// no author available: return "title"
		// else:				return "author - title"
		if (getAuthor().isEmpty()) {
			return getTitle();
		} else {
			return getAuthor() + " - " + getTitle();
		}
		
	}

}
