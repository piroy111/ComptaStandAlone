package ifb.transactions;

import ledgerendofmonth.transaction.LDGTransaction;

public class IFBTransaction extends LDGTransaction implements Comparable<IFBTransaction> {

	protected IFBTransaction(int _sDate, String _sComment, String _sCurrency, double _sAmount) {
		super(_sDate, _sCurrency, _sAmount);
		pComment = _sComment;
	}
	
	/*
	 * Data
	 */
	private String pComment;
	private String pBKIncome;
	private String pLineInFile;
	
	@Override public int compareTo(IFBTransaction _sIFBTransaction) {
		int lCompare = Integer.compare(pDate, _sIFBTransaction.getpDate());
		if (lCompare != 0) {
			return lCompare;
		} else {
			return pComment.compareTo(_sIFBTransaction.getpComment());
		} 
	}

	/**
	 * 
	 */
	public String toString() {
		return pDate + "," + pComment + "," + pCurrency + "," + pAmount + "," + pBKIncome;
	}
	
	/*
	 * Getters & Setters
	 */
	public String getpComment() {
		return pComment;
	}
	public String getpBKIncome() {
		return pBKIncome;
	}
	public void setpBKIncome(String pBKIncome) {
		this.pBKIncome = pBKIncome;
	}
	public String getpLineInFile() {
		return pLineInFile;
	}
	public void setpLineInFile(String pLineInFile) {
		this.pLineInFile = pLineInFile;
	}
	
}
