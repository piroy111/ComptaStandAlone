package argentor.transactions;

import ledgerendofmonth.transaction.LDGTransaction;
import rhb.transactions.RHBTransaction;

public class ARGTransaction extends LDGTransaction implements Comparable<RHBTransaction> {

	protected ARGTransaction(int _sDate, String _sAsset, double _sAmount, 
			String _sDocNo, String _sComment, double _sBalance) {
		super(_sDate, _sAsset, _sAmount);
		pDocNo = _sDocNo;
		pComment = _sComment;
		pBalance = _sBalance;
	}

	/*
	 * Data
	 */
	private String pDocNo;
	private String pComment;
	private double pBalance;
	
	@Override public int compareTo(RHBTransaction _sRHBTransaction) {
		return Integer.compare(pDate, _sRHBTransaction.getpDate());
	}

	/*
	 * Getters & Setters
	 */
	public final String getpDocNo() {
		return pDocNo;
	}
	public final double getpBalance() {
		return pBalance;
	}
	public final String getpComment() {
		return pComment;
	}

}
