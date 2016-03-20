package Topic;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;

import javax.jms.*;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

 


public class MySubscriber extends JFrame{

	
	
	 public int meassage_num=0,unmsg_num=0;
	 public String name="";
	 
	    JTextField jTextField ;//�����ı������
	    JPasswordField jPasswordField;//������������
	    JLabel jLabel1,jLabel2;
	    JPanel jp1,jp2,jp3;
	    Button jb1,jb2,jb3;//������ť
	    boolean status=true;
	    //statusΪtrueʱ����ʾ����Ϣ�������Լ�������false������ϢΪ�Լ�����
	 
	    public MySubscriber(final String name){
	        jTextField = new JTextField(12);
	        jPasswordField = new JPasswordField(13);
	        jLabel1 = new JLabel("�û���");
	        jLabel2 = new JLabel("����");
	        jb1 = new Button("ȷ��");
	        jb2 = new Button("ȡ��");
	        jb3 = new Button("������Ϣ");
	        jb3.addActionListener(new ActionListener() {//������Ϣ
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                
	            	meassage_num++;//�Ѿ�������Ϣ����
	            	if(meassage_num<=100){//100����Ϣ���ڿɷ�
	            	status=false;//��־����ϢΪ�Լ�����
	                try {
	            	ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

	        		Connection connection = factory.createConnection();

	        		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        		Destination dest = session.createTopic("Ericsson");//����Ϣ������topic��
	        		MessageProducer producer = session.createProducer(dest);

	        		
	        			TextMessage msg = session.createTextMessage();
	        			msg.setText("MSG_from: "+name + "  count: "+meassage_num);//����No.x�Ŀͻ��˷��͵ĵ�X����Ϣ
	        			producer.send(msg);

	        			System.out.println("Message sent: " + msg.getText());

	        		
	        		//producer.close();
	        		//session.close();
	        		//connection.close();

	        			} catch (JMSException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
	      
	            }else{//����һ��������Ϣ��Ϊ��Ч
	            	unmsg_num++;
	            }
	            
	            }
	        });
	        	
	        
	        jp1 = new JPanel();
	        jp2 = new JPanel();
	        jp3 = new JPanel();
	        
	        
	        this.setLayout(new GridLayout(3,1));
	        
	        jp1.add(jLabel1); 
	        jp1.add(jTextField); 
	        
	        jp2.add(jLabel2);
	        jp2.add(jPasswordField);
	        
	        jp3.add(jb1);
	        jp3.add(jb2); 
	        jp3.add(jb3);
	        this.add(jp1);
	        this.add(jp2);
	        this.add(jp3); 
	        this.setSize(300, 200);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setVisible(true);
            this.setTitle(name);
	         
	    
	    }
	
	    public static long getConsumerCount() throws Exception{
	    	//JMX���
            JMXServiceURL url;
			url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url);  
	    	MBeanServerConnection conn = jmxc.getMBeanServerConnection();  
	    	ObjectName activeMQ = new ObjectName("org.apache.activemq:BrokerName=localhost,Type=Broker");  
	    	ObjectName mbeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost");
	    	BrokerViewMBean mbean = MBeanServerInvocationHandler.newProxyInstance(conn, mbeanName, BrokerViewMBean.class, true);
            for (ObjectName topicName : mbean.getTopics()) {
            	//Ѱ��topic��ΪEricsson
            TopicViewMBean topicMbean = (TopicViewMBean) MBeanServerInvocationHandler.newProxyInstance(conn, topicName, TopicViewMBean.class,true);
            	
	            if(topicMbean.getName().equals("Ericsson")){
	            	//System.out.println(topicMbean.getConsumerCount());
	            	return topicMbean.getConsumerCount();//��ȡ�������ͻ��ˣ�
	            }
	       
          }
	    	return 0;
	    }
	   
	
	    public static void main(String[] args) throws Exception
	    	 {
	    	
	    	long Client_num=MySubscriber.getConsumerCount()+1;//��ȡ��ǰ�Ŀͻ�����Ŀ
	    	final MySubscriber MS=new MySubscriber("No."+Client_num);
		    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection;
		try {
			connection = factory.createConnection();
		
		    connection.start();

		    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		    Destination topic = session.createTopic("Ericsson");//����Topic
		    MessageConsumer consumer = session.createConsumer(topic);

		    consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message msg) {//�ȴ���Ϣ
				TextMessage txtMsg = (TextMessage) msg;
				try {
					if(MS.status){//�����Լ���������Ϣ
					
					System.out.println("Message received: " + txtMsg.getText());
					}else{
						MS.status=true;//�ָ�״̬
					}
				} catch (JMSException e) {
					//e.printStackTrace();
				}
			}
		});

		System.out.println("Waiting for messages... Type 'exit' + <Enter> to exit.");//�رտͻ���
		Scanner s = new Scanner(System.in);
		s.next();
		s.close();

		consumer.setMessageListener(null);

		consumer.close();
		session.close();
		connection.close();

		System.out.println("Completed.");
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
	}
	 
}

