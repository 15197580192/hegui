package com.hzy.finance.OCR;

import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONReader;

/**
 * @author Hu Zheyuan
 * @description: iocr
 * @date: Created in 14:20 2023/1/25
 * @version: 1.0
 */
public class OCR {
  // 使用百度智能云iocr（财会版） https://ai.baidu.com/tech/ocr/iocr_finance
  public static final String API_KEY = "Bf0y1wCwX5VczYz0ClzDmLaE";
  public static final String SECRET_KEY = "orxPBGmsxFinZYRjiPH0nAWtGzX6ktTm";
  static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

  /**
   * @param
   * @Description: OCRRecognize 调用iocr
   * @return: java.lang.String
   * @Author: Hu Zheyuan 1980452465@qq.com
   * @Date: 2023/1/25-17:10
   */
  public static String OCRRecognize(String filePath) throws IOException, JSONException {
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    // image 可以通过 getFileContentAsBase64("C:\fakepath\下载发票.png") 方法获取
    // System.out.println(getFileContentAsBase64("G:\\Desktop\\下载发票.png"));
    // String img = URLEncoder.encode(getFileContentAsBase64("G:\\Desktop\\下载发票.png"));
    String img = URLEncoder.encode(getFileContentAsBase64(filePath));
    RequestBody body = RequestBody.create(mediaType, "image=" + img + "&templateSign=vat_invoice_v2");

    Request request = new Request.Builder()
            .url("https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise/finance?access_token=" + getAccessToken())
            .method("POST", body)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Accept", "application/json")
            .build();
    Response response = HTTP_CLIENT.newCall(request).execute();

    // response.body().string() 注意只能读取一次
    String result = response.body().string();
    System.out.println(result);
    // {"data":{"ret":[{"word_name":"AmountInWords","word":"肆佰圆零伍角"},{"word_name":"InvoiceNumConfirm","word":"46915250"},{"word_name":"SellerAddress","word":":济南市历城区山大北路56号0531-66898908"},{"word_name":"NoteDrawer","word":"王玉森"},{"word_name":"InvoiceTag","word":"其他"},{"word_name":"SellerRegisterNum","word":"91370100705882496U"},{"word_name":"SellerBank","word":":齐鲁银行济南山大北路支行000000716003100000963"},{"word_name":"Remarks","word":""},{"word_name":"MachineCode","word":"661616318584"},{"word_name":"TotalTax","word":"46.08"},{"word_name":"ServiceType","word":"其他"},{"word_name":"InvoiceCodeConfirm","word":"037002000311"},{"word_name":"CheckCode","word":"85696928040078102310"},{"word_name":"InvoiceCode","word":"037002000311"},{"word_name":"InvoiceDate","word":"2021年03月30日"},{"word_name":"PurchaserRegisterNum","word":""},{"word_name":"InvoiceTypeOrg","word":"山东增值税电子普通发票"},{"word_name":"Password","word":"<+68<9*+106<<7**<>1304/7<9**5-98*6*577>1/-3196+62<1>56-8855<*/9*<><163<7*-->338*97*/4+7498*6*577>1/-3196*>8+"},{"word_name":"OnlinePay","word":""},{"word_name":"PurchaserBank","word":""},{"word_name":"Agent","word":"否"},{"word_name":"AmountInFiguers","word":"400.50"},{"word_name":"Checker","word":"胡飞飞"},{"word_name":"TotalAmount","word":"354.42"},{"word_name":"City","word":""},{"word_name":"PurchaserName","word":"沈卫军"},{"word_name":"Province","word":"山东省"},{"word_name":"SheetNum","word":""},{"word_name":"InvoiceType","word":"电子普通发票"},{"word_name":"PurchaserAddress","word":""},{"word_name":"Payee","word":"李明"},{"word_name":"SellerName","word":"漱玉平民大药房连锁股份有限公司"},{"word_name":"InvoiceNum","word":"46915250"},{"word_name":"DetailsOfTax#1#CommodityName","word":"*化学药品制剂*格列美脲片"},{"word_name":"DetailsOfTax#2#CommodityName","word":"*化学药品制剂*达格列净片/安达唐"},{"word_name":"DetailsOfTax#3#CommodityName","word":"*化学药品制剂*阿法骨化醇软胶囊/盖诺真0.25ug*"},{"word_name":"DetailsOfTax#1#CommodityType","word":"2mg*60片"},{"word_name":"DetailsOfTax#2#CommodityType","word":"10mg*10片*3板"},{"word_name":"DetailsOfTax#3#CommodityType","word":"0.25UG*40粒"},{"word_name":"DetailsOfTax#1#CommodityUnit","word":"盒"},{"word_name":"DetailsOfTax#2#CommodityUnit","word":"盒"},{"word_name":"DetailsOfTax#3#CommodityUnit","word":"盒"},{"word_name":"DetailsOfTax#1#CommodityNum","word":"1"},{"word_name":"DetailsOfTax#2#CommodityNum","word":"1"},{"word_name":"DetailsOfTax#1#CommodityPrice","word":"185.84"},{"word_name":"DetailsOfTax#2#CommodityPrice","word":"115.75"},{"word_name":"DetailsOfTax#3#CommodityPrice","word":"52.83"},{"word_name":"DetailsOfTax#1#CommodityAmount","word":"185.84"},{"word_name":"DetailsOfTax#2#CommodityAmount","word":"115.75"},{"word_name":"DetailsOfTax#3#CommodityAmount","word":"52.83"},{"word_name":"DetailsOfTax#1#CommodityTaxRate","word":"13%"},{"word_name":"DetailsOfTax#2#CommodityTaxRate","word":"13%"},{"word_name":"DetailsOfTax#3#CommodityTaxRate","word":"13%"},{"word_name":"DetailsOfTax#1#CommodityTax","word":"24.16"},{"word_name":"DetailsOfTax#2#CommodityTax","word":"15.05"},{"word_name":"DetailsOfTax#3#CommodityTax","word":"6.87"}],"templateSign":"vat_invoice_v2","templateName":"增值税发票(iOCR格式)","scores":1.0,"isStructured":true,"logId":"167472204795316","version":1},"error_code":0,"error_msg":"","log_id":"167472204795316"}
    return result;

  }

