package rhb;

import java.util.List;

import ledgerendofmonth.LDGWriter;
import rhb.transactions.RHBTransaction;
import rhb.transactions.RHBTransactionLoader;

public class RHBManager {

	public RHBManager() {
		pRHBTransactionLoader = new RHBTransactionLoader(this);
	}
	
	/*
	 * Data
	 */
	private RHBTransactionLoader pRHBTransactionLoader;
	
	/**
	 * 
	 */
	public final void run() {
		pRHBTransactionLoader.loadFiles();
		new LDGWriter<RHBTransaction>("RHB").run(pRHBTransactionLoader.getpListRHBTransaction());
	}

	/*
	 * Getters & Setters
	 */
	public RHBTransactionLoader getpRHBTransactionLoader() {
		return pRHBTransactionLoader;
	}
	public final List<RHBTransaction> getpListRHBTransaction() {
		return pRHBTransactionLoader.getpListRHBTransaction();
	}
	
}
