package Topic;


import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.*;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;

import java.awt.*;   
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;

 


public class MySubscriber extends JFrame{

	
	
	 public int meassage_num=0,unmsg_num=0;
	 public String name="";
	 
	    JTextField jTextField ;//定义文本框组件
	    JPasswordField jPasswordField;//定义密码框组件
	    JLabel jLabel1,jLabel2;
	    JPanel jp1,jp2,jp3;
	    Button jb1,jb2,jb3;//创建按钮
	    boolean status=true;
	    //status为true时，表示该消息不是由自己发出，false代表改消息为自己发出
	 
	    public MySubscriber(final String name){
	        jTextField = new JTextField(12);
	        jPasswordField = new JPasswordField(13);
	        jLabel1 = new JLabel("用户名");
	        jLabel2 = new JLabel("密码");
	        jb1 = new Button("确认");
	        jb2 = new Button("取消");
	        jb3 = new Button("发送消息");
	        jb3.addActionListener(new ActionListener() {//发送消息
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                
	            	meassage_num++;//已经发送消息条数
	            	if(meassage_num<=100){//100条消息以内可发
	            	status=false;//标志该消息为自己发送
	                try {
	            	ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

	        		Connection connection = factory.createConnection();

	        		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        		Destination dest = session.createTopic("Ericsson");//将消息发送至topic上
	        		MessageProducer producer = session.createProducer(dest);

	        		
	        			TextMessage msg = session.createTextMessage();
	        			msg.setText("MSG_from: "+name + "  count: "+meassage_num);//来着No.x的客户端发送的第X条消息
	        			producer.send(msg);

	        			System.out.println("Message sent: " + msg.getText());

	        		
	        		//producer.close();
	        		//session.close();
	        		//connection.close();

	        			} catch (JMSException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
	      
	            }else{//超过一百条，消息设为无效
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
	    	//JMX监控
            JMXServiceURL url;
			url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url);  
	    	MBeanServerConnection conn = jmxc.getMBeanServerConnection();  
	    	ObjectName activeMQ = new ObjectName("org.apache.activemq:BrokerName=localhost,Type=Broker");  
	    	ObjectName mbeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost");
	    	BrokerViewMBean mbean = MBeanServerInvocationHandler.newProxyInstance(conn, mbeanName, BrokerViewMBean.class, true);
            for (ObjectName topicName : mbean.getTopics()) {
            	//寻找topic名为Ericsson
            TopicViewMBean topicMbean = (TopicViewMBean) MBeanServerInvocationHandler.newProxyInstance(conn, topicName, TopicViewMBean.class,true);
            	
	            if(topicMbean.getName().equals("Ericsson")){
	            	//System.out.println(topicMbean.getConsumerCount());
	            	return topicMbean.getConsumerCount();//获取消费者数（客户端）
	            }
	       
          }
	    	return 0;
	    }
	   
	
	    public static void main(String[] args) throws Exception
	    	 {
	    	
	    	long Client_num=MySubscriber.getConsumerCount()+1;//获取当前的客户端数目
	    	final MySubscriber MS=new MySubscriber("No."+Client_num);
		    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection;
		try {
			connection = factory.createConnection();
		
		    connection.start();

		    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		    Destination topic = session.createTopic("Ericsson");//订阅Topic
		    MessageConsumer consumer = session.createConsumer(topic);

		    consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message msg) {//等待消息
				TextMessage txtMsg = (TextMessage) msg;
				try {
					if(MS.status){//不是自己发出的消息
					
					System.out.println("Message received: " + txtMsg.getText());
					}else{
						MS.status=true;//恢复状态
					}
				} catch (JMSException e) {
					//e.printStackTrace();
				}
			}
		});

		System.out.println("Waiting for messages... Type 'exit' + <Enter> to exit.");//关闭客户端
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

