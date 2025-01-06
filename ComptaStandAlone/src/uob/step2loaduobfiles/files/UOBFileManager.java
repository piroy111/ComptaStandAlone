package uob.step2loaduobfiles.files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicFichiersNioRaw;
import basicmethods.LitUnFichierEnLignes;
import uob.staticdata.UOBStatic;
import uob.step1objects.transactions.UOBTransaction;
import uob.step1objects.uobaccount.UOBAccount;
import uob.step2loaduobfiles.UOBAccountLoader;

public class UOBFileManager {

	public UOBFileManager(UOBAccountLoader _sUOBAccountLoader) {
		mUOBAccountLoader = _sUOBAccountLoader;
		/*
		 * 
		 */
		mMapNameFileToUOBFile = new HashMap<>();
	}

	/*
	 * Data
	 */
	private UOBAccountLoader mUOBAccountLoader;
	private List<Path> mListPathToRead;
	private LitUnFichierEnLignes mReadFile;
	private Map<String, UOBFile> mMapNameFileToUOBFile;
	private UOBFile mUOBFile;

	/**
	 * Load files of UOB import into UOBTransaction & UOBAccount
	 */
	public final void run() {
		mUOBAccountLoader.getmComManager().displaySuperTitle(this, "Load files UOB imported manually in "
				+ UOBStatic.getDIR_IMPORT_UOB());
		/*
		 * 
		 */
		mUOBAccountLoader.getmComManager().displayTitle(this, "Detect files");
		detectFiles();
		/*
		 * 
		 */
		mUOBAccountLoader.getmComManager().displayTitle(this, "Load Files");
		loadFiles();
		/*
		 * 
		 */
		mUOBAccountLoader.getmComManager().displayTitle(this, "runUOBFiles");
		runUOBFile();
	}

	/**
	 * Detects the files with the correct extension
	 */
	private void detectFiles() {
		String lDir = UOBStatic.getDIR_IMPORT_UOB();
		List<Path> lListPaths = BasicFichiersNioRaw.getListPath(Paths.get(lDir));
		mListPathToRead = new ArrayList<Path>();
		for (Path lPath : lListPaths) {
			if (lPath.getFileName().toString().endsWith(UOBStatic.getNAME_FILE_UOB_ACCOUNT_STATEMENT())) {
				mListPathToRead.add(lPath);
			} else {
				mUOBAccountLoader.getmComManager().displayWarning(this, "a file UOB is not properly written"
						+ "; We expect the file to end with '" + UOBStatic.getNAME_FILE_UOB_ACCOUNT_STATEMENT() + "'"
						+ "\nFile= " + lDir + lPath.getFileName().toString());
			}
		}
	}

