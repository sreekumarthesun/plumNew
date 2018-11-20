package com.plum.utility;
import org.apache.log4j.Logger;
public class log4j {

	//Static inner class for machine one : - To restrict the creation of corresponding logger instance limit to 1
	
	public static class log4jService{
		public static Logger log=Logger.getLogger("Machine");
	}
	
	public static class log4jService1{
		public static Logger log=Logger.getLogger("Machine1");
	}
	
	public static class log4jService2{
		public static Logger log=Logger.getLogger("Machine2");
	}
	
	public static class log4jService3{
		public static Logger log=Logger.getLogger("Machine3");
	}
	
	public static class log4jService4{
		public static Logger log=Logger.getLogger("Machine4");
	}
	
	public static class log4jService5{
		public static Logger log=Logger.getLogger("Machine5");
	}
	
	public static class log4jService6{
		public static Logger log=Logger.getLogger("Machine6");
	}
	
	public static class log4jService7{
		public static Logger log=Logger.getLogger("Machine7");
	}
	
	public static class log4jService8{
		public static Logger log=Logger.getLogger("Machine8");
	}
	
	public static class log4jService9{
		public static Logger log=Logger.getLogger("Machine9");
	}
}
