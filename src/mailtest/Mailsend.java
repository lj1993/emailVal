package mailtest;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class Mailsend {
	public static String myEmailAccount = "tlac-admin@tlac.xin";//邮箱地址
    public static String myEmailPassword = "TLac170701";//密码
    public static String myEmailSMTPHost = "smtpdm.aliyun.com";//邮箱服务器
    //public static String receiveMailAccount = "";//收件人邮箱
    
    /**
    * show 发送邮件
    * <p>show 固定邮箱发送邮件<br>
    * show 
    * @param receiveAddress 收件人邮箱地址，title 邮件主题，text 邮件正文
    * @return 没有返回值
    */
	public static void send(String receiveAddress,String title,String text){
		Properties prop=new Properties();//定义连接邮件服务器的参数
		prop.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
	    prop.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
	    prop.setProperty("mail.smtp.auth", "true");    // 需要请求认证
	 // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        */
	    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.port", "465");
     // 发件人的账号
        prop.put("mail.user", "tlac-admin");
        // 访问SMTP服务时需要提供的密码
        prop.put("mail.password", "TLac170701");
     // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = prop.getProperty("mail.user");
                String password = prop.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
		Session sess=Session.getDefaultInstance(prop);//创建会话
		sess.setDebug(true);
		MimeMessage msg;
		try {
			msg = Mailsend.createMimeMessage(sess, receiveAddress,title,text);//创建邮件
			Transport tran=sess.getTransport();//获取传输对象
			tran.connect(myEmailAccount, myEmailPassword);//连接服务器
			tran.sendMessage(msg, msg.getAllRecipients());//发送邮件
			tran.close();//关闭连接
		} catch (Exception e) {
			
		}
		finally{
		}
	}
	
	public static MimeMessage createMimeMessage(Session session, String receiveMail,String stitel,String stext) throws Exception {
		MimeMessage msg=new MimeMessage(session);
		msg.setFrom(new InternetAddress(myEmailAccount));//设置发件人
		msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail));//设置收件人
		//msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress());//增加收件人
		//msg.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress());//抄送
		//msg.setRecipient(MimeMessage.RecipientType.BCC,new InternetAddress());//密送
		msg.setSubject(stitel);//设置主题
		msg.setContent(stext,"text/html;charset=UTF-8");//设置正文
		msg.setSentDate(new Date());//设置日期
		msg.saveChanges();//保存设置
		return msg;
	}
}
