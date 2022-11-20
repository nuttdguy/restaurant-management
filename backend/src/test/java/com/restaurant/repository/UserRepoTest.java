package com.restaurant.repository;

import com.restaurant.domain.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private IUserRepo userRepo;

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    User user = User.builder()
            .username("alice@restaurant.com")
            .firstName("alice")
            .lastName("jones")
            .password("password")
            .build();

    @Test
    void shouldFindUserByUsername() {
        // given
        String username = "alice@restaurant.com";
        userRepo.save(user);

        // when
        Optional<User> expected = userRepo.findByUsername(username);

        // then
        assertThat(expected).isNotEmpty();
        assertThat(expected.get().getUsername()).isEqualTo(username);

    }

    @Test
    void shouldReturnEmptyWhenUserIsNotFoundByUsername() {
        String username = "notfounduser@restaurant.com";
        userRepo.save(user);

        Optional<User> expected = userRepo.findByUsername(username);

        assertThat(expected.isEmpty()).isTrue();

    }

}

