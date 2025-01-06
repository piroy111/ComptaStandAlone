package ifb;

import java.util.List;

import ifb.ignorator.IFBIgnorator;
import ifb.transactions.IFBTransaction;
import ifb.transactions.IFBTransactionManager;
import ledgerendofmonth.LDGWriter;

public class IFBManager {

	public IFBManager() {
		pIFBTransactionManager = new IFBTransactionManager(this);
		pIFBIgnorator = new IFBIgnorator(this);
	}
	
	/*
	 * Data
	 */
	private IFBTransactionManager pIFBTransactionManager;
	private IFBIgnorator pIFBIgnorator;
	
	/**
	 * 
	 */
	public final void run() {
		pIFBTransactionManager.loadFiles();
		pIFBIgnorator.assignBKIncomeOrIgnore(pIFBTransactionManager);
		new LDGWriter<IFBTransaction>("IFB").run(pIFBIgnorator.getpListIFBTransactionAfterFilter());
	}

	/*
	 * Getters & Setters
	 */
	public final List<IFBTransaction> getpListIFBTransaction() {
		return pIFBIgnorator.getpListIFBTransactionAfterFilter();
	}
	
}
