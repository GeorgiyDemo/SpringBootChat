package com.demka.demkaserver.dto;

import com.demka.demkaserver.models.JusticeLeagueMemberDetail;

public class DTOToDomainTransformer {

    /**
     * This method will trasform an instance of {@link JusticeLeagueMemberDTO}
     * to {@link JusticeLeagueMemberDetail}
     *
     * @param memberDTO
     *            an instance of {@link JusticeLeagueMemberDTO} with the details
     *            of the member.
     * @return an instance of {@link JusticeLeagueMemberDetail} with the details
     *         of the member.
     */
    public static JusticeLeagueMemberDetail transform(final JusticeLeagueMemberDTO memberDTO) {
        return new JusticeLeagueMemberDetail(memberDTO.getName(), memberDTO.getSuperPower(), memberDTO.getLocation());
    }
}

