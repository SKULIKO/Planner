package ru.javabegin.micro.planner.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import ru.javabegin.micro.planner.utils.converter.KCRoleConverter;


@Configuration//данный класс будет считан как конфиг спринг контейнера
@EnableWebSecurity//включает механизм защиты адресовбкоторые настраиваются в SecurityFilterChain
@EnableGlobalMethodSecurity(prePostEnabled = true)//включение механизма для защиты методов по ролям
public class SpringSecurityConfig {
    //создается специальный бин, который отвечает за настройки запросов в http(метод вызывается)
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        //конвертер для настройки spring security
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        //подключаем конвектер ролей

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KCRoleConverter());
        //все сетевые настройки
        http.authorizeRequests()
                .antMatchers("/admin/*").hasRole("admin")//настройка ролей
                .antMatchers("/auth/*").hasRole("user")//
                .anyRequest().authenticated()//остальной API  будет доступен только аутентифицированным пользователям

                .and()//добавляем новые настройки

                .oauth2ResourceServer()//добавляем конвертер ролей JWT в AUthority (Role)
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
        return http.build();
    }
}
