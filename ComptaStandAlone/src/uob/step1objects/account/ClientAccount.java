package uob.step1objects.account;

public class ClientAccount {

	protected ClientAccount(String _sKeyWire, String _sCurrency, String _sEmail) {
		mKeyWire = _sKeyWire;
		mCurrency = _sCurrency;
		mEmail = _sEmail;
		/*
		 * 
		 */
		mKeyMap = getUniqueKeyStr(_sKeyWire, _sCurrency, _sEmail);
	}
	
	/*
	 * Data
	 */
	private String mKeyWire;
	private String mCurrency;
	private String mEmail;
	private String mKeyMap;
	
	/**
	 * 
	 * @param _sKeyWire
	 * @param _sCurrency
	 * @param _sEmail
	 * @return
	 */
	protected static final String getUniqueKeyStr(String _sKeyWire, String _sCurrency, String _sEmail) {
		/*
		 * Change Key wire to remove spaces and put it upper case, so we maximize the chances to identify it 
		 */
		String lKeyWire = replaceKeyWire(_sKeyWire);
		return lKeyWire + ";;" + _sCurrency + ";;" + _sEmail;
	}

	/**
	 * Change Key wire to remove spaces and put it upper case, so we maximize the chances to identify it
	 * @param _sKeyWire
	 * @return
	 */
	public static String replaceKeyWire(String _sKeyWire) {
		return _sKeyWire.replaceAll(" ", "").toUpperCase();
	}
	
	/**
	 * 
	 */
	public String toString() {
		return mEmail + "-" + mCurrency;
	}
	
	/*
	 * Getters & Setters
	 */
	public final String getmKeyWire() {
		return mKeyWire;
	}
	public final String getmCurrency() {
		return mCurrency;
	}
	public final String getmEmail() {
		return mEmail;
	}
	public final String getmKeyMap() {
		return mKeyMap;
	}
	
	
	
	
	
	
	
}
