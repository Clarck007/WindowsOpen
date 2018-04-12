

 
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
	 m=new Menu("�ļ�");
	 sub=new  Menu("�ļ�");
	 openItem=new MenuItem("���ļ�");
	 textArea=new TextArea();
	 m.add(openItem);
	 mb.add(m);
	 TestTray.this.setMenuBar(mb);
	 TestTray.this.add(textArea);
	 openDia=new FileDialog(TestTray.this,"��",FileDialog.LOAD);
 this.setSize(500, 400);
 
 this.setLocationRelativeTo(null);// �Ѵ�����������Ļ�м�
 
 systemTray(); // ����ϵͳ����
 
 // ��ӹرհ�ť�¼����ر�ʱ��ʵ���ǰѴ�������
 this.addWindowListener(new WindowAdapter() {
 
  public void windowClosing(WindowEvent e) {
  TestTray.this.setVisible(false);
  }
 });
 this.setVisible(true);
 openItem.addActionListener(new ActionListener(){
	 public void actionPerformed(ActionEvent e){//���ļ�
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
//				 System.out.println ("����δ���򿪣�"); 
//				 } 
		 
		 String strcmd = "cmd /c start /b   "+fileDirectory+fileName;  
		 run_cmd(strcmd);  
	 }
 });
 
 
 }
 
 /**
 * ����ϵͳ����
 */
 private void systemTray() {
	 
	 
	 
	 //����ϵͳ
 if (SystemTray.isSupported()) { // �ж�ϵͳ�Ƿ�֧�����̹���.
  // ���������һ������˵�
  PopupMenu popupMenu = new PopupMenu();
   
  //���������˵��е��˳���
  MenuItem itemExit = new MenuItem("�˳�ϵͳ");
  itemExit.addActionListener(new ActionListener() {
   
   public void actionPerformed(ActionEvent e) {
    System.exit(0);
   }
   });
  popupMenu.add(itemExit);
   
  //��������ͼ��
  ImageIcon icon = new ImageIcon("img/icon.png"); // ����ͼƬ����
  TrayIcon trayIcon = new TrayIcon(icon.getImage(), "�Ҽ�����˳�����",popupMenu);
  trayIcon.addActionListener(new ActionListener() {
 
  public void actionPerformed(ActionEvent e) {
   TestTray.this.setVisible(true);
  }
  });
   
  //������ͼ����ӵ�ϵͳ����
  //������Ե���ر�֮���ٷŵ��������棬�ڴ��Ǵ򿪳���ֱ����ʾ����ͼ����
  try {
  SystemTray.getSystemTray().add(trayIcon);
  } catch (AWTException e1) {
  e1.printStackTrace();
  }
 }
 }
 
 public void run_cmd(String strcmd) {
//
      Runtime rt = Runtime.getRuntime(); //Runtime.getRuntime()���ص�ǰӦ�ó����Runtime����
      Process ps = null;  //Process���Կ��Ƹ��ӽ��̵�ִ�л��ȡ���ӽ��̵���Ϣ��
      try {
          ps = rt.exec(strcmd);   //�ö����exec()����ָʾJava���������һ���ӽ���ִ��ָ���Ŀ�ִ�г��򣬲���������ӽ��̶�Ӧ��Process����ʵ����
          ps.waitFor();  //�ȴ��ӽ������������ִ�С�
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
     

      int i = ps.exitValue();  //����ִ����ϵķ���ֵ
      if (i == 0) {
//          System.out.println("ִ�����.");
          textArea.append("ִ�����.");
      } else {
//          System.out.println("ִ��ʧ��."); 
          textArea.append("ִ��ʧ��.");
      }

      ps.destroy();  //�����ӽ���
      ps = null;   
  }
 
 public static void main(String[] args) {
 new TestTray();
 }
 
}
