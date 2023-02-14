package com.hzy.finance.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Hu Zheyuan
 * @description: 增值税发票
 * @date: Created in 17:20 2023/1/26
 * @version: 1.0
 */
@Data
public class VatInvoice {
  // 发票信息
  private String InvoiceType;
  private String InvoiceCode;
  private String InvoiceCodeConfirm;
  private String InvoiceNum;
  private String InvoiceNumConfirm;
  private String InvoiceDate;
  private String CheckCode;
  // 购买方信息
  private String PurchaserName;
  private String PurchaserRegisterNum;
  private String PurchaserAddress;
  private String PurchaserBank;
  // 销售方信息
  private String SellerName;
  private String SellerRegisterNum;
  private String SellerAddress;
  private String SellerBank;

  // 合计
  private double TotalAmount;
  private double TotalTax;
  private String AmountInWords;
  private double AmountInFiguers;
  //商品明细
  private List<DetailsOfTax> detailsOfTaxes;

  public boolean priceCheck() {
    double tmpPrice=0.0;
    for (DetailsOfTax details : detailsOfTaxes) {
      tmpPrice+=details.getCommodityAmount();
    }
    if(tmpPrice!=getTotalAmount()) {
      return false;
    }
    return true;
  }

}
