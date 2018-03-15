entitygenerator是一个用于快速创建ORM中实体类文件的工具，为了更简单方便的完成一些项目中的基础搭建工作，
当你的项目涉及的表很多，它可以令你节省时间。虽然，mybatis和hibernate都有generator此类功能或插件，但是本人认为还是不够简易和顺手，
特别在有较多不确定性或者偏向于自主的情况下，配置和善后是一个繁琐的过程，当然，适合自己的才是最好的。

###################Instruction###################
版本 1.0
依赖：依赖dom4j-2.0.1.jar，早期版本应该也是没问题的，不依赖mybatis和jdbc。
1、目前仅支持mysql数据库，如果你用mybatis推荐使用。
2、确保autogenerator.properties和datatype.xml这两个文件置于项目包路径。
3、根据你的项目修改上述两个文件，内有说明。
4、在项目中调用（或者直接执行）com.yu.autogenerator.MainExecutor.main(new String[]{})即可开始生成任务。
5、具有手动和自动模式，执行过程的信息会输出到控制台进行互动。

配置项：
###################config###################
# VERSION 1.0
# 目前仅支持mysql数据库，如果你用mybatis推荐使用，在这个配置文件中根据自身设定修改键值即可
#
#

#这条配置暂时不起作用
mybaitsConfig=mybatis-config.xml

dbms=mysql
version=5.7

#这里和mybatis或者hibernate相同，注意，数据库中的表名通常为SYS_USER，生成器也会自动识别为类型User,并且建议按照此规范设置表名
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/hotknow
param=?useSSL=false&characterEncoding=utf8
username=root
password=1234

#项目包路径，也就是所有包的最上级目录，maven项目通常是src\main\java
projectRoot=src

#实体类所在的包，根据此自动创建包
entityPackage=com/entity

#是否自动执行，手动执行是通过控制可有更多操作
auto=false

#是否覆盖已存在的文件
cover=true

#是否开启属性解析预览，开启后可以在控制台看到类型属性完整的输出
fieldPreview=true

#属性类型使用包装类还是基本类型，多数情况下推荐使用包装类，但如果是个别使用基本类型，可以在生成后自行修改
ByteORbyte=true

#扫描视表或者视图，或者两者都是（table，view，both）,如果both，可能会造成实体类重名，建议视图名和表名部分不尽相同。
scan=table

###################Ouput###################
INFO:17:52:58---解析配置项
INFO:17:52:58---初始化数据类型
INFO:17:52:58----------------初始化完成-------------
INFO:17:52:58---已连接到数据库jdbc:mysql://localhost:3306/hotknow
INFO:17:52:58---仅扫描表
select table_name from information_schema.TABLES where table_type='BASE TABLE' AND table_schema='hotknow'
INFO:17:52:58---core_article
INFO:17:52:58---core_manager
INFO:17:52:58---core_user
INFO:17:52:58---正在执行手动模式......
INFO:17:52:58---解析表：core_article
INFO:17:52:59---创建包路径D:\workspace2\autogenerator\\src\\com/entity\
INFO:17:52:59---开始构建类型：Article
INFO:17:52:59---类型构建完成：Article
core_article:
    private Integer id;//
    private String title;//标题
    private String artType;//文章发布类型:原创/转载
    private Integer user_id;//发布者
    private String provenance;//来源和出处
    private String content;//文章内容
    private Integer browse;//浏览数量
    private Timestamp created;//创建日期
    private Timestamp published;//发布日期
    private Timestamp modifytime;//修改日期
    private String status;//状态
    private String remark;//备注

INFO:17:52:59---请核对表和预览。是否生成Article.java?(Y/N,E:终止任务。不区分大小写)
e
INFO:17:53:45---终止任务
INFO:17:53:45---statement和connection已关闭
