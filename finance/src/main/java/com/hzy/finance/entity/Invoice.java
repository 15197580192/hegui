package com.hzy.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 发票
 * </p>
 *
 * @author hzy
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dk_invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "invoice_id", type = IdType.AUTO)
    private Long invoiceId;

    private Long projectId;

    private Blob invoiceImage;

    private String invoiceUrl;


    private String invoiceType;

    private String invoiceCode;

    private String invoiceNum;

    private String invoiceCodeConfirm;

    private String invoiceNumConfirm;

    private String invoiceDate;

    private String checkCode;


    private String purchaserName;

    private String purchaserRegisterNum;

    private String purchaserAddress;

    private String purchaserBank;


    private String sellerName;

    private String sellerRegisterNum;

    private String sellerAddress;

    private String sellerBank;


    private Double totalAmount;

    private Double totalTax;

    private String amountInWords;

    private Double amountInFiguers;

    private String noteDrawer;

    private String machineCode;

    private String serviceType;

    private String invoiceTypeOrg;

    private String invoicePassword;

    private String onlinePay;

    private String payee;

    private String checker;

    private String city;

    private String province;

    private String agent;

    @TableField(exist = false)
    //商品明细
    private List<DetailsOfTax> detailsOfTaxes;

    public boolean check() {
        double num=0.0;
        for (DetailsOfTax details : detailsOfTaxes) {
            num+=details.getCommodityAmount();
        }
        if(num!=getTotalAmount()) {
            return false;
        }
        return true;
    }
}
