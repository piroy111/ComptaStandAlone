package ledgerendofmonth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import ledgerendofmonth.staticdata.LDGStatic;
import ledgerendofmonth.transaction.LDGTransaction;

public class LDGWriter<T extends LDGTransaction> {

	public LDGWriter(String _sBankName) {
		pBankName = _sBankName;
	}

	/*
	 * Data
	 */
	private String pBankName;
	private Map<String, Double> pMapCurrencyToLedger;
	private List<T> pListLDGTransaction;

	/**
	 * Write file of ledger (sum of all amounts per currency)
	 * @param _sListLDGTransaction
	 */
	public final void run(List<T> _sListLDGTransaction) {
		pListLDGTransaction = _sListLDGTransaction;
		computeMapCurrencyToLedger();
		writeFile();
	}

	/**
	 * 
	 */
	private void computeMapCurrencyToLedger() {
		pMapCurrencyToLedger = new HashMap<>();
		for (LDGTransaction lLDGTransaction : pListLDGTransaction) {
			if (lLDGTransaction.getpDate() <= LDGStatic.getDATE_LIMIT()) {
				Double lLedger = pMapCurrencyToLedger.get(lLDGTransaction.getpCurrency());
				if (lLedger == null) {
					lLedger = 0.;
				}
				lLedger += lLDGTransaction.getpAmount();
				pMapCurrencyToLedger.put(lLDGTransaction.getpCurrency(), lLedger);
			}
		}
	}

	/**
	 * 
	 * @param _sBankName
	 * @param _sListLDGTransaction
	 */
	public final void writeFile() {
		/*
		 * Prepare DIR + name file
		 */
		String lDir = LDGStatic.getDIR_OUTPUT() + pBankName + "/";
		BasicFichiers.getOrCreateDirectory(lDir);
		String lNameFile = BasicDateInt.getmToday() + "_" + pBankName + LDGStatic.getSUFFIX_OUTPUT_FILE();
		/*
		 * Build file content
		 */
		List<String> lListLineToWrite = new ArrayList<>();
		for (String lCurrency : pMapCurrencyToLedger.keySet()) {
			double lLedger = pMapCurrencyToLedger.get(lCurrency);
			String lLine = lCurrency + "," + lLedger;
			lListLineToWrite.add(lLine);
		}
		/*
		 * Write file
		 */
		String lHeader = "Date,Currency,Amount";
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
		BasicPrintMsg.display(this, "File written successfully= '" + lDir + lNameFile + "'");
	}

}
