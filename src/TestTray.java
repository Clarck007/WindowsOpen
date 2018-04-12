

 
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
 
import javax.swing.ImageIcon;
import javax.swing.JFrame;
 
public class TestTray extends JFrame {
	 
	private Menu m,sub;
	private MenuBar mb;
	private MenuItem openItem;
	private FileDialog openDia;
	private TextArea textArea;
 public TestTray() {
	 
	 mb=new MenuBar();
	 m=new Menu("文件");
	 sub=new  Menu("文件");
	 openItem=new MenuItem("打开文件");
	 textArea=new TextArea();
	 m.add(openItem);
	 mb.add(m);
	 TestTray.this.setMenuBar(mb);
	 TestTray.this.add(textArea);
	 openDia=new FileDialog(TestTray.this,"打开",FileDialog.LOAD);
 this.setSize(500, 400);
 
 this.setLocationRelativeTo(null);// 把窗体设置在屏幕中间
 
 systemTray(); // 设置系统托盘
 
 // 添加关闭按钮事件，关闭时候实质是把窗体隐藏
 this.addWindowListener(new WindowAdapter() {
 
  public void windowClosing(WindowEvent e) {
  TestTray.this.setVisible(false);
  }
 });
 this.setVisible(true);
 openItem.addActionListener(new ActionListener(){
	 public void actionPerformed(ActionEvent e){//打开文件
		 openDia.setVisible(true); 
		 String fileDirectory = openDia.getDirectory();
		 String fileName = openDia.getFile();
		 System.out.println (fileDirectory+fileName); 
//		 try { 
//			 Process process = Runtime.getRuntime().exec(fileDirectory+fileName); 
//			 process.waitFor(); 
//			 process.destroy(); 
//			 } catch (Exception ex) {
//				 ex.printStackTrace();
//				 System.out.println ("程序未被打开！"); 
//				 } 
		 
		 String strcmd = "cmd /c start /b   "+fileDirectory+fileName;  
		 run_cmd(strcmd);  
	 }
 });
 
 
 }
 
 /**
 * 处理系统托盘
 */
 private void systemTray() {
	 
	 
	 
	 //托盘系统
 if (SystemTray.isSupported()) { // 判断系统是否支持托盘功能.
  // 创建托盘右击弹出菜单
  PopupMenu popupMenu = new PopupMenu();
   
  //创建弹出菜单中的退出项
  MenuItem itemExit = new MenuItem("退出系统");
  itemExit.addActionListener(new ActionListener() {
   
   public void actionPerformed(ActionEvent e) {
    System.exit(0);
   }
   });
  popupMenu.add(itemExit);
   
  //创建托盘图标
  ImageIcon icon = new ImageIcon("img/icon.png"); // 创建图片对象
  TrayIcon trayIcon = new TrayIcon(icon.getImage(), "右键点击退出程序",popupMenu);
  trayIcon.addActionListener(new ActionListener() {
 
  public void actionPerformed(ActionEvent e) {
   TestTray.this.setVisible(true);
  }
  });
   
  //把托盘图标添加到系统托盘
  //这个可以点击关闭之后再放到托盘里面，在此是打开程序直接显示托盘图标了
  try {
  SystemTray.getSystemTray().add(trayIcon);
  } catch (AWTException e1) {
  e1.printStackTrace();
  }
 }
 }
 
 public void run_cmd(String strcmd) {
//
      Runtime rt = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
      Process ps = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
      try {
          ps = rt.exec(strcmd);   //该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
          ps.waitFor();  //等待子进程完成再往下执行。
      } catch (Exception e1) {
          e1.printStackTrace();
      }  
      try {
    	  InputStream inputStream = ps.getInputStream();
    	  Scanner scan=new Scanner(inputStream);

    	  while(scan.hasNextLine())
    	  textArea.append(scan.nextLine());
    	  
    	    scan.close();


//    	  int temp,i=0;
//
//    	  byte b[]=new byte[10240];
//
//    	  while((temp=inputStream.read())!=-1){
//
//    	  b[i]=(byte)temp;
//    	
//    	  i++;
//
//    	  }
//    	  textArea.append(new String(b));
//    	  textArea.append("/r/n");
////    	  System.out.println(new String(b));
//    	

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
     

      int i = ps.exitValue();  //接收执行完毕的返回值
      if (i == 0) {
//          System.out.println("执行完成.");
          textArea.append("执行完成.");
      } else {
//          System.out.println("执行失败."); 
          textArea.append("执行失败.");
      }

      ps.destroy();  //销毁子进程
      ps = null;   
  }
 
 public static void main(String[] args) {
 new TestTray();
 }
 
}
