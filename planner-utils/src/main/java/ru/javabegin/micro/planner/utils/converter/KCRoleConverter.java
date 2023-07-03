package ru.javabegin.micro.planner.utils.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
//класс конвертер из данных JWT в роли spring security

public class KCRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        //  получаем доступ к разделу JSON
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        //если раздел JSON не будет найдет- значит нет ролей
if (realmAccess == null || realmAccess.isEmpty()){
    return new ArrayList<>();//пустая коллекция, нет ролей
}
     //
//Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))//функциональный код
//        .stream().map(roleName -> "ROLE_" +roleName)//
//        .map(SimpleGrantedAuthority::new)//
//        .collect(Collectors.toList());//
        Collection<GrantedAuthority> returnValue = new ArrayList<GrantedAuthority>();
        for (String roleName:(List<String>) realmAccess.get("roles")){
            returnValue.add(new SimpleGrantedAuthority("ROLE_"+ roleName));
        }
return returnValue;
    }
}
