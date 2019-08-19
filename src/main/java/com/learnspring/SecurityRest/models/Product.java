package com.learnspring.SecurityRest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@AllArgsConstructor
@Data
public class Product {

    @Id
    String id;
    String prodName;
    String prodDesc;
    Double prodPrice;
    String prodImage;

}
