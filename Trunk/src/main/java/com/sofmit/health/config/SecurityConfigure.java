package com.sofmit.health.config;

import com.dm.security.verification.VerificationCodeFilter;
import com.dm.security.verification.VerificationCodeStorage;
import com.dm.security.verification.support.SessionScopeVerificationCodeStorage;
import com.dm.springboot.autoconfigure.security.EnableVerificationCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofmit.health.dto.DataDictDto;
import com.sofmit.health.dto.VaccineTypeDto;
import com.sofmit.health.service.DataDictService;
import com.sofmit.health.service.VaccineTypeService;
import com.sofmit.zlb.sso.annotation.EnableZlbSso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;

@EnableWebSecurity
@EnableZlbSso
@EnableVerificationCode
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Autowired
    private DataDictService dataDictService;
    @Value("${initdata}")
    private boolean init;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/zlUser**/**", "/users/current**").authenticated()
                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and().logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().addFilterBefore(verificationCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }

    private VerificationCodeFilter verificationCodeFilter() {
        VerificationCodeFilter filter = new VerificationCodeFilter();
        filter.requestMatcher(new AntPathRequestMatcher("/login", "POST"));
        filter.setStorage(codeStorage());
        filter.setObjectMapper(om);
        return filter;
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public VerificationCodeStorage codeStorage() {
        return new SessionScopeVerificationCodeStorage();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService);
    }

    @PostConstruct
    private void init() {
        if (init) {
            // 疫苗初始化
            VaccineTypeDto vaccineType =  new VaccineTypeDto();
            vaccineType.setParent(null);
            vaccineType.setName("成人疫苗");
            vaccineTypeService.save(vaccineType);
            VaccineTypeDto vaccineType1 =  new VaccineTypeDto();
            vaccineType1.setParent(null);
            vaccineType1.setName("儿童疫苗");
            vaccineTypeService.save(vaccineType1);
            // 字典初始化
            DataDictDto dataDictDto = new DataDictDto();
            dataDictDto.setCode("1000");
            dataDictDto.setName("肺部");
            dataDictService.save(dataDictDto);
            DataDictDto dataDictDto1 = new DataDictDto();
            dataDictDto1.setCode("1001");
            dataDictDto1.setName("心脏");
            dataDictService.save(dataDictDto1);
            DataDictDto dataDictDto11 = new DataDictDto();
            dataDictDto11.setCode("1002");
            dataDictDto11.setName("生殖");
            dataDictService.save(dataDictDto11);
            DataDictDto dataDictDto2 = new DataDictDto();
            dataDictDto2.setCode("1000");
            dataDictDto2.setName("精神");
            dataDictService.save(dataDictDto2);
            DataDictDto dataDictDto3 = new DataDictDto();
            dataDictDto3.setCode("1000");
            dataDictDto3.setName("手臂");
            dataDictService.save(dataDictDto3);
            DataDictDto dataDictDto4 = new DataDictDto();
            dataDictDto4.setCode("1000");
            dataDictDto4.setName("小腿");
            dataDictService.save(dataDictDto4);
            DataDictDto dataDictDto5 = new DataDictDto();
            dataDictDto5.setCode("1000");
            dataDictDto5.setName("胸部");
            dataDictService.save(dataDictDto5);
            DataDictDto dataDictDto6 = new DataDictDto();
            dataDictDto6.setCode("1000");
            dataDictDto6.setName("肩部");
            dataDictService.save(dataDictDto6);
            DataDictDto dataDictDto7 = new DataDictDto();
            dataDictDto7.setCode("1000");
            dataDictDto7.setName("手肘");
            dataDictService.save(dataDictDto7);
            DataDictDto dataDictDto8 = new DataDictDto();
            dataDictDto8.setCode("1000");
            dataDictDto8.setName("大腿");
            dataDictService.save(dataDictDto8);
            DataDictDto dataDictDto9 = new DataDictDto();
            dataDictDto9.setCode("1000");
            dataDictDto9.setName("手掌");
            dataDictService.save(dataDictDto9);
            DataDictDto dataDictDto10 = new DataDictDto();
            dataDictDto10.setCode("1000");
            dataDictDto10.setName("足");
            dataDictService.save(dataDictDto10);
        }
    }
}
