package pl.inteca.credit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    long id;

    @JsonView(Views.Public.class) // variable with this annotation are added to response
    String firstName;

    @JsonView(Views.Public.class) // variable with this annotation are added to response
    String lastName;

    @JsonView(Views.Public.class) // variable with this annotation are added to response
    long personalId;
}
