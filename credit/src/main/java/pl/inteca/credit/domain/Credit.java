package pl.inteca.credit.domain;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.inteca.credit.controllers.Views;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credit {

    long id;

    @JsonView(Views.Public.class)
    String name;


    long customerId;


    long productId;
}
