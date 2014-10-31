package server.command;


public enum ArgumentType {
		INTEGER {
			
			@Override
			public Object parse(String str) {
				try {
					return Integer.parseInt(str);
				} catch (NumberFormatException e) {
					return null;
				}
			}
			
		},
		
		DECIMAL {
			
			@Override
			public Object parse(String str) {
				try {
					return Double.parseDouble(str);
				} catch (NumberFormatException e) {
					return INTEGER.parse(str);
				}
			}
			
		},
		
		STRING {
			
			@Override
			public Object parse(String str) {
				return str;
			}
			
		};
		
		
		public abstract Object parse(String str);
		
	}
