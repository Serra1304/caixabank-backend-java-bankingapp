package com.hackathon.finservice.Mappers;

import com.hackathon.finservice.DTO.TransactionResponse;
import com.hackathon.finservice.Entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Mapper interface for converting {@link Transaction} entities into {@link TransactionResponse} DTOs.
 * <p>
 * This interface uses MapStruct to generate the implementation at compile time. The {@code componentModel = "spring"}
 * parameter enables integration with the Spring framework.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Converts a {@link Transaction} entity into a {@link TransactionResponse} DTO.
     * <p>
     * The mapping includes the following customizations:
     * <ul>
     *     <li>{@code transactionType} is mapped using {@link #enumToString(Enum)}.</li>
     *     <li>{@code transactionStatus} is mapped using {@link #enumToString(Enum)}.</li>
     *     <li>{@code transactionDate} is converted to a timestamp (milliseconds since epoch) using {@link #localDateTimeToLong(LocalDateTime)}.</li>
     *     <li>{@code sourceAccountNumber} is mapped from {@code sourceAccount.accountNumber}, with a default value of "N/A".</li>
     *     <li>{@code targetAccountNumber} is mapped from {@code targetAccount.accountNumber}, with a default value of "N/A".</li>
     * </ul>
     * </p>
     *
     * @param transaction the {@link Transaction} entity to convert.
     * @return the mapped {@link TransactionResponse} DTO.
     */
    @Mapping(source = "transactionType", target = "transactionType", qualifiedByName = "enumToString")
    @Mapping(source = "transactionStatus", target = "transactionStatus", qualifiedByName = "enumToString")
    @Mapping(source = "transactionDate", target = "transactionDate", qualifiedByName = "localDateTimeToLong")
    @Mapping(source = "sourceAccount.accountNumber", target = "sourceAccountNumber", defaultValue = "N/A")
    @Mapping(source = "targetAccount.accountNumber", target = "targetAccountNumber", defaultValue = "N/A")
    TransactionResponse toTransactionResponse(Transaction transaction);


    /**
     * Converts an {@link Enum} to its {@link String} representation.
     * <p>
     * If the enum value is {@code null}, it returns "N/A".
     * </p>
     *
     * @param enumValue the {@link Enum} value to convert.
     * @return the name of the enum, or "N/A" if {@code enumValue} is {@code null}.
     */
    @Named("enumToString")
    static String enumToString(Enum<?> enumValue) {
        return enumValue != null ? enumValue.name() : "N/A";
    }

    /**
     * Converts a {@link LocalDateTime} to a timestamp in milliseconds since the Unix epoch.
     * <p>
     * If the {@code dateTime} value is {@code null}, it returns {@code null}.
     * </p>
     *
     * @param dateTime the {@link LocalDateTime} to convert.
     * @return the timestamp in milliseconds, or {@code null} if {@code dateTime} is {@code null}.
     */
    @Named("localDateTimeToLong")
    static Long localDateTimeToLong(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() : null;
    }
}
