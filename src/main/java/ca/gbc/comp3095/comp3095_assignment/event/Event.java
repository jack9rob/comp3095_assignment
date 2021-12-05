package ca.gbc.comp3095.comp3095_assignment.event;

import ca.gbc.comp3095.comp3095_assignment.model.BaseEntity;
import ca.gbc.comp3095.comp3095_assignment.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    @Column
    String title;

    @Column
    String description;

    @Column
    Date date;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
