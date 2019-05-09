package com.my.demo.wallet.entity;

import lombok.*;

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

    @AllArgsConstructor
    @Getter
    public enum CoinType {
        SYS((byte) 0, "系统地址"),
        USER((byte) 1, "用户地址"),
        ;

        private Byte type;

        private String desc;
    }
}
