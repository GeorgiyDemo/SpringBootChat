package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import com.demka.demkaserver.repos.LongPollRepository;
import com.demka.demkaserver.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с лонгпулами
 */
@Service
public class LongPollService {

    private final LongPollRepository longPollRepo;

    @Autowired
    public LongPollService(LongPollRepository longPollRepo) {
        this.longPollRepo = longPollRepo;
    }

    /**
     * Создание нового лонгпула
     *
     * @param userId - иднетификатор пользователя
     * @param ts     - UNIX-время последнего принятого сообщения
     * @return
     */
    public LongPollDBEntity create(String userId, Long ts) {

        LongPollDBEntity newPoll = new LongPollDBEntity();
        newPoll.setId(UUIDUtil.newId());
        newPoll.setTs(ts);
        newPoll.setKey(UUIDUtil.newKey());
        newPoll.setUserId(userId);
        newPoll.setUrl(UUIDUtil.newURL());

        longPollRepo.save(newPoll);
        return newPoll;
    }

    /**
     * Поиск лонгпула по URL
     *
     * @param url - URL лонгпула
     * @return
     */
    public Optional<LongPollDBEntity> findByUrl(String url) {
        return longPollRepo.findByUrl(url);
    }

    /**
     * Поиск лонгпула по ключу и url
     *
     * @param key - ключ лонгпула
     * @param url - URL лонгпула
     * @return
     */
    public Optional<LongPollDBEntity> findByKeyAndUrl(String key, String url) {
        return longPollRepo.findByKeyAndUrl(key, url);
    }

    /**
     * Обновление данных лонгпула
     *
     * @param oldObj - старый объект лонгпула
     * @param newObj - новый объект лонгпула
     */
    public void update(LongPollDBEntity oldObj, LongPollDBEntity newObj) {
        longPollRepo.delete(oldObj);
        longPollRepo.save(newObj);
    }

    /**
     * Удаление лонгпула по его id
     *
     * @param pollId - иднетификатор лонгпула
     */
    public void delete(String pollId) {
        longPollRepo.deleteById(pollId);
    }

    /**
     * Удаление всех лонгпулов в коллекции.
     * Необходим для отчистки всей коллекции при перезапуске сервера
     * Используется в переопределенном InitializingBean
     */
    public void deleteAll() {
        longPollRepo.deleteAll();
    }

    /**
     * Поиск всех лонгпулов
     *
     * @return
     */
    public List<LongPollDBEntity> findAll() {
        return longPollRepo.findAll();
    }

    /**
     * Поиск лонгпула по его id
     *
     * @param id - идентификатор лонгпула
     * @return
     */
    public Optional<LongPollDBEntity> find(String id) {
        return longPollRepo.findById(id);
    }
}