package rhb.transactions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiersNio;
import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import rhb.RHBManager;
import rhb.staticdata.RHBStatic;

public class RHBTransactionLoader {

	public RHBTransactionLoader(RHBManager _sRHBManager) {
		pRHBManager = _sRHBManager;		
	}

	/*
	 * Data
	 */
	private RHBManager pRHBManager;
	private List<RHBTransaction> pListRHBTransaction;
	
	/**
	 * 
	 */
	public final void loadFiles() {
		/*
		 * Initiate
		 */
		pListRHBTransaction = new ArrayList<>();
		String lDir = RHBStatic.getDIR_RHB_STATEMENTS();
		/*
		 * Communicate
		 */
		BasicPrintMsg.displayTitle(this, "Read all the imported files RHB");
		BasicPrintMsg.display(this, "Reading directory '" + lDir);
		/*
		 * List the files with the correct suffix
		 */
		List<Path> lListPath = BasicFichiersNioRaw.getListPath(Paths.get(lDir));
		for (Path lPath : lListPath) {
			if (!BasicFichiersNio.getIsDirectory(lPath)) {
				String lNameFile = lPath.getFileName().toString();
				if (lNameFile.endsWith(RHBStatic.getRHB_SUFFIX_FILE_INPUT())) {
					/*
					 * Read file
					 */
					LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lPath, true);
					for (int lIdxLine = 1; lIdxLine < lReadFile.getmContenuFichierListe().size(); lIdxLine++) {
						List<String> lLineStr = lReadFile.getmContenuFichierListe().get(lIdxLine);
						/*
						 * Load line for file
						 */
						int lIdx = -1;
						lIdx++;
						lIdx++;
						String lDateStr = lLineStr.get(++lIdx);
						String lDescription = lLineStr.get(++lIdx);
						String lCurrency = lLineStr.get(++lIdx);
						double lDebit = BasicString.getDouble(lLineStr.get(++lIdx));
						double lCredit = BasicString.getDouble(lLineStr.get(++lIdx));
						/*
						 * Treat the data
						 */
						double lAmount = lCredit - lDebit;
						int lDate = convertDateIntInt(lDateStr);
						/*
						 * Create Transaction
						 */
						RHBTransaction lRHBTransaction = new RHBTransaction(lDate, lDescription, lCurrency, lAmount);
						pListRHBTransaction.add(lRHBTransaction);
					}
					BasicPrintMsg.display(this, "Read the file '" + lReadFile.getmNomFichier() + "' succesfully");
				}
			}
		}
		Collections.sort(pListRHBTransaction);
		BasicPrintMsg.display(this, "All files read successfully");
	}

	/**
	 * Convert a date of type "31 DEC 2019" into '20191231'
	 * @param _sDateStr
	 * @return
	 */
	public static int convertDateIntInt(String _sDateStr) {
		String[] lWords = _sDateStr.split(" ");
		if (lWords.length != 3) {
			BasicPrintMsg.error("Error with date '" + _sDateStr + "'");
		}
		int lYear = BasicString.getInt(lWords[2]);
		int lDay =  BasicString.getInt(lWords[0]);
		String lMonthStr = lWords[1].toUpperCase();
		int lMonth = -1;
		if (lMonthStr.equals("JAN")) {
			lMonth = 1;
		} else if (lMonthStr.equals("FEB")) {
			lMonth = 2;
		} else if (lMonthStr.equals("MAR")) {
			lMonth = 3;
		} else if (lMonthStr.equals("APR")) {
			lMonth = 4;
		} else if (lMonthStr.equals("MAY")) {
			lMonth = 5;
		} else if (lMonthStr.equals("JUN")) {
			lMonth = 6;
		} else if (lMonthStr.equals("JUL")) {
			lMonth = 7;
		} else if (lMonthStr.equals("AUG")) {
			lMonth = 8;
		} else if (lMonthStr.equals("SEP")) {
			lMonth = 9;
		} else if (lMonthStr.equals("OCT")) {
			lMonth = 10;
		} else if (lMonthStr.equals("NOV")) {
			lMonth = 11;
		} else if (lMonthStr.equals("DEC")) {
			lMonth = 12;
		} else {
			BasicPrintMsg.error("Error with date '" + _sDateStr + "' --> cannot convert month");
		}
		return BasicDateInt.getmDateInt(lYear, lMonth, lDay);
	}

	/*
	 * Getters & Setters
	 */
	public List<RHBTransaction> getpListRHBTransaction() {
		return pListRHBTransaction;
	}
	public RHBManager getpRHBManager() {
		return pRHBManager;
	}




}
