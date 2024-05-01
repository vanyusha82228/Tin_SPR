package edu.java.scrapper.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_link")
@IdClass(UserLinkId.class)
public class UserLink {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    private Link linkId;
}
