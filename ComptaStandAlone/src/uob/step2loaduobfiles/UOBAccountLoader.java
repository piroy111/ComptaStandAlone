package uob.step2loaduobfiles;

import basicmethods.BasicPrintMsg;
import uob.UOBMainManager;
import uob.com.ComManager;
import uob.step1objects.uobaccount.UOBAccountManager;
import uob.step2loaduobfiles.dumpoutput.UOBDumpKeysUOBTransaction;
import uob.step2loaduobfiles.files.UOBFileManager;

public class UOBAccountLoader {

	public UOBAccountLoader(UOBMainManager _sMainManager) {
		mMainManager = _sMainManager;
		/*
		 * Instantiate
		 */
		instantiate();
	}
	
	/*
	 * Data
	 */
	private UOBMainManager mMainManager;
	private UOBAccountManager mUOBAccountManager;
	private UOBFileManager mUOBFileManager;
	private UOBDumpKeysUOBTransaction mUOBDumpKeysUOBTransaction;

	/**
	 * 
	 */
	private void instantiate() {
		mUOBAccountManager = new UOBAccountManager(this);
		mUOBFileManager = new UOBFileManager(this);
		mUOBDumpKeysUOBTransaction = new UOBDumpKeysUOBTransaction(this);
	}
	
	/**
	 * Load the files UOB from the directory where we input them manually every month. Create UOBTransactions and fill them from the files.
	 */
	public final void run() {
		mUOBFileManager.run();
		
		////////////////////////////////////////////////////////////////
		BasicPrintMsg.displayTitle(this, "DEBUG 4");
		
		////////////////////////////////////////////////////////////////
		
		mUOBDumpKeysUOBTransaction.writeFileDump();
	}
	
	/*
	 * Getters & Setters
	 */
	public final UOBMainManager getmMainManager() {
		return mMainManager;
	}
	public final ComManager getmComManager() {
		return mMainManager.getmComManager();
	}
	public final UOBAccountManager getmUOBAccountManager() {
		return mUOBAccountManager;
	}

	public final UOBFileManager getmUOBFileManager() {
		return mUOBFileManager;
	}
	
	
	
	
	
}
