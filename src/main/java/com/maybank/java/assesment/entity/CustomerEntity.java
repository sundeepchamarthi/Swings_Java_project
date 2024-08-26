package com.maybank.java.assesment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

        private int id;

        private String shortName;

        private String fullName;

        private String address1;

        private String address2;

        private String address3;

        private String city;

        private String postalCode;
}
