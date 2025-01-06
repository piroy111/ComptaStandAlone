package uob.step1objects.forex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import uob.UOBMainManager;
import uob.staticdata.UOBStatic;

public class ForexManager {

	public ForexManager(UOBMainManager _sMainManager) {
		mMainManager = _sMainManager;
		/*
		 * 
		 */
		loadFile();
	}
	
	/*
	 * Data
	 */
	private UOBMainManager mMainManager;
	private Map<String, Double> mpMapForexToValueUSD;
	
	/**
	 * Load the FOREX from the CONF file
	 */
	private void loadFile() {
		String lDir = UOBStatic.getDIR_CONF_STATIC();
		String lNameFile = UOBStatic.getNAME_FILE_FOREX();
		mpMapForexToValueUSD = new HashMap<>();
		LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lDir, lNameFile, true);
		for (List<String> lLineStr : lReadFile.getmContenuFichierListe()) {
			int lIdx = -1;
			String lForex = lLineStr.get(++lIdx);
			double lValueUSD = BasicString.getDouble(lLineStr.get(++lIdx));
			mpMapForexToValueUSD.put(lForex, lValueUSD);
		}
	}
	
	
	/**
	 * @return the value in USD. 1 unit of FOREX = x% USD
	 * @param _sNameForex
	 * 
	 */
	public final double getValue(String _sNameForex) {
		String lDir = UOBStatic.getDIR_CONF_STATIC();
		String lNameFile = UOBStatic.getNAME_FILE_FOREX();
		/*
		 * Case the FOREX was not in the CONF file --> we issue a warning and return NaN
		 */
		if (!mpMapForexToValueUSD.containsKey(_sNameForex)) {
			mMainManager.getmComManager().displayWarning(this, "The forex is not in the conf file. "
					+ "\n_sNameForex= " + _sNameForex
					+ "\nYou must put it in the conf file manually. The value of the forex can be very approximate, it is just for information only."
					+ "\nConf File= " + lDir + lNameFile);
			return Double.NaN;
		}
		/*
		 * Return the value from the map we built from the CONF file
		 */
		else {
			return mpMapForexToValueUSD.get(_sNameForex);
		}
	}

	/*
	 * Getters & Setters
	 */
	public final Map<String, Double> getMpMapForexToValueUSD() {
		return mpMapForexToValueUSD;
	}
	
	
}
