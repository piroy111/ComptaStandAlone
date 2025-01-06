package argentor.staticdata;

public class ARGStatic {

	private static String DIR_IMPORT = "C:/Compta Bunker UOB/02 - Import Argentor/";
	private static String NAME_FILE_IMPORT = "_Argentor_";
	private static String USD = "Amount (USD)";
	private static String XAU = "XAU (OZ)";
	private static String XAG = "XAG (OZ)";
	private static String XPT = "XPT (OZ)";
	private static int[] IDX_COLUMNS = new int[]{5, 8};
	
	/*
	 * Getters & Setters
	 */
	public static final String getDIR_IMPORT() {
		return DIR_IMPORT;
	}
	public static final String getNAME_FILE_IMPORT() {
		return NAME_FILE_IMPORT;
	}
	public static final String getUSD() {
		return USD;
	}
	public static final String getXAU() {
		return XAU;
	}
	public static final String getXAG() {
		return XAG;
	}
	public static final String getXPT() {
		return XPT;
	}
	public static final int[] getIDX_COLUMNS() {
		return IDX_COLUMNS;
	}
	
	
}
