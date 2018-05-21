import org.apache.hadoop.hbase.util.Bytes;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Description:
 *
 * @author YKL on 2018/5/15.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class test {

    public static void main(String[] args) {

        String str = "63个AC值排列好的，将AC系数转换成中间符号，中间符号表示为RRRR/SSSS，\n" +
                "RRRR是指第非零的AC之前，其值为0的AC个数，SSSS是指AC值所需的位数，AC系\n" +
                "数的范围与SSSS的对应关系与DC差值Bits数与差值内容对照表相似。\n" +
                "如果连续为0的AC个数大于15，则用15/0来表示连续的16个0，15/0称为ZRL\n" +
                "（Zero Rum Length），而（0/0）称为EOB（Enel of Block）用来表示其后所\n" +
                "剩余的AC系数皆等于0，以中间符号值作为索引值，从相应的AC编码表中找出适\n" +
                "当的霍夫曼码值，再与AC值相连即可。";
        byte[] bytes = Bytes.toBytes(str);
        for (byte bt : bytes) {
            System.out.println(bt);
        }


    }

    public void cropImage(String srcPath,String toPath,
                          int x,int y,int width,int height,
                          String readImageFormat,String writeImageFormat) throws IOException {
        FileInputStream fis = null ;
        ImageInputStream iis =null ;
        try{
            //读取图片文件
            fis = new FileInputStream(srcPath);
            Iterator it = ImageIO.getImageReadersByFormatName(readImageFormat);
            ImageReader reader = (ImageReader) it.next();
            //获取图片流
            iis = ImageIO.createImageInputStream(fis);
            reader.setInput(iis,true) ;
            ImageReadParam param = reader.getDefaultReadParam();
            //定义一个矩形
            Rectangle rect = new Rectangle(x, y, width, height);
            //提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0,param);
            //保存新图片
            ImageIO.write(bi, writeImageFormat, new File(toPath));
        }finally{
            if(fis!=null)
                fis.close();
            if(iis!=null)
                iis.close();
        }
    }

}
