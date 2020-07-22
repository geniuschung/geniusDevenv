package com.genius.devenv.repository.h2.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEST2")
public class H2TestDao implements Serializable  {

    private static final long serialVersionUID = 5025134076075174080L;
    @Id
    private String id;
    //@Column
    private String name;
    //@Column
    private Long age;
}
