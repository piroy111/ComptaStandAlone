package uob.staticdata;

public class UOBStatic {

	/*
	 * Directories
	 */
	private static String DIR_ROOT = "C:/Compta Bunker UOB/";
	private static String DIR_CONF_DYNAMIC = DIR_ROOT + "01 - Conf file dynamic/";
	private static String DIR_CONF_STATIC = DIR_ROOT + "01 - Conf file static/";
	private static String DIR_IMPORT_UOB = DIR_ROOT + "02 - Import UOB files/";
	private static String DIR_MANUAL_ASSIGNEMENT = DIR_ROOT + "03 - Manual assignement UOB transaction/";
	private static String DIR_OUTPUT = DIR_ROOT + "05 - Output - Missing UOBTransactions/";
	private static String DIR_DUMP = DIR_ROOT + "04 - Dump/";
	private static String DIR_OUTPUT_TRANSACTIONS = DIR_ROOT + "05 - Output - UOB Transactions/";

	/*
	 * File names
	 */
	private static String NAME_FILE_ACCOUNT = "Accounts and currency.csv";
	private static String NAME_FILE_BKINCOME = "BKIncome.csv";
	private static String NAME_FILE_UOB_ACCOUNT_STATEMENT = "_AccountStatement.csv";
	private static String NAME_FILE_FOREX = "Forex.csv";
	private static String NAME_FILE_DESIGNATION = "_UOB_designations_into_categories.csv";
	private static String NAME_FILE_MANUAL_ASSIGNEMENT = "_Manual_UOB.csv";
	private static String NAME_FILE_OUTPUT_MISSING = "_OutPut_Missing_UOB_Transactions.csv";
	private static String NAME_FILE_DUMP_KEYS = "_Unique_keys_of_UOBTransactions.csv";
	private static String NAME_OUTPUT_TRANSACTIONS = "_UOB_Transactions.csv";
	/*
	 * Data
	 */
	private static int DATE_FILE_UOB_SORT_DESCENDING = 20180401;
	private static String BKIncomeClient = "Operations_Incoming_funds_from_client";
	private static int DATE_ACTIVATE_SUB_CODE_FOR_DESIGNATION = 20190301;
	public static enum UOB_DISPLAY {On, Off}
	
	/*
	 * Getters * Setters
	 */
	public static final String getDIR_ROOT() {
		return DIR_ROOT;
	}
	public static final String getDIR_CONF_DYNAMIC() {
		return DIR_CONF_DYNAMIC;
	}
	public static final String getDIR_CONF_STATIC() {
		return DIR_CONF_STATIC;
	}
	public static final String getDIR_IMPORT_UOB() {
		return DIR_IMPORT_UOB;
	}
	public static final String getDIR_MANUAL_ASSIGNEMENT() {
		return DIR_MANUAL_ASSIGNEMENT;
	}
	public static final String getNAME_FILE_ACCOUNT() {
		return NAME_FILE_ACCOUNT;
	}
	public static final String getNAME_FILE_BKINCOME() {
		return NAME_FILE_BKINCOME;
	}
	public static final String getNAME_FILE_UOB_ACCOUNT_STATEMENT() {
		return NAME_FILE_UOB_ACCOUNT_STATEMENT;
	}
	public static final String getNAME_FILE_FOREX() {
		return NAME_FILE_FOREX;
	}
	public static final int getDATE_FILE_UOB_SORT_DESCENDING() {
		return DATE_FILE_UOB_SORT_DESCENDING;
	}
	public static final String getNAME_FILE_DESIGNATION() {
		return NAME_FILE_DESIGNATION;
	}
	public static final String getBKIncomeClient() {
		return BKIncomeClient;
	}
	public static final String getNAME_FILE_MANUAL_ASSIGNEMENT() {
		return NAME_FILE_MANUAL_ASSIGNEMENT;
	}
	public static final String getDIR_OUTPUT() {
		return DIR_OUTPUT;
	}
	public static final String getNAME_FILE_OUTPUT_MISSING() {
		return NAME_FILE_OUTPUT_MISSING;
	}
	public static final int getDATE_ACTIVATE_SUB_CODE_FOR_DESIGNATION() {
		return DATE_ACTIVATE_SUB_CODE_FOR_DESIGNATION;
	}
	public static final String getDIR_DUMP() {
		return DIR_DUMP;
	}
	public static final String getNAME_FILE_DUMP_KEYS() {
		return NAME_FILE_DUMP_KEYS;
	}
	public static final String getDIR_OUTPUT_TRANSACTIONS() {
		return DIR_OUTPUT_TRANSACTIONS;
	}
	public static final String getNAME_OUTPUT_TRANSACTIONS() {
		return NAME_OUTPUT_TRANSACTIONS;
	}
	
	
	
	
}
