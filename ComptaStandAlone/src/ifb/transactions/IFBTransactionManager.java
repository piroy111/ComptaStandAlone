package ifb.transactions;

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
import ifb.IFBManager;
import ifb.staticdata.IFBStatic;

public class IFBTransactionManager {

	public IFBTransactionManager(IFBManager _sIFBManager) {
		pIFBManager = _sIFBManager;
	}
	
	/*
	 * Data
	 */
	private IFBManager pIFBManager;
	private List<IFBTransaction> pListIFBTransaction;
	
	/**
	 * 
	 */
	public final void loadFiles() {
		/*
		 * Initiate
		 */
		pListIFBTransaction = new ArrayList<>();
		String lDir = IFBStatic.getIMPORT_IFB_STATEMENTS();
		/*
		 * Communicate
		 */
		BasicPrintMsg.displayTitle(this, "Read all the imported files IFB");
		BasicPrintMsg.display(this, "Reading directory '" + lDir);
		/*
		 * List the files with the correct suffix
		 */
		List<Path> lListPath = BasicFichiersNioRaw.getListPath(Paths.get(lDir));
		for (Path lPath : lListPath) {
			if (!BasicFichiersNio.getIsDirectory(lPath)) {
				String lNameFile = lPath.getFileName().toString();
				if (lNameFile.endsWith(IFBStatic.getIFB_SUFFIX_FILE_INPUT())) {
					/*
					 * Read file + load the currency from the name of the file
					 */
					LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lPath, true);
					String[] lWords = lReadFile.getmNomFichier().split("_", -1);
					String lCurrency = lWords[2];
					/*
					 * Read file content and create the object IFBTransactions
					 */
					for (int lIdxLine = 1; lIdxLine < lReadFile.getmContenuFichierListe().size(); lIdxLine++) {
						List<String> lListLineStr = lReadFile.getmContenuFichierListe().get(lIdxLine);
						String lLineStr = lReadFile.getmContenuFichierLignes().get(lIdxLine);
						/*
						 * Load line for file
						 */
						int lIdx = -1;
						lIdx++;
						String lDateStr = lListLineStr.get(++lIdx);
						lIdx++;
						String lDescription = lListLineStr.get(++lIdx);
						double lAmount = BasicString.getDouble(lListLineStr.get(++lIdx));
						String lCreditOrDebitStr = lListLineStr.get(++lIdx);
						/*
						 * Treat the data
						 */
						if (lCreditOrDebitStr.equals("DR")) {
							lAmount = -lAmount;
						} else if (!lCreditOrDebitStr.equals("CR")) {
							BasicPrintMsg.error("Unknwon CR/DR in line"
									+ "\nFile= " + lReadFile.getmNomCheminPlusFichier()
									+ "\nLine= " + lListLineStr);
						}
						lDateStr = lDateStr.substring(0, 6) + "20" + lDateStr.substring(6, 8);
						int lDate = BasicDateInt.getmDateFromString(lDateStr, true);
						/*
						 * Create Transaction
						 */
						IFBTransaction lIFBTransaction = new IFBTransaction(lDate, lDescription, lCurrency, lAmount);
						pListIFBTransaction.add(lIFBTransaction);
						lIFBTransaction.setpLineInFile(lLineStr);
					}
					BasicPrintMsg.display(this, "Read the file '" + lReadFile.getmNomFichier() + "' succesfully");
				}
			}
		}
		Collections.sort(pListIFBTransaction);
		BasicPrintMsg.display(this, "All files read successfully");
	}

	/*
	 * Getters & Setters
	 */
	public IFBManager getpIFBManager() {
		return pIFBManager;
	}
	public List<IFBTransaction> getpListIFBTransaction() {
		return pListIFBTransaction;
	}
	
}
