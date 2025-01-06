package uob;

import argentor.ARGManager;
import ifb.IFBManager;
import rhb.RHBManager;
import uob.staticdata.UOBStatic.UOB_DISPLAY;

public class UOBLaunchMe {

	public static void main(String[] _sArgs) {
		new ARGManager().run();
		new IFBManager().run();
		new RHBManager().run();
		new UOBMainManager(UOB_DISPLAY.On).run();
	}
	
}
