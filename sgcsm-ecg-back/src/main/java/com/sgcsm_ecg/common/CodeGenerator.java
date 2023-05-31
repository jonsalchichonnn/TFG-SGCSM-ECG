package com.sgcsm_ecg.common;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {

    /**
     * <p>
     * read from console
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("Introduce " + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("Introduce correct " + tip + "！");
    }

    public static void main(String[] args) {
        // Code generator
        AutoGenerator mpg = new AutoGenerator();

        // global config
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/sgcsm-ecg-back";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("sgcsm");
        gc.setOpen(false);
        gc.setSwagger2(true); // Swagger2 annotation
        gc.setBaseResultMap(true); // XML ResultMap
        gc.setBaseColumnList(true); // XML ColumnList
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // Data source config
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/sgcsm"); //?useUnicode=true&useSSL=false&characterEncoding=utf8
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("qwertyuiop123");
        mpg.setDataSource(dsc);

        // PackageConfig
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("module name"));
        pc.setParent("com.sgcsm_ecg")
                .setEntity("entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller");
        mpg.setPackageInfo(pc);

        // Custom config
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // If template engine = freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // If template engine = velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // Custom output config
        List<FileOutConfig> focList = new ArrayList<>();
        // Custom output config is prioritized
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // Customize output file name, if set prefix and suffix for Entity, note the name of xml will change!
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // TemplateConfig
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // strategy config
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("SuperEntity, not mandatory!");
//        strategy.setSuperEntityClass("BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("SuperController, not mandatory!");
//        strategy.setSuperControllerClass("BaseController");
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("Table name, if various separated with comma").split(","));
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


}
