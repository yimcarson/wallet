package com.my.demo.wallet.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "user_deposit_record")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDepositRecord {
    @Id
    private Integer id;

    private Integer userId;

    private Integer coinId;

    private String from;

    private String to;

    private BigDecimal value;

    private BigDecimal gas;

    private String hash;

    private String blockHash;

    private BigDecimal gasPrice;

    private Date payDate;

    private Date createDate;

    private String nonce;
}