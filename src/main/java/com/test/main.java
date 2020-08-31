package com.test;

import com.test.util.ShowImgUtil;

/**
 * 主类运行
 * @author: Yimning
 * @date: 2020/2/29  16:12
 * @description: 开启本地摄像头，进行脸部跟踪，以及图片抓取与保存
 */


public class main {
    public static void main(String[] args) throws Exception {
        new ShowImgUtil().run();
    }
}