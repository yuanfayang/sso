package com.changhong.sso.core.dao.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/13
 * @Time: 9:08
 * @Email: flyyuanfayang@sina.com
 * @Description: 本地文件系统数据存取对象抽象类
 */
public abstract class FileSystemDao {
    private static Logger logger = Logger.getLogger(FileSystemDao.class.getName());

    /**
     * 外部数据文件的地址，优先级最高
     */
    protected String externalData = null;

    /**
     * 默认的数据文件的地址，在classPath下
     */
    protected String classPathData = null;

    public String getExternalData() {
        return externalData;
    }

    /**
     * 重新设置外部数据文件的路径
     * 设置成功后，需要触发重新加载数据内容
     *
     * @param externalData 外部数据文件的路径
     */
    public void setExternalData(String externalData) {
        //设置的参数不为空
        if (!StringUtils.isEmpty(externalData)) {
            //外部数据文件的路径不同
            if (!externalData.equals(this.externalData)){
                this.loadAppData();
            }
        }
        else{
            if (!StringUtils.isEmpty(this.externalData)){
                this.externalData=externalData;
                //重新加载数据
                this.loadAppData();
            }
        }
    }

    public String getClassPathData() {
        return classPathData;
    }

    public void setClassPathData(String classPathData) {
        this.classPathData = classPathData;
    }

    /**
     * 从数据文件中加载数据，抽象方法，由具体子类实现
     */
    protected abstract void loadAppData();

    /**
     * 从数据文件中读取数据。
     * @return 读取的字符串。
     */
    public String readDataFromFile(){
        try{
            InputStream inputStream = null;
            //优先使用外部数据文件。
            if(!StringUtils.isEmpty(this.getExternalData())){
                try{
                    inputStream = new FileInputStream(this.getExternalData());
                }catch (Exception e) {
                    inputStream = null;
                }
            }
            //若无外部文件，则使用默认的内部资源文件。
            if(inputStream==null){
                if(!StringUtils.isEmpty(this.getClassPathData())){
                    try{
                        Resource resource = new ClassPathResource(this.getClassPathData());
                        inputStream = resource.getInputStream();
                    }catch (Exception e) {
                        inputStream = null;
                    }
                }
            }
            if(inputStream!=null){
                return new String(readStream(inputStream));
            }
        }catch (Exception e) {
            logger.log(Level.SEVERE, "load app data file error.", e);
        }
        return null;
    }

    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}
