package com.hzy.finance;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzy.finance.OCR.OCR;
import com.hzy.finance.entity.DetailsOfTax;
import com.hzy.finance.entity.Invoice;
import com.hzy.finance.entity.Project;
import com.hzy.finance.service.DetailsOfTaxService;
import com.hzy.finance.service.InvoiceService;
import com.hzy.finance.service.ProjectService;

@SpringBootTest
class FinanceApplicationTests {

	@Autowired
	ProjectService projectService;
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	DetailsOfTaxService detailsOfTaxService;

	@Test
	void contextLoads() throws JSONException, IOException {
		Project project = projectService.getById(1);
		System.out.println(project.toString());

		String filePath = "G:\\Desktop\\电网\\hegui\\tests\\专用发票.png";
		String savePath = "G:\\Desktop\\电网\\hegui\\tests";
		String excelName = "out";

		String ocrRecognize = OCR.OCRRecognize(filePath);

		// String ocrRecognize = "{\"data\":{\"ret\":[{\"word_name\":\"AmountInWords\",\"word\":\"肆佰圆零伍角\"},{\"word_name\":\"InvoiceNumConfirm\",\"word\":\"46915250\"},{\"word_name\":\"SellerAddress\",\"word\":\":济南市历城区山大北路56号0531-66898908\"},{\"word_name\":\"NoteDrawer\",\"word\":\"王玉森\"},{\"word_name\":\"InvoiceTag\",\"word\":\"其他\"},{\"word_name\":\"SellerRegisterNum\",\"word\":\"91370100705882496U\"},{\"word_name\":\"SellerBank\",\"word\":\":齐鲁银行济南山大北路支行000000716003100000963\"},{\"word_name\":\"Remarks\",\"word\":\"\"},{\"word_name\":\"MachineCode\",\"word\":\"661616318584\"},{\"word_name\":\"TotalTax\",\"word\":\"46.08\"},{\"word_name\":\"ServiceType\",\"word\":\"其他\"},{\"word_name\":\"InvoiceCodeConfirm\",\"word\":\"037002000311\"},{\"word_name\":\"CheckCode\",\"word\":\"85696928040078102310\"},{\"word_name\":\"InvoiceCode\",\"word\":\"037002000311\"},{\"word_name\":\"InvoiceDate\",\"word\":\"2021年03月30日\"},{\"word_name\":\"PurchaserRegisterNum\",\"word\":\"\"},{\"word_name\":\"InvoiceTypeOrg\",\"word\":\"山东增值税电子普通发票\"},{\"word_name\":\"Password\",\"word\":\"<+68<9*+106<<7**<>1304/7<9**5-98*6*577>1/-3196+62<1>56-8855<*/9*<><163<7*-->338*97*/4+7498*6*577>1/-3196*>8+\"},{\"word_name\":\"OnlinePay\",\"word\":\"\"},{\"word_name\":\"PurchaserBank\",\"word\":\"\"},{\"word_name\":\"Agent\",\"word\":\"否\"},{\"word_name\":\"AmountInFiguers\",\"word\":\"400.50\"},{\"word_name\":\"Checker\",\"word\":\"胡飞飞\"},{\"word_name\":\"TotalAmount\",\"word\":\"354.42\"},{\"word_name\":\"City\",\"word\":\"\"},{\"word_name\":\"PurchaserName\",\"word\":\"沈卫军\"},{\"word_name\":\"Province\",\"word\":\"山东省\"},{\"word_name\":\"SheetNum\",\"word\":\"\"},{\"word_name\":\"InvoiceType\",\"word\":\"电子普通发票\"},{\"word_name\":\"PurchaserAddress\",\"word\":\"\"},{\"word_name\":\"Payee\",\"word\":\"李明\"},{\"word_name\":\"SellerName\",\"word\":\"漱玉平民大药房连锁股份有限公司\"},{\"word_name\":\"InvoiceNum\",\"word\":\"46915250\"},{\"word_name\":\"DetailsOfTax#1#CommodityName\",\"word\":\"*化学药品制剂*格列美脲片\"},{\"word_name\":\"DetailsOfTax#2#CommodityName\",\"word\":\"*化学药品制剂*达格列净片/安达唐\"},{\"word_name\":\"DetailsOfTax#3#CommodityName\",\"word\":\"*化学药品制剂*阿法骨化醇软胶囊/盖诺真0.25ug*\"},{\"word_name\":\"DetailsOfTax#1#CommodityType\",\"word\":\"2mg*60片\"},{\"word_name\":\"DetailsOfTax#2#CommodityType\",\"word\":\"10mg*10片*3板\"},{\"word_name\":\"DetailsOfTax#3#CommodityType\",\"word\":\"0.25UG*40粒\"},{\"word_name\":\"DetailsOfTax#1#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#2#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#3#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#1#CommodityNum\",\"word\":\"1\"},{\"word_name\":\"DetailsOfTax#2#CommodityNum\",\"word\":\"1\"},{\"word_name\":\"DetailsOfTax#1#CommodityPrice\",\"word\":\"185.84\"},{\"word_name\":\"DetailsOfTax#2#CommodityPrice\",\"word\":\"115.75\"},{\"word_name\":\"DetailsOfTax#3#CommodityPrice\",\"word\":\"52.83\"},{\"word_name\":\"DetailsOfTax#1#CommodityAmount\",\"word\":\"185.84\"},{\"word_name\":\"DetailsOfTax#2#CommodityAmount\",\"word\":\"115.75\"},{\"word_name\":\"DetailsOfTax#3#CommodityAmount\",\"word\":\"52.83\"},{\"word_name\":\"DetailsOfTax#1#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#2#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#3#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#1#CommodityTax\",\"word\":\"24.16\"},{\"word_name\":\"DetailsOfTax#2#CommodityTax\",\"word\":\"15.05\"},{\"word_name\":\"DetailsOfTax#3#CommodityTax\",\"word\":\"6.87\"}],\"templateSign\":\"vat_invoice_v2\",\"templateName\":\"增值税发票(iOCR格式)\",\"scores\":1.0,\"isStructured\":true,\"logId\":\"167472204795316\",\"version\":1},\"error_code\":0,\"error_msg\":\"\",\"log_id\":\"167472204795316\"}";


		String parseOCRResult = OCR.parseOCRResult(ocrRecognize);

		// json转VatInvoice对象
		Invoice mObject= com.alibaba.fastjson.JSONObject.parseObject(parseOCRResult, Invoice.class);
		mObject.setProjectId(1L);

		List<DetailsOfTax> detailsOfTaxes = mObject.getDetailsOfTaxes();
		boolean b = invoiceService.saveOrUpdate(mObject);

		for (DetailsOfTax details : detailsOfTaxes) {
			Invoice one = invoiceService.getOne(new QueryWrapper<Invoice>().eq("invoice_num", mObject.getInvoiceNum()));
			details.setInvoiceId(one.getInvoiceId());
			detailsOfTaxService.saveOrUpdate(details);
		}
	}

}
