package mailtest;

public class EmailProcess {
	public String ProcessLink;//��������
	public static final int TIMELIMIT = 1000*60*60*48; //�����ʼ�����ʱ��48Сʱ
	public String ActivateCode;
	
	/** 
     * ���ü������� 
     * @param id �û�id
     * @return ����ʧЧʱ�� 
     */  
	public long setLink(String id){
		//��ǰʱ���
        Long curTime = System.currentTimeMillis();
        //�������Чʱ��
        Long activateTime = curTime+TIMELIMIT;
        this.ActivateCode=MD5util.encode2hex(id+activateTime);
		StringBuffer sb=new StringBuffer("�𾴵��û����ã�<br>");
		sb.append("��ӭע��ͼ��Լ����<br>");
		sb.append("����ע��idΪ��"+id+"<br>");
        sb.append("����������Ӽ����˺ţ�48Сʱ��Ч����������ע���˺ţ�����ֻ��ʹ��һ�Σ��뾡�켤�</br>");
		//url��ַ
		sb.append("<a href=\"url?action=activate&ID=");  
        //�û�id
		sb.append(id);   
        sb.append("&validateCode="); 
        //������
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
     * ���ͼ����ʼ� 
     * @param receiveAddress ��֤����
     * @return 
     */  
	public void sendLink(String receiveAddress){
		Mailsend.send(receiveAddress, "�˺ż����ʼ�", this.ProcessLink);
	}

}
