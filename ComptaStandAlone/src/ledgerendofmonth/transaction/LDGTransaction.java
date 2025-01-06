package ledgerendofmonth.transaction;

public abstract class LDGTransaction {

	public LDGTransaction(int _sDate, String _sCurrency, double _sAmount) {
		pDate = _sDate;
		pCurrency = _sCurrency;
		pAmount = _sAmount;
	}
	
	/*
	 * Data
	 */
	protected int pDate;
	protected String pCurrency;
	protected double pAmount;
	
	/*
	 * Getters & Setters
	 */
	public int getpDate() {
		return pDate;
	}
	public String getpCurrency() {
		return pCurrency;
	}
	public double getpAmount() {
		return pAmount;
	}
	
	
}
