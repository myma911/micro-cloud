package cn.aaron911.micro.ai.process;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.aaron911.micro.common.util.FileUtil;

/**
 * 把分词文本内容训练成机器符号
 * 
 */
@Service
public class Word2VecService {

	// 模型分词路径
	@Value("${ai.wordLib}")
	private String wordLib;

	// 爬虫分词后的数据路径
	@Value("${ai.dataPath}")
	private String dataPath;

	// 模型训练保存的路径
	@Value("${ai.vecModel}")
	private String vecModel;

	/**
	 * 合并
	 */
	public void mergeWord() {
		List<String> fileNames = FileUtil.getFiles(dataPath);
		try {
			FileUtil.merge(wordLib, fileNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据分词模型生成 词向量模型
	 * 
	 * minWordFrequency是一个词在语料中必须出现的最少次数。本例中出现不到5次的词都不予学习。词语必须在多种上下文中出现，才能让模型学习到有用的特征。对于规模很大的语料库，理应提高出现次数的下限。
	 * iterations是网络在处理一批数据时允许更新系数的次数。迭代次数太少，网络可能来不及学习所有能学到的信息；迭代次数太多则会导致网络定型时间变长。
	 * layerSize指定词向量中的特征数量，与特征空间的维度数量相等。以500个特征值表示的词会成为一个500维空间中的点。
	 * windowSize：表示当前词与预测词在一个句子中的最大距离是多少
	 * seed：用于随机数发生器。与初始化词向量有关
	 * iterate: 告知网络当前定型的是哪一批数据集。
	 * vec.fit() 让已配置好的网络开始定型。
	 * 
	 */
	public boolean build() {
		boolean flag = false;
		try {
			// 加载数爬虫分词数据集
			SentenceIterator iter = new LineSentenceIterator(new File(wordLib));
			Word2Vec vec = new Word2Vec.Builder().minWordFrequency(5).iterations(1).layerSize(100).seed(42)
					.windowSize(5).iterate(iter).build();
			vec.fit();
			// 保存模型之前先删除
			new File(vecModel).delete();// 删除
			WordVectorSerializer.writeWordVectors(vec, vecModel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}