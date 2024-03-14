package com.logistics.snowapi.service;

import com.logistics.snowapi.model.SnowUser;
import com.logistics.snowapi.repository.SnowUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SnowUserServiceTest {

    @Autowired
    private SnowUserService userService;

    @MockBean
    private SnowUserRepository userRepository;

    @Test
    public void testCreateUser() {
        SnowUser newUser = new SnowUser();
        newUser.setId(1); // Assuming ID is set after saving

        when(userRepository.save(any(SnowUser.class))).thenReturn(newUser);

        SnowUser createdUser = userService.createUser(new SnowUser());
        assertThat(createdUser.getId()).isEqualTo(1);

        verify(userRepository, times(1)).save(any(SnowUser.class));
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 1;
        SnowUser existingUser = new SnowUser();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
