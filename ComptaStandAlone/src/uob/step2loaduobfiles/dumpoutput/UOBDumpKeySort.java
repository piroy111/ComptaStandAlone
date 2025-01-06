package uob.step2loaduobfiles.dumpoutput;

import java.util.Comparator;

import uob.step1objects.transactions.UOBTransaction;

public class UOBDumpKeySort implements Comparator<UOBTransaction> {

	@Override public int compare(UOBTransaction _sUOBTransaction0, UOBTransaction _sUOBTransaction1) {
		int lCompareFile = _sUOBTransaction0.getmFileUOBOrigin().compareTo(_sUOBTransaction1.getmFileUOBOrigin());
		if (lCompareFile != 0) {
			return lCompareFile;
		} else {
			int lCompareKey = _sUOBTransaction0.getmKeyStr().compareTo(_sUOBTransaction1.getmKeyStr());
			return lCompareKey;
		}
	}

}
