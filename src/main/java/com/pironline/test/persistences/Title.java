package com.pironline.test.persistences;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dict_titles")
public class Title {

    @Id
    private Integer id;

    @Column(name = "description")
    private String description;
}
