package uob;

import uob.com.ComManager;
import uob.staticdata.UOBStatic.UOB_DISPLAY;
import uob.step1objects.account.ClientAccountManager;
import uob.step1objects.bkincome.UOBBKIncomeManager;
import uob.step1objects.forex.ForexManager;
import uob.step1objects.transactions.UOBTransactionManager;
import uob.step1objects.transactions.UOBValueInColumnManager;
import uob.step2loaduobfiles.UOBAccountLoader;
import uob.step3linkuobfilestotransactions.Linker;
import uob.step5writer.WriteAllUOBTransactions;
import uob.step5writer.WriteMissingUOBTransactions;
import uob.step6checker.UOBChecker;

public class UOBMainManager {

	public UOBMainManager(UOB_DISPLAY _sUOBDisplay) {
		mUOBDisplay = _sUOBDisplay;
		instantiate();
	}
	
	/*
	 * Data
	 */
	private UOB_DISPLAY mUOBDisplay;
	private ComManager mComManager;
	private ClientAccountManager mClientAccountManager;
	private UOBBKIncomeManager mUOBBKIncomeManager;
	private ForexManager mForexManager;
	private UOBTransactionManager mUOBTransactionManager;
	private UOBAccountLoader mUOBAccountLoader;
	private Linker mLinker;
	private WriteMissingUOBTransactions mWriteMissingUOBTransactions;
	private UOBValueInColumnManager mUOBValueInColumnManager;
	private UOBChecker mUOBChecker;
	private WriteAllUOBTransactions mWriteAllUOBTransactions;
	
	/**
	 * 
	 */
	private void instantiate() {
		mComManager = new ComManager(this);
		mClientAccountManager = new ClientAccountManager(this);
		mUOBBKIncomeManager = new UOBBKIncomeManager(this);
		mForexManager = new ForexManager(this);
		mUOBTransactionManager = new UOBTransactionManager(this);
		mUOBAccountLoader = new UOBAccountLoader(this);
		mLinker = new Linker(this);
		mWriteMissingUOBTransactions = new WriteMissingUOBTransactions(this);
		mUOBValueInColumnManager = new UOBValueInColumnManager();
		mUOBChecker = new UOBChecker(this);
		mWriteAllUOBTransactions = new WriteAllUOBTransactions(this);
	}
	
	/**
	 * 
	 */
	public final void run() {
		/*
		 * Load CONF files
		 */
		mClientAccountManager.run();
		mUOBBKIncomeManager.run();
		/*
		 * Load the files of UOB and create the UOBTransactions and fill them from the file
		 */
		mUOBAccountLoader.run();
		/*
		 * Link the UOBTransaction to BKIncome
		 */
		mLinker.run();
		/*
		 * Write remaining UOBTransactions in an output file
		 */
		mWriteMissingUOBTransactions.run();
		/*
		 * Check specific cases
		 */
		mUOBChecker.run();
		/*
		 * Write all UOBTransactions
		 */
		mWriteAllUOBTransactions.run();
	}

	/*
	 * Getters & Setters
	 */
	public final ClientAccountManager getmClientAccountManager() {
		return mClientAccountManager;
	}
	public final UOBBKIncomeManager getmUOBBKIncomeManager() {
		return mUOBBKIncomeManager;
	}
	public final ComManager getmComManager() {
		return mComManager;
	}
	public final ForexManager getmForexManager() {
		return mForexManager;
	}
	public final UOBTransactionManager getmUOBTransactionManager() {
		return mUOBTransactionManager;
	}

	public final Linker getmLinker() {
		return mLinker;
	}

	public final UOBValueInColumnManager getmUOBValueInColumnManager() {
		return mUOBValueInColumnManager;
	}

	public final UOBAccountLoader getmUOBAccountLoader() {
		return mUOBAccountLoader;
	}

	public final UOB_DISPLAY getmUOBDisplay() {
		return mUOBDisplay;
	}

	public final WriteMissingUOBTransactions getmWriteMissingUOBTransactions() {
		return mWriteMissingUOBTransactions;
	}

	public final UOBChecker getmUOBChecker() {
		return mUOBChecker;
	}
	
	
}
