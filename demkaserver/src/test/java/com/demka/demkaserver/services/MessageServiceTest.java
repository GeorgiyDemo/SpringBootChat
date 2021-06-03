package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.gen.MyTestsGenerator;
import com.demka.demkaserver.repos.MessageRepository;
import com.demka.demkaserver.utils.TimeUtil;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    public void create() {
        MessageDBEntity testMessage = MyTestsGenerator.createdMessageGen(messageService);
        Assert.assertNotNull(testMessage);
        Assert.assertNotNull(testMessage.getId());
        Assert.assertNotNull(testMessage.getUserName());
        Assert.assertNotNull(testMessage.getUserId());
        Assert.assertNotNull(testMessage.getText());
        Assert.assertNotNull(testMessage.getRoomId());
        Assert.assertNotNull(testMessage.getTimeCreated());
    }

    @Test
    public void find() {
        MessageDBEntity testMessage = MyTestsGenerator.createdMessageGen(messageService);
        Optional<MessageDBEntity> expected = Optional.of(testMessage);
        Mockito.doReturn(expected).when(messageRepository).findById(testMessage.getId());

        Optional<MessageDBEntity> nullMessageOptional = messageService.find(testMessage.getId() + "1");
        Assert.assertTrue(nullMessageOptional.isEmpty());

        Optional<MessageDBEntity> checkMessageOptional = messageService.find(testMessage.getId());
        Assert.assertTrue(checkMessageOptional.isPresent());
        MessageDBEntity checkMessage = checkMessageOptional.get();

        Assert.assertEquals(testMessage.getId(), checkMessage.getId());
        Assert.assertEquals(testMessage.getUserName(), checkMessage.getUserName());
        Assert.assertEquals(testMessage.getUserId(), checkMessage.getUserId());
        Assert.assertEquals(testMessage.getText(), checkMessage.getText());
        Assert.assertEquals(testMessage.getRoomId(), checkMessage.getRoomId());
        Assert.assertEquals(testMessage.getTimeCreated(), checkMessage.getTimeCreated());
    }

    @Test
    public void findAll() {
        List<MessageDBEntity> testMessagesList = new ArrayList<>();
        int itemsCount = 10;
        for (int i = 0; i < itemsCount; i++)
            testMessagesList.add(MyTestsGenerator.createdMessageGen(messageService));
        Mockito.doReturn(testMessagesList).when(messageRepository).findAll();
        List<MessageDBEntity> resultList = messageService.findAll();
        Assert.assertEquals(itemsCount, resultList.size());
    }

    @Test
    public void delete() {
        MessageDBEntity testMessage = MyTestsGenerator.createdMessageGen(messageService);
        messageService.delete(testMessage);
        Mockito.verify(messageRepository, Mockito.times(1)).delete(testMessage);
    }

    @Test
    public void update() {
        MessageDBEntity oldMessage = MyTestsGenerator.createdMessageGen(messageService);
        MessageDBEntity newMessage = MyTestsGenerator.createdMessageGen(messageService);
        String testMessageId = oldMessage.getId();
        newMessage.setId(testMessageId);
        messageService.update(oldMessage, newMessage);
        Mockito.verify(messageRepository, Mockito.times(1)).delete(oldMessage);
    }

    @Test
    public void getLastMessageByUser() {

        int limit = 10;
        UserDBEntity testUser = MyTestsGenerator.createdUserGen(userService);
        List<MessageDBEntity> userMessageList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            MessageDBEntity bufMessage = MyTestsGenerator.createdMessageGen(messageService);
            bufMessage.setUserId(testUser.getId());
            userMessageList.add(bufMessage);
        }
        MessageDBEntity expectedMessage = userMessageList.get(limit - 1);

        Pageable pageLimit = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "time_created"));
        List<MessageDBEntity> userMessageList1 = new ArrayList<>();
        userMessageList1.add(userMessageList.get(limit - 1));
        Mockito.doReturn(userMessageList, userMessageList1).when(messageRepository).findAllByUser(testUser.getId(), pageLimit);
        MessageDBEntity messageResult = messageService.getLastMessageByUser(testUser.getId());
        Assert.assertNull(messageResult);
        MessageDBEntity messageResult1 = messageService.getLastMessageByUser(testUser.getId());
        Assert.assertEquals(expectedMessage.getId(), messageResult1.getId());
    }

    @Test
    public void findByRoom() {
        RoomDBEntity testRoom = MyTestsGenerator.createdURoomGen(roomService);
        List<MessageDBEntity> messagesList = new ArrayList<>();
        int limit = 10;
        for (int i = 0; i < limit; i++) {
            MessageDBEntity bufMessage = MyTestsGenerator.createdMessageGen(messageService);
            bufMessage.setRoomId(testRoom.getId());
            messagesList.add(bufMessage);
        }
        Pageable pageLimit = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "time_created"));
        Mockito.doReturn(messagesList).when(messageRepository).findAllByRoomId(testRoom.getId(), pageLimit);
        List<MessageDBEntity> resultList = messageService.findByRoom(testRoom.getId());
        Assert.assertEquals(messagesList.size(), resultList.size());
    }

    @Test
    public void getNewMessagesByRooms() {

        long myTs = TimeUtil.unixTime();

        RoomDBEntity testRoom1 = MyTestsGenerator.createdURoomGen(roomService);
        RoomDBEntity testRoom2 = MyTestsGenerator.createdURoomGen(roomService);
        RoomDBEntity testRoom3 = MyTestsGenerator.createdURoomGen(roomService);
        List<RoomDBEntity> roomsList = Arrays.asList(testRoom1, testRoom2, testRoom3);

        List<MessageDBEntity> expectedMessagesList = new ArrayList<>();
        for (RoomDBEntity currentRoom : roomsList) {

            List<MessageDBEntity> currentMessagesList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                MessageDBEntity bufMessage = MyTestsGenerator.createdMessageGen(messageService);
                bufMessage.setRoomId(currentRoom.getId());
                currentMessagesList.add(bufMessage);
                expectedMessagesList.add(bufMessage);
            }
            Mockito.doReturn(currentMessagesList).when(messageRepository).getNewMessagesByRoom(currentRoom.getId(), myTs);
        }

        List<MessageDBEntity> currentMessagesList = messageService.getNewMessagesByRooms(roomsList, myTs);
        Assert.assertNotNull(currentMessagesList);
        Assert.assertEquals(expectedMessagesList.size(), currentMessagesList.size());
        Assert.assertEquals(expectedMessagesList, currentMessagesList);

    }
}