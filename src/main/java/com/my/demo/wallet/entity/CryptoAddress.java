package com.my.demo.wallet.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "crypto_address")
@Getter
@Setter
@ToString
@Builder
public class CryptoAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String address;

    private String keystore;

    private String password;

    private Byte type;

    private Byte status;

    private Date createTime;

    private Date modifyTime;

    private Boolean isDelete;
}
