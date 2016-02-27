/**
 * 基于XML形式建树的操作
 */
package org.treeEditor.process;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.dom4j.Node;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.treeEditor.ui.BasicGraphEditor;
import org.treeEditor.ui.CustomGraphComponent;

public class TreeOnXML {

    public static final String LINE = System.getProperty("line.separator");

    /**
     * 从文件读取XML，输入文件名，返回XML文档
     * 读取的字符编码是按照XML文件头定义的编码来转换
     *
     * @param fileName
     * @return
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public static Document readXML(File fileName) throws MalformedURLException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = (Document) reader.read(fileName);
        return document;
    }

    public static Document convertXML(String string) {
        if (string == null) return null;

        Document document = null;
        try {
            document = DocumentHelper.parseText(string);
        } catch (DocumentException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: convertXML");
            return null;
        }
        return document;
    }


    /**
     * 取得Root节点
     *
     * @param doc
     * @return
     */
    public static Element getRootElement(Document doc) {
        return doc.getRootElement();
    }

    /**
     * 从文件里读入数据
     *
     * @param file     文件路径和文件名
     * @param dataTree 此变量返回文件中的信息
     * @return true | false
     */
    @SuppressWarnings("unchecked")
    public static boolean readData(File file, DataTree dataTree) {

        HashMap<Integer, String> enMap = new HashMap<Integer, String>();
        HashMap<Integer, String> chMap = new HashMap<Integer, String>();
        HashMap<Integer, String> errorMap = new HashMap<Integer, String>();
        HashMap<Integer, String> groupNameMap = new HashMap<Integer, String>();

        try {
            // 获取XML文件
            Document XML = readXML(file);

            // 读取根节点
            Element root = getRootElement(XML);
            //Node root = XML.selectSingleNode("/root");

            int count = 0;

            Iterator<Element> i = root.elementIterator();
            while (i.hasNext()) {
                Element group = i.next();
                String groupName = group.getName();
                List<Element> groupElements = group.elements();

                if (groupElements.size() != 2) {
                    JOptionPane.showMessageDialog(null,
                            "第" + (count + 1) + "组 句子个数错误。");

                    errorMap.put(count, group.asXML());
                    chMap.put(count, null);
                    enMap.put(count, null);

                } else {
                    chMap.put(count, groupElements.get(0).asXML());
                    enMap.put(count, groupElements.get(1).asXML());
                    groupNameMap.put(count, groupName);
                }
                count++;
            }

//			for (Element  i : (List<Element>)root.selectNodes("//s")) {
//				process.put(count, i.asXML());
//				count++;
//			}
            if (count == 0) return false;

            //返回值
            dataTree.setTreeCount(new Integer(count));
            dataTree.setEnMap(enMap);
            dataTree.setChMap(chMap);
            dataTree.setErrorMap(errorMap);
            dataTree.setGroupNameMap(groupNameMap);

            return true;

        } catch (MalformedURLException e1) {
            //e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: readXML--MalformedURLException");
        } catch (DocumentException e1) {
            //e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: readXML--DocumentException");
        }

        return false;
    }

    /**
     *
     */
    public static Integer VERTEX_X;

