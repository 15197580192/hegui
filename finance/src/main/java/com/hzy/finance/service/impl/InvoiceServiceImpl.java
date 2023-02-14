package com.hzy.finance.service.impl;

import com.hzy.finance.entity.Invoice;
import com.hzy.finance.mapper.DetailsOfTaxMapper;
import com.hzy.finance.mapper.InvoiceMapper;
import com.hzy.finance.service.DetailsOfTaxService;
import com.hzy.finance.service.InvoiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 发票 服务实现类
 * </p>
 *
 * @author hzy
 * @since 2023-02-03
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {
}
