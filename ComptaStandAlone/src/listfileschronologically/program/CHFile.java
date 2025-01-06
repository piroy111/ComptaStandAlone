package listfileschronologically.program;

import java.io.IOException;
import java.nio.file.Path;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.BasicTime;

public class CHFile implements Comparable<CHFile> {

	protected CHFile(Path _sPath) {
		pPath = _sPath;
		/*
		 * 
		 */
		initiate();
	}
	
	/*
	 * Data
	 */
	private Path pPath;
	private long pTimeLastUpdate;
	private int pDateFromTitle;
	private int pDateFromWindows;
	private String pNameFile;

	/**
	 * 
	 */
	private void initiate() {
		/*
		 * Read date from windows
		 */
		try {
			pTimeLastUpdate = BasicFichiersNio.getLastModifiedTime(pPath);
		} catch (IOException e) {
			BasicPrintMsg.error("Impossbile to read file '" + pPath.toString() + "'");
		}
		/*
		 * Change format of windows into our format
		 */
		pDateFromWindows = BasicTime.getDateFromTimeStamp(pTimeLastUpdate);
		/*
		 * Date from the title of the file
		 */
		pNameFile = pPath.getFileName().toString();
		pDateFromTitle = BasicString.getInt(pNameFile.substring(0, 8));		
	}
	
	
	@Override public int compareTo(CHFile _sCHFile) {
		return Long.compare(pTimeLastUpdate, _sCHFile.pTimeLastUpdate);
	}
	
	/*
	 * Getters & Setters
	 */
	public final Path getpPath() {
		return pPath;
	}
	public final void setpPath(Path pPath) {
		this.pPath = pPath;
	}
	public final long getpTimeLastUpdate() {
		return pTimeLastUpdate;
	}
	public final int getpDateFromTitle() {
		return pDateFromTitle;
	}
	public final int getpDateFromWindows() {
		return pDateFromWindows;
	}
	public final String getpNameFile() {
		return pNameFile;
	}
	
	
}
