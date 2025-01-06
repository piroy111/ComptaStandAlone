package ifb.staticdata;

public class IFBStatic {

	private static String IMPORT_IFB_STATEMENTS = "C:/Compta Bunker UOB/02 - Import IFB files/";
	private static String IFB_SUFFIX_FILE_INPUT = "_AccountStatement_IFB.csv";
	private static String CONF_FILE_IGNORE = "IFB_Transactions_to_ignore_back_forth_Condor.csv";
	private static String DIR_OUTPUT = "C:/Compta Bunker UOB/05 - Output - IFB/";
	
	/*
	 * Getters & Setters
	 */
	public static String getIMPORT_IFB_STATEMENTS() {
		return IMPORT_IFB_STATEMENTS;
	}
	public static String getIFB_SUFFIX_FILE_INPUT() {
		return IFB_SUFFIX_FILE_INPUT;
	}
	public static String getCONF_FILE_IGNORE() {
		return CONF_FILE_IGNORE;
	}
	public static final String getDIR_OUTPUT() {
		return DIR_OUTPUT;
	}
	
}
