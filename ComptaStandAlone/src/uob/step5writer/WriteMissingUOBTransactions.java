package uob.step5writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicTime;
import uob.UOBMainManager;
import uob.staticdata.UOBStatic;
import uob.step1objects.transactions.UOBTransaction;
import uob.step3linkuobfilestotransactions.Linker;

public class WriteMissingUOBTransactions {

	public WriteMissingUOBTransactions(UOBMainManager _sMainManager) {
		mLinker = _sMainManager.getmLinker();
		/*
		 * 
		 */
		if (mLinker == null) {
			BasicPrintMsg.error("Linker should be instantiated before this class");
		}
	}
	
	/*
	 * Static
	 */
	private static int SIZE_TO_BE_BIG_NUMBER = 10;
	/*
	 * Data
	 */
	private Linker mLinker;
	private boolean mIsHaveMissing;
	
	/**
	 * 
	 */
	public final void run() {
		mLinker.getmComManager().displayTitle(this, "Write output file with UOBTransaction which I could not link to an account and a BKIncome");
		if (mLinker.getmListUOBTransactionNotLinked().size() == 0) {
			mLinker.getmMainManager().getmComManager().display(this, "All UOBTransactions were assigned sucessfully!");
			return;
		}
		/*
		 * Sort the missing UOBTransaction to group them by account number
		 */
		Collections.sort(mLinker.getmListUOBTransactionNotLinked(), new SortByUOBAccount());
		/*
		 * Fill the content of the file
		 */
		List<String> lListLineToWrite = new ArrayList<>();
		for (UOBTransaction lUOBTransaction : mLinker.getmListUOBTransactionNotLinked()) {
			String lLineUOB = treatSpecialCaseLargeNumbers(lUOBTransaction.getmLineInFileUOBOrigin());
			String lLineStr = lUOBTransaction.getmFileUOBOrigin() + "," + lLineUOB;
			lListLineToWrite.add(lLineStr);
			mLinker.getmMainManager().getmComManager().display(this, "Impossible to link UOBTransaction= '" + lUOBTransaction + "'");
		}
		mIsHaveMissing = lListLineToWrite.size() > 0;
		/*
		 * Prepare file
		 */
		String lDir = UOBStatic.getDIR_OUTPUT();
		String lNameFile = BasicDateInt.getmToday()
				+ "_" + BasicTime.getHeureTexteHHMMSSFromLong(BasicTime.getmNow()).replaceAll(":", "")
				+ UOBStatic.getNAME_FILE_OUTPUT_MISSING();
		String lHeader = "File UOB origin,,AccountNumber,ValueDate,Date,Time,Description,YourReference,OurReference,ChequeNumber,Reference5,Remarks,Reference7,Reference8,Reference9,Reference10,Reference11,Reference12,Deposit,Withdrawal,Ledger";
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
		mLinker.getmMainManager().getmComManager().display(this, "File output written= '" + lDir + lNameFile + "'");
	}
	
	/**
	 * Add the symbol ' in front of Strings which are big numbers so excel does not round them
	 * @return
	 */
	private static String treatSpecialCaseLargeNumbers(String _sLineUOB) {
		List<String> lListWords = Arrays.asList(_sLineUOB.split(",", -1));
		boolean lIsLargeNumberFound = false;
		for (int lIdx = 0; lIdx < lListWords.size(); lIdx++) {
			String lOldWord = lListWords.get(lIdx);
			/*
			 * Check if the word is a large number
			 */
			if (lOldWord.length() >= SIZE_TO_BE_BIG_NUMBER) {
				boolean lIsNumber = true;
				for (int lIdxWord = 0; lIdxWord < lOldWord.length(); lIdxWord++) {
					char lChar = lOldWord.charAt(lIdxWord);
					/*
					 * We ignore the spaces and the symbol " which Excel uses to surround the word
					 */
					if (lChar == ' ' || lChar == '"') {
						continue;
					}
					/*
					 * We keep only if the word is a number
					 */
					if (lChar < '0' || lChar > '9') {
						lIsNumber = false;
						break;
					}
				}
				/*
				 * If the word is a large number then we add the symbol ' in front
				 */
				if (lIsNumber) {
					/*
					 * The String in excel are surrounded by the symbol " most of the time. We need to keep thos surroundings 
					 */
					String lNewWord;
					if (lOldWord.startsWith("\"")) {
						lNewWord = "\"'" + lOldWord.substring(1);
					} else {
						lNewWord = "'" + lOldWord;
					}
					lListWords.set(lIdx, lNewWord);
					lIsLargeNumberFound = true;
				}
			}
		}
		/*
		 * return the line re-composed with the list of words
		 */
		if (lIsLargeNumberFound) {
			String lNewLineUOB = "";
			for (int lIdx = 0; lIdx < lListWords.size(); lIdx++) {
				if (lIdx > 0) {
					lNewLineUOB += ",";
				}
				lNewLineUOB += lListWords.get(lIdx);
			}
			return lNewLineUOB;
		} else {
			return _sLineUOB;
		}
	}

	/*
	 * Getters & Setters
	 */
	public final boolean getmIsHaveMissing() {
		return mIsHaveMissing;
	}
	
}
