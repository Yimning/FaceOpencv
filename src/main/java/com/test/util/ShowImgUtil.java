package com.test.util;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yimning
 */
public class ShowImgUtil extends JPanel {

    public static BufferedImage mImg;
    private VideoCapture capture;
    private JFrame frame;

    /**
     * 运行窗体并进行显示
     */
    public void run() {
        this.init();

        Mat temp = new Mat();
        Mat capImg = new Mat();

        while (this.frame.isShowing()) {
            this.capture.read(capImg);
            // temp的图片是灰色
            Imgproc.cvtColor(capImg, temp, Imgproc.COLOR_RGB2GRAY);
            // 将图片进行识别
            mImg = OpenCVUtil.mat2BI(OpenCVUtil.getFace(capImg));
           // this.repaint();
            //拍照
            SimpleDateFormat data = new SimpleDateFormat("yyyyMMddHHmmss");
            String name = data.format(new Date());
            //获取桌面路径
            //File path = FileSystemView.getFileSystemView().getHomeDirectory();
            String path = "C:/E盘(文件)/IdeaProjects/FaceMaskSample/images";
            System.out.println(path);
            String format = "jpeg";
            File f = new File(path + File.separator + name + "." + format);
            try {
                System.out.println(name);
                ImageIO.write(ShowImgUtil.mImg, format, f);
                //Thread.sleep(5000);
                //要检测的图片
/*                String filePath = path+"\\"+name+".jpeg";
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
          */  } catch (IOException e) {
                e.printStackTrace();
            }
        }
        capture.release();
        frame.dispose();
    }

    /**
     * 初始化摄像头和lib
     */
    private void init() {
        // 加载dll文件,在opencv安装目录下找到
        System.loadLibrary("opencv_java420");

        capture = new VideoCapture();
        capture.open(0);
        int height = (int) capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
        int width = (int) capture.get(Videoio.CAP_PROP_FRAME_WIDTH);

        if (height == 0 || width == 0) {
            System.out.println("摄像头打开失败");
        }

        // 创建页面
        frame = new JFrame("I Can Capture Face");
        frame.setDefaultCloseOperation(2);
        frame.add(this);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(width + frame.getInsets().left + frame.getInsets().right,
                height + frame.getInsets().top + frame.getInsets().bottom);
        frame.setLocationRelativeTo(null);
    }

    /**
     * 重绘Panel
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.repaint();
        if (mImg != null) {
            g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), this);
        }
    }

}
