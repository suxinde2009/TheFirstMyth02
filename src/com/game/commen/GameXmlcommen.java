package com.game.commen;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.game.base.PubSet;
import com.game.data.StroyTipData;

public class GameXmlcommen {

	String Stroy = "Stroy";
	String NAME = "name";
	String StroyInfo = "StroyInfo";
	String HeadUrl = "HeadUrl";
	String Todo = "Todo";
	String chapter = "chapter";
	String progress = "progress";
	// 获取全部河流数据
	/**
	 * 参数fileName：为xml文档路径
	 */
	public List<StroyTipData> getRiversFromXml(String fileName) {
		List<StroyTipData> rivers = new ArrayList<StroyTipData>();
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document = null;
		InputStream inputStream = null;
		// 首先找到xml文件
		factory = DocumentBuilderFactory.newInstance();
		try {
			// 找到xml，并加载文档
			builder = factory.newDocumentBuilder();
			inputStream = PubSet.context.getResources().getAssets()
					.open(fileName);
			document = builder.parse(inputStream);
			// 找到根Element
			Element root = document.getDocumentElement();
			NodeList nodes = root.getElementsByTagName(Stroy);
			// 遍历根节点所有子节点,rivers 下所有river
			StroyTipData stroydata = null;
			for (int i = 0; i < nodes.getLength(); i++) {
				stroydata = new StroyTipData();
				// 获取Stroys元素节点
				Element riverElement = (Element) (nodes.item(i));
				// 获取Stroys中name属性值
				stroydata.setStroyName(riverElement.getAttribute(NAME));
				stroydata.setChapter(Integer.parseInt(riverElement
						.getAttribute(chapter)));
				stroydata.setProgress(Integer.parseInt(riverElement
						.getAttribute(progress)));
				// 获取river下introduction标签
				Element introduction = (Element) riverElement
						.getElementsByTagName(StroyInfo).item(0);
				stroydata.setStroyInfo(introduction.getFirstChild()
						.getNodeValue());
				Element el_headurl = (Element) riverElement.getElementsByTagName(
						HeadUrl).item(0);
				stroydata.setHeadUrl(el_headurl.getFirstChild().getNodeValue());
				Element el_Todo = (Element) riverElement.getElementsByTagName(
						Todo).item(0);
				stroydata.setTodoInfo(el_Todo.getFirstChild().getNodeValue());
				rivers.add(stroydata);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rivers;
	}
}