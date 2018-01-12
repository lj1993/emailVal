package mailtest;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class Mailsend {
	public static String myEmailAccount = "tlac-admin@tlac.xin";//�����ַ
    public static String myEmailPassword = "TLac170701";//����
    public static String myEmailSMTPHost = "smtpdm.aliyun.com";//���������
    //public static String receiveMailAccount = "";//�ռ�������
    
    /**
    * show �����ʼ�
    * <p>show �̶����䷢���ʼ�<br>
    * show 
    * @param receiveAddress �ռ��������ַ��title �ʼ����⣬text �ʼ�����
    * @return û�з���ֵ
    */
	public static void send(String receiveAddress,String title,String text){
		Properties prop=new Properties();//���������ʼ��������Ĳ���
		prop.setProperty("mail.transport.protocol", "smtp");   // ʹ�õ�Э�飨JavaMail�淶Ҫ��
	    prop.setProperty("mail.smtp.host", myEmailSMTPHost);   // �����˵������ SMTP ��������ַ
	    prop.setProperty("mail.smtp.auth", "true");    // ��Ҫ������֤
	 // PS: ĳЩ���������Ҫ�� SMTP ������Ҫʹ�� SSL ��ȫ��֤ (Ϊ����߰�ȫ��, ����֧��SSL����, Ҳ�����Լ�����),
        //     ����޷������ʼ�������, ��ϸ�鿴����̨��ӡ�� log, ����������� ������ʧ��, Ҫ�� SSL ��ȫ���ӡ� �ȴ���,
        //     ������ /* ... */ ֮���ע�ʹ���, ���� SSL ��ȫ���ӡ�
        /*
        // SMTP �������Ķ˿� (�� SSL ���ӵĶ˿�һ��Ĭ��Ϊ 25, ���Բ����, ��������� SSL ����,
        //                  ��Ҫ��Ϊ��Ӧ����� SMTP �������Ķ˿�, ����ɲ鿴��Ӧ�������İ���,
        //                  QQ�����SMTP(SLL)�˿�Ϊ465��587, ������������ȥ�鿴)
        */
	    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.port", "465");
     // �����˵��˺�
        prop.put("mail.user", "tlac-admin");
        // ����SMTP����ʱ��Ҫ�ṩ������
        prop.put("mail.password", "TLac170701");
     // ������Ȩ��Ϣ�����ڽ���SMTP���������֤
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // �û���������
                String userName = prop.getProperty("mail.user");
                String password = prop.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
		Session sess=Session.getDefaultInstance(prop);//�����Ự
		sess.setDebug(true);
		MimeMessage msg;
		try {
			msg = Mailsend.createMimeMessage(sess, receiveAddress,title,text);//�����ʼ�
			Transport tran=sess.getTransport();//��ȡ�������
			tran.connect(myEmailAccount, myEmailPassword);//���ӷ�����
			tran.sendMessage(msg, msg.getAllRecipients());//�����ʼ�
			tran.close();//�ر�����
		} catch (Exception e) {
			
		}
		finally{
		}
	}
	
	public static MimeMessage createMimeMessage(Session session, String receiveMail,String stitel,String stext) throws Exception {
		MimeMessage msg=new MimeMessage(session);
		msg.setFrom(new InternetAddress(myEmailAccount));//���÷�����
		msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail));//�����ռ���
		//msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress());//�����ռ���
		//msg.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress());//����
		//msg.setRecipient(MimeMessage.RecipientType.BCC,new InternetAddress());//����
		msg.setSubject(stitel);//��������
		msg.setContent(stext,"text/html;charset=UTF-8");//��������
		msg.setSentDate(new Date());//��������
		msg.saveChanges();//��������
		return msg;
	}
}
