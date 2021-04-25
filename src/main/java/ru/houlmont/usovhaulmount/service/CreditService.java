package ru.houlmont.usovhaulmount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.houlmont.usovhaulmount.entity.Credit;
import ru.houlmont.usovhaulmount.entity.CreditOffer;
import ru.houlmont.usovhaulmount.repository.CreditRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final CreditOffer creditOffer;

    public void addCredit(Credit credit) {
        this.creditRepository.save(credit);
    }

    public void editCredit(Credit credit) {
        this.creditRepository.save(credit);
    }

    public void deleteCredit(String id) {
        this.creditRepository.deleteById(id);
    }


    /**
     * Формула расчета аннуитетного платежа
     * (А) представляет собой следующее соотношение: А=К*(П/(1+П)-М-1),
     * где К – сумма кредита, П – процентная ставка, М – количество месяцев
     * @param credit
     */

    public double creditPaymentInMonth(Credit credit) {
        double k = credit.getCreditLimit();
        double p = credit.getCreditRate();
        int m = credit.getCreditValidity();
        double creditPayment = k * (p / (1 + p) - m - 1);
        return creditPayment;
    }

    /**
     * Кредитное предложение
     * o Клиент
     * o Кредит
     * o Сумма кредита
     * o График платежей
     * ▪ Дата платежа
     * ▪ Сумма платежа
     * ▪ Сумма гашения тела кредит
     * ▪ Сумма гашения процентов
     *
     * @param credit
     * @return
     */

    public BigDecimal creditPaymentSchedule(Credit credit) {

        return creditOffer.getPaymentSchedule().getPaymentAmount();

    }


    /**
     * список всех кредитов
     * @return
     */
    public List<Credit> creditList() {

        List<Credit> credits = new ArrayList<>();
        creditRepository.findAll().forEach(credits::add);
        return credits;
    }
}

