export interface PatientRecords {
	status: string;
	data: [{
		glycose: string;
		carbs: string;
		timeCreated: string;
		id: string;
	}]
}
