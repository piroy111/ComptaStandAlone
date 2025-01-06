package uob.step3linkuobfilestotransactions.fromdesignations.designations;

import java.util.Map;
import java.util.TreeMap;

import uob.step3linkuobfilestotransactions.fromdesignations.FromDesignationManager;

public class DesignationManager {

	public DesignationManager(FromDesignationManager _sFromDesignationManager) {
		mFromDesignationManager = _sFromDesignationManager;
		/*
		 * Instantiate
		 */
		mTreeMapDateMaxToDesignationGroup = new TreeMap<>();
	}

	/*
	 * Data
	 */
	private FromDesignationManager mFromDesignationManager;
	private Map<String, Designation> mMapKeyToUOBDesignation;
	private TreeMap<Integer, DesignationGroup> mTreeMapDateMaxToDesignationGroup;

	/**
	 * 
	 * @param _sDateMax
	 */
	public final DesignationGroup getmOrCreateDesignationGroup(int _sDateMaxToApply) {
		DesignationGroup lDesignationGroup = mTreeMapDateMaxToDesignationGroup.get(_sDateMaxToApply);
		if (lDesignationGroup == null) {
			lDesignationGroup = new DesignationGroup(this, _sDateMaxToApply);
			mTreeMapDateMaxToDesignationGroup.put(_sDateMaxToApply, lDesignationGroup);
		}
		return lDesignationGroup;
	}
	
	/*
	 * Getters & Setters
	 */
	public final FromDesignationManager getmFromDesignationManager() {
		return mFromDesignationManager;
	}
	public final Map<String, Designation> getmMapKeyToUOBDesignation() {
		return mMapKeyToUOBDesignation;
	}
	public final TreeMap<Integer, DesignationGroup> getmTreeMapDateMaxToDesignationGroup() {
		return mTreeMapDateMaxToDesignationGroup;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
