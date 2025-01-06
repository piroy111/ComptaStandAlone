package uob.step3linkuobfilestotransactions.frommanual;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicDir;
import basicmethods.BasicFichiers;
import basicmethods.BasicFile;
import basicmethods.BasicPrintMsg;
import uob.com.ComManager;
import uob.staticdata.UOBStatic;
import uob.step1objects.account.ClientAccount;
import uob.step1objects.transactions.UOBTransaction;
import uob.step1objects.transactions.UOBValueInColumnManager;
import uob.step1objects.uobaccount.UOBAccount;
import uob.step3linkuobfilestotransactions.Linker;
import uob.step3linkuobfilestotransactions.objects.FromSubManager;

public class FromManual extends FromSubManager {

	public FromManual(Linker _sLinker) {
		super(_sLinker);
	}

	/*
	 * Static
	 */
	protected static int LENGTH_LINE_IN_FILE = 21;
	protected static int NUMBER_MAX_UOB_TRANSACTION_SIMILAR = 3;
	private static boolean IS_WRITE_TEMP = false;

	/**
	 * 
	 */
	@Override public final void run() {
		ComManager lComManager = mLinker.getmMainManager().getmComManager();
		UOBValueInColumnManager lUOBValueInColumnManager = mLinker.getmMainManager().getmUOBValueInColumnManager();
		/*
		 * Load all the files CONF manual
		 */
		String lDir = UOBStatic.getDIR_MANUAL_ASSIGNEMENT();
		String lSuffix = UOBStatic.getNAME_FILE_MANUAL_ASSIGNEMENT();
		BasicDir lBasicDir = new BasicDir(lDir, lSuffix);

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		 * Initiate
		 */
		List<String> lListLineToWriteMatch = new ArrayList<>();
		List<String> lListLineToWriteMultiple = new ArrayList<>();
		List<String> lListLineToWriteNotFound = new ArrayList<>();

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/*
		 * Loop over the files to link the UOBTransactions
		 */
		for (BasicFile lBasicFile : lBasicDir.getmMapDateToBasicFile().values()) {
			for (int lIdxLine = 0; lIdxLine < lBasicFile.getmLitUnFichierEnLignes().getmContenuFichierListe().size(); lIdxLine++) {
				List<String> lLineListStr = lBasicFile.getmLitUnFichierEnLignes().getmContenuFichierListe().get(lIdxLine);
				String lLineStr = lBasicFile.getmLitUnFichierEnLignes().getmContenuFichierLignes().get(lIdxLine);
				/*
				 * Remove the special case where the line begins with the symbol '
				 * This symbol is used to be put in front of large numbers in order to prevent Excel from rounding the large numbers
				 */
				for (int lIdxWord = 0; lIdxWord < lLineListStr.size(); lIdxWord++) {
					if (lLineListStr.get(lIdxWord).startsWith("'")) {
						lLineListStr.set(lIdxWord, lLineListStr.get(lIdxWord).substring(1));
					}
				}
				/*
				 * Check the size of the line
				 */
				if (lLineListStr.size() != LENGTH_LINE_IN_FILE) {
					lComManager.displayFatalError(this, "There is a line which is does not have the expected length. Most likely there is a number with a ',' and it adds a column in the CSV file"
							+ "\nLine= '" + lLineStr + "'"
							+ "\nRow of the line= " + (lIdxLine + 1)
							+ "\nActual length versus expected length= " + lLineListStr.size() + " versus " + LENGTH_LINE_IN_FILE
							+ "\nFile manual= " + lDir + lBasicFile.getmNameFile());
				}
				/*
				 * Load line
				 */
				int lIdx = -1;
				String lAccountNumberStr = lLineListStr.get(++lIdx);
				String lValueDateStr = lLineListStr.get(++lIdx).trim();
				String lDateStr = lLineListStr.get(++lIdx).trim();
				String lTimeStr = lLineListStr.get(++lIdx).trim();
				String lDescription = lLineListStr.get(++lIdx).trim().replaceAll(",", ";");
				String lYourReference = lLineListStr.get(++lIdx).trim().replaceAll(",", ";");
				String lOurReference = lLineListStr.get(++lIdx).trim().replaceAll(",", ";");
				String lChequeNumber = lLineListStr.get(++lIdx).trim().replaceAll(",", ";");
				++lIdx;
				String lRemarks = lLineListStr.get(++lIdx).trim().replaceAll(",", ";");
				++lIdx;
				++lIdx;
				++lIdx;
				++lIdx;
				++lIdx;
				++lIdx;
				String lDepositStr = lLineListStr.get(++lIdx).replaceAll(",", "");
				String lWithdrawalStr = lLineListStr.get(++lIdx).replaceAll(",", "");
				String lLedgerStr = lLineListStr.get(++lIdx).replaceAll(",", "");
				String lAccount = lLineListStr.get(++lIdx).trim();
				String lBKIncome = lLineListStr.get(++lIdx).trim();
				/*
				 * Transform String into values
				 */
				Long lAccountNumber = UOBTransaction.getValueFromStrAccountNumber(lAccountNumberStr);
				int lValueDate = UOBTransaction.getValueFromStrDate(lValueDateStr);
				int lDate = UOBTransaction.getValueFromStrDate(lDateStr);
				long lTime = UOBTransaction.getValueFromStrTime(lTimeStr);
				double lDeposit = UOBTransaction.getValueFromStrDollar(lDepositStr);
				double lWithdrawal = UOBTransaction.getValueFromStrDollar(lWithdrawalStr);
				double lLedger = UOBTransaction.getValueFromStrDollar(lLedgerStr);
				/*
				 * Impossible to have a transaction where BKIncome= '' and BKAccount= Bunker
				 */
				if (lAccount.equals("contact@bunker-group.com") && lBKIncome.equals("Operations_Incoming_funds_from_client")) {
					lComManager.displayFatalError(this, "It is not possible to assign a transaction to Bunker if the BKIncome is 'Operations_Incoming_funds_from_client'"
							+ "\nI did read from file manual= '" + lBasicFile.getmNameFile() + "'"
							+ "\nI did read the UOBTransaction= '" + lLineStr + "'");
				}
				/*
				 * Check if the account exists and the BKIncome exists from the CONF files. Send a fatal error otherwise
				 */
				UOBAccount lUOBAccount = mLinker.getmMainManager().getmUOBAccountLoader().getmUOBAccountManager().getmMapIDNumberToUOBAccount().get(lAccountNumber);
				ClientAccount lClientAccount = mLinker.getmMainManager().getmClientAccountManager().getmClientAccountFromEmail(lAccount, lUOBAccount.getmCurrency());
				if (lClientAccount == null) {
					lComManager.displayFatalError(this, "You did input a UOBTransaction in the file manual, but you must also input the account in the conf file"
							+ "\nI did read from file manual= '" + lBasicFile.getmNameFile() + "'"
							+ "\nI did read the UOBTransaction= '" + lLineStr + "'"
							+ "\nI Expected the account= '" + lAccount + "'" + " / currency= '" + lUOBAccount.getmCurrency() + "'"
							+ "\nto be in the conf file= '" + UOBStatic.getNAME_FILE_ACCOUNT() + "'"
							+ "\nYou must add it in the conf file with the other data (currency, origin, etc.)");
				}
				/*
				 * Find an existing UOBTransaction
				 */
				String lKeyStr = UOBTransaction.getUniqueKey(lAccountNumber, lValueDate, lDate, lTime, lDescription, 
						lYourReference, lOurReference, lChequeNumber, lRemarks, lDeposit, lWithdrawal, lLedger);
				UOBTransaction lUOBTransaction = mLinker.getmMainManager()
						.getmUOBTransactionManager().getmMapKeyToUOBTransaction().get(lKeyStr);
				/*
				 * Case we found the UOBTransaction with an exact match
				 */
				String lComment = "";
				if (lUOBTransaction != null) {
					lComment = BasicPrintMsg.addErrorMessage(lComment, "Exact match", true);
				} 
				/*
				 * Otherwise we try to guess
				 */
				else {
					List<UOBTransaction> lListUOBTransactionGuess = lUOBValueInColumnManager.getmGuessMatch(lAccountNumber, lValueDate, lDate, 
							lTime, lDescription, lYourReference, lOurReference, lChequeNumber, lRemarks, lDeposit, lWithdrawal);
					if (lListUOBTransactionGuess.size() == 1) {
						lUOBTransaction = lListUOBTransactionGuess.get(0);
						lComment = BasicPrintMsg.addErrorMessage(lComment, "Guess match", true);
					} 
					/*
					 * If we cannot guess, we communicate with a fatal error
					 */
					else {
						String lMessageSimilar = "";
						if (lListUOBTransactionGuess.size() > 1 && lListUOBTransactionGuess.size() <= NUMBER_MAX_UOB_TRANSACTION_SIMILAR) {
							lMessageSimilar = "\n\nI found a list of UOBTransaction which are similar";
							for (UOBTransaction lUOBTransactionGuess : lListUOBTransactionGuess) {
								lMessageSimilar += "\nFile origin= '" + lUOBTransactionGuess.getmFileUOBOrigin() + "'"
										+ "; UOBTransaction= '" + lUOBTransactionGuess.getmLineInFileUOBOrigin() + "'";
							}
						} else {
							lMessageSimilar = "\n\nI cannot find any similar UOBTransactions. There are zero or too many"
									+ "(" + lListUOBTransactionGuess.size() + " found similar)";
						}
						/*
						 * Fatal error because UOBTransaction == null
						 */
						lComManager.displayFatalError(this, 
								"\nOne UOBTransaction of the manual file does not exist in any file of UOB"
										+ "\n\nManual file= '" + lBasicFile.getmLitUnFichierEnLignes().getmNomCheminPlusFichier() + "'"
										+ "\nLine with a problem in the manual file; Line number= " + (lIdxLine + 2)
										+ "\nLine with a problem in the manual file; Line= '" + lBasicFile.getmLitUnFichierEnLignes().getmContenuFichierLignes().get(lIdxLine) + "'"
										+ "\n\nUnique key of UOBTransaction we are looking for= '" + lKeyStr + "'"
										+ lMessageSimilar
										+ "\n");
					}
				}
				/*
				 * Case the UOBTransaction exists --> we fill the Account (email) and BKIncome
				 */
				lUOBTransaction.setmAccount(lAccount);
				lUOBTransaction.setmBKincome(lBKIncome);
				lUOBTransaction.setmFileLinkedCompta(lBasicFile.getmLitUnFichierEnLignes().getmNomFichier());
				lUOBTransaction.setmLineInFileLinkedCompta(lLineStr);
				lComment = BasicPrintMsg.addErrorMessage(lComment, "From manual file '" + lBasicFile.getmLitUnFichierEnLignes().getmNomFichier() + "'", true);
				lUOBTransaction.setmComment(lComment);
				/*
				 * Communication
				 */
				mLinker.getmComManager().display(this, "Succesfully linked UOBTransaction; " + lUOBTransaction.getmComment());

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if (IS_WRITE_TEMP) {
					/*
					 * Separate the line to insert the ledger
					 */
					String lLineRight = getStringRight(lLineStr);
					String lLineLeft = lLineStr.substring(0, lLineStr.length() - lLineRight.length()) + ",";
					/*
					 * Check if there are several transactions for this transactions
					 */
					String lKeyStrUnique = lKeyStr.substring(0, lKeyStr.length() - "0.0".length());
					List<String> lListLineToAdd = new ArrayList<>();

					for (UOBTransaction lUOBTRansactionLoop : mLinker.getmMainManager().getmUOBTransactionManager().getmListUOBTransaction()) {
						if (lUOBTRansactionLoop.getmKeyStr().startsWith(lKeyStrUnique)) {
							lListLineToAdd.add(lLineLeft + lUOBTRansactionLoop.getmLedgerBalance() + lLineRight);
							///////////////////////////////////////////////////////////
							if (!lUOBTransaction.equals(lUOBTRansactionLoop)) {
								BasicPrintMsg.error("Not equal");
							}
							///////////////////////////////////////////////////////////
						}
					}
					if (lListLineToAdd.size() == 0 && lUOBTransaction != null) {
						lListLineToAdd.add(lLineLeft + lUOBTransaction.getmLedgerBalance() + lLineRight);
					}
					/*
					 * Fill the list of lines to write
					 */
					if (lListLineToAdd.size() > 1) {
						lListLineToWriteMultiple.addAll(lListLineToAdd);
					} else if (lListLineToAdd.size() == 1) {
						lListLineToWriteMatch.addAll(lListLineToAdd);
					} else {
						lListLineToWriteNotFound.add(lLineStr);
					}
					/*
					 * Write in file
					 */
					if (lIdxLine == lBasicFile.getmLitUnFichierEnLignes().getmContenuFichierListe().size() - 1) {
						String lDirWrite = "C:/Temp/";
						BasicFichiers.writeFile(lDirWrite, lBasicFile.getmNameFile(), null, lListLineToWriteMatch);
					}
				}

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////////////////////////////

	}


	//////////////////////////////////////////////////////////////////////////////
	/**
	 * Return the right part of the String including the comma
	 */
	private String getStringRight(String _sLineStr) {
		String lStringRight = "";
		int lIdxComma = 0;
		int lIdxChar = _sLineStr.length();
		while (lIdxComma < 2) {
			char lChar = _sLineStr.charAt(--lIdxChar);
			lStringRight = lChar + lStringRight;
			if (lChar == ',') {
				lIdxComma++;
			}
		}
		return lStringRight;
	}

	//////////////////////////////////////////////////////////////////////////////

}
