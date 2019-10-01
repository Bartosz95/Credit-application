package pl.inteca.credit.api;

import pl.inteca.credit.domain.Credit;

import java.util.List;

public interface CreditRepoApi extends CustomerRepoApi, ProductRepoApi {

    Credit createCredit(Credit credit);
    List<Credit> getCredits();
}
