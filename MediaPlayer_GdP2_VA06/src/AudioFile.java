import java.io.File;

public class AudioFile {

	/* ------------------------------------------------- */
	// Globals
	private String pathName = null;
	private String fileName = null;
	private String author = null;
	private String title = null;
	
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
	
	
	public static void main(String[] args) {
		AudioFile audioFile = new AudioFile();
		AudioFile audioFile2 = new AudioFile();
		AudioFile audioFile3 = new AudioFile();
		AudioFile audioFile4 = new AudioFile();
		AudioFile audioFile5 = new AudioFile();
		AudioFile audioFile6 = new AudioFile();
		AudioFile audioFile7 = new AudioFile();
		AudioFile audioFile8 = new AudioFile();
		AudioFile audioFile9 = new AudioFile();
		
		/*
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
		*/
		System.out.println("-------------------------------------------------------------\n");
		audioFile.parseFilename(" Falco  -  Rock me    Amadeus .mp3  ");
		System.out.println("Input:  Falco  -  Rock me    Amadeus .mp3  \n");
		System.out.println("Author: " + audioFile.author + "\n");
		System.out.println("Title: " + audioFile.title + "\n");
		
		System.out.println("-------------------------------------------------------------\n");
		audioFile2.parseFilename("Frankie Goes To Hollywood - The Power Of Love.ogg");
		System.out.println("Frankie Goes To Hollywood - The Power Of Love.ogg\n");
		System.out.println("Author: " + audioFile2.author + "\n");
		System.out.println("Title: " + audioFile2.title + "\n");
		
		System.out.println("-------------------------------------------------------------\n");
		audioFile3.parseFilename("audiofile.aux");
		System.out.println("audiofile.aux\n");
		System.out.println("Author: " + audioFile3.author + "\n");
		System.out.println("Title: " + audioFile3.title + "\n");
		
		System.out.println("-------------------------------------------------------------\n");
		audioFile4.parseFilename("   A.U.T.O.R   -  T.I.T.E.L  .EXTENSION");
		System.out.println("   A.U.T.O.R   -  T.I.T.E.L  .EXTENSION\n");
		System.out.println("Author: " + audioFile4.author + "\n");
		System.out.println("Title: " + audioFile4.title + "\n");
		
		System.out.println("-------------------------------------------------------------\n");
		audioFile5.parseFilename("Hans-Georg Sonstwas - Blue-eyed boy-friend.mp3");
		System.out.println("Hans-Georg Sonstwas - Blue-eyed boy-friend.mp3\n");
		System.out.println("Author: " + audioFile5.author + "\n");
		System.out.println("Title: " + audioFile5.title + "\n");
		
		System.out.println("-------------------------------------------------------------\n");
		audioFile6.parseFilename(".mp3");
		System.out.println(".mp3\n");
		System.out.println("Author: " + audioFile6.author + "\n");
		System.out.println("Title: " + audioFile6.title + "\n");
		
		System.out.println("-------------------------------------------------------------\n");
		audioFile7.parseFilename("Falco - Rock me Amadeus.");
		System.out.println("Falco - Rock me Amadeus.\n");
		System.out.println("Author: " + audioFile7.author + "\n");
		System.out.println("Title: " + audioFile7.title + "\n");
		
		System.out.println("-------------------------------------------------------------\n");
		audioFile8.parseFilename("-");
		System.out.println("-\n");
		System.out.println("Author: " + audioFile8.author + "\n");
		System.out.println("Title: " + audioFile8.title + "\n");
		
		System.out.println("-------------------------------------------------------------\n");
		audioFile9.parseFilename(" - ");
		System.out.println(" - \n");
		System.out.println("Author: " + audioFile9.author + "\n");
		System.out.println("Title: " + audioFile9.title + "\n");
		
	}
	
}
