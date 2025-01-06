package uob.step2loaduobfiles.dumpoutput;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import uob.staticdata.UOBStatic;
import uob.step1objects.transactions.UOBTransaction;
import uob.step2loaduobfiles.UOBAccountLoader;

public class UOBDumpKeysUOBTransaction {

	public UOBDumpKeysUOBTransaction(UOBAccountLoader _sUOBAccountLoader) {
		mUOBAccountLoader = _sUOBAccountLoader;
	}
	
	/*
	 * Data
	 */
	private UOBAccountLoader mUOBAccountLoader;
	
	/**
	 * Write a file dump with the first column being the file origin of the UOBTransaction<br>
	 * and the second column being the key of the UOBTransaction<br>
	 * This file will help for the debug when we dont recognize a UOBTransaction<br>
	 */
	public final void writeFileDump() {
		/*
		 * 
		 */
		String lDir = UOBStatic.getDIR_DUMP();
		String lNameFile = BasicDateInt.getmToday() + UOBStatic.getNAME_FILE_DUMP_KEYS();
		/*
		 * Load the list of UOBTransaction and sort it by file origin, and then by alphabetical order
		 */
		List<UOBTransaction> lListUOBTransaction = new ArrayList<>(mUOBAccountLoader
				.getmMainManager().getmUOBTransactionManager().getmListUOBTransaction());
		/*
		 * Build file content
		 */
		List<String> lListLineToWrite = new ArrayList<>();
		for (UOBTransaction lUOBTransaction : lListUOBTransaction) {
			lListLineToWrite.add(lUOBTransaction.getmFileUOBOrigin()
					+ "," + lUOBTransaction.getmKeyStr());
		}
		/*
		 * Write file
		 */
		String lHeader = "File origin of the UOBTransaction"
				+ ",Unique key which identifies the UOBTransaction"
				+ "(Account number/ValueDate/Date/Time/Description/YourReference/OurReference/ChequeNumber/Remarks/Deposit/Withdrawal)";
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
	}
	
}
