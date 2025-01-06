package rhb.transactions;

import ledgerendofmonth.transaction.LDGTransaction;

public class RHBTransaction extends LDGTransaction implements Comparable<RHBTransaction> {

	protected RHBTransaction(int _sDate, String _sComment, String _sCurrency, double _sAmount) {
		super(_sDate, _sCurrency, _sAmount);
		pComment = _sComment;
	}
	
	/*
	 * Data
	 */
	private String pComment;
	
	@Override public int compareTo(RHBTransaction _sRHBTransaction) {
		int lCompare = Integer.compare(pDate, _sRHBTransaction.getpDate());
		if (lCompare != 0) {
			return lCompare;
		} else {
			return pComment.compareTo(_sRHBTransaction.getpComment());
		} 
	}
	
	/*
	 * Getters & Setters
	 */
	public String getpComment() {
		return pComment;
	}
	
	
	
	
	
}
