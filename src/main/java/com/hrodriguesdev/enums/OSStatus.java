package com.hrodriguesdev.enums;

public enum OSStatus {
	
		STATUS_1_AG_OS(1 , "Aguardando Orçamento"),
		STATUS_2_AG_OS(2 , "Enviar Orçamento"),
		STATUS_3_AG_OS(3 , "Aguardando Aprovação"),
		STATUS_4_AG_OS(4 , "Aprovado, aquardando Reparo!"),
		STATUS_5_AG_OS(5 , "Liberado, aquardando Coleta!"),
		STATUS_6_AG_OS(6 , "Não Aprovado, aquardando coleta!"),
		STATUS_7_AG_OS(7 , "Coletado"),
		STATUS_8_AG_OS(8 , "Aprovado sem orcamento, aquardando reparo!"),
		STATUS_9_AG_OS(9 , "Liberado sem orcamento, aquardando coleta!"),
		STATUS_12_AG_OS(12 , "Coletado, enviar Orçamento"),
		STATUS_13_AG_OS(13 , "Coletado, aguardando Aprovação"),
		STATUS_15_AG_OS(15 , "Manutenção na area, enviar Orçamento"),
		STATUS_16_AG_OS(16 , "Manutenção na area, aguardando Aprovação");
		
		
		public int statusInt;
		public String statusStr;

		
		OSStatus(int statusInt, String statusStr) {			
			this.statusInt = statusInt;			
			this.statusStr = statusStr;
		}
		
		OSStatus(int statusInt){
			this.statusInt = statusInt;
		}
		
		public String getStatusStr() {
			return statusStr;
		}

		public int getStatusInt() {
			return statusInt;
		}

		
	
	

}
