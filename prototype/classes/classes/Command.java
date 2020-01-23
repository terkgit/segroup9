package classes;

import java.io.Serializable;

public class Command  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String msg;
	public Object obj;
	
	public Command(String _msg, Object _obj){
		msg=_msg;
		obj=_obj;
	}	
	
	public Command(String _msg){
		msg=_msg;
		obj="n/a";
	}
}
