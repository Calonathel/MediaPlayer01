package studiplayer.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;


@SuppressWarnings("serial")
public class Player extends JFrame implements ActionListener {
	
	
	// Constants
	private static final String EMPTY_DESCRIPTION = "no current song";
	private static final String EMPTY_PLAYTIME = "--:--";
	private static final String EMPTY_TITLE = "Studiplayer: empty play list";
	private static final String INITIAL_TIME = "00:00";
	private static final String INITIAL_TITLE = "Current song: ";
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	
	// Attributes
	private PlayList playList;
	private JLabel songDescription;
	private JLabel playTime;
	private volatile boolean stopped;
	private PlayListEditor playListEditor;
	private boolean editorVisible;
	
	// Buttons
	private JButton bplay;
	private JButton bpause;
	private JButton bstop;
	private JButton bnext;
	private JButton bpl_editor;
	
	// Constructor
	public Player(PlayList pl) {
		
		// Set stooped attribute
		stopped = true;
		// Set given playlist
		playList = pl;
		// initialize playlist editor and visibility
		playListEditor = new PlayListEditor(this, this.playList);
		editorVisible = false;
		
		// Initialize the main frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Labels for description and time
		songDescription = new JLabel();
		playTime = new JLabel();
		
		// Update song info
		if (playList.size() > 0) {
			updateSongInfo(playList.getCurrentAudioFile());
		} else {
			updateSongInfo(null);
		}
		
		// Create GUI components
		// Panel for buttons
		JPanel buttons = new JPanel();
		// Create the buttons
		bplay = new JButton(new ImageIcon("icons/play.png"));
		bplay.setEnabled(true);
		bplay.setActionCommand("AC_PLAY");
		bplay.addActionListener(this);;
		buttons.add(bplay);
		// pause
		bpause = new JButton(new ImageIcon("icons/pause.png"));
		bpause.setEnabled(false);
		bpause.setActionCommand("AC_PAUSE");
		bpause.addActionListener(this);
		buttons.add(bpause);
		// stop
		bstop = new JButton(new ImageIcon("icons/stop.png"));
		bstop.setEnabled(false);
		bstop.setActionCommand("AC_STOP");
		bstop.addActionListener(this);
		buttons.add(bstop);
		// next
		bnext = new JButton(new ImageIcon("icons/next.png"));
		bnext.setEnabled(true);
		bnext.setActionCommand("AC_NEXT");
		bnext.addActionListener(this);
		buttons.add(bnext);
		// pl editor
		bpl_editor = new JButton(new ImageIcon("icons/pl_editor.png"));
		bpl_editor.setEnabled(true);
		bpl_editor.setActionCommand("AC_PLEDITOR");
		bpl_editor.addActionListener(this);
		buttons.add(bpl_editor);
		
		// Add components to frame
		this.add(buttons, BorderLayout.CENTER);
		this.add(songDescription, BorderLayout.NORTH);
		this.add(playTime, BorderLayout.WEST);
		
		// Activate GUI
		this.pack();
		this.setVisible(true);
	}
	
	
	// update song info
	private void updateSongInfo(AudioFile af) {
		if (af != null) {
			songDescription.setText(af.toString());
			this.setTitle(INITIAL_TITLE + af.toString());
			
			playTime.setText(INITIAL_TIME);
		} else {
			this.setTitle(EMPTY_TITLE);
			songDescription.setText(EMPTY_DESCRIPTION);
			playTime.setText(EMPTY_PLAYTIME);
		}
	}
	
	// add the actionPerformed method for buttons
	public void actionPerformed(ActionEvent ae) {
		AudioFile af;
		String cmd = ae.getActionCommand();
		
		switch (cmd) {
		case "AC_PLAY": {
			// button play
			playCurrentSong();
			// set availability
			bplay.setEnabled(false);
			bpause.setEnabled(true);
			bstop.setEnabled(true);
			break;
		}
		
		case "AC_PAUSE": {
			// button pause
			af = playList.getCurrentAudioFile();
			if (af != null) {
				af.togglePause();
			}
			// Set availability
			bplay.setEnabled(false);
			bpause.setEnabled(true);
			bstop.setEnabled(true);
			break;
			
		} case "AC_STOP": {
			// button stop
			stopCurrentSong();
			// Set availability
			bplay.setEnabled(true);
			bpause.setEnabled(false);
			bstop.setEnabled(false);
			break;
			
		} case "AC_NEXT": {
			// button next
			// Switch to next song, then the usual
			if (!stopped) {
				// We are playing
				// Stop playing song
				stopCurrentSong();
			}
			// We are stopped or not playing
			// Move on to the next song in the playlist
			playList.changeCurrent();
			// Play the next song
			playCurrentSong();
			bplay.setEnabled(false);
			bpause.setEnabled(true);
			bstop.setEnabled(true);
			break;
		} case "AC_PLEDITOR": {
			if (editorVisible) {
				editorVisible = false;
			} else {
				editorVisible = true;
			}
			playListEditor.setVisible(editorVisible);
		}
		default:
			break;
		}
	}
		
	// Play current song
	private void playCurrentSong() {
		AudioFile af = playList.getCurrentAudioFile();
		updateSongInfo(af);
		stopped = false;
		if (af != null) {
			// Start threads
			(new TimerThread()).start();
			(new PlayerThread()).start();
		}
	}
	
	// Stop current song
	private void stopCurrentSong() {
		AudioFile af = playList.getCurrentAudioFile();
		stopped = true;
		if (af != null) {
			af.stop();
		}
		updateSongInfo(af);
	}
	
	// Timer Thread Class
	private class TimerThread extends Thread {
		public void run() {
			AudioFile af = playList.getCurrentAudioFile();
			while (!stopped && (playList != null)) {
				playTime.setText(af.getFormattedPosition());
				try {
					sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Player thread class
	private class PlayerThread extends Thread {	
		public void run() {
			AudioFile af = playList.getCurrentAudioFile();
			while (!stopped && (playList != null)) {
				try {
					af.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(!stopped) {
					playList.changeCurrent();
					af = playList.getCurrentAudioFile();
					updateSongInfo(af);
				}
			}
		}
	}
	
	// MAIN
	public static void main(String[] args) {
		
		// New playlist object
		PlayList pl = new PlayList();
		
		// If argument is given, try to load the given playlist
		if (args.length > 0) {
			try {
				pl.loadFromM3U(args[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {	// Else use DEFAULT_PLAYLIST
			try {
				pl.loadFromM3U(DEFAULT_PLAYLIST);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// New player object
		new Player(pl);
	}
	
	
}
