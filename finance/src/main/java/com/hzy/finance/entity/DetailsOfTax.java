package com.hzy.finance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 发票商品价税详情
 * </p>
 *
 * @author hzy
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dk_details_of_tax")
public class DetailsOfTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "details_of_tax_id", type = IdType.AUTO)
    private Long detailsOfTaxId;

    private Long invoiceId;

    private String commodityName;

    private String commodityType;

    private String commodityUnit;

    private Integer commodityNum;

    private Double commodityPrice;

    private Double commodityAmount;

    private String commodityTaxRate;

    private Double commodityTax;


}
