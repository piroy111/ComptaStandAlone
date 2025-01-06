package ledgerendofmonth.staticdata;

public class LDGStatic {

	private static String DIR_OUTPUT = "C:/Compta Bunker UOB/08 - Ledgers end of months/";
	private static String SUFFIX_OUTPUT_FILE = "_Ledgers_end_of_month.csv";
	private static int DATE_LIMIT = -1;
	
	
	/*
	 * Data
	 */
	public static String getDIR_OUTPUT() {
		return DIR_OUTPUT;
	}
	public static String getSUFFIX_OUTPUT_FILE() {
		return SUFFIX_OUTPUT_FILE;
	}
	public static final int getDATE_LIMIT() {
		return DATE_LIMIT;
	}
	public static final void setDATE_LIMIT(int dATE_LIMIT) {
		DATE_LIMIT = dATE_LIMIT;
	}
	
	
}