	/**
	 * @return Load the files into the class UOBTransaction & UOBAccount
	 */
	private void loadFiles() {
		for (Path lPath : mListPathToRead) {
			mReadFile = new LitUnFichierEnLignes(lPath, true);
			mUOBFile = getmOrCreateUOBFile(mReadFile);
			int lNbUOBTransaction = 0;
			/*
			 * IDX of columns
			 */
			UOBAccount lUOBAccount = null;
			int lIdxAccountNumber = -1;
			int lIdxAccountCurrency = -1;
			int lIdxValueDate = -1;
			int lIdxDate = -1;
			int lIdxTime = -1;
			int lIdxDescription = -1;
			int lIdxYourReference = -1;
			int lIdxOurReference = -1;
			int lidxChequeNumber = -1;
			int lIdxRemarks = -1;
			int lIdxDeposit = -1;
			int lIdxWithdrawal = -1;
			int lIdxLedgerBalance = -1;
			/*
			 * Read file
			 */
			for (int lIdxLine = 0; lIdxLine < mReadFile.getmContenuFichierListe().size(); lIdxLine++) {
				List<String> lLineStr = mReadFile.getmContenuFichierListe().get(lIdxLine);
				if (lLineStr.size() < 1) {
					continue;
				}
				String lType = lLineStr.get(0).replaceAll(" ", "");
				/*
				 * Exit on T
				 */
				if (lType.startsWith("T")) {
					break;
				}
				/*
				 * Load header account
				 */
				if (lType.equals("H1")) {
					List<String> lListHeader = removeWhiteSpaces(lLineStr);
					lIdxAccountNumber = getIndex(lListHeader, "Account Number");
					lIdxAccountCurrency = getIndex(lListHeader, "Account Currency");
				}
				/*
				 * Load account
				 */
				else if (lType.equals("H2")) {
					if (lIdxAccountNumber == -1) {
						mUOBFile.errorInFile("The header H1 is missing in the file");
					}
					long lNumber = UOBTransaction.getValueFromStrAccountNumber(lLineStr.get(lIdxAccountNumber));
					String lCurrency = lLineStr.get(lIdxAccountCurrency).trim();
					lUOBAccount = mUOBAccountLoader.getmUOBAccountManager().getmOrCreateUOBAccount(lNumber, lCurrency);
					lUOBAccount.checkCurrency(mReadFile.getmNomFichier(), lCurrency);
				}
				/*
				 * Load header transaction
				 */
				else if (lType.equals("D1")) {
					List<String> lListHeader = removeWhiteSpaces(lLineStr);
					lIdxValueDate = getIndex(lListHeader, " Value Date ");
					lIdxDate = getIndex(lListHeader, " Date ");
					lIdxTime = getIndex(lListHeader, " Time ");
					lIdxDescription = getIndex(lListHeader, " Description ");
					lIdxYourReference = getIndex(lListHeader, " Your Reference ");
					lIdxOurReference = getIndex(lListHeader, " Our Reference ");
					lidxChequeNumber = getIndex(lListHeader, " Cheque Number ");
					lIdxRemarks = getIndex(lListHeader, " Remarks ");
					lIdxDeposit = getIndex(lListHeader, " Deposit ");
					lIdxWithdrawal = getIndex(lListHeader, " Withdrawal ");
					lIdxLedgerBalance = getIndex(lListHeader, " Ledger Balance");
				}
				/*
				 * Load transaction
				 */
				else if (lType.equals("D2")) {
					/*
					 * Errors
					 */
					if (lIdxValueDate == -1) {
						mUOBFile.errorInFile("The header D1 is missing in the file");
					}
					if (lUOBAccount == null) {
						mUOBFile.errorInFile("The account is not defined in the file whereas we have some transactions");
					}
					/*
					 * Load
					 */
					int lValueDate = UOBTransaction.getValueFromStrDate(lLineStr.get(lIdxValueDate));
					int lDate = UOBTransaction.getValueFromStrDate(lLineStr.get(lIdxDate));
					long lTime = UOBTransaction.getValueFromStrTime(lLineStr.get(lIdxTime));
					String lDescription = lLineStr.get(lIdxDescription).trim().replaceAll(",", ";");
					String lYourReference = lLineStr.get(lIdxYourReference).trim();
					String lOurReference = lLineStr.get(lIdxOurReference).trim();
					String lChequeNumber = lLineStr.get(lidxChequeNumber).trim();
					String lRemarks = lLineStr.get(lIdxRemarks).trim().replaceAll(",", ";");
					double lDeposit = UOBTransaction.getValueFromStrDollar(lLineStr.get(lIdxDeposit));
					double lWithdrawal = UOBTransaction.getValueFromStrDollar(lLineStr.get(lIdxWithdrawal));
					double lLedgerBalance = UOBTransaction.getValueFromStrDollar(lLineStr.get(lIdxLedgerBalance));
					/*
					 * Create UOBTransaction
					 */
					UOBTransaction lUOBTransaction = mUOBAccountLoader.getmMainManager().getmUOBTransactionManager().getmOrCreateUOBTransaction(lUOBAccount, 
							lValueDate, lDate, lTime, lDescription, lYourReference, lOurReference, 
							lChequeNumber, lRemarks, lDeposit, lWithdrawal, lLedgerBalance);
					lNbUOBTransaction++;
					/*
					 * Add to UOBFile
					 */
					mUOBFile.addNewUOBTransaction(lUOBTransaction);
					lUOBTransaction.setmFileUOBOrigin(mReadFile.getmNomFichier());
					lUOBTransaction.setmLineInFileUOBOrigin(mReadFile.getmContenuFichierLignes().get(lIdxLine));
					/*
					 * Error
					 */
					if (lValueDate == 0) {
						mUOBFile.errorInFile("The transaction does not have any value date; lValueDate= 0"
								+ "\nlLine in file= '" + lLineStr + "'");
					}
				}
			}
			/*
			 * Communication
			 */
			String lDisplay = "File '" + mReadFile.getmNomFichier() + "' loaded --> " 
					+ (lNbUOBTransaction == 0 ? "empty" : lNbUOBTransaction + " transactions");
			mUOBAccountLoader.getmComManager().display(this, lDisplay);
		}
	}
	
