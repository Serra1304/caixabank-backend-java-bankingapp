package com.hackathon.finservice.Mappers;

import com.hackathon.finservice.DTO.RegisterResponse;
import com.hackathon.finservice.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting {@link User} entities into {@link RegisterResponse} DTOs.
 * <p>
 * This interface is managed by the MapStruct library, which automatically generates the mapping implementation
 * at compile time. The {@code componentModel = "spring"} parameter allows this mapper to be used as a Spring component.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface RegisterMapper {

    /**
     * Converts a {@link User} entity into a {@link RegisterResponse} DTO.
     * <p>
     * The mapping is customized to include the account information from the user's main account:
     * <ul>
     *     <li>{@code accountNumber} is mapped from {@code mainAccount.accountNumber}.</li>
     *     <li>{@code accountType} is mapped from {@code mainAccount.accountType}.</li>
     * </ul>
     * </p>
     *
     * @param user the {@link User} entity to convert.
     * @return the mapped {@link RegisterResponse} DTO.
     */
    @Mapping(target = "accountNumber", source = "mainAccount.accountNumber")
    @Mapping(target = "accountType", source = "mainAccount.accountType")
    RegisterResponse toRegisterResponse(User user);
}
