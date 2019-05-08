package com.my.demo.wallet.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "crypto_coin")
@Getter
@Setter
@ToString
@Builder
public class CryptoCoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String symbol;

    private String contract;

    private BigDecimal ticker;

    private Integer decimals;

    private BigInteger gasPrice;

    private BigInteger gasLimit;

    private Integer confirm;

    private Byte type;

    private Byte status;

    private Date createTime;

    private Date modifyTime;

    private Boolean isDelete;

    @AllArgsConstructor
    @Getter
    public enum CoinType {
        MAIN((byte) 0, "主链币"),
        ERC20((byte) 1, "代币"),
        ;

        private Byte type;

        private String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum CoinStatus {
        HIDE((byte) 0, "隐藏"),
        SHOW((byte) 1, "显示"),
        ;

        private Byte status;

        private String desc;
    }
}
