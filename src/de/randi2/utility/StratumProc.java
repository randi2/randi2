package de.randi2.utility;

public abstract class StratumProc {
	
	public static StratumProc noStratification(){
		return new StratumProc(){

			@Override
			public int stratify(String value) {
				return 0;
			}
			
		};
	}
	
	public static StratumProc binaryStratification(String op1, String op2){
		final String opt1 = op1;
		final String opt2 = op2;
		return new StratumProc(){

			@Override
			public int stratify(String value) {
				if(value.equals(opt1)){
					return 0;
				}
				else if(value.equals(opt2)){
					return 1;
				}
				return -1;
			}
			
		};
	}
	
	public abstract int stratify(String value);
	
}