  /**
   * @Description: OCRRecognize
   * @param	file
   * @return: java.lang.String
   * @Author: Hu Zheyuan 1980452465@qq.com
   * @Date: 2023/2/7-16:18
   */
  public static String OCRRecognize(MultipartFile file) throws IOException, JSONException {
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    // image 可以通过 getFileContentAsBase64("C:\fakepath\下载发票.png") 方法获取
    // System.out.println(getFileContentAsBase64("G:\\Desktop\\下载发票.png"));
    // String img = URLEncoder.encode(getFileContentAsBase64("G:\\Desktop\\下载发票.png"));
    String img = URLEncoder.encode(getFileContentAsBase64(file));
    RequestBody body = RequestBody.create(mediaType, "image=" + img + "&templateSign=vat_invoice_v2");

    Request request = new Request.Builder()
            .url("https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise/finance?access_token=" + getAccessToken())
            .method("POST", body)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Accept", "application/json")
            .build();
    Response response = HTTP_CLIENT.newCall(request).execute();

    // response.body().string() 注意只能读取一次
    String result = response.body().string();
    System.out.println(result);
    // {"data":{"ret":[{"word_name":"AmountInWords","word":"肆佰圆零伍角"},{"word_name":"InvoiceNumConfirm","word":"46915250"},{"word_name":"SellerAddress","word":":济南市历城区山大北路56号0531-66898908"},{"word_name":"NoteDrawer","word":"王玉森"},{"word_name":"InvoiceTag","word":"其他"},{"word_name":"SellerRegisterNum","word":"91370100705882496U"},{"word_name":"SellerBank","word":":齐鲁银行济南山大北路支行000000716003100000963"},{"word_name":"Remarks","word":""},{"word_name":"MachineCode","word":"661616318584"},{"word_name":"TotalTax","word":"46.08"},{"word_name":"ServiceType","word":"其他"},{"word_name":"InvoiceCodeConfirm","word":"037002000311"},{"word_name":"CheckCode","word":"85696928040078102310"},{"word_name":"InvoiceCode","word":"037002000311"},{"word_name":"InvoiceDate","word":"2021年03月30日"},{"word_name":"PurchaserRegisterNum","word":""},{"word_name":"InvoiceTypeOrg","word":"山东增值税电子普通发票"},{"word_name":"Password","word":"<+68<9*+106<<7**<>1304/7<9**5-98*6*577>1/-3196+62<1>56-8855<*/9*<><163<7*-->338*97*/4+7498*6*577>1/-3196*>8+"},{"word_name":"OnlinePay","word":""},{"word_name":"PurchaserBank","word":""},{"word_name":"Agent","word":"否"},{"word_name":"AmountInFiguers","word":"400.50"},{"word_name":"Checker","word":"胡飞飞"},{"word_name":"TotalAmount","word":"354.42"},{"word_name":"City","word":""},{"word_name":"PurchaserName","word":"沈卫军"},{"word_name":"Province","word":"山东省"},{"word_name":"SheetNum","word":""},{"word_name":"InvoiceType","word":"电子普通发票"},{"word_name":"PurchaserAddress","word":""},{"word_name":"Payee","word":"李明"},{"word_name":"SellerName","word":"漱玉平民大药房连锁股份有限公司"},{"word_name":"InvoiceNum","word":"46915250"},{"word_name":"DetailsOfTax#1#CommodityName","word":"*化学药品制剂*格列美脲片"},{"word_name":"DetailsOfTax#2#CommodityName","word":"*化学药品制剂*达格列净片/安达唐"},{"word_name":"DetailsOfTax#3#CommodityName","word":"*化学药品制剂*阿法骨化醇软胶囊/盖诺真0.25ug*"},{"word_name":"DetailsOfTax#1#CommodityType","word":"2mg*60片"},{"word_name":"DetailsOfTax#2#CommodityType","word":"10mg*10片*3板"},{"word_name":"DetailsOfTax#3#CommodityType","word":"0.25UG*40粒"},{"word_name":"DetailsOfTax#1#CommodityUnit","word":"盒"},{"word_name":"DetailsOfTax#2#CommodityUnit","word":"盒"},{"word_name":"DetailsOfTax#3#CommodityUnit","word":"盒"},{"word_name":"DetailsOfTax#1#CommodityNum","word":"1"},{"word_name":"DetailsOfTax#2#CommodityNum","word":"1"},{"word_name":"DetailsOfTax#1#CommodityPrice","word":"185.84"},{"word_name":"DetailsOfTax#2#CommodityPrice","word":"115.75"},{"word_name":"DetailsOfTax#3#CommodityPrice","word":"52.83"},{"word_name":"DetailsOfTax#1#CommodityAmount","word":"185.84"},{"word_name":"DetailsOfTax#2#CommodityAmount","word":"115.75"},{"word_name":"DetailsOfTax#3#CommodityAmount","word":"52.83"},{"word_name":"DetailsOfTax#1#CommodityTaxRate","word":"13%"},{"word_name":"DetailsOfTax#2#CommodityTaxRate","word":"13%"},{"word_name":"DetailsOfTax#3#CommodityTaxRate","word":"13%"},{"word_name":"DetailsOfTax#1#CommodityTax","word":"24.16"},{"word_name":"DetailsOfTax#2#CommodityTax","word":"15.05"},{"word_name":"DetailsOfTax#3#CommodityTax","word":"6.87"}],"templateSign":"vat_invoice_v2","templateName":"增值税发票(iOCR格式)","scores":1.0,"isStructured":true,"logId":"167472204795316","version":1},"error_code":0,"error_msg":"","log_id":"167472204795316"}
    return result;

  }


