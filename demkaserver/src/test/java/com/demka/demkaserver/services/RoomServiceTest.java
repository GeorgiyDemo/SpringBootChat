package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.gen.MyGenerator;
import com.demka.demkaserver.repos.RoomRepository;
import com.demka.demkaserver.utils.UUIDUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    public void create() {
        RoomDBEntity testRoom = MyGenerator.createdURoomGen(roomService);
        Assert.assertNotNull(testRoom);
        Assert.assertNotNull(testRoom.getCreatorId());
        Assert.assertNotNull(testRoom.getUsers());
        Assert.assertNotNull(testRoom.getName());
        Assert.assertNotNull(testRoom.getTimeCreated());
        Assert.assertNotNull(testRoom.getId());
    }

    @Test
    public void find() {
        RoomDBEntity testRoom = MyGenerator.createdURoomGen(roomService);
        Optional<RoomDBEntity> expected = Optional.of(testRoom);
        Mockito.doReturn(expected).when(roomRepository).findById(testRoom.getId());
        Optional<RoomDBEntity> nullRoomOptional = roomService.find(testRoom.getId() + "1");
        Assert.assertTrue(nullRoomOptional.isEmpty());
        Optional<RoomDBEntity> checkRoomOptional = roomService.find(testRoom.getId());
        Assert.assertTrue(checkRoomOptional.isPresent());
        RoomDBEntity checkRoom = checkRoomOptional.get();
        Assert.assertEquals(testRoom.getCreatorId(), checkRoom.getCreatorId());
        Assert.assertEquals(testRoom.getUsers(), checkRoom.getUsers());
        Assert.assertEquals(testRoom.getName(), checkRoom.getName());
        Assert.assertEquals(testRoom.getTimeCreated(), checkRoom.getTimeCreated());
        Assert.assertEquals(testRoom.getId(), checkRoom.getId());
    }


    @Test
    public void findUserRooms() {
        List<RoomDBEntity> testList = new ArrayList<>();
        testList.add(MyGenerator.createdURoomGen(roomService));
        String roomUserId = testList.get(0).getUsers().get(0);
        Mockito.doReturn(testList).when(roomRepository).findAllByUser(roomUserId);

        List<RoomDBEntity> nullRoomsList = roomService.findUserRooms(roomUserId + "1");
        Assert.assertEquals(0, nullRoomsList.size());
        List<RoomDBEntity> RoomsList = roomService.findUserRooms(roomUserId);
        Assert.assertEquals(1, RoomsList.size());
    }

    @Test
    public void update() {
        RoomDBEntity oldRoom = MyGenerator.createdURoomGen(roomService);
        RoomDBEntity newRoom = MyGenerator.createdURoomGen(roomService);
        String testRoomId = oldRoom.getId();
        newRoom.setId(testRoomId);
        roomService.update(oldRoom, newRoom);
        Mockito.verify(roomRepository, Mockito.times(1)).delete(oldRoom);
    }

    @Test
    public void delete() {
        RoomDBEntity testRoom = MyGenerator.createdURoomGen(roomService);
        roomService.delete(testRoom);
        Mockito.verify(roomRepository, Mockito.times(1)).delete(testRoom);
    }

    @Test
    public void findAll() {

        List<RoomDBEntity> testRoomsList = new ArrayList<>();
        int itemsCount = 10;
        for (int i = 0; i < itemsCount; i++)
            testRoomsList.add(MyGenerator.createdURoomGen(roomService));
        Mockito.doReturn(testRoomsList).when(roomRepository).findAll();
        List<RoomDBEntity> resultList = roomService.findAll();
        Assert.assertEquals(itemsCount, resultList.size());
    }

    @Test
    public void removeUserById() {
        RoomDBEntity testRoom = MyGenerator.createdURoomGen(roomService);
        int roomUsersCount = testRoom.getUsers().size();
        String existingUserId = testRoom.getUsers().get(0);
        String nonExistentUserId = UUIDUtil.newId();
        roomService.removeUser(testRoom, nonExistentUserId);
        Assert.assertEquals(roomUsersCount, testRoom.getUsers().size());
        roomService.removeUser(testRoom, existingUserId);
        Assert.assertEquals(roomUsersCount - 1, testRoom.getUsers().size());
    }

    @Test
    public void testRemoveUserByObj() {
        RoomDBEntity testRoom = MyGenerator.createdURoomGen(roomService);
        int roomUsersCount = testRoom.getUsers().size();
        UserDBEntity existingUser = MyGenerator.createdUserGen(userService);
        existingUser.setId(testRoom.getUsers().get(0));
        UserDBEntity nonExistentUser = MyGenerator.createdUserGen(userService);
        roomService.removeUser(testRoom, nonExistentUser);
        Assert.assertEquals(roomUsersCount, testRoom.getUsers().size());
        roomService.removeUser(testRoom, existingUser);
        Assert.assertEquals(roomUsersCount - 1, testRoom.getUsers().size());
    }

    @Test
    public void addUserById() {
        RoomDBEntity testRoom = MyGenerator.createdURoomGen(roomService);
        int roomUsersCount = testRoom.getUsers().size();
        //Какая жизнь, такие и тесты
        String testUserId = UUIDUtil.newId();
        roomService.addUser(testRoom, testUserId);
        Assert.assertEquals(roomUsersCount + 1, testRoom.getUsers().size());
    }

    @Test
    public void addUserByObj() {
        RoomDBEntity testRoom = MyGenerator.createdURoomGen(roomService);
        UserDBEntity testUser = MyGenerator.createdUserGen(userService);
        int roomUsersCount = testRoom.getUsers().size();
        roomService.addUser(testRoom, testUser);
        Assert.assertEquals(roomUsersCount + 1, testRoom.getUsers().size());
    }
}