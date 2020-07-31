package cn.aaron911.micro.common;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import java.io.IOException;
import java.io.StringReader;

/**
 * 测试类
 */
public class Test {
	
	/**
	 * 分词测试
	 * 
	 */
    public static void main(String[] args) throws IOException {
        String text="基于java语言开发的轻量级的中文分词工具包";
        StringReader sr=new StringReader(text);
        
        // IKSegmenter 的第一个构造参数为StringReader类型 。 StringReader是装饰Reader的类，其用法是读取一个String字符串
        // IKSegmenter 的第二个构造参数userSmart 为切分粒度 true表示最大切分 false表示最细切分
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        while((lex=ik.next())!=null){
            System.out.print(lex.getLexemeText()+" ");
        }
    }
}