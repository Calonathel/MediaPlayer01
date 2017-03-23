import java.io.File;

public class AudioFile {

	/* ------------------------------------------------- */
	// Globals
	private String pathName = null;
	private String fileName = null;
	
	/* ------------------------------------------------- */
	// Constructor
	public AudioFile() {
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
			
			// prepare fileName
			fileNameIndex = pathName.lastIndexOf(sep);
			fileName = pathName.substring(fileNameIndex + 1);
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
	
	
	public static void main(String[] args) {
		AudioFile audioFile = new AudioFile();
		AudioFile audioFile2 = new AudioFile();
		AudioFile audioFile3 = new AudioFile();
		AudioFile audioFile4 = new AudioFile();
		AudioFile audioFile5 = new AudioFile();
		AudioFile audioFile6 = new AudioFile();
		
		audioFile.parsePathname("");
		System.out.println("Pathname: " + audioFile.pathName);
		System.out.println("Filename: " + audioFile.getFilename() + "\n");
		audioFile2.parsePathname("        				");
		System.out.println("Pathname: " + audioFile2.pathName);
		System.out.println("Filename: " + audioFile2.getFilename() + "\n");
		audioFile3.parsePathname("file.mp3");
		System.out.println("Pathname: " + audioFile3.pathName);
		System.out.println("Filename: " + audioFile3.getFilename() + "\n");
		audioFile4.parsePathname("/my-tmp/file.mp3");
		System.out.println("Pathname: " + audioFile4.pathName);
		System.out.println("Filename: " + audioFile4.getFilename() + "\n");
		audioFile5.parsePathname("//my-tmp////part1//file.mp3/");
		System.out.println("Pathname: " + audioFile5.pathName);
		System.out.println("Filename: " + audioFile5.getFilename() + "\n");
		audioFile6.parsePathname("d:\\\\part1///file.mp3");
		System.out.println("Pathname: " + audioFile6.pathName);
		System.out.println("Filename: " + audioFile6.getFilename() + "\n");
	}
	
}
