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
@Table(name = "states_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateEntity {
    
    @Id
    @Column(name = "state_id")
    private Integer stateId;
    
    @Column(name = "state_name")
    private String stateName;
    
    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;
}
