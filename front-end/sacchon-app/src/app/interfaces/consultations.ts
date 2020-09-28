export interface Consultations {
	status: number;
	data: {
		comment: string;
		doctor: {};
		id: number;
		patient: {};
		seenByPatient: number;
		timeCreated: number;
	}
}
