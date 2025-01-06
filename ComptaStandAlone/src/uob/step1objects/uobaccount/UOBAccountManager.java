package uob.step1objects.uobaccount;

import java.util.HashMap;
import java.util.Map;

import uob.step2loaduobfiles.UOBAccountLoader;

public class UOBAccountManager {

	public UOBAccountManager(UOBAccountLoader _sUOBAccountLoader) {
		mUOBAccountLoader = _sUOBAccountLoader;
		/*
		 * 
		 */
		mMapIDNumberToUOBAccount = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private UOBAccountLoader mUOBAccountLoader;
	private Map<Long, UOBAccount> mMapIDNumberToUOBAccount;

	/**
	 * @return Classic get or create
	 * @param _sNumber : number of the account
	 * @param _sCurrency : currency of the account
	 */
	public final UOBAccount getmOrCreateUOBAccount(long _sNumber, String _sCurrency) {
		UOBAccount lUOBAccount = mMapIDNumberToUOBAccount.get(_sNumber);
		if (lUOBAccount == null) {
			lUOBAccount = new UOBAccount(this, _sCurrency, _sNumber);
			mMapIDNumberToUOBAccount.put(_sNumber, lUOBAccount);
		}
		return lUOBAccount;
	}

	/*
	 * Getters & Setters
	 */
	public final Map<Long, UOBAccount> getmMapIDNumberToUOBAccount() {
		return mMapIDNumberToUOBAccount;
	}
	public final UOBAccountLoader getmUOBAccountLoader() {
		return mUOBAccountLoader;
	}
	

	
}
