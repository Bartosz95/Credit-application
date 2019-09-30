package bolo.spring.creditapplication.domain;

import bolo.spring.creditapplication.controllers.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
