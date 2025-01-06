package uob.step3linkuobfilestotransactions.objects;

import uob.step3linkuobfilestotransactions.Linker;

public abstract class FromSubManager {

	public FromSubManager(Linker _sLinker) {
		mLinker = _sLinker;
	}
	
	/*
	 * Abstract
	 */
	public abstract void run();
	/*
	 * Data
	 */
	protected Linker mLinker;
	
	/**
	 * 
	 */
	public final void runFrom() {
		mLinker.getmComManager().displayTitle(this, "Link UOBTransactions to Accounts and BKIncomes from " 
				+ this.getClass().getSimpleName().substring("From".length()));
		run();
		mLinker.removeUOBTransactionLinked();
	}

	/*
	 * Getters & Setters
	 */
	public final Linker getmLinker() {
		return mLinker;
	}
	
}
