package se.collagekoren.repository;

import org.springframework.data.repository.CrudRepository;
import se.collagekoren.domain.Image;

/**
 * Created by Jonatan on 2017-01-07.
 */
public interface ImageRepository extends CrudRepository<Image, Integer> {

}
