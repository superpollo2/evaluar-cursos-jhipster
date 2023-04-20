package com.evaluar_cursos.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.evaluar_cursos.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.evaluar_cursos.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.evaluar_cursos.domain.User.class.getName());
            createCache(cm, com.evaluar_cursos.domain.Authority.class.getName());
            createCache(cm, com.evaluar_cursos.domain.User.class.getName() + ".authorities");
            createCache(cm, com.evaluar_cursos.domain.UserO.class.getName());
            createCache(cm, com.evaluar_cursos.domain.Student.class.getName());
            createCache(cm, com.evaluar_cursos.domain.AcademicProgram.class.getName());
            createCache(cm, com.evaluar_cursos.domain.AcademicProgram.class.getName() + ".students");
            createCache(cm, com.evaluar_cursos.domain.Profesor.class.getName());
            createCache(cm, com.evaluar_cursos.domain.EnrollCourse.class.getName());
            createCache(cm, com.evaluar_cursos.domain.Role.class.getName());
            createCache(cm, com.evaluar_cursos.domain.Course.class.getName());
            createCache(cm, com.evaluar_cursos.domain.CourseQuestion.class.getName());
            createCache(cm, com.evaluar_cursos.domain.ProfesorQuestion.class.getName());
            createCache(cm, com.evaluar_cursos.domain.Student.class.getName() + ".enrollCourses");
            createCache(cm, com.evaluar_cursos.domain.AcademicProgram.class.getName() + ".enrollCourses");
            createCache(cm, com.evaluar_cursos.domain.Profesor.class.getName() + ".enrollCourses");
            createCache(cm, com.evaluar_cursos.domain.Profesor.class.getName() + ".totalScoreCourses");
            createCache(cm, com.evaluar_cursos.domain.Role.class.getName() + ".userOS");
            createCache(cm, com.evaluar_cursos.domain.Course.class.getName() + ".totalScoreCourses");
            createCache(cm, com.evaluar_cursos.domain.AcademicPeriod.class.getName());
            createCache(cm, com.evaluar_cursos.domain.AcademicPeriod.class.getName() + ".enrollCourses");
            createCache(cm, com.evaluar_cursos.domain.AcademicPeriod.class.getName() + ".totalScoreCourses");
            createCache(cm, com.evaluar_cursos.domain.AcademicPeriod.class.getName() + ".totalScoreProfesors");
            createCache(cm, com.evaluar_cursos.domain.EvaluationCourse.class.getName());
            createCache(cm, com.evaluar_cursos.domain.EvaluationProfesor.class.getName());
            createCache(cm, com.evaluar_cursos.domain.TotalScoreCourse.class.getName());
            createCache(cm, com.evaluar_cursos.domain.TotalScoreProfesor.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
