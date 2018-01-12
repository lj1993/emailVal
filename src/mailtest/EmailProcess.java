package mailtest;

public class EmailProcess {
	public String ProcessLink;//激活链接
	public static final int TIMELIMIT = 1000*60*60*48; //激活邮件过期时间48小时
	public String ActivateCode;
	
	/** 
     * 设置激活链接 
     * @param id 用户id
     * @return 链接失效时间 
     */  
	public long setLink(String id){
		//当前时间戳
        Long curTime = System.currentTimeMillis();
        //激活的有效时间
        Long activateTime = curTime+TIMELIMIT;
        this.ActivateCode=MD5util.encode2hex(id+activateTime);
		StringBuffer sb=new StringBuffer("尊敬的用户您好：<br>");
		sb.append("欢迎注册图灵约课网<br>");
		sb.append("您的注册id为："+id+"<br>");
        sb.append("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
		//url地址
		sb.append("<a href=\"url?action=activate&ID=");  
        //用户id
		sb.append(id);   
        sb.append("&validateCode="); 
        //激活码
        sb.append(ActivateCode);  
        sb.append("\">url?action=activate&ID=");   
        sb.append(id);  
        sb.append("&validateCode=");  
        sb.append(ActivateCode);  
        sb.append("</a>");
        this.ProcessLink=sb.toString();
        return activateTime;
	}
	
	/** 
     * 发送激活邮件 
     * @param receiveAddress 验证邮箱
     * @return 
     */  
	public void sendLink(String receiveAddress){
		Mailsend.send(receiveAddress, "账号激活邮件", this.ProcessLink);
	}

}
