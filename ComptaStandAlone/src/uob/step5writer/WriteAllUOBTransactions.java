package uob.step5writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicTime;
import uob.UOBMainManager;
import uob.staticdata.UOBStatic;
import uob.step1objects.transactions.UOBTransaction;

public class WriteAllUOBTransactions {

	
	public WriteAllUOBTransactions(UOBMainManager _sUOBMainManager) {
		pUOBMainManager = _sUOBMainManager;
		/*
		 * 
		 */
		BasicFichiers.getOrCreateDirectory(UOBStatic.getDIR_OUTPUT_TRANSACTIONS());
	}
	
	/*
	 * Data
	 */
	private UOBMainManager pUOBMainManager;
	
	/**
	 * 
	 */
	public final void run() {
		List<UOBTransaction> lListUOBTransaction = pUOBMainManager.getmUOBTransactionManager().getmListUOBTransaction();
		Collections.sort(lListUOBTransaction);
		List<String> lListLineToWrite = new ArrayList<>();
		for (UOBTransaction lUOBTransaction : lListUOBTransaction) {
			String lLine = lUOBTransaction.getmDate()
					+ "," + BasicTime.getHeureExcelFromHeureLong(lUOBTransaction.getmTime())
					+ "," + lUOBTransaction.getmOurReference().replaceAll(",", ";")
					+ "," + lUOBTransaction.getmYourReference().replaceAll(",", ";")
					+ "," + lUOBTransaction.getmRemarks().replaceAll(",", ";")
					+ "," + lUOBTransaction.getmUOBAccount().getmCurrency()
					+ "," + lUOBTransaction.getmAmount()
					+ "," + lUOBTransaction.getmFileUOBOrigin()
					+ "," + lUOBTransaction.getmAccount()
					+ "," + lUOBTransaction.getmBKIncome();
			lListLineToWrite.add(lLine);
		}
		String lHeader = "Date,Time,OurReference,YourReference,Remarks,Currency,Amount,File UOB Origin,Account,BKIncome";
		String lDir = UOBStatic.getDIR_OUTPUT_TRANSACTIONS();
		String lNameFile = BasicDateInt.getmToday() + UOBStatic.getNAME_OUTPUT_TRANSACTIONS();
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
	}
	
}