    /**
     * 绘制树形结构
     *
     * @param graphComponent
     * @param XML
     * @param vertexX
     * @param vertexY
     * @param attrMap        记录节点属性
     * @return 返回根节点对象
     */
    @SuppressWarnings("unchecked")
    public static Object creatTree(
            CustomGraphComponent graphComponent,
            Document XML,
            Integer vertexX,
            Integer vertexY,
            HashMap<Integer, List<Attribute>> attrMap
    ) {

        BasicGraphEditor.CustomGraph graph = graphComponent.getGraph();

        graphComponent.setToolTips(true);

        Object parent = graph.getDefaultParent();

        if (XML != null) {
            graph.getModel().beginUpdate();

            //记录儿子节点起始X坐标
            int startX = VERTEX_X;

            //节点之间的距离
            final int vertexDistance = 100;

            //获取根节点
            Element root = XML.getRootElement();

            //获取根节点下的所有内容
            List<Node> childContent = root.content();
            List<Object> childObject = new ArrayList<Object>();

            //遍历根节点下内容
            for (Node index : childContent) {
                String isEmpty = index.asXML();
                if (isEmpty.matches("[ ]*")) {
                    continue;
                }

                String indexType = index.getNodeTypeName();

                //如果仅包含文本，则为叶子节点
                if (indexType.equals("Text")) {
                    String text = index.getText();
                    int maxLenght = 5;
                    if (text.replaceAll(" ", "").matches("[a-zA-Z0-9._ ]*")) {
                        maxLenght = 15;
                    }

                    //建立叶子节点
                    Object leafObject =
                            graph.insertLeafNode(
                                    parent,
                                    text,//text.length()>maxLenght ? text.substring(0, 3)+"··" : text,
                                    VERTEX_X,
                                    vertexY + 60,
                                    text.length() > maxLenght
                            );

                    //记录叶子节点内容
                    attrMap.put(leafObject.hashCode(), convertXML("<textSub caseText=\"" + index.getText() + "\"></textSub>").getRootElement().attributes());

                    VERTEX_X += vertexDistance * 1;

                    childObject.add(leafObject);

                }//如果含有标签，则以此节点为根进行建树
                else if (indexType.equals("Element")) {
                    //获取此子节点的XML
                    Document childTreeRootXML = convertXML(index.asXML());

                    //创建以此子节点为根的树
                    Object childTreeRootObject =
                            creatTree(
                                    graphComponent,
                                    childTreeRootXML,
                                    vertexX,
                                    vertexY + 60,
                                    attrMap
                            );
                    childObject.add(childTreeRootObject);
                }
            }

            //显示属性
            List<Attribute> attrList = root.attributes();
            String attr = "";
            if (attrList != null && !attrList.isEmpty()) {
                attr = LINE + "{";
                for (Attribute index : attrList) {
                    attr += (index.getName() + "=" + index.getValue() + " ");
                }
                attr += "}";
            }

            //建立根节点
            Object rootObject =
                    graph.insertNode(
                            parent,
                            root.getName() + attr,
                            (startX + VERTEX_X - vertexDistance) / 2,
                            vertexY
                    );

            //记录节点属性
            attrMap.put(rootObject.hashCode(), root.attributes());

            //创建与根节点之间的连线
            for (Object index : childObject) {
                graph.insertEdge(parent, rootObject, index);
            }

            graph.getModel().endUpdate();

            return rootObject; //返回根节点对象

        }// end of if
        return null;
    }


    /**
     * 绘制树形结构
     *
     * @param editor 图像编辑的基本单元
     * @return
     */
    public static void creatTree(BasicGraphEditor editor) {
//        //获取显示的页码
//        int pageIndex = editor.getToolBarComboBoxIndex();
//
//        //获得显示的数据
//        DataTree dataTree = editor.getDataTree();
//
//        String chStr = dataTree.getChMap().get(pageIndex);
//        String enStr = dataTree.getEnMap().get(pageIndex);
//        if (chStr == null || chStr.isEmpty()) return;
//        if (enStr == null || enStr.isEmpty()) return;
//        Document chXML = convertXML(chStr.replaceAll("[" + BasicGraphEditor.LINE_TAB + "]*", "").trim());
//        Document enXML = convertXML(enStr.replaceAll("[" + BasicGraphEditor.LINE_TAB + "]*", "").trim());
//        if (chXML == null) return;
//        if (enXML == null) return;
//
//        //获得显示面板
//        CustomGraphComponent chGraphComponent = editor.getChGraphComponent();
//        CustomGraphComponent enGraphComponent = editor.getEnGraphComponent();
//        CustomGraph chGraph = chGraphComponent.getGraph();
//        CustomGraph enGraph = enGraphComponent.getGraph();
//
//        //设置内容超出后自动剪裁
//        chGraph.setLabelsClipped(true);
//        enGraph.setLabelsClipped(true);
//
//        //移去change监听器，以免建树时被记为更改
//        chGraph.getModel().removeListener(chGraph.getChangeTracker(), mxEvent.CHANGE);
//        enGraph.getModel().removeListener(enGraph.getChangeTracker(), mxEvent.CHANGE);
//
//        //清空面板
//        chGraph.selectAll();
//        chGraph.removeCells();
//        enGraph.selectAll();
//        enGraph.removeCells();
//
//        //绘制树形图
//        creatTree(
//                chGraphComponent,
//                chXML,
//                VERTEX_X = 50,
//                10,
//                dataTree.getChAttrMap()
//        );
//
//        //绘制树形图
//        creatTree(
//                enGraphComponent,
//                enXML,
//                VERTEX_X = 50,
//                10,
//                dataTree.getEnAttrMap()
//        );
//
//        //加上change监听器
//        chGraph.getModel().addListener(mxEvent.CHANGE, chGraph.getChangeTracker());
//        enGraph.getModel().addListener(mxEvent.CHANGE, enGraph.getChangeTracker());
//
//        //更新文字框的内容
//        editor.updateTextScrollPane();
    }

