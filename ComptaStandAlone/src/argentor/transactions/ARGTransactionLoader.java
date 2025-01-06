package argentor.transactions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import argentor.ARGManager;
import argentor.staticdata.ARGStatic;
import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import rhb.transactions.RHBTransactionLoader;

public class ARGTransactionLoader {

	public ARGTransactionLoader(ARGManager _sARGManager) {
		pARGManager = _sARGManager;
	}
	
	/*
	 * Data
	 */
	private ARGManager pARGManager;
	private List<ARGTransaction> pListARGTransaction;
	
	/**
	 * 
	 */
	public final void loadFiles() {
		pListARGTransaction = new ArrayList<>();
		String lDir = ARGStatic.getDIR_IMPORT();
		List<Path> lListPath = BasicFichiersNio.getListFilesAndSubFiles(Paths.get(lDir));
		for (Path lPath : lListPath) {
			String lNameFile = lPath.getFileName().toString();
			if (lNameFile.contains(ARGStatic.getNAME_FILE_IMPORT())) {
				LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lPath, true);
				loadOneFile(lReadFile);
			}
		}
	}
	
	/**
	 * 
	 * @param _sPath
	 */
	private void loadOneFile(LitUnFichierEnLignes _sReadFile) {
		/*
		 * Loop on the columns because the report has 2 columns with 2 different assets
		 */
		for (int lIdxAsset = 0; lIdxAsset < 2; lIdxAsset++) {
			/*
			 * Case of the header --> load the asset name
			 */
			String lHeader = _sReadFile.getmContenuFichierListe().get(0).get(ARGStatic.getIDX_COLUMNS()[lIdxAsset]);
			String lAsset = "";
			if (lHeader.equals(ARGStatic.getUSD())) {
				lAsset = "USD";
			} else if (lHeader.equals(ARGStatic.getXAU())) {
				lAsset = "XAU";
			} else if (lHeader.equals(ARGStatic.getXAG())) {
				lAsset = "XAG";
			} else if (lHeader.equals(ARGStatic.getXPT())) {
				lAsset = "XPT";
			} else {
				BasicPrintMsg.error("Unknown header"
						+ "\nHeader= '" + lHeader + "'");
			}
			/*
			 * Read the transactions
			 */
			for (int lIdxRow = 1; lIdxRow < _sReadFile.getmContenuFichierListe().size(); lIdxRow++) {
				List<String> lListLine = _sReadFile.getmContenuFichierListe().get(lIdxRow);
				if (lListLine.size() == 0 || lListLine.get(0).equals("")) {
					continue;
				}
				/*
				 * Load raw
				 */
				int lIdx = -1;
				String lDocNo = lListLine.get(++lIdx);
				++lIdx;
				String lDateStr = lListLine.get(++lIdx);
				String lComment = lListLine.get(++lIdx);
				String lCreditStr = lListLine.get(++lIdx + lIdxAsset * 3);
				String lDebitStr = lListLine.get(++lIdx + lIdxAsset * 3);
				String lBalanceStr = lListLine.get(++lIdx + lIdxAsset * 3);
				/*
				 * Interpret date and cash flow
				 */
				if (lCreditStr.equals("") && lDebitStr.equals("")) {
					continue;
				}
				lDateStr = lDateStr.replaceFirst("-", " ");
				lDateStr = lDateStr.replaceFirst("-", " 20");
				lDateStr = lDateStr.toUpperCase();
				int lDate = RHBTransactionLoader.convertDateIntInt(lDateStr);
				double lAmount;
				if (lCreditStr.equals("")) {
					lAmount = -BasicString.getDouble(lDebitStr);
				} else {
					lAmount = BasicString.getDouble(lCreditStr);
				}
				double lBalance;
				if (lBalanceStr.endsWith(" Cr")) {
					lBalance = BasicString.getDouble(lBalanceStr.substring(0, lBalanceStr.length() - " Cr".length()));
				} else if (lBalanceStr.endsWith(" Dr")) {
					lBalance = -BasicString.getDouble(lBalanceStr.substring(0, lBalanceStr.length() - " Dr".length()));
				} else {
					lBalance = Double.NaN;
					BasicPrintMsg.error("Error with the balance. It should end with 'Cr' or 'Dr'"
							+ "\nBalanceStr= '" + lBalanceStr + "'");
				}
				/*
				 * Create and store transactions
				 */
				ARGTransaction lARGTransaction = new ARGTransaction(lDate, lAsset, lAmount, lDocNo, lComment, lBalance);
				pListARGTransaction.add(lARGTransaction);
			}
		}
		BasicPrintMsg.display(this, "Read the file '" + _sReadFile.getmNomFichier() + "' succesfully");
	}

	/*
	 * Getters & Setters
	 */
	public final List<ARGTransaction> getpListARGTransaction() {
		return pListARGTransaction;
	}
	public final ARGManager getpARGManager() {
		return pARGManager;
	}
	
	
	
	
	
	
	
	
}
