package cl.twitter.tweemedia.infrastructure.output.persistance;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class MediaDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    
    private LocalDateTime transactionRequest;
    private String requestedProfile;
    private int getMediaPhoto;
    private int getMediaVideo;
}
