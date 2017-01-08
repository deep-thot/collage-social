package se.collagekoren.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import se.collagekoren.controller.CurrentProfileFactory;
import se.collagekoren.request.CurrentUserMethodArgumentResolver;

import java.util.List;

/**
 * Created by Jonatan on 2017-01-08.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
    private final CurrentProfileFactory profileFactory;

    @Autowired
    public WebConfig(CurrentProfileFactory profileFactory) {
        this.profileFactory = profileFactory;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserMethodArgumentResolver(profileFactory));
    }
}
