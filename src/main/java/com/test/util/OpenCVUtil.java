package com.test.util;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * openCV中不能出现中文字符，加载照片不能出现，加载dll不能出现。
 *
 * @author Yimning
 */
public class OpenCVUtil {

    public static Mat getFace(Mat img) {
        //	加载模型（模型在Opencv的安装目录）
        String xmlPath = "C:/IDEA/OpenCV/opencv/build/etc/haarcascades/haarcascade_frontalface_default.xml";
        // 级联分类器
        CascadeClassifier faceDetector = new CascadeClassifier();
        // 加载级联分类器
        faceDetector.load(xmlPath);
        //矩形向量组
        MatOfRect faceDetections = new MatOfRect();
        try {
            //检测出人脸，用矩阵保存
            faceDetector.detectMultiScale(img, faceDetections);
        } catch (Exception e) {
            System.out.println("It has some error:" + faceDetections);
        }

        Rect[] rects = faceDetections.toArray();
        //如果有人脸
        if(rects != null && rects.length >= 1){
            //画框
            for (Rect rect : rects) {
                // 设置识别方块的颜色
                Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 255, 0));
            }

            //拍照
/*            SimpleDateFormat data = new SimpleDateFormat("yyyyMMddHHmmss");
            String name = data.format(new Date());
            //获取桌面路径
            //File path = FileSystemView.getFileSystemView().getHomeDirectory();
            String path = "C:\\E盘(文件)\\IdeaProjects\\FaceMaskSample\\images";
            System.out.println(path);
            String format = "jpeg";
            File f = new File(path + File.separator + name + "." + format);
            try {
                System.out.println(name);
                ImageIO.write(ShowImgUtil.mImg, format, f);
              //要检测的图片
                String filePath = path+"\\"+name+".jpeg";
                System.out.println("test:"+filePath);
                //  String filePath = "resources\5.jpeg";
                //图片转base64字符串处理
                String base64Img = Base64Util.encode(FileUtil.readFileByBytes(filePath));
               //参数对象转JSON字符串
                FaceRequest bean = new FaceRequest();
                bean.setImage_type(IMAGEBASE64);
                bean.setImage(base64Img);
                //查询的属性 age,beauty,expression,face_shape,gender,glasses,landmark,landmark150,race,
                // quality,eye_status,emotion,face_type,mask信息
                // 逗号分隔. 默认只返回face_token、人脸框、概率和旋转角度
                bean.setFace_field("age,beauty,mask");
                String param = JSON.toJSONString(bean);
                //发送请求并获取结果
                String result = HttpUtil.post(REFUSE_URL+"?access_token="+accessToken, param);
                //打印检测结果
                System.out.println(result);
                //给图片画框
                getReact(filePath, JSON.parseObject(result, FaceMaskSample.class));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }else
        {
            System.out.println("N");
        }


/*        for (Rect rect : faceDetections.toArray()) {
            // 设置识别方块的颜色
            Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }*/
        return img;
    }
    /**
     * 将Mat转化为BufferedImage
     *
     * @param mat
     * @return
     */
    public static BufferedImage mat2BI(Mat mat) {
        int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
        byte[] data = new byte[dataSize];
        mat.get(0, 0, data);
        int type = mat.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
        if (type == BufferedImage.TYPE_3BYTE_BGR) {
            for (int i = 0; i < dataSize; i += 3) {
                byte blue = data[i];
                data[i] = data[i + 2];
                data[i + 2] = blue;
            }
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }



}
