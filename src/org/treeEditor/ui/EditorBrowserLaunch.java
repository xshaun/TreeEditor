package org.treeEditor.ui;

/////////////////////////////////////////////////////////  
//Bare Bones Browser Launch                            //  
//Version 1.5 (December 10, 2005)                    //  
//By Dem Pilafian                                                //  
//支持: Mac OS X, GNU/Linux, Unix, Windows XP//  
//可免费使用                                                        //
/////////////////////////////////////////////////////////

import java.awt.Desktop;
import java.lang.reflect.Method;
import java.net.URI;

public class EditorBrowserLaunch {

    public static void openURL(String url) {
        try {
            browse(url);
        } catch (Exception e) {
            /* should add logger*/
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void browse(String url) throws Exception {

        // Get system OS
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Mac OS")) { // Mac OS
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
            openURL.invoke(null, new Object[]{url});
        } else if (osName.startsWith("Windows")) { // Windows
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else { // Unix or Linux
            URI uri = new URI(url);
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            if (desktop != null) {
                desktop.browse(uri);
            } else {
                String[] browsers = {"google-chrome-stable", "google-chrome", "Chromium", "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++)
                    // 执行代码，在brower有值后跳出，
                    // 这里是如果进程创建成功了，==0是表示正常结束。
                    if (Runtime.getRuntime().exec(new String[]{"which", browsers[count]}).waitFor() == 0)
                        browser = browsers[count];
                if (browser == null)
                    throw new Exception("Could not find web browser");
                else
                    // 这个值在上面已经成功的得到了一个进程。
                    Runtime.getRuntime().exec(new String[]{browser, url});
            }
        }
    }
}  