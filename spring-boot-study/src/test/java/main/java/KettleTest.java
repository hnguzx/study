package main.java;

import org.junit.jupiter.api.Test;
//import org.pentaho.di.core.KettleEnvironment;
//import org.pentaho.di.core.database.DatabaseMeta;
//import org.pentaho.di.core.exception.KettleException;
//import org.pentaho.di.core.plugins.PluginFolder;
//import org.pentaho.di.core.plugins.StepPluginType;
//import org.pentaho.di.core.util.EnvUtil;
//import org.pentaho.di.repository.ObjectId;
//import org.pentaho.di.repository.RepositoryDirectoryInterface;
//import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
//import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
//import org.pentaho.di.trans.Trans;
//import org.pentaho.di.trans.TransMeta;

public class KettleTest {

    /**
     * kettle
     * 通过资源库运行
     *
     * @throws
     */
    /*@Test
    void runWithDb() throws KettleException {
        // 加载插件
        StepPluginType.getInstance().getPluginFolders().add(new PluginFolder("D:\\software\\pdi-ce-9.3.0.0-428\\data-integration\\plugins", false, true));
        // 初始化
        KettleEnvironment.init();

        // 数据库连接元对象
        // 资源库名称，资源库类型，连接方式，ip,数据库名称，端口，用户名，密码
        DatabaseMeta databaseMeta = new DatabaseMeta("database-repo", "mysql", "Native(JDBC)", "127.0.0.1", "kettle", "3306", "root", "123456");
        // 数据库形式的资源库元对象
        // 选择资源库
        KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta = new KettleDatabaseRepositoryMeta();
        kettleDatabaseRepositoryMeta.setConnection(databaseMeta);
        // 数据库形式的资源库对象
        // 创建DB资源库
        KettleDatabaseRepository repository = new KettleDatabaseRepository();
        repository.init(kettleDatabaseRepositoryMeta);
        //连接资源库
        repository.connect("admin", "admin");
        if (repository.isConnected()) {
            System.out.println("连接成功");
        } else {
            System.out.println("连接失败");
        }
        // 获取目录
        RepositoryDirectoryInterface directoryInterface = repository.loadRepositoryDirectoryTree().findDirectory("/transformation");
//        RepositoryDirectoryInterface directoryInterface = repository.findDirectory("/job");
        //选择转换
        ObjectId transformationID = repository.getTransformationID("flow", directoryInterface);
        TransMeta transMeta = repository.loadTransformation(transformationID, null);
        Trans trans = new Trans(transMeta);
        trans.execute(null);
        trans.waitUntilFinished();//等待直到数据结束
        if (trans.getErrors() > 0) {
            System.out.println("transformation error");
        } else {
            System.out.println("transformation successfully");
        }
    }*/

    /**
     * kettle
     * 通过文件运行
     */
    @Test
    void runTransfer() {
        /*String[] params = new String[5];
        String ktrPath = "D:\\file\\kettle\\transition\\csv2excel.ktr";
        Trans trans = null;
        try {
            // 加载插件
            StepPluginType.getInstance().getPluginFolders().add(new PluginFolder("D:\\software\\pdi-ce-9.3.0.0-428\\data-integration\\plugins\\kettle-json-plugin", false, true));
            // 初始化
            // 转换元对象
            KettleEnvironment.init();// 初始化
            EnvUtil.environmentInit();
            TransMeta transMeta = new TransMeta(ktrPath);
            // 转换
            trans = new Trans(transMeta);

            // 执行转换
            trans.execute(params);
            // 等待转换执行结束
            trans.waitUntilFinished();
            // 抛出异常
            if (trans.getErrors() > 0) {
                throw new Exception(
                        "There are errors during transformation exception!(传输过程中发生异常)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
