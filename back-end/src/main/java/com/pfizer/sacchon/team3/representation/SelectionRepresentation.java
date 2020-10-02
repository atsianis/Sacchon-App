package com.pfizer.sacchon.team3.representation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectionRepresentation {
    private long doctor_id;
    private long patient_id;
}
