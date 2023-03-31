package com.example.JPA.model;

import com.example.JPA.TestConfig;
import com.example.JPA.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TestConfig.class})
public class UserTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        System.out.println(applicationContext.getBeanDefinitionCount());
    }
    @Test
    public void prePersist_createCardPass_shouldSetCardPass() {
        // Given

        User user = new User("John Doe", "john.doe@mail.com", "encodedPassword", 30, Role.USER);

        // When
        repository.save(user);

        //  Then
        assertThat(user.getCardPass()).isNotNull();
        assertThat(user.getCardPass().getUser()).isEqualTo(user);
        assertThat(user.getCardPass().getAmount()).isEqualTo(1000);
    }

}
