package se.collagekoren.repository;

import org.springframework.data.repository.CrudRepository;
import se.collagekoren.domain.Profile;
import se.collagekoren.domain.Voice;

import java.util.stream.Stream;

/**
 * Created by Jonatan on 2015-11-25.
 */
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

    Iterable<Profile> findAll();

    Stream<Profile> findByVoice(Voice voice);
}
