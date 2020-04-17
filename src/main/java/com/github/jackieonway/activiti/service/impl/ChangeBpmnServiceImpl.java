/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jackieonway.activiti.service.ChangeBpmnService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jackie
 * @version $id: ChangeBpmnServiceImpl.java v 0.1 2020-03-03 15:08 Jackie Exp $$
 */
@Service
public class ChangeBpmnServiceImpl implements ChangeBpmnService {

    private Logger LOGGER = LoggerFactory.getLogger(ChangeBpmnServiceImpl.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${activiti.root.path}")
    private String rootPath;
    private  static final Map<Integer,String> MODEL_IDS = new HashMap<>();
    static {
        MODEL_IDS.put(2,"1");
        MODEL_IDS.put(3,"3");
        MODEL_IDS.put(4,"5");
        MODEL_IDS.put(5,"7");
    }

    @Override
    public void changeBpmnService() throws IOException {

        String path = "D:\\activiti\\xml\\data\\implements_basic.csv";
        String[] strings = handle(path);
        int count = 1;
        for (String string : strings) {
            System.out.print(count + "---"+string+"------");
            String[] s = string.split("\t");
            if (s.length == 3){
                System.out.println("写入");
                Integer num = Integer.valueOf(s[2]);
                String modelId = MODEL_IDS.get(num);
                String taskName = s[1];
                String name = s[0];
                JsonNode jsonNode = objectMapper.readTree(new String(repositoryService.getModelEditorSource(modelId), StandardCharsets.UTF_8));
                byte[] pngBytes = repositoryService.getModelEditorSourceExtra(modelId);
                ObjectNode objectNode = (ObjectNode)jsonNode;
                ObjectNode properties = (ObjectNode)jsonNode.get("properties");
                properties.put("process_id","qh_"+name);
                properties.put("name",taskName);
                objectNode.set("properties",properties);
                BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
                BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(jsonNode);
                byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
                //写入流程图xml
                transToFile(rootPath, "//xml//imp//"+count+"-"+name+"-"+taskName+"//", count+"-"+name+"-"+taskName+"-"+num, ".bpmn20.xml", bpmnBytes);
                // 写入流程图图片
                transToFile(rootPath, "//xml//imp//"+count+"-"+name+"-"+taskName+"//", count+"-"+name+"-"+taskName+"-"+num, ".png", pngBytes);
            }
            count++;
        }
       /* String modelId = "";
        String name = "";
        JsonNode jsonNode = objectMapper.readTree(new String(repositoryService.getModelEditorSource(modelId), StandardCharsets.UTF_8));
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(jsonNode);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        //写入流程图xml
        transToFile(rootPath, "//xml//imp//", name, ".bpmn20.xml", bpmnBytes);*/
    }

    @SuppressWarnings("resource")
    public static String[] handle(String path) {
        String[] item = {};
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String info[] = line.split(",");
                int iteml = item.length;
                int infol = info.length;
                item = Arrays.copyOf(item, iteml + infol);//填充
                System.arraycopy(info, 0, item, iteml, infol);//组合数组
            }
        } catch (FileNotFoundException ex) {
            System.out.println("没找到文件！");
        } catch (IOException ex) {
            System.out.println("读写文件出错！");
        }
        System.out.println(Arrays.toString(item));
        return item;
    }

        /**
         * 将Bytes写到文件中
         *
         * @param rootPath 文件路径 如: <i>win: <b>D:\\xx\\</b> linux : <b>/app/file/</b></i>
         * @param busiPath 业余文件路径 如: <i><b>xml,pic</b></i>
         * @param fileName 文件名
         * @param extName  文件扩展名 如: <i><b>.xml , .jpg</b></i>
         * @param bytes    待写入文件的byte数组
         * @return 文件的相对路径
         * @author Jackie
         * @date 2019/10/30 20:51
         * @method-name com.github.jackieonway.activiti.service.impl.ChangeBpmnServiceImpl#transToFile
         * @see ChangeBpmnServiceImpl
         * @since 1.0
         */
    private String transToFile(String rootPath, String busiPath, String fileName, String extName, byte[] bytes) {
        File file = new File(rootPath + busiPath);
        String path = busiPath + fileName + extName;
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            fileOutputStream = new FileOutputStream(new File(rootPath + path));
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            LOGGER.error("写文件[{}]出错", fileName + extName, e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("写文件[{}]关闭流出错", fileName + extName, e);
                }
            }
        }
        return path;
    }
}
