package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.request.UpdatePasswordEntity;
import com.demka.demkaserver.gen.MyDataGenerator;
import com.demka.demkaserver.repos.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type User service test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    /**
     * Create.
     */
    @Test
    public void create() {
        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);
        Assert.assertNotNull(testUser);
        Assert.assertNotNull(testUser.getLogin());
        Assert.assertNotNull(testUser.getPassword());
        Assert.assertNotNull(testUser.getMasterKey());
        Assert.assertNotNull(testUser.getName());
        Assert.assertNotNull(testUser.getKey());
        Assert.assertNotNull(testUser.getTimeCreated());
        Assert.assertNotNull(testUser.getId());
    }

    /**
     * Find.
     */
    @Test
    public void find() {

        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);
        Optional<UserDBEntity> expected = Optional.of(testUser);
        Mockito.doReturn(expected).when(userRepository).findById(testUser.getId());
        Optional<UserDBEntity> nullUserOptional = userService.find(testUser.getId() + "1");
        Assert.assertTrue(nullUserOptional.isEmpty());
        Optional<UserDBEntity> checkUserOptional = userService.find(testUser.getId());
        Assert.assertTrue(checkUserOptional.isPresent());
        UserDBEntity checkUser = checkUserOptional.get();
        Assert.assertEquals(testUser.getLogin(), checkUser.getLogin());
        Assert.assertEquals(testUser.getPassword(), checkUser.getPassword());
        Assert.assertEquals(testUser.getName(), checkUser.getName());
        Assert.assertEquals(testUser.getMasterKey(), checkUser.getMasterKey());
        Assert.assertNotNull(checkUser.getKey());
        Assert.assertNotNull(checkUser.getTimeCreated());
        Assert.assertNotNull(checkUser.getId());
    }

    /**
     * Update.
     */
    @Test
    public void update() {
        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);

        UpdatePasswordEntity passwordEntity = new UpdatePasswordEntity();
        String updatedPassword = testUser.getPassword() + "1";
        passwordEntity.setEmail(testUser.getLogin());
        passwordEntity.setNewPassword(updatedPassword);
        passwordEntity.setMasterKey(testUser.getMasterKey());

        String oldPassword = testUser.getPassword();
        userService.update(testUser, passwordEntity);
        String newPassword = testUser.getPassword();
        Assert.assertNotEquals(oldPassword, newPassword);

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

    /**
     * Find by master key and email.
     */
    @Test
    public void findByMasterKeyAndEmail() {
        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);
        Optional<UserDBEntity> expected = Optional.of(testUser);
        Mockito.doReturn(expected).when(userRepository).findByMasterKeyAndEmail(testUser.getMasterKey(), testUser.getLogin());
        Optional<UserDBEntity> nullUserOptional = userService.findByMasterKeyAndEmail(testUser.getMasterKey() + "1", testUser.getLogin());
        Assert.assertTrue(nullUserOptional.isEmpty());
        Optional<UserDBEntity> checkUserOptional = userService.findByMasterKeyAndEmail(testUser.getMasterKey(), testUser.getLogin());
        Assert.assertTrue(checkUserOptional.isPresent());
        UserDBEntity checkUser = checkUserOptional.get();
        Assert.assertNotNull(checkUser);
        Assert.assertEquals(testUser.getLogin(), checkUser.getLogin());
        Assert.assertEquals(testUser.getPassword(), checkUser.getPassword());
        Assert.assertEquals(testUser.getName(), checkUser.getName());
        Assert.assertEquals(testUser.getMasterKey(), checkUser.getMasterKey());
        Assert.assertNotNull(checkUser.getKey());
        Assert.assertNotNull(checkUser.getTimeCreated());
        Assert.assertNotNull(checkUser.getId());
    }

    /**
     * Check auth by login password.
     */
    @Test
    public void checkAuthByLoginPassword() {

        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);
        Optional<UserDBEntity> expected = Optional.of(testUser);

        Mockito.doReturn(expected).when(userRepository).checkUserAuth(testUser.getLogin(), testUser.getPassword());
        UserDBEntity testNullUser = userService.checkAuth(testUser.getLogin(), testUser.getPassword() + "1");
        Assert.assertNull(testNullUser);
        UserDBEntity checkUser = userService.checkAuth(testUser.getLogin(), testUser.getPassword());
        Assert.assertNotNull(checkUser);
        Assert.assertEquals(testUser.getPassword(), checkUser.getPassword());
        Assert.assertEquals(testUser.getLogin(), checkUser.getLogin());
        Assert.assertEquals(testUser.getName(), checkUser.getName());
        Assert.assertEquals(testUser.getMasterKey(), checkUser.getMasterKey());
        Assert.assertNotNull(checkUser.getKey());
        Assert.assertNotNull(checkUser.getTimeCreated());
        Assert.assertNotNull(checkUser.getId());
    }

    /**
     * Test check auth by key.
     */
    @Test
    public void testCheckAuthByKey() {

        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);
        Optional<UserDBEntity> expected = Optional.of(testUser);

        Mockito.doReturn(expected).when(userRepository).checkUserKey(testUser.getKey());
        UserDBEntity testNullUser = userService.checkAuth(testUser.getKey() + "1");
        Assert.assertNull(testNullUser);
        UserDBEntity checkUser = userService.checkAuth(testUser.getKey());
        Assert.assertNotNull(checkUser);
        Assert.assertEquals(testUser.getPassword(), checkUser.getPassword());
        Assert.assertEquals(testUser.getLogin(), checkUser.getLogin());
        Assert.assertEquals(testUser.getName(), checkUser.getName());
        Assert.assertEquals(testUser.getMasterKey(), checkUser.getMasterKey());
        Assert.assertNotNull(checkUser.getKey());
        Assert.assertNotNull(checkUser.getTimeCreated());
        Assert.assertNotNull(checkUser.getId());
    }

    /**
     * Check user key.
     */
    @Test
    public void checkUserKey() {
        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);
        Optional<UserDBEntity> expected = Optional.of(testUser);
        Mockito.doReturn(expected).when(userRepository).checkUserKey(testUser.getKey());
        boolean authFalseFlag = userService.checkUserKey(testUser.getKey() + "1");
        Assert.assertFalse(authFalseFlag);
        boolean authTrueFlag = userService.checkUserKey(testUser.getKey());
        Assert.assertTrue(authTrueFlag);
    }

    /**
     * Find by key.
     */
    @Test
    public void findByKey() {
        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);
        Optional<UserDBEntity> expected = Optional.of(testUser);
        Mockito.doReturn(expected).when(userRepository).checkUserKey(testUser.getKey());
        Optional<UserDBEntity> nullUserOptional = userService.findByKey(testUser.getKey() + "1");
        Assert.assertTrue(nullUserOptional.isEmpty());
        Optional<UserDBEntity> checkUserOptional = userService.findByKey(testUser.getKey());
        Assert.assertTrue(checkUserOptional.isPresent());
        UserDBEntity checkUser = checkUserOptional.get();
        Assert.assertEquals(testUser.getLogin(), checkUser.getLogin());
        Assert.assertEquals(testUser.getPassword(), checkUser.getPassword());
        Assert.assertEquals(testUser.getName(), checkUser.getName());
        Assert.assertEquals(testUser.getMasterKey(), checkUser.getMasterKey());
        Assert.assertNotNull(checkUser.getKey());
        Assert.assertNotNull(checkUser.getTimeCreated());
        Assert.assertNotNull(checkUser.getId());
    }

    /**
     * Search all users by limit.
     */
    @Test
    public void searchAllUsersByLimit() {
        List<UserDBEntity> testUsersList = new ArrayList<>();
        int limit = 200;
        for (int i = 0; i < limit; i++)
            testUsersList.add(MyDataGenerator.createdUserGen(userService));
        UserDBEntity requestUser = MyDataGenerator.createdUserGen(userService);
        Pageable pageLimit = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "time_created"));
        Mockito.doReturn(testUsersList).when(userRepository).findAllLimit(pageLimit);
        List<UserDBEntity> resultList = userService.searchUsers(null, limit, requestUser.getKey());
        Assert.assertEquals(limit, resultList.size());
        Assert.assertEquals(testUsersList, resultList);
    }

    /**
     * Search all users by name.
     */
    @Test
    public void searchAllUsersByName() {
        List<UserDBEntity> testUsersList = new ArrayList<>();
        UserDBEntity requestUser = MyDataGenerator.createdUserGen(userService);
        String subString = "TEST";
        int limit = 200;
        for (int i = 0; i < limit; i++) {
            UserDBEntity bufUser = MyDataGenerator.createdUserGen(userService);
            bufUser.setName(subString + bufUser.getName());
            testUsersList.add(bufUser);
        }
        Pageable pageLimit = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "time_created"));
        Mockito.doReturn(testUsersList).when(userRepository).findAllByNameLimit(subString, pageLimit);
        List<UserDBEntity> resultList = userService.searchUsers(subString, limit, requestUser.getKey());
        Assert.assertEquals(limit, resultList.size());
        Assert.assertEquals(testUsersList, resultList);
    }

    /**
     * Delete.
     */
    @Test
    public void delete() {
        UserDBEntity testUser = MyDataGenerator.createdUserGen(userService);
        userService.delete(testUser);
        Mockito.verify(userRepository, Mockito.times(1)).delete(testUser);
    }

    /**
     * Find all.
     */
    @Test
    public void findAll() {
        List<UserDBEntity> testUsersList = new ArrayList<>();
        int itemsCount = 3;
        for (int i = 0; i < itemsCount; i++)
            testUsersList.add(MyDataGenerator.createdUserGen(userService));
        Mockito.doReturn(testUsersList).when(userRepository).findAll();
        List<UserDBEntity> resultList = userService.findAll();
        Assert.assertEquals(itemsCount, resultList.size());
    }
}