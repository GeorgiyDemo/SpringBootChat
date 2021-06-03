package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import com.demka.demkaserver.gen.MyDataGenerator;
import com.demka.demkaserver.repos.LongPollRepository;
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

/**
 * The type Long poll service test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LongPollServiceTest {

    @Autowired
    private LongPollService longPollService;

    @MockBean
    private LongPollRepository longPollRepository;

    /**
     * Create.
     */
    @Test
    public void create() {
        LongPollDBEntity testLongPoll = MyDataGenerator.createdLongPollGen(longPollService);
        Assert.assertNotNull(testLongPoll);
        Assert.assertNotNull(testLongPoll.getId());
        Assert.assertNotNull(testLongPoll.getTs());
        Assert.assertNotNull(testLongPoll.getKey());
        Assert.assertNotNull(testLongPoll.getUrl());
        Assert.assertNotNull(testLongPoll.getUserId());
    }

    /**
     * Find.
     */
    @Test
    public void find() {
        LongPollDBEntity testLongPoll = MyDataGenerator.createdLongPollGen(longPollService);
        String longPollUrl = testLongPoll.getUrl();
        Optional<LongPollDBEntity> longPollRequestOptional = Optional.of(testLongPoll);
        Mockito.doReturn(longPollRequestOptional).when(longPollRepository).findById(longPollUrl);
        Optional<LongPollDBEntity> longPollResultOptional = longPollService.find(longPollUrl);
        Assert.assertTrue(longPollResultOptional.isPresent());
        LongPollDBEntity longPollResult = longPollResultOptional.get();
        Assert.assertEquals(testLongPoll.getId(), longPollResult.getId());
        Assert.assertEquals(testLongPoll.getTs(), longPollResult.getTs());
        Assert.assertEquals(testLongPoll.getKey(), longPollResult.getKey());
        Assert.assertEquals(testLongPoll.getUrl(), longPollResult.getUrl());
        Assert.assertEquals(testLongPoll.getUserId(), longPollResult.getUserId());
    }

    /**
     * Find by url.
     */
    @Test
    public void findByUrl() {
        LongPollDBEntity testLongPoll = MyDataGenerator.createdLongPollGen(longPollService);
        String longPollUrl = testLongPoll.getUrl();
        Optional<LongPollDBEntity> longPollRequestOptional = Optional.of(testLongPoll);
        Mockito.doReturn(longPollRequestOptional).when(longPollRepository).findByUrl(longPollUrl);
        Optional<LongPollDBEntity> longPollResultOptional = longPollService.findByUrl(longPollUrl);
        Assert.assertTrue(longPollResultOptional.isPresent());
        LongPollDBEntity longPollResult = longPollResultOptional.get();
        Assert.assertEquals(testLongPoll.getId(), longPollResult.getId());
        Assert.assertEquals(testLongPoll.getTs(), longPollResult.getTs());
        Assert.assertEquals(testLongPoll.getKey(), longPollResult.getKey());
        Assert.assertEquals(testLongPoll.getUrl(), longPollResult.getUrl());
        Assert.assertEquals(testLongPoll.getUserId(), longPollResult.getUserId());
    }

    /**
     * Delete.
     */
    @Test
    public void delete() {
        LongPollDBEntity testLongPoll = MyDataGenerator.createdLongPollGen(longPollService);
        longPollService.delete(testLongPoll.getId());
        Mockito.verify(longPollRepository, Mockito.times(1)).deleteById(testLongPoll.getId());
    }

    /**
     * Delete all.
     */
    @Test
    public void deleteAll() {
        longPollService.deleteAll();
        Mockito.verify(longPollRepository, Mockito.times(2)).deleteAll();
    }

    /**
     * Find by key and url.
     */
    @Test
    public void findByKeyAndUrl() {
        LongPollDBEntity testLongPoll = MyDataGenerator.createdLongPollGen(longPollService);
        Optional<LongPollDBEntity> longPollRequestOptional = Optional.of(testLongPoll);
        String key = testLongPoll.getKey();
        String url = testLongPoll.getUrl();
        Mockito.doReturn(longPollRequestOptional).when(longPollRepository).findByKeyAndUrl(key, url);
        Assert.assertTrue(longPollService.findByKeyAndUrl(key, url + "1").isEmpty());
        Assert.assertTrue(longPollService.findByKeyAndUrl(key + "1", url).isEmpty());
        Assert.assertTrue(longPollService.findByKeyAndUrl(key + "1", url + "1").isEmpty());
        Optional<LongPollDBEntity> longPollResultOptional = longPollService.findByKeyAndUrl(key, url);
        Assert.assertTrue(longPollResultOptional.isPresent());
        LongPollDBEntity longPollResult = longPollResultOptional.get();
        Assert.assertNotNull(longPollResult);
        Assert.assertEquals(testLongPoll.getId(), longPollResult.getId());
        Assert.assertEquals(testLongPoll.getTs(), longPollResult.getTs());
        Assert.assertEquals(testLongPoll.getKey(), longPollResult.getKey());
        Assert.assertEquals(testLongPoll.getUrl(), longPollResult.getUrl());
        Assert.assertEquals(testLongPoll.getUserId(), longPollResult.getUserId());
    }

    /**
     * Find all.
     */
    @Test
    public void findAll() {
        List<LongPollDBEntity> longPollsTestList = new ArrayList<>();
        int length = 10;
        for (int i = 0; i < length; i++)
            longPollsTestList.add(MyDataGenerator.createdLongPollGen(longPollService));

        Mockito.doReturn(longPollsTestList).when(longPollRepository).findAll();
        List<LongPollDBEntity> longPollsResultList = longPollService.findAll();
        Assert.assertEquals(length, longPollsResultList.size());
        Assert.assertEquals(longPollsTestList, longPollsResultList);
    }

    /**
     * Update.
     */
    @Test
    public void update() {
        LongPollDBEntity oldPoll = MyDataGenerator.createdLongPollGen(longPollService);
        LongPollDBEntity newPoll = MyDataGenerator.createdLongPollGen(longPollService);
        longPollService.update(oldPoll, newPoll);
        Mockito.verify(longPollRepository, Mockito.times(1)).delete(oldPoll);
    }
}