package com.my.demo.wallet.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_deposit_address")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDepositAddress {
    @Id
    private Integer id;

    private Integer userId;

    private Integer coinSeries;

    private String password;

    private String mnemonic;

    private String privateKey;

    private String address;

    private Date createDate;
}