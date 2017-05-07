package cn.lfy.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class XmlUtils {

	public static byte[] toXmlBytes(Object object) {
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();  
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(object, os);
			return os.toByteArray();
		} catch (JAXBException e) {
			System.err.println("toXmlBytes error.");
		}
		return new byte[0];
	}
	
	public static String toXmlString(Object object) {
		byte[] bytes = toXmlBytes(object);
		if(bytes.length > 0) {
			try {
				String content = new String(bytes, "UTF-8");
				return content;
			} catch (UnsupportedEncodingException e) {
				System.err.println("toXmlString error.");
			}
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String content, Class<T> cls) {
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(cls);
			StringReader reader = new StringReader(content.trim());
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			T obj = (T)unmarshaller.unmarshal(reader);
			return obj;
		} catch (JAXBException e) {
			System.err.println("toObject error.");
			e.printStackTrace();
		}
		return null;
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)  
	@XmlRootElement(name = "kedou")  
	@XmlType(propOrder = {})
	public static class ShunWangAuth {

		@XmlElement(name = "Result")
		private Result result;
		
		public Result getResult() {
			return result;
		}

		public void setResult(Result result) {
			this.result = result;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(propOrder = { "time", "items" }) 
		public static class Result {
			
			@XmlAttribute(name = "msgId")
			private String msgId;
			@XmlAttribute(name = "msg")
			private String msg;
			@XmlAttribute(name = "returnSign")
			private String returnSign;
			
			@XmlElement
			private String time;
			@XmlElement
			private String items;
			
			public String getMsgId() {
				return msgId;
			}
			public void setMsgId(String msgId) {
				this.msgId = msgId;
			}
			public String getMsg() {
				return msg;
			}
			public void setMsg(String msg) {
				this.msg = msg;
			}
			public String getReturnSign() {
				return returnSign;
			}
			public void setReturnSign(String returnSign) {
				this.returnSign = returnSign;
			}
			public String getTime() {
				return time;
			}
			public void setTime(String time) {
				this.time = time;
			}
			public String getItems() {
				return items;
			}
			public void setItems(String items) {
				this.items = items;
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<kedou>" +
							"<Result returnSign=\"A3D410473E44954EA5A2177277E01BC3\" msgId=\"0\" msg=\"操作成功\">" +
							"<time>20161031155724</time>" +
							"<items></items>" +
							"</Result>" +
						"</kedou>";
		ShunWangAuth shunWangAuth = XmlUtils.toObject(content, ShunWangAuth.class);
		System.out.println(shunWangAuth.getResult().getReturnSign());
	}
	
}
