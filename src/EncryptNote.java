import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import javax.swing.*;
public class EncryptNote{
	
	/********����һЩȫ�ֱ���***********/
	public static String Key;		//����ȫ�ֱ���Key�������洢��Կ
	public static int KeyNumber=0;			//����ȫ�ֱ���KeyNumber�������洢��Կ��ժҪֵ
	public static String FileName;			//����ȫ�ֱ���FileName�������洢�ռ���
	public static boolean IsOpenFile=false;		//�����ж��Լ������openFile()�����Ƿ񱻵��ã��˴�������һ��BUG��
	
	/***************main������*****************/
	public static void main(String[] args) {
		
		/***********ͼ���û��������******/
		JFrame f=new JFrame();				
		f.setBounds(100,100,500,700);				//�趨����λ�ü���С
		f.setLayout(null);							//�趨Ϊ�ղ���
		f.setTitle("�����ռǱ�");						//�趨��������
		JButton b1, b2,b3;							//����������ť���ֱ��������ܣ����ܺ�����
		final JTextArea text=new JTextArea(2,1);	//����һ���ı��������������û�������ַ�
		JScrollPane p = new JScrollPane(text);		//Ϊ�ı�����ӹ�����				
			/*Ϊ��ť�ֱ�����*/
		b1=new JButton("���ܶ�ȡ");
		b2=new JButton("���ܱ���");
		b3=new JButton("����ı�");
		
		/*�ڴ�������Ӱ�ť���ı��򣬲��ֱ��趨���ڴ����е�λ��*/
		f.getContentPane().add(b1);
		b1.setBounds(3,3,100,20);
		f.getContentPane().add(b2);
		b2.setBounds(106,3,100,20);
		f.getContentPane().add(b3);
		b3.setBounds(209,3,100,20);
		f.getContentPane().add(p);
		p.setBounds(3,28,494,670);
		
		f.setVisible(true);										//���ô��ڿɼ�
		f.setResizable(false);									//���ò��ɵ������ڴ�С	
		f.setLocationRelativeTo(null);                 	   		//�ô��������ʾ
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		
		/***************�����¼�����Ӧ***************/
		
		  /**Ϊb1�����ܶ�ȡ�ļ�����ť��ӵ����¼���Ӧ**/
		b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String fileName;		//�����ֲ�����fileName���������û�������ĵ�����
            	fileName = JOptionPane.showInputDialog("������Ҫ�Ķ��ռǵı���ʱ�䣨��2010-1-1����"); 
            	if(fileName!=null)		//ȷ���û��������ļ������˴�������һ��BUG��
            	{
            	/*�ж��û�������ĵ����Ƿ���ڣ�*ע������searchFaile(str)������D��Ŀ¼�²����ļ���Ϊstr��txt�ĵ�����*/
            	while(searchFile(fileName)==false)		//��������ڸ��ռ��ļ�
				{
            		JOptionPane.showMessageDialog(null, "�����ڸ��ռ��ļ�", "����", JOptionPane.ERROR_MESSAGE); 		//��ʾ�û������ڸ��ĵ�
            		fileName = JOptionPane.showInputDialog("������Ҫ�Ķ��ռǵı���ʱ�䣨��2010-1-1����"); 				//��ʾ�û����������ĵ�����
				}
            	//�����Ϸ���whileѭ���󣨼���D�����ҵ����û��ƶ����ļ�����ʾ�û�������֮��Ӧ����Կ
            	Key = JOptionPane.showInputDialog("���������ƪ�ռǶ�Ӧ����Կ��"); 				
            	if(Key!=null)
            	{
            	/*�����û�������Կ��ժҪ����ԿժҪΪ��Կ�����ַ���Ascii�����ֵ��*/
            	for(int i=0;i<Key.length();i++)				
				{
					int s=Key.charAt(i);
					KeyNumber=KeyNumber+s;
				}
            	
				/*�ж��û��������Կ����ԿժҪ�Ƿ����ĵ��д洢��һ�£�*ע:����keyTest��key��keynumber�������ж���key��keynumber�Ƿ����û���ǰ�������ĵ��ֵ���Կ����ԿժҪһ�£�*/
				while(keyTest(Key,KeyNumber)==false)
				{
					KeyNumber=0;		//��ȫ�ֱ�������ԿժҪ����Ϊ0
					JOptionPane.showMessageDialog(null, "��Կ��ƥ��", "����", JOptionPane.ERROR_MESSAGE); 		//��ʾ�û���Կ��ƥ��
					Key = JOptionPane.showInputDialog("���������ƪ�ռǶ�Ӧ����Կ��");								//��ʾ�û�����������Կ
					
					/*�����û�������Կ��ժҪ*/
					for(int i=0;i<Key.length();i++)
					{
						int s=Key.charAt(i);
						KeyNumber=KeyNumber+s;
					}
				}
				text.setText("");			//����ı�����	
				String readStr;
				readStr=read();				//��ȡĿ���ĵ��ֵ��ַ��������ܲ������ַ���readStr��
				text.setText(readStr);		//��readStr�ֵ�������ʾ��ͼ�ν�����ı�������
            	}
            }
            }
        });
		
		 /**Ϊb2�����ܱ����ļ�����ť��ӵ����¼���Ӧ**/
		b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String s=text.getText();		//��ȡ�ı������е����ݲ���ֵ���ַ���s
            	openFile();						//����openFile�����������˺���������D�̸�Ŀ¼�´�����Ϊ��ǰ���ڵ��ı��ĵ��������û�����ĵ���Կ����ԿժҪ�������ĵ���ǰ���С�
            	if(IsOpenFile==true)			//�ж�opeFile()�����Ƿ񱻵��ã��˴�������һ��BUG
            	{
            	write(s);						//��s����д��ոմ������ı��ĵ��У�write����Ϊ������д����
            	JOptionPane.showMessageDialog(null, "�����ļ��ѳɹ������ڣ�"+FileName, "��ʾ", JOptionPane.INFORMATION_MESSAGE);		//��ʾ�û��ĵ����ܱ���ɹ�������ʾ�û��ļ������·�����ļ���
            	text.setText("");				//���ͼ���û������е��ı�����
            	KeyNumber=0;					//��ȫ�ֱ�������ԿժҪ����Ϊ0
            	IsOpenFile=false;				//���ж�openFile()�����Ƿ񱻵��õĲ����ж�ֵ���ó�false
            	}
            }
        });
		
		 /**Ϊb3�����ܱ����ļ�����ť��ӵ����¼���Ӧ**/
		b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	text.setText("");		//���ͼ���û������е��ı�����
            }
        });
	}
	
	/******************����Ϊ�����һЩ���ܺ���***********************/
	
		/*����openFile������������Ŀ��Ŀ¼�´����ռ��ı��ĵ�,���洢��Կ*/
		public static void openFile()
		{
			int year,month,day;		//���ڴ��浱ǰ������
			
			/********��ȡ��ǰ���ڣ������գ���Ϊ�ռ���********/
			Calendar cal=Calendar.getInstance();
			year=cal.get(Calendar.YEAR);    
			month=cal.get(Calendar.MONTH)+1;    //�����·ݴ�0��ʼ���ʼ�1������ȷ��ʾ��ǰ�·�
			day=cal.get(Calendar.DATE);  
			FileName="d:\\"+year+"-"+month+"-"+day+".txt";		//��Ϊ�ռ��ĵ���
			
			/*********��ȡ�û��Զ������Կ**********/
			String key;			//������ʱ�洢�û��������Կ
			int keyNumber=0;		//������ʱ�洢��Կ��ժҪֵ
			boolean keyLegal=false;	//�ж���Կ��ʽ�Ƿ�Ϸ�
			
			key = JOptionPane.showInputDialog("���������ڼ��ܵ���Կ����5��Ӣ����ĸ��ɣ���"); 		//��ʾ�û�������Կ
			if(key!=null)
			{
			/**�ж��û��������Կ�Ƿ�Ϸ�**/
			//ȷ����Կ����Ϊ5
			if(key.length()==5)
			{
				int i=0;
				for(i=0;i<key.length();i++)
				{
					//ȷ����Կ��ֻ������ĸ
					if(!(('A'<=(key.charAt(i))&&(key.charAt(i))<='Z')||('a'<=(key.charAt(i))&&(key.charAt(i))<='z')))
					{
						break;
					}
				}
				if(i==5)
				{
					keyLegal=true;
				}
			}
			
			/*����Կ��ʽ���Ϸ�����ʾ�û���������*/
			while(keyLegal==false)
			{
				JOptionPane.showMessageDialog(null, "�������Կ���Ϸ�", "����", JOptionPane.ERROR_MESSAGE); 		//��ʾ�û��������
				key = JOptionPane.showInputDialog("���������ڼ��ܵ���Կ����5��Ӣ����ĸ��ɣ���"); 					//��ʾ�û���������
				
				/*�ж���Կ�Ƿ�Ϸ�*/
				if(key.length()==5)	//ȷ��������ַ�����Ϊ5
				{
					int i=0;
					for(i=0;i<key.length();i++)
					{
						//ȷ����Կ��ֻ����ĸ
						if(!(('A'<=(key.charAt(i))&&(key.charAt(i))<='Z')||('a'<=(key.charAt(i))&&(key.charAt(i))<='z')))	//ȷ��������ַ�������ĸ
						{
							break;
						}
					}
					if(i==5)
					{
						keyLegal=true;
					}
				}
			}
			
			//����Կ�ɾֲ���������ȫ�ֱ�����
			Key=key;	
			
			//������Կ��ժҪֵ������ֵ��keyNumber
			for(int i=0;i<key.length();i++)
			{
				int s=key.charAt(i);
				keyNumber=keyNumber+s;
			}
			//����ԿժҪ�ɾֲ��������ݸ�ȫ�ֱ���
			KeyNumber=keyNumber;
			
			/**��Ŀ��Ŀ¼�´����Ե�ǰ����Ϊ�ļ������ռ��ĵ�**/
			try
			{
				File f=new File(FileName);
				FileWriter f_w=new FileWriter(f,false);
				BufferedWriter bf_w=new BufferedWriter(f_w);
				//�Ƚ���Կ���ܲ������ռ��ĵ��ĵ�һ��
				bf_w.write(encrypt(Key));
				bf_w.newLine();
				//�ٽ���Կ��ժҪ���ܲ������ռ��ĵ��ĵڶ���
				bf_w.write(encrypt(Integer.toString(keyNumber)));
				bf_w.newLine();
				bf_w.flush();
				bf_w.close();
				f_w.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			//openFile�������ɹ����ã�IsOpenFile��ֵ��Ϊ��true
			IsOpenFile=true;
			}
		}
		
		//����write���������ڽ��ַ���str����д���ļ���
			public static void write(String str)
			{
				//���ַ���str���ܣ���ֵ��s
				String s=encrypt(str);
				try{
				File f=new File(FileName);
				FileWriter f_w=new FileWriter(f,true);
				BufferedWriter bf_w=new BufferedWriter(f_w);
				//��Ŀ���ĵ���д����ܹ����ַ���s
				bf_w.write(s);
				bf_w.newLine();
				bf_w.flush();
				bf_w.close();
				f_w.close();
				}catch(Exception ex){
				ex.printStackTrace();
				}
			}
		
		//����read���������ڽ��ռ��ĵ��еļ������ֽ��ܶ���
		public static String read()
		{
			String s,s1,s2;
			StringBuffer readStr=new StringBuffer();
			try{
				File f=new File(FileName);
				FileReader f_r=new FileReader(f);
				BufferedReader bf_r=new BufferedReader(f_r);
				//��Ŀ���ĵ��е��ַ������ܲ�����
				int i=1;
				while((s1=bf_r.readLine())!=null)
				{ 
					//�����ַ���
					s2=decrypt(s1);
					//����ַ���
					readStr.append(s2);
					//����Կ����ԿժҪ������Լ��ϻ��з�
					if(i<=2)
					{
						readStr.append("\r\n");
						i++;
					}			
				}
				bf_r.close();
				f_r.close();
				}catch(Exception ex){
				System.out.println("error!");
				}
			s=readStr.toString();
			return s;
		}
		
		//����searchFile���������ڲ���Ŀ��Ŀ¼���Ƿ�����Ϊstr���ı��ĵ�
		public static boolean searchFile(String str)
		{	
			String fileName;		//�洢�ĵ�����
			boolean fileExist=true;		//�ж��ĵ��Ƿ����
			fileName="d:\\"+str+".txt";
			try{
				File f=new File(fileName);
				FileReader f_r=new FileReader(f);
				BufferedReader bf_r=new BufferedReader(f_r);
				bf_r.close();
				f_r.close();
				//�����ڴ��ļ������ļ�������ȫ�ֱ���FileName��
				FileName=fileName;
				}catch(Exception ex){
				fileExist=false;
				}
			return fileExist;
		}
		
		//����encrypt����������������Կ��Դ�ַ�������λ��
		public static String encrypt(String str)
		{
			String s,s0,key;
			int a,m;
			char key0;
			StringBuffer keyBuf=new StringBuffer();
			StringBuffer enStr=new StringBuffer();
			//���û��������Կ���򱣴���key��
			for(int i=4;i>=0;i--)
			{
				key0=Key.charAt(i);
				keyBuf.append(key0);
			}
			key=keyBuf.toString();
			//����
			for(int i=0;i<str.length();i++)
			{
				a=i%5;//���ڿ���ѭ����Կ�е�Ԫ��
				//�ַ�����ÿ���ַ���ascii�������Կ������ַ���ascii�����
				m=(str.charAt(i)+key.charAt(a));
				//Ϊ�˱�������ÿ���ַ���ascii��λ����ÿ����һ���ַ�����ӿո�
				s0=m+" ";
				enStr.append(s0);
			}
			s=enStr.toString();
			return s;
		}
		
		//����decrypt�����������������Կ�Լ����ַ������н���
		public static String decrypt(String str)
		{
			String s,key;
			int a,m,j=0;
			char c,key0;
			StringBuffer keyBuf=new StringBuffer();
			StringBuffer s0=new StringBuffer();
			StringBuffer deStr=new StringBuffer();
			//���û��������Կ���򱣴���key��
			for(int i=4;i>=0;i--)
			{
				key0=Key.charAt(i);
				keyBuf.append(key0);
			}
			key=keyBuf.toString();
			
			//����
			for(int i=0;i<str.length();i++)
			{
				//���������ļ���Ascii�루�ո�Ϊ������
				if(str.charAt(i)!=' ')
				{
					s0.append(str.charAt(i));
				}
				//���ܸն����ļ���Ascii��
				else
				{
					a=j%5;
					m=Integer.parseInt(s0.toString());
					c=(char)(m-key.charAt(a));
					deStr.append(c);
					s0=new StringBuffer();	//����s0
					j++;		//j������Կѭ����i���Ƽ����ַ����������ȡ��
				}
			}
			s=deStr.toString();
			return s;
		}
		
		//����keyTest����,���ں˶���Կ
		public static boolean keyTest(String key,int keyNumber)
		{
			boolean isMatched=false;
			String s1,s2;
			int m=0;
			//���û��������ڶ�Ӧ���ĵ�
			try{
				File f=new File(FileName);
				FileReader f_r=new FileReader(f);
				BufferedReader bf_r=new BufferedReader(f_r);
				//��Ŀ���ĵ���ĵ�һ�У����ܹ�����Կ���ַ���
				s1=bf_r.readLine();
				//��Ŀ���ĵ���ڶ��У����ܹ�����ԿժҪ���ַ���
				s2=bf_r.readLine();
				//�Ե�һ���ַ������ܹ�����Կ��������
				s1=decrypt(s1);
				//�Եڶ����ַ��������ܹ�����ԿժҪ������
				s2=decrypt(s2);
				//����ԿժҪת����int��
				m=Integer.parseInt(s2);
				//�жϽ��ܳ�������Կ��ժҪ�Ƿ����û������һ��
				if(s1.equals(Key)&&(m==KeyNumber))
				{
					isMatched=true;
				}
				bf_r.close();
				f_r.close();
				}catch(Exception ex){
				System.out.println("error!");
				}
			return isMatched;
		}
	}

