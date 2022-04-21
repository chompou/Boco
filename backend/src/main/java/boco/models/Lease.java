package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
@Entity
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isApproved;
    private Timestamp fromDatetime;
    private Timestamp toDatetime;
    private boolean isCompleted;

    @OneToOne
    private Review ownerReview;

    @OneToOne
    private Review leaseeReview;

    @OneToOne
    private Review itemReview;
}
