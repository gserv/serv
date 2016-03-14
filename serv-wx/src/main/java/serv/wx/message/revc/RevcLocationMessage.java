package serv.wx.message.revc;

import org.dom4j.Element;

/**
 * 接收地理位置消息类型
 * 
 * @author shiying
 *
 */
public class RevcLocationMessage extends AbstractRevcMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6562886914795600022L;
	
	
	/**
	 * 地理位置维度
	 */
	private String Location_X;
	
	/**
	 * 地理位置经度
	 */
	private String Location_Y;
	
	/**
	 * 地图缩放大小
	 */
	private Integer Scale;
	
	/**
	 * 地理位置信息
	 */
	private String Label;

	public RevcLocationMessage() {
		super();
	}

	public RevcLocationMessage(String location_X, String location_Y,
			Integer scale, String label) {
		super();
		Location_X = location_X;
		Location_Y = location_Y;
		Scale = scale;
		Label = label;
	}

	@Override
	protected void parseProperties(Element xmlElement) {
		this.setLocation_X(xmlElement.elementText("Location_X"));
		this.setLocation_Y(xmlElement.elementText("Location_Y"));
		this.setScale(Integer.parseInt(xmlElement.elementText("Scale")));
		this.setLabel(xmlElement.elementText("Label"));
		super.parseProperties(xmlElement);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getLocation_X() != null) xmlElement.addElement("Location_X").setText(this.getLocation_X().toString());
		if (this.getLocation_Y() != null) xmlElement.addElement("Location_Y").setText(this.getLocation_Y().toString());
		if (this.getScale() != null) xmlElement.addElement("Scale").setText(this.getScale().toString());
		if (this.getLabel() != null) xmlElement.addElement("Label").setText(this.getLabel().toString());
		super.setProperties(xmlElement);
	}

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public Integer getScale() {
		return Scale;
	}

	public void setScale(Integer scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}
	
	
	
}
