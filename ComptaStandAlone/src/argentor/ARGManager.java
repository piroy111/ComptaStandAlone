package argentor;

import java.util.List;

import argentor.transactions.ARGTransaction;
import argentor.transactions.ARGTransactionLoader;

public class ARGManager {

	public ARGManager() {
		pARGTransactionLoader = new ARGTransactionLoader(this);
	}
	
	/*
	 * Data
	 */
	private ARGTransactionLoader pARGTransactionLoader;
	
	/**
	 * 
	 */
	public final void run() {
		pARGTransactionLoader.loadFiles();
	}

	/*
	 * Getters & Setters
	 */
	public final ARGTransactionLoader getpARGTransactionLoader() {
		return pARGTransactionLoader;
	}
	public final List<ARGTransaction> getpList() {
		return pARGTransactionLoader.getpListARGTransaction();
	}
	
	
}
