package com.xxty.utils.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: llun
 * @DateTime: 2020/8/5 10:00
 * @Description: TODO
 */
public class M2PDF {
    private static final String FONT = "C:\\Windows\\Fonts\\simfang.ttf";
    public static void text2pdf(String text, String pdf) throws DocumentException, IOException {
        Document document = new Document();
        OutputStream os = new FileOutputStream(new File(pdf));
        PdfWriter.getInstance(document, os);
        document.open();
        //方法一：使用Windows系统字体(TrueType)
        BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        //也可以直接传一个流过来，直接读取流，就不用在此处读取文件了
        InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(text)), "GBK");
        BufferedReader bufferedReader = new BufferedReader(isr);
        String str = "";
        while ((str = bufferedReader.readLine()) != null) {
            document.add(new Paragraph(str, font));
        }
        document.close();
    }

    /**
     * 合并多个pdf成一个
     */
    public static void more2one(){
        //先删除之前的all.pdf
        String filePath="all.pdf";
        File file=new File(filePath);
        file.delete();
        //要合并的所有pdf的路径
        List<String> fileList=new ArrayList<>();

        //all.pdf保存路径
        String savepath="all.pdf";
        Document document = null;
        try {
            document = new Document(new PdfReader(fileList.get(0)).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));
            document.open();
            for (int i = 0; i < fileList.size(); i++) {
                PdfReader reader = new PdfReader(fileList.get(i));
                int n = reader.getNumberOfPages();// 获得总页码
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);// 从当前Pdf,获取第j页
                    copy.addPage(page);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    /**
     * 插入数据和图片到pdf模板
     * @param
     * @throws Exception
     */
    public static void image2pdf() throws IOException, DocumentException {
        //要插入的数据,,可作为参数传进来
        Map<String, Object> data = new HashMap<>();
        //初始化itext
        //设置编码
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        //pdf模板文件路径
        PdfReader pdfReader=new PdfReader("F:\\pdf\\3.pdf");
        //输出pdf文件路径
        PdfStamper pdfStamper=new PdfStamper(pdfReader, new FileOutputStream("F:\\pdf"));
        AcroFields form = pdfStamper.getAcroFields();
        form.addSubstitutionFont(baseFont);

        //写入数据
        for(String key:data.keySet()){
            String value=data.get(key).toString();
            //key对应模板数据域的名称
            form.setField(key,value);
        }

        //添加图片
        int pageNo = form.getFieldPositions("img").get(0).page;
        Rectangle signRect = form.getFieldPositions("img").get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();
        Image image = Image.getInstance("图片路径");
        PdfContentByte under = pdfStamper.getOverContent(pageNo);
        //设置图片大小
        image.scaleAbsolute(signRect.getWidth(), signRect.getHeight());
        //设置图片位置
        image.setAbsolutePosition(x, y);
        under.addImage(image);

        //设置不可编辑
        pdfStamper.setFormFlattening(true);
        pdfStamper.close();
    }


    public static void main(String[] args) throws Exception {
        String PDFTIMEDIR = "F:/pdf/";
        String text = PDFTIMEDIR + "1.txt";
        String pdf = PDFTIMEDIR + "1.txt.pdf";
        text2pdf(text, pdf);
        System.out.println("ok");
    }
}
