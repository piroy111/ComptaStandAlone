package uob.step3linkuobfilestotransactions.fromdesignations.linker;

import java.util.List;
import java.util.TreeMap;

import uob.staticdata.DesignationStatic.Column;
import uob.step1objects.transactions.UOBTransaction;
import uob.step3linkuobfilestotransactions.fromdesignations.FromDesignationManager;
import uob.step3linkuobfilestotransactions.fromdesignations.designations.Designation;
import uob.step3linkuobfilestotransactions.fromdesignations.designations.DesignationGroup;

public class DesignationLinker {

	public DesignationLinker(FromDesignationManager _sFromDesignationManager) {
		mFromDesignationManager = _sFromDesignationManager;
	}

	/*
	 * Data
	 */
	private FromDesignationManager mFromDesignationManager;

	/**
	 * 
	 */
	public final void linkeDesignationToUOBTransaction() {
		mFromDesignationManager.getmComManager().displayTitle(this, "Link UOBTransaction to the Designation");
		/*
		 * Allocate UOBTransaction into the appropriate DesignationGroup:
		 * This is the DesignationGroup with the date closest and higher than the date of the UOBTransaction
		 */
		TreeMap<Integer, DesignationGroup> lTreeMapDateToDesignationGroup = mFromDesignationManager.getmDesignationManager()
				.getmTreeMapDateMaxToDesignationGroup();
		for (UOBTransaction lUOBTransaction : mFromDesignationManager.getmLinker().getmListUOBTransactionNotLinked()) {
			int lDateUOBTransaction = lUOBTransaction.getmDate();
			Integer lDateDesignationGroup = lTreeMapDateToDesignationGroup.ceilingKey(lDateUOBTransaction);
			DesignationGroup lDesignationGroup;
			if (lDateDesignationGroup != null) {
				lDesignationGroup = lTreeMapDateToDesignationGroup.get(lDateDesignationGroup);
				lDesignationGroup.addNewUOBTransaction(lUOBTransaction);
			}
		}
		/*
		 * link each UOBTransaction to a Designation (a category and an account)
		 */
		for (DesignationGroup lDesignationGroup : lTreeMapDateToDesignationGroup.values()) {
			/*
			 * Loop over the columns (of the UOBFile) which we can refer to in the Designation
			 */
			for (Column lColumn : Column.values()) {
				for (UOBTransaction lUOBTransaction : lDesignationGroup.getmListUOBTransaction()) {
					/*
					 * Identify the Designation to which the UOBTransaction can be linked to, given the column
					 */
					String lDesignationStr = lUOBTransaction.getmValueInColumn(lColumn);
					List<Designation> lListDesignation = lDesignationGroup.getmMapColumToListDesignation().get(lColumn);
					if (lListDesignation != null) {
						for (Designation lDesignation : lListDesignation) {
							/*
							 * If the Designation is found, then we build the link between the Designation and the UOBTransaction
							 */
							if (lDesignation.getmIsEqualsDesignation(lDesignationStr)
									&& lDesignation.getmIsMatchCurrency(lUOBTransaction)) {
								lDesignation.addNewUOBTransaction(lUOBTransaction);
								/*
								 * We link the UOBTransaction by setting the account and the BKIncome
								 */
								lUOBTransaction.setmAccount(lDesignation.getmAccount());
								lUOBTransaction.setmBKincome(lDesignation.getmBKCategory());
								/*
								 * We store the name of the file designation and the line for debug purposes
								 */
								lUOBTransaction.setmFileLinkedCompta(lDesignationGroup.getmDesignationFile().getmReadFileInLinesWithMap().getmLitUnFichierEnLignes().getmNomFichier());
								lUOBTransaction.setmLineInFileLinkedCompta(lDesignation.toString());
								/*
								 * We set the comment of the UOBTransaction to know where the link comes from
								 */
								String lComment = "Designation Compta= {" + lDesignation + "}";
								lUOBTransaction.setmComment(lComment);
								/*
								 * Communication
								 */
								String lMsg = "UOBTransaction linked via designation"
										+ "; Column= " + lColumn
										+ "; Designation= " + lDesignationStr
										+ "; UOBTransaction= " + lUOBTransaction;
								mFromDesignationManager.getmComManager().display(this, lMsg);
							}
						}
					}
				}
			}
		}
	}



}
