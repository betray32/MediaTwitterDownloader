package cl.twitter.tweemedia.infrastructure.output.persistance;

import org.springframework.data.repository.CrudRepository;

public interface MediaDataRepository extends CrudRepository<MediaDataEntity, String> {
}
