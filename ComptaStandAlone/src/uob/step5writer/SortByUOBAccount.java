package uob.step5writer;

import java.util.Comparator;

import uob.step1objects.transactions.UOBTransaction;

public class SortByUOBAccount implements Comparator<UOBTransaction> {

	@Override public int compare(UOBTransaction _sUOBTransaction1, UOBTransaction _sUOBTransaction2) {
		return Long.compare(_sUOBTransaction1.getmUOBAccount().getmNumber(), _sUOBTransaction2.getmUOBAccount().getmNumber());
	}

	
	
}
