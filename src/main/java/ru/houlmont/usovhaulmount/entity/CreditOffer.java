package ru.houlmont.usovhaulmount.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;

/**
 *
 */


@Entity
@Table(name = "CREDIT_OFFER")
@Data
public class CreditOffer {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;


    @JoinColumn(name = "ID")
    @ManyToOne(fetch =  FetchType.LAZY)
    private Client client;

    @JoinColumn(name = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Credit credit;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Embedded
    @Convert(converter = CreditOffer.JpaJsonConverter.class)
    @Column(name = "PAYMENT_SCHEDULE")
    private PaymentSchedule paymentSchedule;


    public static class JpaJsonConverter implements AttributeConverter<PaymentSchedule, String> {
        private static ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(PaymentSchedule paymentSchedule) {
            try {
                return objectMapper.writeValueAsString(paymentSchedule);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public PaymentSchedule convertToEntityAttribute(String s) {
            try {
                return objectMapper.readValue(s, PaymentSchedule.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
