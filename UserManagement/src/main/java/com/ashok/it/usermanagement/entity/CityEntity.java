package com.ashok.it.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cities_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityEntity {
    
    @Id
    @Column(name = "city_id")
    private Integer cityId;
    
    @Column(name = "city_name")
    private String cityName;
    
    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateEntity state;
}
