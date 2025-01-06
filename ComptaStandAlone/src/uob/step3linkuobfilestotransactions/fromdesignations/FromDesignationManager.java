package uob.step3linkuobfilestotransactions.fromdesignations;

import uob.UOBMainManager;
import uob.com.ComManager;
import uob.step3linkuobfilestotransactions.Linker;
import uob.step3linkuobfilestotransactions.fromdesignations.designations.DesignationManager;
import uob.step3linkuobfilestotransactions.fromdesignations.files.DesignationFileManager;
import uob.step3linkuobfilestotransactions.fromdesignations.linker.DesignationLinker;
import uob.step3linkuobfilestotransactions.objects.FromSubManager;

public class FromDesignationManager extends FromSubManager {

	public FromDesignationManager(Linker _sLinker) {
		super(_sLinker);
		/*
		 * 
		 */
		instantiate();
	}
	
	/*
	 * Data
	 */
	private DesignationManager mDesignationManager;
	private DesignationFileManager mDesignationFileManager;
	private DesignationLinker mDesignationLinker;
	
	/**
	 * 
	 */
	private void instantiate() {
		mDesignationManager = new DesignationManager(this);
		mDesignationFileManager = new DesignationFileManager(this);
		mDesignationLinker = new DesignationLinker(this);
	}
	
	/**
	 * 
	 */
	@Override public final void run() {
		mDesignationFileManager.loadFileConfIntoDesignation();
		mDesignationLinker.linkeDesignationToUOBTransaction();
	}

	/*
	 * Getters & Setters
	 */
	public final DesignationManager getmDesignationManager() {
		return mDesignationManager;
	}
	public final DesignationFileManager getmDesignationFileManager() {
		return mDesignationFileManager;
	}
	public final UOBMainManager getmMainManager() {
		return mLinker.getmMainManager();
	}
	public final ComManager getmComManager() {
		return getmMainManager().getmComManager();
	}
	
}
