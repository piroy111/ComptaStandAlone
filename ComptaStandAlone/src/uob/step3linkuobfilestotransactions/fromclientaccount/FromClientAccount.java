package uob.step3linkuobfilestotransactions.fromclientaccount;

import uob.staticdata.DesignationStatic.Column;
import uob.staticdata.UOBStatic;
import uob.step1objects.account.ClientAccount;
import uob.step1objects.transactions.UOBTransaction;
import uob.step3linkuobfilestotransactions.Linker;
import uob.step3linkuobfilestotransactions.objects.FromSubManager;

public class FromClientAccount extends FromSubManager {

	public FromClientAccount(Linker _sLinker) {
		super(_sLinker);
	}
	
	/**
	 * 
	 */
	@Override public final void run() {
		/*
		 * Check the BKIncome static exists in the CONF file of BKIncome
		 */
		String lBKIncome = UOBStatic.getBKIncomeClient();
		mLinker.getmMainManager().getmUOBBKIncomeManager().checkBKIncomeExists(this, lBKIncome);
		for (UOBTransaction lUOBTransaction : mLinker.getmListUOBTransactionNotLinked()) {
			for (Column lColumn : Column.values()) {
				/*
				 * Find the key for the map which gives the email of the account
				 */
				String lValueInColumn = lUOBTransaction.getmValueInColumn(lColumn);
				String lKeyWire = ClientAccount.replaceKeyWire(lValueInColumn); 
				String lCurrency = lUOBTransaction.getmUOBAccount().getmCurrency();
				String lKeyStr = lKeyWire + ";;" + lCurrency;
				/*
				 * Get the account email from the map of the AccountManager, which has been built from the CONF file
				 */
				ClientAccount lClientAccount = mLinker.getmMainManager().getmClientAccountManager()
						.getmMapKeyWireCurrencyToClientAccount().get(lKeyStr);
				if (lClientAccount != null) {
					lUOBTransaction.setmAccount(lClientAccount.getmEmail());
					lUOBTransaction.setmBKincome(lBKIncome);
					lUOBTransaction.setmComment("From conf file client account + '" + UOBStatic.getNAME_FILE_ACCOUNT() + "'");
					lUOBTransaction.setmFileLinkedCompta(UOBStatic.getNAME_FILE_ACCOUNT());
					lUOBTransaction.setmLineInFileLinkedCompta(lClientAccount.toString());
					/*
					 * Communication
					 */
					mLinker.getmMainManager().getmComManager().display(this, "UOB transaction succesfully linked to client= "
							+ lClientAccount.getmEmail() + "; UOBTransaction= " + lUOBTransaction);
				}
			}
		}
	}
	
	
	
	
}
