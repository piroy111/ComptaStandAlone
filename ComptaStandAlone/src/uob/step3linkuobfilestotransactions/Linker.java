package uob.step3linkuobfilestotransactions;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicPrintMsg;
import uob.UOBMainManager;
import uob.com.ComManager;
import uob.step1objects.transactions.UOBTransaction;
import uob.step3linkuobfilestotransactions.fromclientaccount.FromClientAccount;
import uob.step3linkuobfilestotransactions.fromdesignations.FromDesignationManager;
import uob.step3linkuobfilestotransactions.frommanual.FromManual;

public class Linker {

	public Linker(UOBMainManager _sMainManager) {
		mMainManager = _sMainManager;
		/*
		 * 
		 */
		instantiate();
	}
	
	/*
	 * Data
	 */
	private UOBMainManager mMainManager;
	private FromDesignationManager mFromDesignationManager;
	private FromClientAccount mFromClientAccount;
	private FromManual mFromManual;
	private List<UOBTransaction> mListUOBTransactionNotLinked;
	
	/**
	 * 
	 */
	private void instantiate() {
		mFromDesignationManager = new FromDesignationManager(this);
		mFromClientAccount = new FromClientAccount(this);
		mFromManual = new FromManual(this);
	}
	
	/**
	 * 
	 */
	public final void run() {
		
		/////////////////////////////////////////////////////////////////
		BasicPrintMsg.displayTitle(this, "mListUOBTransactionNotLinked");
		/////////////////////////////////////////////////////////////////

		
		/*
		 * Initiate the list of UOBTransaction not linked to all the UOBTransaction
		 */
		mListUOBTransactionNotLinked = new ArrayList<>(mMainManager.getmUOBTransactionManager().getmListUOBTransaction());
		/*
		 * Link the UOBTransaction thanks to the Designation file
		 */
		
		/////////////////////////////////////////////////////////////////
		BasicPrintMsg.displayTitle(this, "mFromDesignationManager");
		/////////////////////////////////////////////////////////////////

		
		mFromDesignationManager.runFrom();
		/*
		 * Link UOBTransaction to client account thanks to the Client Account CONF file
		 */
		
		/////////////////////////////////////////////////////////////////
		BasicPrintMsg.displayTitle(this, "mFromClientAccount");
		/////////////////////////////////////////////////////////////////

		mFromClientAccount.runFrom();
		/*
		 * Link the UOBTransaction thanks to the manual file. This is very specific so we need to treat it first
		 */
		
		/////////////////////////////////////////////////////////////////
		BasicPrintMsg.displayTitle(this, "mFromManual");
		/////////////////////////////////////////////////////////////////

		mFromManual.runFrom();
	}
	
	/**
	 * 
	 */
	public final void removeUOBTransactionLinked() {
		List<UOBTransaction> lListUOBTransactionToRemove = new ArrayList<>();
		for (UOBTransaction lUOBTransaction : mListUOBTransactionNotLinked) {
			if (lUOBTransaction.getmAccount() != null) {
				lListUOBTransactionToRemove.add(lUOBTransaction);
			}
		}
		mListUOBTransactionNotLinked.removeAll(lListUOBTransactionToRemove);
	}
	
	
	/*
	 * Getters & Setters
	 */
	public final UOBMainManager getmMainManager() {
		return mMainManager;
	}
	public final FromDesignationManager getmFromDesignationManager() {
		return mFromDesignationManager;
	}
	public final List<UOBTransaction> getmListUOBTransactionNotLinked() {
		return mListUOBTransactionNotLinked;
	}
	public ComManager getmComManager() {
		return mMainManager.getmComManager();
	}
	
}