    /**
     * 重新绘制修改树
     *
     * @param editor 图像编辑的基本单元
     * @return
     */
    public static void repaintTree(BasicGraphEditor editor) {
//        //获得显示面板
//        CustomGraphComponent curGraphComponent = editor.getCurGraphComponent();
//        CustomGraph curGraph = curGraphComponent.getGraph();
//
//        // 判断中英文
//        boolean curIsCh = false;
//        if (curGraphComponent == editor.getChGraphComponent()) {
//            curIsCh = true;
//        } else if (curGraphComponent == editor.getEnGraphComponent()) {
//            curIsCh = false;
//        } else {
//            return;
//        }
//
//        //获取显示的页码
//        int pageIndex = editor.getToolBarComboBoxIndex();
//
//        //获得显示的数据
//        DataTree dataTree = editor.getDataTree();
//        String curStr = curIsCh ? dataTree.getChMap().get(pageIndex) : dataTree.getEnMap().get(pageIndex);
//        if (curStr == null || curStr.isEmpty()) return;
//        Document curXML = convertXML(curStr.replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").trim());
//        if (curXML == null) return;
//
//        //设置内容超出后自动剪裁
//        curGraph.setLabelsClipped(true);
//
//        //清空面板
//        curGraph.selectAll();
//        curGraph.removeCells();
//
//        //绘制树形图
//        creatTree(
//                curGraphComponent,
//                curXML,
//                VERTEX_X = 50,
//                10,
//                curIsCh ? dataTree.getChAttrMap() : dataTree.getEnAttrMap()
//        );

    }


    public static String getSaveString(CustomGraphComponent GraphComponent, HashMap<Integer, List<Attribute>> attrMap) {
//        //获得显示面板
//        CustomGraph graph = GraphComponent.getGraph();
//
//        //设置分析面板
//        mxAnalysisGraph aGraph = new mxAnalysisGraph();
//        aGraph.setGraph(graph);
//
//        boolean isTree = mxGraphStructure.isTree(aGraph);
//        if (isTree) {
//            Object root = graph.findTreeRoots(graph.getDefaultParent()).get(0);
//
//            List<Object> traverseList = traverse(root, graph, attrMap);
//            if (traverseList == null) return null;
//            String str = (String) traverseList.get(1);
//
//            return str;
//        }

        return null;

    }

