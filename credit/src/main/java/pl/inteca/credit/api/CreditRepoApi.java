package pl.inteca.credit.api;

import pl.inteca.credit.domain.Credit;

import java.util.List;

public interface CreditRepoApi {

    Credit createCredit(Credit credit);
    List<Credit> getCredits();
}
