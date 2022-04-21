package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class Lease {
    private long id; // Primary key
    private boolean isApproved;
    private Timestamp fromDatetime;
    private Timestamp toDatetime;
    private boolean isCompleted;
}
