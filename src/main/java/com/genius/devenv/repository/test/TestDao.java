package com.genius.devenv.repository.test;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TEST")
public class TestDao  implements Serializable
{
    private static final long serialVersionUID = 5667676272372629322L;
    @Id
    private String id;
    //@Column
    private String name;
    //@Column
    private Long age;
}
