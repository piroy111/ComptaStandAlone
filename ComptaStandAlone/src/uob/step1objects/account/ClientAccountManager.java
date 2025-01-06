package uob.step1objects.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import uob.UOBMainManager;
import uob.staticdata.UOBStatic;

public class ClientAccountManager {

	public ClientAccountManager(UOBMainManager _sUOBMainManager) {
		mUOBMainManager = _sUOBMainManager;
		/*
		 * 
		 */
		mMapKeyStrToClientAccount = new HashMap<>();
		mMapKeyWireCurrencyToClientAccount = new HashMap<>();
		mMapEmailCurrencyToClientAccount = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private UOBMainManager mUOBMainManager;
	private Map<String, ClientAccount> mMapKeyStrToClientAccount;
	private Map<String, ClientAccount> mMapKeyWireCurrencyToClientAccount;
	private Map<String, ClientAccount> mMapEmailCurrencyToClientAccount;
	
	
	/**
	 * 
	 * @param _sKeyWire
	 * @param _sCurrency
	 * @param _sEmail
	 * @return
	 */
	private final ClientAccount getpOrCreateClientAccount(String _sKeyWire, String _sCurrency, String _sEmail) {
		/*
		 * Change Key wire to remove spaces and put it upper case, so we maximize the chances to identify it 
		 */
		String lKeyWire = _sKeyWire.replaceAll(" ", "").toUpperCase();
		/*
		 * 
		 */
		String lKeyStr = ClientAccount.getUniqueKeyStr(lKeyWire, _sCurrency, _sEmail);
		ClientAccount lClientAccount = mMapKeyStrToClientAccount.get(lKeyStr);
		if (lClientAccount == null) {
			lClientAccount = new ClientAccount(lKeyWire, _sCurrency, _sEmail);
			mMapKeyStrToClientAccount.put(lKeyStr, lClientAccount);
			mMapKeyWireCurrencyToClientAccount.put(lKeyWire + ";;" + _sCurrency, lClientAccount);
			mMapEmailCurrencyToClientAccount.put(_sEmail + ";;" + _sCurrency, lClientAccount);
			/*
			 * Communication
			 */
			BasicPrintMsg.display(this, "Account loaded= " + lClientAccount);
		}
		return lClientAccount;
	}
	
	/**
	 * 
	 * @param _sKeyWire
	 * @param _sCurrency
	 * @return
	 */
	public final ClientAccount getpClientAccountFromKeyWire(String _sKeyWire, String _sCurrency) {
		return mMapKeyWireCurrencyToClientAccount.get(_sKeyWire + ";;" + _sCurrency);
	}
	
	/**
	 * 
	 * @param _sEmail
	 * @param _sCurrency
	 * @return
	 */
	public final ClientAccount getmClientAccountFromEmail(String _sEmail, String _sCurrency) {
		if ("pierre.roy@hotmail.com".equals(_sEmail)) {
			_sCurrency = "USD";
		}
		return mMapEmailCurrencyToClientAccount.get(_sEmail + ";;" + _sCurrency);
	}
	
	/**
	 * 
	 */
	public final void run() {
		/*
		 * Initiate
		 */
		String lDir = UOBStatic.getDIR_CONF_DYNAMIC();
		String lFileName = UOBStatic.getNAME_FILE_ACCOUNT();
		mUOBMainManager.getmComManager().displayTitle(this, "Load the account from file " + lFileName);
		/*
		 * Read file
		 */
		LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lDir, lFileName, true);
		for (List<String> lLineStr : lReadFile.getmContenuFichierListe()) {
			/*
			 * Load
			 */
			int lIdx = -1;
			String lKeyWire = BasicString.getItem(lLineStr, ++lIdx);
			String lCurrency = BasicString.getItem(lLineStr, ++lIdx);
			String lEmail = BasicString.getItem(lLineStr, ++lIdx);
			/*
			 * Replace Key wire
			 */
			lKeyWire = ClientAccount.replaceKeyWire(lKeyWire);
			/*
			 * 
			 */
			if (lEmail != null) {
				getpOrCreateClientAccount(lKeyWire, lCurrency, lEmail);
			}
		}
		/*
		 * Special case of Bunker --> we create an account for all the currencies
		 */
		/**
		 * Create the special case of the account Bunker
		 */
		for (String lCurrency : mUOBMainManager.getmForexManager().getMpMapForexToValueUSD().keySet()) {
			getpOrCreateClientAccount("Bunker", lCurrency, "contact@bunker-group.com");
		}
	}
	
	/*
	 * Getters & Setters
	 */
	public final UOBMainManager getmUOBMainManager() {
		return mUOBMainManager;
	}
	public final Map<String, ClientAccount> getmMapKeyWireCurrencyToClientAccount() {
		return mMapKeyWireCurrencyToClientAccount;
	}
	public final Map<String, ClientAccount> getmMapEmailCurrencyToClientAccount() {
		return mMapEmailCurrencyToClientAccount;
	}
	
	
}
