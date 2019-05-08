package com.my.demo.wallet.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "crypto_transaction")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CryptoTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "coin_id", referencedColumnName = "id")
    private CryptoCoin coin;

    @Column(length = 64, nullable = false)
    private String fromAddress;

    @Column(length = 64, nullable = false)
    private String toAddress;

    @Column(columnDefinition = "decimal(36,18)")
    private BigDecimal amount;

    @Column(length = 20)
    private BigInteger gasPrice;

    @Column(length = 20)
    private BigInteger gasLimit;

    @Column(length = 11)
    private Integer blockNumber;

    private Integer confirm;

    private String transactionHash;

    private String errorMessage;

    private String remarks;

    private Byte type;

    private Byte status;

    private Date createTime;

    private Date modifyTime;

    private Boolean isDelete;

    @AllArgsConstructor
    @Getter
    public enum TransactionType {
        WITHDRAWAL((byte) 0, "提币"),
        RECHARGE((byte) 1, "充值"),
        ;

        private Byte type;

        private String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum TransactionStatus {
        NORMAL((byte) 0, "未审核"),
        APPROVE((byte) 1, "审核通过"),
        PENDING((byte) 2, "pending"),
        COMFIRM((byte) 3, "等待确认数"),
        SUCCESS((byte) 4, "完成"),
        FAILED((byte) 5, "失败"),
        REFUNDED((byte) 6, "已退款"),
        ;

        private Byte status;

        private String desc;
    }
}
