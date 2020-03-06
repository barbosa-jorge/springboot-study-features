package com.study.springbootfeatures.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
@Table(name="authorities")
public class AuthorityEntity implements Serializable {

    private static final long serialVersionUID = -5828101164006114538L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false, length=20)
    private String name;

    @ManyToMany(mappedBy="authorities")
    private Collection<RoleEntity> roles;

    public AuthorityEntity(String name) {
        this.name = name;
    }

}
