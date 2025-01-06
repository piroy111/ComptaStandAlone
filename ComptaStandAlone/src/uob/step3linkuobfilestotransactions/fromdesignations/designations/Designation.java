package uob.step3linkuobfilestotransactions.fromdesignations.designations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uob.staticdata.DesignationStatic.Column;
import uob.staticdata.UOBStatic;
import uob.step1objects.transactions.UOBTransaction;

public class Designation {

	public Designation(DesignationGroup _sDesignationGroup, String _sDesignation, Column _sColumn, String _sCurrency) {
		mDesignationGroup = _sDesignationGroup;
		mDesignationStr = trim(_sDesignation.trim());
		mColumn = _sColumn;
		mCurrency = _sCurrency;
		/*
		 * 
		 */
		mIsActivateSubCode = mDesignationGroup.getmDateMaxToApply() > UOBStatic.getDATE_ACTIVATE_SUB_CODE_FOR_DESIGNATION();
		if (mIsActivateSubCode) {
			if (mDesignationStr.contains("*")) {
				mListSubDesignation = Arrays.asList(mDesignationStr.split("\\*", -1));
				for (int lIdx = 0; lIdx < mListSubDesignation.size(); lIdx++) {
					mListSubDesignation.set(lIdx, trim(mListSubDesignation.get(lIdx)));
				}
			} else {
				mIsActivateSubCode = false;
			}
		}
		/*
		 * 
		 */		
		mKeyStr = getKeyUOBDesignation(_sDesignation, _sColumn, _sCurrency);
		mListUOBTransaction = new ArrayList<>();
	}
	
	/*
	 * Data ID
	 */
	private String mDesignationStr;
	private Column mColumn;
	private String mCurrency;
	/*
	 * Data
	 */
	private boolean mIsActivateSubCode;
	private List<String> mListSubDesignation;
	/*
	 * Data
	 */
	private DesignationGroup mDesignationGroup;
	private String mAccount;
	private String mBKCategory;
	private String mKeyStr;
	private List<UOBTransaction> mListUOBTransaction;
	
	/**
	 * @return true if the designation is the one of the UOBTransaction
	 * @param _sUOBTransaction
	 */
	public final boolean getmIsMatch(UOBTransaction _sUOBTransaction) {
		String lDesignationStr = _sUOBTransaction.getmValueInColumn(mColumn);
		boolean lIsMatch = lDesignationStr != null && lDesignationStr.equals(mDesignationStr);
		if (lIsMatch) {
			lIsMatch = lIsMatch && getmIsMatchCurrency(_sUOBTransaction);
		}
		return lIsMatch;
	}
	
	/**
	 * 
	 * @param _sUOBTransaction
	 * @return
	 */
	public final boolean getmIsMatchCurrency(UOBTransaction _sUOBTransaction) {
		return mCurrency.equals("") || _sUOBTransaction.getmUOBAccount()
				.getmCurrency().equals(mCurrency);
	}
	
	/**
	 * Unique key for the map
	 * @param _sDesignationStr
	 * @param _sColumn
	 * @param _sCurrency
	 * @return
	 */
	public static String getKeyUOBDesignation(String _sDesignationStr, Column _sColumn, String _sCurrency) {
		return _sDesignationStr.trim() + ";;" + _sColumn + ";;" + _sCurrency;
	}
	
	/**
	 * Classic toString
	 */
	public String toString() {
		return "Designation= '" + mDesignationStr + "'"
				+ "; Column= '" + mColumn.toString() + "'"
				+ "; Currency= '" + mCurrency + "'";
	}
	
	/**
	 * Treats the case there are '*' in the designation. '*' can be replaced by anything
	 * @param _sDesignationCandidate
	 * @return
	 */
	public final boolean getmIsEqualsDesignation(String _sDesignationCandidate) {
		String lDesignationCandidate = trim(_sDesignationCandidate);
		if (!mIsActivateSubCode) {
			return mDesignationStr.equals(lDesignationCandidate);
		} else {
			if (_sDesignationCandidate == null) {
				return false;
			}
			if (!lDesignationCandidate.startsWith(mListSubDesignation.get(0))) {
				return false;
			}			
			for (String lSubDesignation : mListSubDesignation) {
				if (!lDesignationCandidate.contains(lSubDesignation)) {
					return false;
				}
				String[] lArray = lDesignationCandidate.split(lSubDesignation, 2);
				if (lArray.length > 1) {
					lDesignationCandidate = lArray[1];
				} else {
					lDesignationCandidate = "";
				}
			}
			return lDesignationCandidate.equals("") || mDesignationStr.endsWith("*");
		}
	}

	/**
	 * 
	 * @param _sUOBTransaction
	 */
	public final void addNewUOBTransaction(UOBTransaction _sUOBTransaction) {
		if (!mListUOBTransaction.contains(_sUOBTransaction)) {
			mListUOBTransaction.add(_sUOBTransaction);
		}
	}

	/**
	 * Remove unnecessary spaces
	 * @param _sString
	 * @return
	 */
	public static String trim(String _sString) {
		String lTrim = _sString.trim();
		String lTrimDump;
		do {
			lTrimDump = lTrim;
			lTrim = lTrim.replaceAll("  ", " ");
		} while (lTrim.length() != lTrimDump.length());
		return lTrim;
	}
	
	/*
	 * Getters & Setters
	 */
	public final String getmDesignationStr() {
		return mDesignationStr;
	}
	public final Column getmColumn() {
		return mColumn;
	}
	public final String getmCurrency() {
		return mCurrency;
	}
	public final String getmAccount() {
		return mAccount;
	}
	public final String getmBKCategory() {
		return mBKCategory;
	}
	public final void setmAccount(String mAccount) {
		this.mAccount = mAccount;
	}
	public final void setmBKCategory(String mBKCategory) {
		this.mBKCategory = mBKCategory;
	}
	public final String getmKeyStr() {
		return mKeyStr;
	}
	public final List<UOBTransaction> getmListUOBTransaction() {
		return mListUOBTransaction;
	}
	public final DesignationGroup getmDesignationGroup() {
		return mDesignationGroup;
	}
	
}

