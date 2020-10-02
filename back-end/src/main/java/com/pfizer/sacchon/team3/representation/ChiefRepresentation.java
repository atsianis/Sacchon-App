package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Chiefs;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChiefRepresentation {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long id;

    public ChiefRepresentation(Chiefs chief) {
        if (chief != null) {
            firstName = chief.getFirstName();
            lastName = chief.getLastName();
            email = chief.getEmail();
            password = chief.getPassword();
            id = chief.getId();
        }
    }

    public Chiefs createChief() {
        Chiefs c = new Chiefs();
        c.setFirstName(this.firstName);
        c.setLastName(this.lastName);
        c.setEmail(this.email);
        c.setPassword(this.password);
        c.setId(this.id);

        return c;
    }
}
