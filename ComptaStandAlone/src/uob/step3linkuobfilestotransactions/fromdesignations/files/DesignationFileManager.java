package uob.step3linkuobfilestotransactions.fromdesignations.files;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicDir;
import basicmethods.BasicFile;
import basicmethods.ReadFileInLinesWithMap;
import uob.staticdata.DesignationStatic;
import uob.staticdata.DesignationStatic.Column;
import uob.staticdata.UOBStatic;
import uob.step3linkuobfilestotransactions.fromdesignations.FromDesignationManager;
import uob.step3linkuobfilestotransactions.fromdesignations.designations.DesignationGroup;

public class DesignationFileManager {

	public DesignationFileManager(FromDesignationManager _sFromDesignationManager) {
		mFromDesignationManager = _sFromDesignationManager;
		/*
		 * 
		 */
		mMapNameToDesignationFile = new HashMap<>();
	}

	/*
	 * Data
	 */
	private FromDesignationManager mFromDesignationManager;
	private Map<String, DesignationFile> mMapNameToDesignationFile;
	
	/**
	 * 
	 */
	public final void loadFileConfIntoDesignation() {
		/*
		 * Load files in the directory with the proper suffix
		 */
		String lDir = UOBStatic.getDIR_CONF_DYNAMIC();
		String lSuffix = UOBStatic.getNAME_FILE_DESIGNATION();
		BasicDir lBasicDir = new BasicDir(lDir, lSuffix);
		/*
		 * 
		 */
		for (BasicFile lBasicFile : lBasicDir.getmMapDateToBasicFile().values()) {
			/*
			 * Read and load the file
			 */
			ReadFileInLinesWithMap lReadFileInLinesWithMap = new ReadFileInLinesWithMap(lDir, lBasicFile.getmNameFile(), true);
			DesignationFile lDesignationFile = getmOrCreateDesignationFile(lReadFileInLinesWithMap, lBasicFile.getmDate());
			/*
			 * Create the DesignationGroup which is a group of the Designation which are in the same CONF file (this BasicFile)
			 */
			DesignationGroup lDesignationGroup = mFromDesignationManager.getmDesignationManager().getmOrCreateDesignationGroup(lBasicFile.getmDate());
			lDesignationGroup.setmDesignationFile(lDesignationFile);
			/*
			 * Loop over the lines of the CONF file of designation
			 */
			for (List<String> lListLineStr : lReadFileInLinesWithMap.getmListLines()) {
				/*
				 * Load line
				 */
				int lIdx = -1;
				String lDesignationStr = lListLineStr.get(++lIdx);
				String lColumnStr = lListLineStr.get(++lIdx);
				String lCurrency = lListLineStr.get(++lIdx);
				String lAccount = lListLineStr.get(++lIdx);
				String lBKCategory = lListLineStr.get(++lIdx);
				/*
				 * Special case of ' which is used for large numbers, to prevent Excel from rounding them
				 */
				if (lDesignationStr.startsWith("'")) {
					lDesignationStr = lDesignationStr.substring(1);
				}
				lDesignationStr = lDesignationStr.trim();
				/*
				 * Check the BKCategory exists (check against the CONF file BKIncome)
				 */
				mFromDesignationManager.getmMainManager().getmUOBBKIncomeManager().checkBKIncomeExists(this, lBKCategory);
				/*
				 * Get the ENUM Column
				 */
				Column lColumn = DesignationStatic.getColumnEnum(lColumnStr);
				if (lColumn == null) {
					mFromDesignationManager.getmComManager().displayFatalError(this, 
							"The name of the column is not among the ones I know."
							+ "\nName of the column given in file= '" + lColumnStr + "'"
							+ "\nFile= '" + lBasicFile.getmLitUnFichierEnLignes().getmNomCheminPlusFichier() + "'"
							+ "\nList of columns that I know= " + Column.values());
				}
				/*
				 * Create and fill the designation --> we make the link between the UOB account and the COMPTA 
				 */
				lDesignationGroup.createAndFillNewDesignation(lDesignationStr, lColumn, lCurrency, 
						lDesignationFile.getmReadFileInLinesWithMap(), 
						lListLineStr, lAccount, lBKCategory);
			}
		}
	}

	/**
	 * Classic get or create
	 * @param _sReadFileInLinesWithMap
	 * @return
	 */
	public final DesignationFile getmOrCreateDesignationFile(ReadFileInLinesWithMap _sReadFileInLinesWithMap, int _sDate) {
		String lNameFile = _sReadFileInLinesWithMap.getmLitUnFichierEnLignes().getmNomFichier();
		DesignationFile lDesignationFile = mMapNameToDesignationFile.get(lNameFile);
		if (lDesignationFile == null) {
			lDesignationFile = new DesignationFile(this, _sReadFileInLinesWithMap);
			mMapNameToDesignationFile.put(lNameFile, lDesignationFile);
		}
		return lDesignationFile;
	}

	/*
	 * Getters & Setters
	 */
	public final FromDesignationManager getmFromDesignationManager() {
		return mFromDesignationManager;
	}
	
	
	
}
