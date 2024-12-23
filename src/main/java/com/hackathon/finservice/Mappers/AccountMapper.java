package com.hackathon.finservice.Mappers;

import com.hackathon.finservice.DTO.AccountResponse;
import com.hackathon.finservice.Entities.Account;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting {@link Account} entities into {@link AccountResponse} DTOs.
 * <p>
 * This interface is managed by the MapStruct library, which automatically generates the mapping implementation
 * at compile time. The {@code componentModel = "spring"} parameter allows this mapper to be used as a Spring component.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * Converts an {@link Account} entity to an {@link AccountResponse} DTO.
     *
     * @param account the {@link Account} entity to convert.
     * @return the mapped {@link AccountResponse} DTO.
     */
    AccountResponse toAccountResponse(Account account);
}
