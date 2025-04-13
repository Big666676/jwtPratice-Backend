package tw.pers.jwt.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.pers.jwt.demo.filter.AuthInterceptor;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    
    private final List<String> AUTHENTICATION_IGNORE_PATHS=
            List.of("/info/check","/user/login");
    
    private final AuthInterceptor authInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(AUTHENTICATION_IGNORE_PATHS);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
