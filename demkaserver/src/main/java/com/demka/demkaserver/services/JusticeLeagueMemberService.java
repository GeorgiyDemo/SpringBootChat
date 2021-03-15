package com.demka.demkaserver.services;

import com.demka.demkaserver.dto.JusticeLeagueMemberDTO;

/**
 * This interface defines the functionality exposed on the justice league
 * management system.
 *
 * @author dinuka
 *
 */
public interface JusticeLeagueMemberService {

    /**
     * This method will add a new member to the system.
     *
     * @param justiceLeagueMember
     *            an instance of {@link JusticeLeagueMemberDTO} with the member
     *            details.
     */
    void addMember(final JusticeLeagueMemberDTO justiceLeagueMember);
}