  /**
   * @Description: getInnerContext 获取str中被character包围的内容
   * @param	character
   * @param	str
   * @return: java.lang.String
   * @Author: Hu Zheyuan 1980452465@qq.com
   * @Date: 2023/1/28-17:39
   */
  public static String getInnerContext(String character,String str) {
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

  /**
   * @Description: parseOCRResult 重写json便于转对象
   * @param	json
   * @return: java.lang.String
   * @Author: Hu Zheyuan 1980452465@qq.com
   * @Date: 2023/1/28-17:03
   */
  public static String parseOCRResult(String json) {
    // 测试用初始化
    // json = "{\"data\":{\"ret\":[{\"word_name\":\"AmountInWords\",\"word\":\"肆佰圆零伍角\"},{\"word_name\":\"InvoiceNumConfirm\",\"word\":\"46915250\"},{\"word_name\":\"SellerAddress\",\"word\":\":济南市历城区山大北路56号0531-66898908\"},{\"word_name\":\"NoteDrawer\",\"word\":\"王玉森\"},{\"word_name\":\"InvoiceTag\",\"word\":\"其他\"},{\"word_name\":\"SellerRegisterNum\",\"word\":\"91370100705882496U\"},{\"word_name\":\"SellerBank\",\"word\":\":齐鲁银行济南山大北路支行000000716003100000963\"},{\"word_name\":\"Remarks\",\"word\":\"\"},{\"word_name\":\"MachineCode\",\"word\":\"661616318584\"},{\"word_name\":\"TotalTax\",\"word\":\"46.08\"},{\"word_name\":\"ServiceType\",\"word\":\"其他\"},{\"word_name\":\"InvoiceCodeConfirm\",\"word\":\"037002000311\"},{\"word_name\":\"CheckCode\",\"word\":\"85696928040078102310\"},{\"word_name\":\"InvoiceCode\",\"word\":\"037002000311\"},{\"word_name\":\"InvoiceDate\",\"word\":\"2021年03月30日\"},{\"word_name\":\"PurchaserRegisterNum\",\"word\":\"\"},{\"word_name\":\"InvoiceTypeOrg\",\"word\":\"山东增值税电子普通发票\"},{\"word_name\":\"Password\",\"word\":\"<+68<9*+106<<7**<>1304/7<9**5-98*6*577>1/-3196+62<1>56-8855<*/9*<><163<7*-->338*97*/4+7498*6*577>1/-3196*>8+\"},{\"word_name\":\"OnlinePay\",\"word\":\"\"},{\"word_name\":\"PurchaserBank\",\"word\":\"\"},{\"word_name\":\"Agent\",\"word\":\"否\"},{\"word_name\":\"AmountInFiguers\",\"word\":\"400.50\"},{\"word_name\":\"Checker\",\"word\":\"胡飞飞\"},{\"word_name\":\"TotalAmount\",\"word\":\"354.42\"},{\"word_name\":\"City\",\"word\":\"\"},{\"word_name\":\"PurchaserName\",\"word\":\"沈卫军\"},{\"word_name\":\"Province\",\"word\":\"山东省\"},{\"word_name\":\"SheetNum\",\"word\":\"\"},{\"word_name\":\"InvoiceType\",\"word\":\"电子普通发票\"},{\"word_name\":\"PurchaserAddress\",\"word\":\"\"},{\"word_name\":\"Payee\",\"word\":\"李明\"},{\"word_name\":\"SellerName\",\"word\":\"漱玉平民大药房连锁股份有限公司\"},{\"word_name\":\"InvoiceNum\",\"word\":\"46915250\"},{\"word_name\":\"DetailsOfTax#1#CommodityName\",\"word\":\"*化学药品制剂*格列美脲片\"},{\"word_name\":\"DetailsOfTax#2#CommodityName\",\"word\":\"*化学药品制剂*达格列净片/安达唐\"},{\"word_name\":\"DetailsOfTax#3#CommodityName\",\"word\":\"*化学药品制剂*阿法骨化醇软胶囊/盖诺真0.25ug*\"},{\"word_name\":\"DetailsOfTax#1#CommodityType\",\"word\":\"2mg*60片\"},{\"word_name\":\"DetailsOfTax#2#CommodityType\",\"word\":\"10mg*10片*3板\"},{\"word_name\":\"DetailsOfTax#3#CommodityType\",\"word\":\"0.25UG*40粒\"},{\"word_name\":\"DetailsOfTax#1#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#2#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#3#CommodityUnit\",\"word\":\"盒\"},{\"word_name\":\"DetailsOfTax#1#CommodityNum\",\"word\":\"1\"},{\"word_name\":\"DetailsOfTax#2#CommodityNum\",\"word\":\"1\"},{\"word_name\":\"DetailsOfTax#1#CommodityPrice\",\"word\":\"185.84\"},{\"word_name\":\"DetailsOfTax#2#CommodityPrice\",\"word\":\"115.75\"},{\"word_name\":\"DetailsOfTax#3#CommodityPrice\",\"word\":\"52.83\"},{\"word_name\":\"DetailsOfTax#1#CommodityAmount\",\"word\":\"185.84\"},{\"word_name\":\"DetailsOfTax#2#CommodityAmount\",\"word\":\"115.75\"},{\"word_name\":\"DetailsOfTax#3#CommodityAmount\",\"word\":\"52.83\"},{\"word_name\":\"DetailsOfTax#1#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#2#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#3#CommodityTaxRate\",\"word\":\"13%\"},{\"word_name\":\"DetailsOfTax#1#CommodityTax\",\"word\":\"24.16\"},{\"word_name\":\"DetailsOfTax#2#CommodityTax\",\"word\":\"15.05\"},{\"word_name\":\"DetailsOfTax#3#CommodityTax\",\"word\":\"6.87\"}],\"templateSign\":\"vat_invoice_v2\",\"templateName\":\"增值税发票(iOCR格式)\",\"scores\":1.0,\"isStructured\":true,\"logId\":\"167472204795316\",\"version\":1},\"error_code\":0,\"error_msg\":\"\",\"log_id\":\"167472204795316\"}";

    // 1.获取JSONArray内容：正则获取方括号内内容
    // String regex = "(?<=\\[)(\\S+)(?=\\])";
    // Pattern pattern = Pattern.compile(regex);
    // Matcher matcher = pattern.matcher(json);
    // String kvResult = "";
    // while (matcher.find()) {
    //   kvResult = matcher.group();
    //   // System.out.println(matcher.group());
    // }
    // kvResult = '[' + kvResult + ']';
    // System.out.println(kvResult);

    // 1.获取JSONArray内容,获取data.ret
    com.alibaba.fastjson.JSONObject object = JSON.parseObject(json);
    com.alibaba.fastjson.JSONObject object1 = (com.alibaba.fastjson.JSONObject) object.get("data");
    com.alibaba.fastjson.JSONArray ret = (com.alibaba.fastjson.JSONArray) object1.get("ret");

    // 2.便于json对象解析，重写json:含明细部分
    // 存放重写的json
    String json1="{";
    // 存放发票明细
    HashMap<String, String> details = new HashMap<>();

    // JSONArray objects =JSONArray.parseArray(kvResult);
    JSONArray objects =ret;
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
    // System.out.println(json1);

    // 2.重写json:不包含明细版本
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
    // VatInvoice mObject= com.alibaba.fastjson.JSONObject.parseObject(json1, VatInvoice.class);
    // System.out.println(mObject.toString());
    return json1;

  }

  /**
   * 获取文件base64编码
   *
   * @param path 文件路径
   * @return base64编码信息，不带文件头
   * @throws IOException IO异常
   */
  static String getFileContentAsBase64(String path) throws IOException {
    byte[] b = Files.readAllBytes(Paths.get(path));
    return Base64.getEncoder().encodeToString(b);
  }

  /**
   * 获取文件base64编码
   *
   * @param file 文件
   * @return base64编码信息，不带文件头
   * @throws IOException IO异常
   */
  static String getFileContentAsBase64(MultipartFile file) throws IOException {
    byte[] b = file.getBytes();
    return Base64.getEncoder().encodeToString(b);
  }


  /**
   * 从用户的AK，SK生成鉴权签名（Access Token）
   *
   * @return 鉴权签名（Access Token）
   * @throws IOException IO异常
   */
  static String getAccessToken() throws IOException, JSONException {
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
            + "&client_secret=" + SECRET_KEY);
    Request request = new Request.Builder()
            .url("https://aip.baidubce.com/oauth/2.0/token")
            .method("POST", body)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build();
    Response response = HTTP_CLIENT.newCall(request).execute();
    return new JSONObject(response.body().string()).getString("access_token");
  }

}