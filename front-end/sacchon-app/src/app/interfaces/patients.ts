export interface Patients {
	status: number;
	data: {
		id: string;
		firstName: string;
		lastName: string;
		email: string;
		password: string;
		dob: string;
		canBeExamined: string;
		lastActive: string;
		gender: string;
	}
}
