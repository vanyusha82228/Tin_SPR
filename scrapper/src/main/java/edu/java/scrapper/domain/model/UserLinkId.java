package edu.java.scrapper.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class UserLinkId implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "link_id", nullable = false)
    private Long linkId;
}
