package com.hzy.finance.controller;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzy.finance.OCR.OCR;
import com.hzy.finance.common.Result;
import com.hzy.finance.entity.DetailsOfTax;
import com.hzy.finance.entity.Invoice;
import com.hzy.finance.service.DetailsOfTaxService;
import com.hzy.finance.service.InvoiceService;
import com.hzy.finance.utils.UpPhotoNameUtils;

/**
 * <p>
 * 发票 前端控制器
 * </p>
 *
 * @author hzy
 * @since 2023-02-03
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

  @Autowired
  InvoiceService invoiceService;
  @Autowired
  DetailsOfTaxService detailsOfTaxService;

  @GetMapping("/")
  public Result getInvoice(@RequestParam("pageNo") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {
    if (currentPage == null || currentPage < 1) {
      currentPage = 1;
    }
    if (pageSize == null || pageSize < 1) {
      pageSize = 10;
    }
    Page page = new Page(currentPage, pageSize);
    IPage pageData = invoiceService.page(page, new QueryWrapper<Invoice>());
    List<Invoice> records = pageData.getRecords();
    for (Invoice in : records) {
      HashMap<String, Object> map = new HashMap<>();
      map.put("invoice_id", in.getInvoiceId());
      List<DetailsOfTax> detailsOfTaxes = (List<DetailsOfTax>) detailsOfTaxService.listByMap(map);
      in.setDetailsOfTaxes(detailsOfTaxes);
    }
    pageData.setRecords(records);
    return Result.success(pageData);
  }

  @PostMapping("/")
  public Result updateInvoice(Invoice invoice) {
    boolean b = invoiceService.saveOrUpdate(invoice);
    for (DetailsOfTax details : invoice.getDetailsOfTaxes()) {
      boolean b1 = detailsOfTaxService.saveOrUpdate(details);
    }
    return Result.success(b);
  }

  @DeleteMapping("/")
  public Result deleteInvoice(Invoice invoice) {
    boolean b = invoiceService.removeById(invoice.getInvoiceId());
    return Result.success(b);
  }


  @GetMapping("/{id}")
  public Result getInvoiceById(@PathVariable("id") Integer id) {
    if (id == null) {
      return Result.fail("id不能为空");
    }
    Invoice invoice = invoiceService.getById(id);
    if (invoice == null) {
      return Result.success(invoice);
    }
    HashMap<String, Object> map = new HashMap<>();
    map.put("invoice_id", id);
    List<DetailsOfTax> detailsOfTaxes = (List<DetailsOfTax>) detailsOfTaxService.listByMap(map);
    invoice.setDetailsOfTaxes(detailsOfTaxes);
    return Result.success(invoice);
  }

  // 文件直接识别
  @PostMapping("/recognize1")
  public Result recognize(@RequestParam("file") MultipartFile file) throws JSONException, IOException {
    try{
      String ocrRecognize = OCR.OCRRecognize(file);
      // 解析ocr识别结果
      String parseOCRResult = OCR.parseOCRResult(ocrRecognize);
      Invoice resultOb = com.alibaba.fastjson.JSONObject.parseObject(parseOCRResult, Invoice.class);
      System.out.println("识别成功："+resultOb.toString());
      // 返回解析的结果
      return Result.success(resultOb);
    } catch (Exception e) {
      e.printStackTrace();
      return Result.fail("识别失败");
    }
  }

  // 先上传文件再识别
  @PostMapping("/recognize")
  public Result recognize1(@RequestParam("file") MultipartFile file) throws JSONException, IOException {

    HashMap<String, Object> resMap = new HashMap<>();
    List<String> risks = new ArrayList<>();

    // 文件上传
    Result result = singleUpload(file);
    // 上传成功
    if (result.getCode() == 200) {
      // ocr识别
      String ocrRecognize = OCR.OCRRecognize((String) result.getData().toString());
      // 解析ocr识别结果
      String parseOCRResult = OCR.parseOCRResult(ocrRecognize);
      Invoice mObject = com.alibaba.fastjson.JSONObject.parseObject(parseOCRResult, Invoice.class);
      System.out.println("解析成功："+mObject.toString());
      resMap.put("recognize",mObject);

      Invoice invoice = invoiceService.getOne(new QueryWrapper<Invoice>().eq("invoice_num", mObject.getInvoiceNum()));
      if (invoice == null) {
        risks.add("发票未录入");
        resMap.put("risk",risks);
        return Result.success(resMap);
      }
      HashMap<String, Object> map = new HashMap<>();
      map.put("invoice_id", invoice.getInvoiceId());
      List<DetailsOfTax> detailsOfTaxes = (List<DetailsOfTax>) detailsOfTaxService.listByMap(map);
      invoice.setDetailsOfTaxes(detailsOfTaxes);

      if (!invoice.getPurchaserName().equals(mObject.getPurchaserName()) || !invoice.getPurchaserRegisterNum().equals(mObject.getPurchaserRegisterNum())) {
        risks.add("发票抬头或税号错误");
        // return Result.success("发票抬头或税号错误");
      } else if (!invoice.getSellerName().equals(mObject.getSellerName()) || !invoice.getSellerRegisterNum().equals(mObject.getSellerRegisterNum())) {
        risks.add("出票单位不一致");
        // return Result.success("出票单位不一致");
      } else if (!invoice.check()) {
        risks.add("发票金额有误");
        // return Result.success("发票金额有误");
      }
      // 返回解析的结果
      resMap.put("risk",risks);
      return Result.success(resMap);
    }
    // 识别失败
    return Result.fail("识别失败");
  }


  @PostMapping("/ocr")
  public String ocr(String url) throws JSONException, IOException {
    return OCR.OCRRecognize(url);
  }

  @PostMapping("/uploadImg")
  public Result singleUpload(@RequestParam("file") MultipartFile file) {
    // @RequestParam("file") MultipartFile file为接收图片参数
    // Long userId,String status 用户Id和状态
    try {
      byte[] bytes = file.getBytes();
      String imageFileName = file.getOriginalFilename();
      // System.out.println(file.getSize());
      // System.out.println(imageFileName);
      String fileName = UpPhotoNameUtils.getPhotoName("img", imageFileName);
      Path path = Paths.get("G:\\Desktop\\电网\\hegui\\tests\\" + fileName);
      // “E:\WebstormProject\blog-vue\src\assets\images\img\”为前端项目本地目录
      // 写入文件
      Files.write(path, bytes);
      String avatarUrl = fileName;
      System.out.println(fileName + "\n");
      // 返回文件名字
      return Result.success(path);
    } catch (IOException e) {
      e.printStackTrace();
      return Result.fail("文件上传失败");
    }
  }


  @PostMapping("/risk")
  public Result risk(@RequestBody Map<String, Object> params) throws JSONException, IOException {

    String url = (String) params.get("url");
    String ocrRecognize = OCR.OCRRecognize(url);

    String parseOCRResult = OCR.parseOCRResult(ocrRecognize);

    // json转VatInvoice对象
    Invoice mObject = com.alibaba.fastjson.JSONObject.parseObject(parseOCRResult, Invoice.class);
    System.out.println(mObject.toString());

    String res = "";
    Invoice invoice = invoiceService.getOne(new QueryWrapper<Invoice>().eq("invoice_num", mObject.getInvoiceNum()));
    if (invoice == null) {
      return Result.success("发票未录入");
    }
    HashMap<String, Object> map = new HashMap<>();
    map.put("invoice_id", invoice.getInvoiceId());
    List<DetailsOfTax> detailsOfTaxes = (List<DetailsOfTax>) detailsOfTaxService.listByMap(map);
    invoice.setDetailsOfTaxes(detailsOfTaxes);

    if (!invoice.getPurchaserName().equals(mObject.getPurchaserName()) || !invoice.getPurchaserRegisterNum().equals(mObject.getPurchaserRegisterNum())) {
      return Result.success("发票抬头或税号错误");
    } else if (!invoice.getSellerName().equals(mObject.getSellerName()) || !invoice.getPurchaserRegisterNum().equals(mObject.getPurchaserRegisterNum())) {
      return Result.success("出票单位不一致");
    } else if (!invoice.check()) {
      return Result.success("发票金额有误");
    }
    return Result.success("");
  }

}
