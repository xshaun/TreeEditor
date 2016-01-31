package org.treeEditor.assist;

/**
 * Created by boy on 16-1-31.
 */
public class LoadFile {

    public static String sp = java.io.File.separator;

    /**
     * 添加文件分隔符
     *
     * @param data
     * @return
     */
    public static String addSeparator(String... data) {
        StringBuilder result = new StringBuilder();
        for (String u : data) {
            result.append(u);
            result.append(sp);
        }
        result.deleteCharAt(result.lastIndexOf(sp));

        return result.toString();
    }

    /**
     * 获取 images 下的资源
     *
     * @param imageName
     * @return
     */
    public static java.net.URL loadImage(String imageName) {
        return LoadFile.class.getResource(addSeparator("..", "images", imageName));
    }

    /**
     * 获取 resource 下的资源
     *
     * @param resourceName
     * @return
     */
    public static java.net.URL loadResource(String resourceName) {
        return LoadFile.class.getResource(addSeparator("..", "resources", resourceName));
    }

}
