package edu.java.scrapper.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class UserLinkId implements Serializable {
    @ManyToOne
    private Long userId;
    @ManyToOne
    private Long linkId;
}
