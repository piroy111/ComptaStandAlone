package uob.step1objects.bkincome;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import uob.UOBMainManager;
import uob.staticdata.UOBStatic;

public class UOBBKIncomeManager {

	public UOBBKIncomeManager(UOBMainManager _sMainManager) {
		pMainManager = _sMainManager;
	}
	
	/*
	 * Data
	 */
	private UOBMainManager pMainManager;
	private List<String> pListBKIncome;
	private List<String> pListBKIncomeDisplay;
	
	/**
	 * 
	 */
	public final void run() {
		String lDir = UOBStatic.getDIR_CONF_STATIC();
		String lNameFile = UOBStatic.getNAME_FILE_BKINCOME();
		pMainManager.getmComManager().displayTitle(this, "Load file conf " + lNameFile);
		LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lDir, lNameFile, true);
		pListBKIncome = new ArrayList<>();
		pListBKIncomeDisplay = new ArrayList<>();
		for (List<String> lLineStr : lReadFile.getmContenuFichierListe()) {
			String lBKIncome = BasicString.getItem(lLineStr, 0);
			if (lBKIncome != null) {
				pListBKIncome.add(lBKIncome);
				pListBKIncomeDisplay.add("'" + lBKIncome.toString() + "'");
				BasicPrintMsg.display(this, "Loaded BKIncome= " + lBKIncome);
			}
		}
	}

	/**
	 * 
	 * @param _sSender
	 * @param _sBKIncome
	 */
	public final void checkBKIncomeExists(Object _sSender, String _sBKIncome) {
		if (!pListBKIncome.contains(_sBKIncome)) {
			String lMessage = "The BKIncome requested by '" + _sSender.getClass().getSimpleName() + "' does not exist in the conf file"
					+ "\nBKIncome requested= '" + _sBKIncome + "'"
					+ "\nConf file= " + UOBStatic.getDIR_CONF_STATIC() + UOBStatic.getNAME_FILE_BKINCOME()
					+ "\nList of BKIncome present in the conf file= " + pListBKIncomeDisplay;
			pMainManager.getmComManager().displayFatalError(_sSender, lMessage);
		}
	}
	
	/*
	 * Getters & Setters
	 */
	public final UOBMainManager getpMainManager() {
		return pMainManager;
	}
	public final List<String> getpListBKIncome() {
		return pListBKIncome;
	}
	
	
	
	
}
