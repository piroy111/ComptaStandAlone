package uob.step3linkuobfilestotransactions.fromdesignations.files;

import basicmethods.ReadFileInLinesWithMap;

public class DesignationFile {

	
	protected DesignationFile(DesignationFileManager _sDesignationFileManager, ReadFileInLinesWithMap _sReadFileInLinesWithMap) {
		mDesignationFileManager = _sDesignationFileManager;
		mReadFileInLinesWithMap = _sReadFileInLinesWithMap;
	}
	
	/*
	 * Data
	 */
	private DesignationFileManager mDesignationFileManager;
	private ReadFileInLinesWithMap mReadFileInLinesWithMap;
	
	/*
	 * Getters & Setters
	 */
	public final DesignationFileManager getmDesignationFileManager() {
		return mDesignationFileManager;
	}
	public final ReadFileInLinesWithMap getmReadFileInLinesWithMap() {
		return mReadFileInLinesWithMap;
	}

	
	
	
	
	
}
