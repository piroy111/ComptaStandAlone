package uob.step3linkuobfilestotransactions.fromdesignations.designations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.ReadFileInLinesWithMap;
import uob.staticdata.DesignationStatic.Column;
import uob.step1objects.transactions.UOBTransaction;
import uob.step3linkuobfilestotransactions.fromdesignations.files.DesignationFile;

public class DesignationGroup {

	public DesignationGroup(DesignationManager _sDesignationManager, int _sDateMaxToApply) {
		mDesignationManager = _sDesignationManager;
		mDateMaxToApply = _sDateMaxToApply;
		/*
		 * 
		 */
		mListUOBTransaction = new ArrayList<>();
		mMapColumToListDesignation = new HashMap<>();
		mMapKeyToUOBDesignation = new HashMap<>();
		mMapColumToMapNameToDesignation = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private int mDateMaxToApply;
	private DesignationManager mDesignationManager;
	private Map<String, Designation> mMapKeyToUOBDesignation;
	private Map<Column, List<Designation>> mMapColumToListDesignation;
	private Map<Column, Map<String, Designation>> mMapColumToMapNameToDesignation;
	private List<UOBTransaction> mListUOBTransaction;
	private DesignationFile mDesignationFile;
	
	
	/**
	 * classic get or create
	 * @return
	 */
	private Designation getmOrCreateDesignation(String _sDesignationStr, Column _sColumn, String _sCurrency) {
		String lKey = Designation.getKeyUOBDesignation(_sDesignationStr, _sColumn, _sCurrency);
		Designation lDesignation = mMapKeyToUOBDesignation.get(lKey);
		if (lDesignation == null) {
			lDesignation = new Designation(this, _sDesignationStr, _sColumn, _sCurrency);
			mMapKeyToUOBDesignation.put(lKey, lDesignation);
			/*
			 * 
			 */
			List<Designation> lListDesignation = mMapColumToListDesignation.get(_sColumn);
			if (lListDesignation == null) {
				lListDesignation = new ArrayList<>();
				mMapColumToListDesignation.put(_sColumn, lListDesignation);
			}
			lListDesignation.add(lDesignation);
			/*
			 * 
			 */
			Map<String, Designation> lMapNameToDesignation = mMapColumToMapNameToDesignation.get(_sColumn);
			if (lMapNameToDesignation == null) {
				lMapNameToDesignation = new HashMap<>();
				mMapColumToMapNameToDesignation.put(_sColumn, lMapNameToDesignation);
			}
			lMapNameToDesignation.put(_sDesignationStr, lDesignation);
		}
		return lDesignation;
	}
	
	/**
	 * 
	 * @param _sDesignation
	 * @param _sLineInFile
	 */
	public final void createAndFillNewDesignation(String _sDesignation, Column _sColumn, String _sCurrency, 
			ReadFileInLinesWithMap _sReadFileInLinesWithMap,
			List<String> _sLineInFile, 
			String _sAccount, 
			String _sBKCategory) {
		/*
		 * Case the designation already exists --> this is a fatal error
		 */
		String lKey = Designation.getKeyUOBDesignation(_sDesignation, _sColumn, _sCurrency);
		if (mMapKeyToUOBDesignation.containsKey(lKey)) {
			String lMsg = "The Designation is twice in the conf file"
					+ "\nConf file= " + _sReadFileInLinesWithMap.getmPathPlusNameFile()
					+ "\nLineStr= " + _sLineInFile.toString()
					+ "\nDesignation= " + _sDesignation;
			mDesignationManager.getmFromDesignationManager().getmComManager().displayFatalError(this, lMsg);
		}
		else {
			Designation lDesignation = getmOrCreateDesignation(_sDesignation, _sColumn, _sCurrency);
			/*
			 * Fill the correspondence (link) between the designation and the COMPTA
			 */
			lDesignation.setmAccount(_sAccount);
			lDesignation.setmBKCategory(_sBKCategory);
		}
	}

	/**
	 * 
	 */	
	public final void addNewUOBTransaction(UOBTransaction _sUOBTransaction) {
		if (!mListUOBTransaction.contains(_sUOBTransaction)) {
			mListUOBTransaction.add(_sUOBTransaction);
		}
	}
	
	/*
	 * Getters & Setters
	 */
	public final int getmDateMaxToApply() {
		return mDateMaxToApply;
	}
	public final DesignationManager getmDesignationManager() {
		return mDesignationManager;
	}
	public final Map<String, Designation> getmMapKeyToUOBDesignation() {
		return mMapKeyToUOBDesignation;
	}
	public final Map<Column, List<Designation>> getmMapColumToListDesignation() {
		return mMapColumToListDesignation;
	}
	public final List<UOBTransaction> getmListUOBTransaction() {
		return mListUOBTransaction;
	}
	public final Map<Column, Map<String, Designation>> getmMapColumToMapNameToDesignation() {
		return mMapColumToMapNameToDesignation;
	}
	public final DesignationFile getmDesignationFile() {
		return mDesignationFile;
	}
	public final void setmDesignationFile(DesignationFile mDesignationFile) {
		this.mDesignationFile = mDesignationFile;
	}
	

	
}
