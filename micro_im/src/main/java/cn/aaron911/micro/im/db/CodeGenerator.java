package cn.aaron911.micro.im.db;

import com.baomidou.mybatisplus.generator.AutoGenerator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 *
 */
public class CodeGenerator {

    //项目路径
    private static String canonicalPath = "";
    //基本包名
    private static String basePackage = "cn.aaron911.micro.im.db";
    //作者
    private static String authorName = "Aaron";
    //要生成的表名
    private static String[] tables = {"user_account","user_department", "user_info", "user_message"};
    //table前缀
    private static String prefix = "";


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(authorName);
        //生成后打开文件夹
        gc.setOpen(false);
        // 开启 activeRecord 模式
        gc.setActiveRecord(true);
        // 是否覆盖文件
        gc.setFileOverride(true);
        //DO中日期类的类型, 缺省值为TIME_PACK
        gc.setDateType(DateType.ONLY_DATE);
        //XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.3.193:3307/qiqiim?useUnicode=true&useSSL=false&characterEncoding=utf8&&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        
        
//        dsc.setUrl("jdbc:postgresql://192.168.3.193:5432/hxzncrm16?charSet=utf-8");
//        dsc.setSchemaName("public");
//        dsc.setDriverName("org.postgresql.Driver");
//        dsc.setUsername("upsys");
//        dsc.setPassword("pg.upeach.com_ok");

        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig()
                // 自定义包路径
                .setParent(basePackage)
                // 设置Mapper XML包名，默认mapper.xml
                .setXml("mapper.xml")
                .setMapper("mapper");

        mpg.setPackageInfo(pc);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();


         // 不生成controller
         templateConfig.setController("");

        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude(tables);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}