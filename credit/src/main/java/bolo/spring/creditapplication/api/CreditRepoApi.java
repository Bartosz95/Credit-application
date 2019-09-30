package bolo.spring.creditapplication.api;

import bolo.spring.creditapplication.domain.Credit;

import java.util.List;

public interface CreditRepoApi extends CustomerRepoApi, ProductRepoApi {
    Credit createCredit(Credit credit);

    List<Credit> getCredits();
}
