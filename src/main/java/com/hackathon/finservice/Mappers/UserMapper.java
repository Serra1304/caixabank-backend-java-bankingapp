package com.hackathon.finservice.Mappers;

import com.hackathon.finservice.DTO.UserProfileResponse;
import com.hackathon.finservice.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting {@link User} entities into {@link UserProfileResponse} DTOs.
 * <p>
 * This interface uses MapStruct to generate the implementation at compile time. The {@code componentModel = "spring"}
 * parameter enables integration with the Spring framework.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a {@link User} entity into a {@link UserProfileResponse} DTO.
     * <p>
     * The mapping includes the following customizations:
     * <ul>
     *     <li>{@code accountNumber} is mapped from {@code mainAccount.accountNumber}.</li>
     *     <li>{@code accountType} is mapped from {@code mainAccount.accountType}.</li>
     * </ul>
     * </p>
     *
     * @param user the {@link User} entity to convert.
     * @return the mapped {@link UserProfileResponse} DTO.
     */
    @Mapping(target = "accountNumber", source = "mainAccount.accountNumber")
    @Mapping(target = "accountType", source = "mainAccount.accountType")
    UserProfileResponse toUserProfileResponse(User user);
}
