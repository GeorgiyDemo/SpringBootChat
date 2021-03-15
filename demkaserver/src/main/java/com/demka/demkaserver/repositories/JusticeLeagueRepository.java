package com.demka.demkaserver.repositories;


import com.demka.demkaserver.models.JusticeLeagueMemberDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface JusticeLeagueRepository extends MongoRepository <JusticeLeagueMemberDetail, String > {

    /**
     * Этот метод извлекает подробности об участнике лиги справедливости, связанным с .
     * переданным именем.
     *
     * @param superHeroName
     *            имя участника лиги справедливости для поиска и извлечения.
     * @return возвращает инстанс {@link JusticeLeagueMemberDetail} с подробностями
     *         об участнике.
     */
    @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
    JusticeLeagueMemberDetail findBySuperHeroName(final String superHeroName);
}