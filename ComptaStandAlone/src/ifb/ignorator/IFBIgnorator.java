package ifb.ignorator;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import basicmethods.LitUnFichierEnLignes;
import ifb.IFBManager;
import ifb.staticdata.IFBStatic;
import ifb.transactions.IFBTransaction;
import ifb.transactions.IFBTransactionManager;
import uob.staticdata.UOBStatic;

public class IFBIgnorator {

	public IFBIgnorator(IFBManager _sIFBManager) {
		pIFBManager = _sIFBManager;
		/*
		 * 
		 */
		initiateAndLoadFiles();
	}
	
	/*
	 * Data
	 */
	private IFBManager pIFBManager;
	private List<IFBIgnoreOne> pListIFBIgnoreOne;
	private List<IFBTransaction> pListIFBTransactionAfterFilter;
	
	/**
	 * 
	 */
	private void initiateAndLoadFiles() {
		pListIFBIgnoreOne = new ArrayList<>();
		String lDir = UOBStatic.getDIR_CONF_DYNAMIC();
		String lNameFile = IFBStatic.getCONF_FILE_IGNORE();
		LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lDir, lNameFile, true);
		for (List<String> lLineStr : lReadFile.getmContenuFichierListe()) {
			/*
			 * Load line
			 */
			int lIdx = -1;
			String lCode = lLineStr.get(++lIdx);
			String lBKIncome = lLineStr.get(++lIdx);
			/*
			 * Create and fill object
			 */
			IFBIgnoreOne lIFBIgnoreOne = new IFBIgnoreOne(lCode, lBKIncome);
			pListIFBIgnoreOne.add(lIFBIgnoreOne);
		}
	}

	/**
	 * 
	 */
	public final void assignBKIncomeOrIgnore(IFBTransactionManager _sIFBTransactionManager) {
		BasicPrintMsg.displayTitle(this, "IFB: Assign BKIncome or decide to ignore transaction");
		pListIFBTransactionAfterFilter = new ArrayList<>();
		List<IFBTransaction> lListIFBTransaction = _sIFBTransactionManager.getpListIFBTransaction();
		List<IFBTransaction> lListIFBTransactionMissing = new ArrayList<>();
		List<IFBTransaction> lListIFBTransactionIgnored = new ArrayList<>();
		for (IFBTransaction lIFBTransaction : lListIFBTransaction) {
			String lBKIncome = null;
			for (IFBIgnoreOne lIFBIgnoreOne : pListIFBIgnoreOne) {
				if (lIFBIgnoreOne.getpIsMatch(lIFBTransaction.getpComment())) {
					lBKIncome = lIFBIgnoreOne.getpBKIncome();
					break;
				}
			}
			if (lBKIncome == null) {
				lListIFBTransactionMissing.add(lIFBTransaction);
			} else if (!lBKIncome.trim().toUpperCase().equals("IGNORE")) {
				lIFBTransaction.setpBKIncome(lBKIncome);
				pListIFBTransactionAfterFilter.add(lIFBTransaction);
			} else {
				lListIFBTransactionIgnored.add(lIFBTransaction);
			}
		}
		/*
		 * Case there are transactions which are not treated by the CONF file ignore --> we display an alert
		 */
		if (lListIFBTransactionMissing.size() > 0) {
			String lErrorMsg = "Some IFB Transactions cannot be determined (ignore or BKIncome) because they are not in the CONF file"
					+ "\nCONF file= " + UOBStatic.getDIR_CONF_DYNAMIC() + IFBStatic.getCONF_FILE_IGNORE()
					+ "\nYou must update the CONF file accordingly"
					+ "\n";
			for (IFBTransaction lIFBTransaction : lListIFBTransactionMissing) {
				lErrorMsg += "\n'" + lIFBTransaction.getpLineInFile() + "'";
			}
			/*
			 * Kill process
			 */
			BasicPrintMsg.error(lErrorMsg);
		}
		/*
		 * Write file with IFBTransactions ignored
		 */
		writeFileDump("IFB_IFBTransactions_ignored.csv", lListIFBTransactionIgnored);
		writeFileDump("IFB_IFBTransactions_kept.csv", pListIFBTransactionAfterFilter);
	}

	
	private void writeFileDump(String _sNameFile, List<IFBTransaction> _sListIFBTransaction) {
		List<String> lListLineToWrite = new ArrayList<>();
		for (IFBTransaction lIFBTransaction : _sListIFBTransaction) {
			lListLineToWrite.add(lIFBTransaction.toString());
		}
		String lDir = IFBStatic.getDIR_OUTPUT();
		BasicFichiers.getOrCreateDirectory(lDir);
		String lNameFile = BasicDateInt.getmToday() + _sNameFile;
		String lHeader = "Date,Comment,Currency,Amount,BKIncome";
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
		BasicPrintMsg.display(this, "File written successfully: '" + lDir + lNameFile + "'");
	}
	
	
	/*
	 * Getters & Setters
	 */
	public List<IFBTransaction> getpListIFBTransactionAfterFilter() {
		return pListIFBTransactionAfterFilter;
	}
	public IFBManager getpIFBManager() {
		return pIFBManager;
	}
	public List<IFBIgnoreOne> getpListIFBIgnoreOne() {
		return pListIFBIgnoreOne;
	}
	
}
