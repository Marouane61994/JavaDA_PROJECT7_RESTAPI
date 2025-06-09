package com.nnk.springboot.unitaire;



import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setUsername("Marouane");
        user.setFullname("Marouane GHANEM");
        user.setPassword("123456789");
        user.setRole("USER");
    }

    @Test
    void testFindById_ValidId() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1);

        assertNotNull(foundUser);
        assertEquals("Marouane GHANEM", foundUser.getFullname());
    }

    @Test
    void testFindById_InvalidId() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.findById(1));
        assertEquals("Invalid user ID: 1", ex.getMessage());
    }

    @Test
    void testCreateUser() {
        userService.create(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setUsername("updateduser");
        updatedUser.setFullname("Updated Name");
        updatedUser.setPassword("newpass");
        updatedUser.setRole("ADMIN");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.update(1, updatedUser);

        assertEquals("updateduser", result.getUsername());
        assertEquals(1, updatedUser.getId());
        verify(userRepository).save(updatedUser);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.delete(1);

        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.delete(99));
        assertEquals("Invalid user ID: 99", exception.getMessage());
    }
}
