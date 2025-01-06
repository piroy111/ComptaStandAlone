package uob.step6checker;

import basicmethods.BasicPrintMsg;
import uob.UOBMainManager;
import uob.staticdata.UOBStatic;
import uob.step1objects.transactions.UOBTransaction;

public class UOBChecker {

	public UOBChecker(UOBMainManager _sUOBMainManager) {
		mUOBMainManager = _sUOBMainManager;
	}

	/*
	 * Data
	 */
	private UOBMainManager mUOBMainManager;


	/**
	 * Check the correct BKIncome is used
	 */
	public final void run() {
		if (!mUOBMainManager.getmWriteMissingUOBTransactions().getmIsHaveMissing()) {
			mUOBMainManager.getmComManager().displayTitle(this, "Check the correct BKIncome is used");
			for (UOBTransaction lUOBTransaction : mUOBMainManager.getmUOBTransactionManager().getmListUOBTransaction()) {
				if (lUOBTransaction.getmBKIncome().startsWith("Hedging ")
						&& lUOBTransaction.getmBKIncome().endsWith(" Cash wire in")) {
					BasicPrintMsg.error("The BKIncome is wrong for the UOBTransaction. It is a 'Cash wire in'. It should be a 'Cash wire out'"
							+ "\nUOBTransaction= " + lUOBTransaction.getmLineInFileLinkedCompta()
							+ "\nBKIncome= " + lUOBTransaction.getmBKIncome());
				}
			}
		} else {
			BasicPrintMsg.error("Some transactions are missing a link"
					+ "\nPLease look in the file '..." + UOBStatic.getNAME_FILE_OUTPUT_MISSING() + "'"
					+ " in the folder '" + UOBStatic.getDIR_OUTPUT() + "'");
		}
	}


}
