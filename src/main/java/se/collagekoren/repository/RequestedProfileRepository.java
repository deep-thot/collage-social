package se.collagekoren.repository;

import org.springframework.data.repository.CrudRepository;
import se.collagekoren.domain.RequestedProfile;

/**
 * Created by Jonatan on 2017-01-07.
 */
public interface RequestedProfileRepository extends CrudRepository<RequestedProfile, String>{
}
