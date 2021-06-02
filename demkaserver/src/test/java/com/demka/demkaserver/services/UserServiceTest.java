package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.request.UpdatePasswordEntity;
import com.demka.demkaserver.repos.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void create() {

        String login = "test@test.com";
        String password = "test_password";
        String username = "testUser";
        String masterPassword = "testUserMasterPassword";

        UserDBEntity testUser = userService.create(login,password, username,masterPassword);
        Assert.assertNotNull(testUser);
        Assert.assertNotNull(testUser.getLogin());
        Assert.assertNotNull(testUser.getPassword());
        Assert.assertNotNull(testUser.getMasterKey());
        Assert.assertNotNull(testUser.getName());
        Assert.assertNotNull(testUser.getKey());
        Assert.assertNotNull(testUser.getTimeCreated());
        Assert.assertNotNull(testUser.getId());
    }

    @Test
    public void find() {

        String login = "test@test.com";
        String password = "test_password";
        String username = "testUser";
        String masterPassword = "testUserMasterPassword";

        UserDBEntity createResult = userService.create(login,password, username,masterPassword);
        Optional<UserDBEntity> expected = Optional.of(createResult);
        Mockito.doReturn(expected).when(userRepository).findById(createResult.getId());
        Optional<UserDBEntity> checkUserOptional = userService.find(createResult.getId());
        Assert.assertTrue(checkUserOptional.isPresent());
        UserDBEntity checkUser = checkUserOptional.get();
        Assert.assertEquals(login,checkUser.getLogin());
        Assert.assertEquals(password, checkUser.getPassword());
        Assert.assertEquals(username, checkUser.getName());
        Assert.assertEquals(masterPassword, checkUser.getMasterKey());
        Assert.assertNotNull(checkUser.getKey());
        Assert.assertNotNull(checkUser.getTimeCreated());
        Assert.assertNotNull(checkUser.getId());
    }

    @Test
    public void update() {
        String login = "test@test.com";
        String password = "test_password";
        String username = "testUser";
        String masterPassword = "testUserMasterPassword";
        UserDBEntity testUser = userService.create(login,password, username,masterPassword);

        UpdatePasswordEntity passwordEntity = new UpdatePasswordEntity();
        String updatedPassword = password + "1";
        passwordEntity.setEmail(login);
        passwordEntity.setNewPassword(updatedPassword);
        passwordEntity.setMasterKey(masterPassword);

        String oldPassword = testUser.getPassword();
        userService.update(testUser, passwordEntity);
        String newPassword = testUser.getPassword();
        Assert.assertNotEquals(oldPassword,newPassword);

        Optional<UserDBEntity> expected = Optional.of(testUser);
        Mockito.doReturn(expected).when(userRepository).findById(testUser.getId());
        Optional<UserDBEntity> checkUserOptional = userService.find(testUser.getId());
        Assert.assertTrue(checkUserOptional.isPresent());
        UserDBEntity checkUser = checkUserOptional.get();
        Assert.assertNotNull(checkUser);
        Assert.assertEquals(passwordEntity.getNewPassword(), checkUser.getPassword());
        Assert.assertNotNull(checkUser.getKey());
        Assert.assertNotNull(checkUser.getTimeCreated());
        Assert.assertNotNull(checkUser.getId());
    }

    @Test
    public void findByMasterKeyAndEmail() {
        String email = "test@test.com";
        String password = "test_password";
        String username = "testUser";
        String masterPassword = "testUserMasterPassword";
        UserDBEntity testUser = userService.create(email, password, username,masterPassword);
        Optional<UserDBEntity> expected = Optional.of(testUser);
        Mockito.doReturn(expected).when(userRepository).findByMasterKeyAndEmail(masterPassword, email);
        Optional<UserDBEntity> checkUserOptional = userService.findByMasterKeyAndEmail(masterPassword, email);
        Assert.assertTrue(checkUserOptional.isPresent());
        UserDBEntity checkUser = checkUserOptional.get();
        Assert.assertNotNull(checkUser);
        Assert.assertEquals(email, checkUser.getLogin());
        Assert.assertEquals(password, checkUser.getPassword());
        Assert.assertEquals(username, checkUser.getName());
        Assert.assertEquals(masterPassword, checkUser.getMasterKey());
        Assert.assertNotNull(checkUser.getKey());
        Assert.assertNotNull(checkUser.getTimeCreated());
        Assert.assertNotNull(checkUser.getId());
    }

    @Test
    public void checkAuth() {
    }

    @Test
    public void testCheckAuth() {
    }

    @Test
    public void findByKey() {
    }

    @Test
    public void searchUsers() {
    }

    @Test
    public void checkUserKey() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findAll() {
    }
}