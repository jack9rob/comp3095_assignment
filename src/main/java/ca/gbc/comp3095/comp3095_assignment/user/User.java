package ca.gbc.comp3095.comp3095_assignment.user;

import ca.gbc.comp3095.comp3095_assignment.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;
    @Column
    private String password;
}