	/**
	 * runUOBFile
	 */
	private void runUOBFile() {
		/*
		 * Initiate
		 */
		mUOBAccountLoader.getmComManager().display(this, "Compute date for UOBFiles");
		List<UOBFile> lListUOBFile = new ArrayList<>(mMapNameFileToUOBFile.values());
		for (UOBFile lUOBFile : lListUOBFile) {
			lUOBFile.computeDate();
		}
		/*
		 * Sort files according to their date
		 */
		mUOBAccountLoader.getmComManager().display(this, "Sort the list of UOBFiles");
		Collections.sort(lListUOBFile);
		/*
		 * Input the previous file for each UOBFile
		 */
		mUOBAccountLoader.getmComManager().display(this, "Input the previous file for each UOBFile");
		for (int lIdx = 0; lIdx < lListUOBFile.size(); lIdx++) {
			if (lIdx > 0) {
				lListUOBFile.get(lIdx).declareUOBFilePrevious(lListUOBFile.get(lIdx - 1));
			}
		}
		/*
		 * Compute final ledger of each file
		 */
		mUOBAccountLoader.getmComManager().display(this, "Compute the final ledger for each UOBFile");
		mUOBAccountLoader.getmComManager().display(this, "List of file= " + lListUOBFile);
		mUOBAccountLoader.getmComManager().display(this, "Number of files== " + lListUOBFile.size());
		for (int lIdx = 0; lIdx < lListUOBFile.size(); lIdx++) {
			UOBFile lUOBFile = lListUOBFile.get(lIdx);
			mUOBAccountLoader.getmComManager().display(this, "Compute for File No " + lIdx + " = " + lUOBFile.getmNameFile());
			lUOBFile.computeAscendingOrDescending();
			mUOBAccountLoader.getmComManager().display(this, "computeAscendingOrDescending Done");
			lUOBFile.computeFinalLedger();
			mUOBAccountLoader.getmComManager().display(this, "computeFinalLedger Done");
			mUOBAccountLoader.getmComManager().display(this, null);
		}
		mUOBAccountLoader.getmComManager().display(this, "Finished computing ledger for each UOBFile");
	}


	/**
	 * @return Remove white spaces of all elements of the list of String
	 * @param _sListString
	 */
	private List<String> removeWhiteSpaces(List<String> _sListString) {
		List<String> lListString = new ArrayList<String>();
		for (String lWord : _sListString) {
			lWord = lWord.replaceAll(" ", "");
			lWord = lWord.replaceAll("\\t", "");
			lListString.add(lWord);
		}
		return lListString;
	}

	/**
	 * @return the index of the header in the list. Send an error message if it is not found
	 * @param _sListString
	 * @param _sWordToLookFor
	 * @param _sFileName
	 */
	private int getIndex(List<String> _sListString, String _sWordToLookFor) {
		_sWordToLookFor = _sWordToLookFor.replaceAll(" ", "");
		_sWordToLookFor = _sWordToLookFor.replaceAll("\t", "");
		if (!_sListString.contains(_sWordToLookFor)) {
			mUOBFile.errorInFile("The list of headers does not contain the header we are looking for"
					+ "\nListHeaders= " + _sListString.toString() 
					+ "\n Header we look for= " + _sWordToLookFor);
		}
		return _sListString.indexOf(_sWordToLookFor);
	}

	/**
	 * @return classic get or create
	 * @param _sReadFile
	 */
	public final UOBFile getmOrCreateUOBFile(LitUnFichierEnLignes _sReadFile) {
		UOBFile lUOBFile = mMapNameFileToUOBFile.get(_sReadFile.getmNomFichier());
		if (lUOBFile == null) {
			lUOBFile = new UOBFile(this, _sReadFile);
			mMapNameFileToUOBFile.put(_sReadFile.getmNomFichier(), lUOBFile);
		}
		return lUOBFile;
	}
	

	
	/*
	 * Getters & Setters
	 */
	public final List<Path> getmListPathToRead() {
		return mListPathToRead;
	}
	public final UOBAccountLoader getmUOBAccountLoader() {
		return mUOBAccountLoader;
	}

	public final Map<String, UOBFile> getmMapNameFileToUOBFile() {
		return mMapNameFileToUOBFile;
	}








































}


