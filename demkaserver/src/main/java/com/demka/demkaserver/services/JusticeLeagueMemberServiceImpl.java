package com.demka.demkaserver.services;


import com.demka.demkaserver.dto.DTOToDomainTransformer;
import com.demka.demkaserver.dto.JusticeLeagueMemberDTO;
import com.demka.demkaserver.models.JusticeLeagueMemberDetail;
import com.demka.demkaserver.repositories.JusticeLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Этот класс сервиса реализует {@link JusticeLeagueMemberService} для обеспечения
 * функциональности, необходимой системе лиги справедливости
 *
 * @author dinuka
 *
 */
@Service
public class JusticeLeagueMemberServiceImpl implements JusticeLeagueMemberService {

    @Autowired
    private JusticeLeagueRepository justiceLeagueRepo;

    /**
     * {@inheritDoc}
     */
    public void addMember(JusticeLeagueMemberDTO justiceLeagueMember) {
        JusticeLeagueMemberDetail dbMember =
                justiceLeagueRepo.findBySuperHeroName(justiceLeagueMember.getName());

        JusticeLeagueMemberDetail memberToPersist = DTOToDomainTransformer.transform(justiceLeagueMember);
        justiceLeagueRepo.insert(memberToPersist);
    }
}