package se.collagekoren;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.collagekoren.domain.Profile;
import se.collagekoren.domain.Voice;
import se.collagekoren.repository.ProfileRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CollageSocialApplication.class)
@WebAppConfiguration
public class CollageSocialApplicationTests {

	@Autowired
	private ProfileRepository repository;


	@Before
	public void setUp() throws Exception {
	}

	private Profile newProfile(String name, Voice voice) {
		return new Profile(name, "bio", "fb", "li", "lf", voice, "");
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void can_save(){
		repository.save(newProfile("Hej", Voice.BASS_1));
		Iterable<Profile> all = repository.findAll();
		assertThat(all.iterator().hasNext(), is(true));
	}

	@Test
	public void can_find_by_Voice(){
		repository.save(newProfile("Jag", Voice.SOPRANO_1));
		repository.save(newProfile("Du", Voice.SOPRANO_1));
		repository.save(newProfile("Han", Voice.BASS_1));
		repository.save(newProfile("Hon", Voice.ALTO_2));

		Stream<Profile> result = repository.findByVoice(Voice.SOPRANO_1);
		List<Profile> list = result.collect(Collectors.toList());
		assertThat(list.size(), is(2));
		System.out.println(list);
	}

}
