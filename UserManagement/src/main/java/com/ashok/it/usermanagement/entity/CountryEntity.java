package com.ashok.it.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "country_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryEntity {
    
    @Id
    @Column(name = "country_id")
    private Integer countryId;
    
    @Column(name = "country_name")
    private String countryName;
}
