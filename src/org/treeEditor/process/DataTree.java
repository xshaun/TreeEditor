package org.treeEditor.process;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

public class DataTree {

    /**
     *
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * 存储树的数据信息
     */
    //保存从文件中读取的句子总数
    protected Integer treeCount = new Integer(0);

    protected HashMap<Integer/*句子的编号*/, String/*句子的内容*/> chMap =
            new HashMap<Integer, String>();

    protected HashMap<Integer/*句子的编号*/, String/*句子的内容*/> enMap =
            new HashMap<Integer, String>();

    protected HashMap<Integer/*节点的编号*/, List<Attribute>/*属性列表*/> chAttrMap =
            new HashMap<Integer, List<Attribute>>();

    protected HashMap<Integer/*节点的编号*/, List<Attribute>/*属性列表*/> enAttrMap =
            new HashMap<Integer, List<Attribute>>();

    protected HashMap<Integer/*句子的编号*/, String/*句子的内容*/> errorMap =
            new HashMap<Integer, String>();

    protected HashMap<Integer/*句子的编号*/, String/*组名*/> groupNameMap =
            new HashMap<Integer, String>();

    //保存所有标签名及其属性
    protected HashMap<String/*标签名*/, List<Element>/*标签属性*/> tagEnMap =
            new HashMap<String, List<Element>>();

    //保存所有标签名及其属性
    protected HashMap<String/*标签名*/, List<Element>/*标签属性*/> tagChMap =
            new HashMap<String, List<Element>>();


    @SuppressWarnings("unchecked")
    public DataTree() {
        //读入标签数据
        File fileEn = new File("configuration" + FILE_SEP + "tag-en.xml");
        File fileCh = new File("configuration" + FILE_SEP + "tag-ch.xml");
        File fileEnPre = new File("configuration" + FILE_SEP + "tag-en.pre");
        File fileChPre = new File("configuration" + FILE_SEP + "tag-ch.pre");
        try {
            if (!fileEn.exists() && fileEnPre.exists()) {
                if (fileEn.createNewFile())
                    ConvertFile.preToXML(fileEnPre, fileEn);
//				System.out.println(ConvertFile.preToXML(fileEnPre, fileEn));
            }
            if (!fileCh.exists() && fileChPre.exists()) {
                if (fileCh.createNewFile())
                    ConvertFile.preToXML(fileChPre, fileCh);
//				System.out.println(ConvertFile.preToXML(fileChPre, fileCh));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Document docEn = TreeOnXML.readXML(fileEn);
            Document docCh = TreeOnXML.readXML(fileCh);
            Element rootEn = docEn.getRootElement();
            Element rootCh = docCh.getRootElement();

            // tag-en.xml 获取名称为s的节点
            for (Iterator<Element> i = rootEn.elementIterator("tag"); i.hasNext(); ) {
                Element foo = i.next();
                tagEnMap.put(foo.attributeValue("name"), foo.elements());
            }

            // tag-ch.xml 获取名称为s的节点
            for (Iterator<Element> i = rootCh.elementIterator("tag"); i.hasNext(); ) {
                Element foo = i.next();
                tagChMap.put(foo.attributeValue("name"), foo.elements());
            }

        } catch (MalformedURLException | DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * the following are getters and setters
     */
    /**
     * @return the treeCount
     */
    public Integer getTreeCount() {
        return treeCount;
    }

    /**
     * @param treeCount the treeCount to set
     */
    public void setTreeCount(Integer treeCount) {
        this.treeCount = treeCount;
    }

    /**
     * @return the chMap
     */
    public HashMap<Integer, String> getChMap() {
        return chMap;
    }

    /**
     * @param chMap the chMap to set
     */
    public void setChMap(HashMap<Integer, String> chMap) {
        this.chMap = chMap;
    }

    /**
     * @return the enMap
     */
    public HashMap<Integer, String> getEnMap() {
        return enMap;
    }

    /**
     * @param enMap the enMap to set
     */
    public void setEnMap(HashMap<Integer, String> enMap) {
        this.enMap = enMap;
    }

    /**
     * @return the chAttrMap
     */
    public HashMap<Integer, List<Attribute>> getChAttrMap() {
        return chAttrMap;
    }

    /**
     * @param chAttrMap the chAttrMap to set
     */
    public void setChAttrMap(HashMap<Integer, List<Attribute>> chAttrMap) {
        this.chAttrMap = chAttrMap;
    }

    /**
     * @return the enAttrMap
     */
    public HashMap<Integer, List<Attribute>> getEnAttrMap() {
        return enAttrMap;
    }

    /**
     * @param enAttrMap the enAttrMap to set
     */
    public void setEnAttrMap(HashMap<Integer, List<Attribute>> enAttrMap) {
        this.enAttrMap = enAttrMap;
    }

    /**
     * @return the enAttrMap
     */
    public HashMap<Integer, String> getErrorMap() {
        return errorMap;
    }

    /**
     * @param enAttrMap the enAttrMap to set
     */
    public void setErrorMap(HashMap<Integer, String> errorMap) {
        this.errorMap = errorMap;
    }

    /**
     * @return the enAttrMap
     */
    public HashMap<Integer, String> getGroupNameMap() {
        return groupNameMap;
    }

    /**
     * @param enAttrMap the enAttrMap to set
     */
    public void setGroupNameMap(HashMap<Integer, String> groupNameMap) {
        this.groupNameMap = groupNameMap;
    }

    /**
     * @return the tagMap
     */
    public HashMap<String, List<Element>> getTagEnMap() {
        return tagEnMap;
    }

    /**
     * @param tagMap the tagMap to set
     */
    public void setTagEnMap(HashMap<String, List<Element>> tagEnMap) {
        this.tagEnMap = tagEnMap;
    }

    /**
     * @return the tagMap
     */
    public HashMap<String, List<Element>> getTagChMap() {
        return tagChMap;
    }

    /**
     * @param tagMap the tagMap to set
     */
    public void setTagChMap(HashMap<String, List<Element>> tagChMap) {
        this.tagChMap = tagChMap;
    }

}