    public static List<Object> traverse(Object vertex, BasicGraphEditor.CustomGraph graph, HashMap<Integer, List<Attribute>> attrMap) {

        List<Object> roots = graph.findTreeRoots(graph.getDefaultParent());
        List<Object> point = new ArrayList<Object>(2);
        Object root = null;
        if (roots.size() == 0) {
            point.add(0);
            point.add("");
            return point;
        }
        root = roots.get(0);
        String saveString = "";
        Integer x = 0;

        if (vertex != null) {
            String label = graph.getLabel(vertex).replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").replaceAll("\\{.*\\}", "").trim();
            int edgeCount = graph.getModel().getEdgeCount(vertex);

            //出度和入度的和为1的结点只有叶子结点也根节点，此处需要判断为非根节点
            if (edgeCount == 1 && !vertex.equals(root)) {
//		    	//获取叶子节点的文本
//		    	List<Attribute> list = attrMap.get(vertex.hashCode());
//		    	if(list != null){
//			    	for(Attribute index:list){
//						if(index.getName()=="caseText"){
//							saveString = index.getValue();
//							break;
//						}
//					}
//		    	}else{
//		    		saveString = graph.getLabel(vertex);
//		    	}

                saveString = graph.getLabel(vertex);

                x = Integer.valueOf((int) graph.getCellBounds(vertex).getCenterX());
                point.add(x);
                point.add(saveString);
                return point;

            } else {
                //非叶子结点
                String labelString = graph.getLabel(vertex).replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", " ");

                String attr = "";
                if (labelString.matches(".*\\{.*\\}.*")) {
                    attr = labelString.substring(labelString.indexOf("{") + 1, labelString.lastIndexOf("}")).trim();
                    String[] attrArray = attr.split(" ");
                    attr = "";
                    for (String index : attrArray) {
                        if (index.contains("=")) {
                            index = index.replaceAll("=", "=\"") + "\"";
                            attr += " " + index;
                        }
                    }
                }

                saveString = "<" + label + attr + ">";
            }

            List<List<Object>> cells = new ArrayList<List<Object>>();

            if (edgeCount > 0) {
                for (int i = 0; i < edgeCount; i++) {
                    Object e1 = graph.getModel().getEdgeAt(vertex, i);
                    boolean isSource = graph.getModel().getTerminal(e1, true) == vertex;

                    if (isSource) {
                        Object next = graph.getModel().getTerminal(e1, !isSource);
                        if (next != null) {
                            List<Object> temp;
                            temp = traverse(next, graph, attrMap);
                            if (temp == null) return null;

                            x += (Integer) temp.get(0);
                            cells.add(temp);
                        }
                    }
                }
                //处理根节点只有一个孩子的情况
                if (vertex.equals(root) && edgeCount == 1)
                    edgeCount++;
                x = x / (edgeCount - 1);
                point.add(x);
            }

            for (int i = cells.size() - 1; i > 0; --i) {
                boolean isSort = false;
                for (int j = 0; j < i; ++j) {
                    if (((Integer) ((List<Object>) cells.get(j + 1)).get(0)) < ((Integer) ((List<Object>) cells.get(j)).get(0))) {
                        List<Object> temp = (List<Object>) cells.get(j);
                        cells.set(j, cells.get(j + 1));
                        cells.set(j + 1, temp);
                        isSort = true;
                    }
                }
                if (!isSort) break;
            }
            for (int i = 0; i < cells.size(); i++) {
                String temp;
                temp = (String) ((List<Object>) cells.get(i)).get(1);
                saveString += temp;
            }

            if (edgeCount != 1) {
                saveString += "</" + label + ">";
            }

        }

        point.add(saveString);
        return point;

    }


    /**
     * 保存modifyMap的信息至文件filename中
     *
     * @param modifyMap
     * @param filename
     * @return
     * @throws Exception
     */
    public static boolean saveToFile(DataTree dataTree, String filename) throws Exception {


        try {
            HashMap<Integer, String> enMap = dataTree.getEnMap();
            HashMap<Integer, String> chMap = dataTree.getChMap();
            HashMap<Integer, String> errorMap = dataTree.getErrorMap();
            HashMap<Integer, String> groupNameMap = dataTree.getGroupNameMap();

            //创建输出流
            FileOutputStream outputStream = new FileOutputStream(new File(filename));

            //写入文件
            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE;
            header += "<root>" + LINE;
            outputStream.write(header.getBytes("utf-8"));

            for (int i = 0; i < chMap.size(); i++) {
                String temp = "<" + groupNameMap.get(i) + ">" + LINE;

                String chTemp = chMap.get(i);
                String enTemp = enMap.get(i);
                if (chTemp == null && enTemp == null) {
                    temp = errorMap.get(i);
                } else {
                    temp += chTemp + LINE;
                    temp += enTemp + LINE;
                    temp += "</" + groupNameMap.get(i) + ">" + LINE;
                }

                outputStream.write(temp.getBytes("utf-8"));
            }

            String footer = "</root>" + LINE;
            outputStream.write(footer.getBytes("utf-8"));

            //输出流强制写回
            outputStream.flush();

            //输出流关闭
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

}
