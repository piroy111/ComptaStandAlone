package listfileschronologically.program;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import listfileschronologically.staticdata.CHStatic;

public class CHManager {


	/**
	 *
	 */
	public final void run(String _sPath) {
		/*
		 * Read files
		 */
		System.out.print("Reading all the files..");
		Path lPathMain = Paths.get(_sPath);
		List<Path> lListPath = BasicFichiersNio.getListFilesAndSubFiles(lPathMain);
		System.out.println("Done");
		/*
		 * Fill list of CHFiles
		 */
		System.out.print("Sorting..");
		List<CHFile> lListCHFile = new ArrayList<>();
		for (Path lPath : lListPath) {
			CHFile lCHFile = new CHFile(lPath);
			lListCHFile.add(lCHFile);
		}
		/*
		 * Sort list of CHFile
		 */
		Collections.sort(lListCHFile);
		System.out.println("Done");
		/*
		 * Print out the result
		 */
		System.out.print("Writing output..");
		List<String> lListLineToWrite = new ArrayList<>();
		for (CHFile lCHFile : lListCHFile) {
			String lLine = lCHFile.getpDateFromTitle()
					+ "," + lCHFile.getpDateFromWindows()
					+ "," + lCHFile.getpNameFile()
					+ "," + lCHFile.getpPath().toString();
			lListLineToWrite.add(lLine);
		}
		String lHeader = "Date from title,Date from windows last update,Name of file,Path";
		String lDir = CHStatic.getDIR_OUTPUT();
		String lNameFile = BasicDateInt.getmToday() + CHStatic.getNAME_FILE_OUTPUT();
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
		System.out.println("File output written: " + lDir + lNameFile);
	}
	
}
