package qas.uicontroller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;
import qas.uicontroller.security.AuthenticateInterceptor;
import qas.uicontroller.security.JwtTokenProvider;
import qas.uicontroller.service.CookieService;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    private final CookieService cookieService;
    private JwtTokenProvider jwtTokenProvider;

    public MvcConfig(CookieService cookieService, JwtTokenProvider jwtTokenProvider) {
        this.cookieService = cookieService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/js/")
        .addResourceLocations("classpath:/static/css/");
    }
    /*@Bean
    public MappedInterceptor CookieInterceptor() {
        return new MappedInterceptor(new String[] {"/**"}, new String[] {"/login", "/reg", "classpath:/static/js/*",
                "classpath:/static/css/*", "/*.map","/*.js","/*.css", "/error"}, new AuthenticateInterceptor(cookieService, jwtTokenProvider));
    }*/
}
