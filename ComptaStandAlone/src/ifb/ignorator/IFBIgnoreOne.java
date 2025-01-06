package ifb.ignorator;

import basicmethods.BasicPrintMsg;

public class IFBIgnoreOne {

	protected IFBIgnoreOne(String _sCode, String _sBKIncome) {
		pCode = _sCode;
		pBKIncome = _sBKIncome;
		/*
		 * 
		 */
		interpretCode();
	}
	
	/*
	 * Data
	 */
	private String pCode;
	private String pCodeExact;
	private String pCodeStart;
	private String pCodeEnd;
	private String pBKIncome;
	
	/**
	 * 
	 */
	private void interpretCode() {
		if (pCode.startsWith("*") && pCode.endsWith("*")) {
			BasicPrintMsg.error("Error code; Code= " + pCode);
		}
		if (pCode.endsWith("*")) {
			pCodeStart = pCode.substring(0, pCode.length() - 1);
		} else if (pCode.startsWith("*")) {
			pCodeEnd = pCode.substring(1);
		} else {
			pCodeExact = pCode;
		}
	}
	
	/**
	 * 
	 */
	public final boolean getpIsMatch(String _sDescription) {
		if (pCodeExact != null) {
			return _sDescription.equals(pCodeExact);
		} else if (pCodeStart != null) {
			return _sDescription.startsWith(pCodeStart);
		} else if (pCodeEnd != null) {
			return _sDescription.endsWith(pCodeEnd);
		} else {
			BasicPrintMsg.error("class not initiated");
			return false;
		}
	}

	public String getpCode() {
		return pCode;
	}

	public String getpBKIncome() {
		return pBKIncome;
	}
	
	
	
}
