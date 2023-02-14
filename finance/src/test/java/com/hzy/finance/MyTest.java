package com.hzy.finance;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzy.finance.entity.Invoice;
import com.hzy.finance.entity.Project;
import com.hzy.finance.entity.VatInvoice;
import com.hzy.finance.mapper.ProjectMapper;
import com.hzy.finance.service.ProjectService;

/**
 * @author Hu Zheyuan 1980452465@qq.com
 * @description: TODO
 * @date: Created in 23:49 2023/1/27
 * @version: 1.0
 */
@Component
public class MyTest {



  public String getInnerContext(String character,String str) {
    // 获取JSONArray内容：正则获取方括号内内容
    String regex = "(?<="+character+")(\\S+)(?="+character+")";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);
    String kvResult = "";
    while (matcher.find()) {
      kvResult = matcher.group();
      str = matcher.group();
      // System.out.println(matcher.group());
    }
    return str;
  }
  @Test
  public void test() {
    String json = "{\"data\":{\"ret\":[{\"word_name\":\"AmountInWords\",\"word\":\"肆佰圆零伍角\"},{\"word_name\":\"InvoiceNumConfirm\",\"word\":\"46915250\"},{\"word_name\":\"SellerAddress\",\"word\":\":济南市历城区山大北路56号0531-66898908\"},{\"word_name\":\"NoteDrawer\",\"word\":\"王玉森\"},{\"word_name\":\"InvoiceTag\",\"word\":\"其他\"},{\"word_name\":\"SellerRegisterNum\",\"word\":\"91370100705882496U\"},{\"word_name\":\"SellerBank\",\"word\":\":齐鲁银行济南山大北路支行000000716003100000963\"},{\"word_name\":\"Remarks\",\"word\":\"\"},{\"word_name\":\"MachineCode\",\"word\":\"661616318584\"},{\"word_name\":\"TotalTax\",\"word\":\"46.08\"},{\"word_name\":\"ServiceType\",\"word\":\"其他\"},{\"word_name\":\"InvoiceCodeConfirm\",\"word\":\"037002000311\"},{\"word_name\":\"CheckCode\",\"word\":\"85696928040078102310\"},{\"word_name\":\"InvoiceCode\",\"word\":\"037002000311\"},{\"word_name\":\"InvoiceDate\",\"word\":\"2021年03月30日\"},{\"word_name\":\"PurchaserRegisterNum\",\"word\":\"\"},{\"word_name\":\"InvoiceTypeOrg\",\"word\":\"山东增值税电子普通发票\"},{\"word_name\":\"Password\",\"word\":\"<+68<9*+106<<7**<>1304/7<9**5-98*6*577>1/-3196+62<1>56-8855<*/9*<><163<7*-->338*97*/4+7498*6*577>1/-3196*>8+\"},{\"word_name\":\"OnlinePay\",\"word\":\"\"},{\"word_name\":\"PurchaserBank\",\"word\":\"\"},{\"word_name\":\"Agent\",\"word\":\"否\"},{\"word_name\":\"AmountInFiguers\",\"word\":\"400.50\"},{\"word_name\":\"Checker\",\"word\":\"胡飞飞\"},{\"word_name\":\"TotalAmount\",\"word\":\"354.42\"},{\"word_name\":\"City\",\"word\":\"\"},{\"word_name\":\"PurchaserName\",\"word\":\"沈卫军\"},{\"word_name\":\"Province\",\"word\":\"山东省\"},{\"word_name\":\"SheetNum\",\"word\":\"\"},{\"word_name\":\"InvoiceType\",\"word\":\"电子普通发票\"},{\"word_name\":\"PurchaserAddress\",\"word\":\"\"},{\"word_name\":\"Payee\",\"word\":\"李明\"},{\"word_name\":\"SellerName\",\"word\":\"漱玉平民大药房连锁股份有限公司\"},{\"word_name\":\"InvoiceNum\",\"word\":\"46915250\"},{\"word_name\":\"DetailsOfTax#1#CommodityName\",\"word\":\"*化学药品制剂*格列美脲片\"},{\"word_name\":\"DetailsOfTax#2#CommodityName\",\"word\":\"*化学药品制剂*达格列净片/安达唐\"},{\"word_name\":\"DetailsOfTax#3#CommodityName\",\"word\":\"*化学药品制剂*阿法骨化醇软胶囊/盖诺真0.25ug*\"},{\"word_name\":\"DetailsOfTax#1#CommodityType\",\"word\":\"2mg*60片\"},{\"word_name\":\"DetailsOfTax#2#CommodityType\",\"word\":\"10mg*10片*3板\"},{\"word_name\":\"DetailsOfTax#3#CommodityType\",\"word\":\"0.25UG*40粒\"},{\"word_name\":\"DetailsOfTax#1#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#2#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#3#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#1#CommodityNum\",\"word\":\"1\"},{\"word_name\":\"DetailsOfTax#2#CommodityNum\",\"word\":\"1\"},{\"word_name\":\"DetailsOfTax#1#CommodityPrice\",\"word\":\"185.84\"},{\"word_name\":\"DetailsOfTax#2#CommodityPrice\",\"word\":\"115.75\"},{\"word_name\":\"DetailsOfTax#3#CommodityPrice\",\"word\":\"52.83\"},{\"word_name\":\"DetailsOfTax#1#CommodityAmount\",\"word\":\"185.84\"},{\"word_name\":\"DetailsOfTax#2#CommodityAmount\",\"word\":\"115.75\"},{\"word_name\":\"DetailsOfTax#3#CommodityAmount\",\"word\":\"52.83\"},{\"word_name\":\"DetailsOfTax#1#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#2#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#3#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#1#CommodityTax\",\"word\":\"24.16\"},{\"word_name\":\"DetailsOfTax#2#CommodityTax\",\"word\":\"15.05\"},{\"word_name\":\"DetailsOfTax#3#CommodityTax\",\"word\":\"6.87\"}],\"templateSign\":\"vat_invoice_v2\",\"templateName\":\"增值税发票(iOCR格式)\",\"scores\":1.0,\"isStructured\":true,\"logId\":\"167472204795316\",\"version\":1},\"error_code\":0,\"error_msg\":\"\",\"log_id\":\"167472204795316\"}";
    // json="{\"errorCode\":0,\"errorMsg\":\"\",\"logId\":null,\"data\":{\"ret\":[{\"word_name\":\"AmountInWords\",\"word\":\"贰佰圆整\"},{\"word_name\":\"NoteDrawer\",\"word\":\"3LT\"},{\"word_name\":\"SellerAddress\",\"word\":\"北京市海淀区天秀路10号中国农大国际创业园3号楼6068010-82601503\"},{\"word_name\":\"SellerRegisterNum\",\"word\":\"91110108662189842D\"},{\"word_name\":\"Remarks\",\"word\":\"\"},{\"word_name\":\"SellerBank\",\"word\":\"中信银行北京奥运村支行302100011198\"},{\"word_name\":\"TotalTax\",\"word\":\"11.32\"},{\"word_name\":\"CheckCode\",\"word\":\"52559165923671288074\"},{\"word_name\":\"InvoiceCode\",\"word\":\"011001800311\"},{\"word_name\":\"InvoiceDate\",\"word\":\"2018年09月07日\"},{\"word_name\":\"PurchaserRegisterNum\",\"word\":\"91110108717743469K\"},{\"word_name\":\"InvoiceTypeOrg\",\"word\":\"北京增值税电子普通发票\"},{\"word_name\":\"Password\",\"word\":\"\"},{\"word_name\":\"AmountInFiguers\",\"word\":\"200.00\"},{\"word_name\":\"PurchaserBank\",\"word\":\"\"},{\"word_name\":\"Checker\",\"word\":\"0月\"},{\"word_name\":\"TotalAmount\",\"word\":\"188.68\"},{\"word_name\":\"PurchaserName\",\"word\":\"百度在线网络技术(北京)有限公司\"},{\"word_name\":\"InvoiceType\",\"word\":\"电子普通发票\"},{\"word_name\":\"PurchaserAddress\",\"word\":\"\"},{\"word_name\":\"Payee\",\"word\":\"1\"},{\"word_name\":\"SellerName\",\"word\":\"北京健力源餐饮管理有限公司\"},{\"word_name\":\"InvoiceNum\",\"word\":\"53780382\"},{\"word_name\":\"DetailsOfTax#1#CommodityName\",\"word\":\"*餐饮服务*餐费\"},{\"word_name\":\"DetailsOfTax#1#CommodityType\",\"word\":\"\"},{\"word_name\":\"DetailsOfTax#1#CommodityUnit\",\"word\":\"\"},{\"word_name\":\"DetailsOfTax#1#CommodityNum\",\"word\":\"\"},{\"word_name\":\"DetailsOfTax#1#CommodityPrice\",\"word\":\"\"},{\"word_name\":\"DetailsOfTax#1#CommodityAmount\",\"word\":\"188.68\"},{\"word_name\":\"DetailsOfTax#1#CommodityTaxRate\",\"word\":\"6%\"},{\"word_name\":\"DetailsOfTax#1#CommodityTax\",\"word\":\"11.32\"}],\"templateSign\":\"vat_invoice_v2\",\"templateName\":\"增值税发票(iOCR格式)\",\"scores\":1,\"isStructured\":true,\"logId\":\"160039564888720\",\"version\":1}}";
    // 1.获取JSONArray内容：正则获取方括号内内容
    String regex = "(?<=\\[)(\\S+)(?=\\])";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(json);
    String kvResult = "";
    while (matcher.find()) {
      kvResult = matcher.group();
      // System.out.println(matcher.group());
    }
    kvResult = '[' + kvResult + ']';
    // System.out.println(kvResult);

    // 2.便于json对象解析，重写json:含明细部分
    // 存放重写的json
    String json1="{";
    // 存放发票明细
    HashMap<String, String> details = new HashMap<>();

    JSONArray objects =JSONArray.parseArray(kvResult);
    for(int j=0;j<objects.size();j++){

      com.alibaba.fastjson.JSONObject jsonObject = objects.getJSONObject(j);

      String key = (String) jsonObject.get("word_name");
      String value = (String) jsonObject.get("word");
      if(StringUtils.contains(key,"DetailsOfTax")) {
        // 明细内容
        // 获取明细编号：#包含内容
        String no = getInnerContext("#",key);
        // System.out.println(str);
        if (details.containsKey(no)) {
          String temp = details.get(no)+"\""+key.substring(key.lastIndexOf("#")+1)+"\":"
                  +"\""+value+"\",";
          details.put(no,temp);
        } else {
          details.put(no,"{");
          String temp = details.get(no)+"\""+key.substring(key.lastIndexOf("#")+1)+"\":"
                  +"\""+value+"\",";
          details.put(no,temp);
        }
      } else {
        // 非明细内容
        json1=json1+"\""+key+"\":"+"\""+value+"\",";
      }
    }
    json1+="\"detailsOfTaxes\":"+"[";
    for(String i:details.values()) {
      i+="}";
      json1+=i;
    }
    json1+="]";

    json1=json1.substring(0,json1.length())+'}';
    System.out.println(json1);
    // 重写json:不包含明细版本
    // String json1 = "{";
    // JSONArray objects = JSONArray.parseArray(kvResult);
    // for (int j = 0; j < objects.size(); j++) {
    //
    //   com.alibaba.fastjson.JSONObject jsonObject = objects.getJSONObject(j);
    //
    //   String key = (String) jsonObject.get("word_name");
    //   String value = (String) jsonObject.get("word");
    //   json1 = json1 + "\"" + key + "\":" + "\"" + value + "\",";
    // }
    // json1 = json1.substring(0, json1.length() - 1) + '}';
    // System.out.println(json1);


    // 3.json转VatInvoice对象
    Invoice mObject= com.alibaba.fastjson.JSONObject.parseObject(json1, Invoice.class);
    System.out.println(mObject.toString());
    // return json1;


  }
  @Autowired
  ProjectService projectService;

  @Test
  public void test1() {
    Project project = projectService.getById(1);
    System.out.println(project.toString());
  }
}
