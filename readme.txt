entitygenerator是一个用于快速创建ORM中实体类文件的工具，为了更简单方便的完成一些项目中的基础搭建工作，
当你的项目涉及的表很多，它可以令你节省时间。虽然，mybatis和hibernate都有generator此类功能或插件，但是本人认为还是不够简易和顺手，
特别在有较多不确定性或者偏向于自主的情况下，配置和善后是一个繁琐的过程，当然，适合自己的才是最好的。

#===============================Instruction=======================
版本：1.1
更新：
1、实现了根据实体类生成属性字典功能
2、生成字典可自动可手动
3、修复一些bug并优化配置项


版本 1.0
依赖：依赖dom4j-2.0.1.jar，早期版本应该也是没问题的，and mysql JDBC Driver。
1、目前仅支持mysql数据库，如果你用mybatis推荐使用。
2、确保entitygenerator.properties和datatype.xml这两个文件置于项目包路径。
3、根据你的项目修改上述两个文件，内有说明。
4、在项目中调用（或者放在项目根目录直接执行）com.yu.generator.MainExecutor.main(new String[]{})即可开始生成任务。
5、具有手动和自动模式，执行过程的信息会输出到控制台进行互动。

配置项：
#==============================config==============================
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

#项目包路径，也就是所有包的最上级目录，maven项目通常是src/main/java,
#通过eclipse建立的项目通常是src，如果项目名称下是包名，则不需要填写
projectRoot=

#实体类所在的包，根据此自动创建包(前后不要加“/”)
entityPackage=com/entity

#实体字典类所在的包，根据此自动创建包(前后不要加“/”)
dictPackage=com/dict/entity

#字典类结尾
dictEndWith=_D

#是否自动执行，手动执行是通过控制可有更多操作
auto=true

#是否覆盖已存在的文件
cover=true

#是否开启属性解析预览，开启后可以在控制台看到类型属性完整的输出
fieldPreview=true

#属性类型使用包装类还是基本类型，多数情况下推荐使用包装类，但如果是个别使用基本类型，可以在生成后自行修改
ByteORbyte=true

#扫描视表或者视图，或者两者都是（table，view，both）,如果both，可能会造成实体类重名，建议视图名和表名部分不尽相同。
scan=table


#=========================Ouput手动=======================
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
INFO:17:52:59---创建包路径D:\workspace2\entitygenerator\\src\\com/entity\
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

#=========================Ouput自动=======================
INFO:03:17:18---解析配置项
INFO:03:17:18---初始化数据类型
INFO:03:17:18----------------初始化完成-------------
生成实体类型：A  生成实体字典：B 输错退出
A
INFO:03:17:23---已连接到数据库jdbc:mysql://localhost:3306/hotknow
INFO:03:17:23---仅扫描表
select table_name from information_schema.TABLES where table_type='BASE TABLE' AND table_schema='hotknow'
INFO:03:17:23---core_article
INFO:03:17:23---core_manager
INFO:03:17:23---core_user
INFO:03:17:23---解析表：core_article
INFO:03:17:23---解析表：core_manager
INFO:03:17:23---启动线程core_article
INFO:03:17:23---解析表：core_user
INFO:03:17:23---启动线程core_manager
INFO:03:17:23---开始构建类型：Article
INFO:03:17:23---开始构建类型：Manager
INFO:03:17:23---类型构建完成：Manager
INFO:03:17:23---类型构建完成：Article
core_manager:
    private Integer id;//
    private String name;//用户名
    private String nick;//昵称
    private String pwd;//密码
    private Timestamp created;//创建时间
    private Timestamp login;//最后登陆时间
    private Timestamp logout;//最后注销时间
    private Integer group_id;//组id
    private String status;//状态
    private String remark;//备注
    private Byte[] head_thumb;//头像

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

INFO:03:17:23---启动线程core_user
INFO:03:17:23---Manager.java已存在,进行覆盖
INFO:03:17:23---Article.java已存在,进行覆盖
INFO:03:17:23---Article.java已生成
INFO:03:17:23---开始构建类型：User
INFO:03:17:23---Manager.java已生成
INFO:03:17:23---类型构建完成：User
core_user:
    private Integer id;//
    private String username;//
    private String nick;//昵称
    private String password;//
    private Timestamp create_time;//
    private Timestamp login_time;//最后登陆时间
    private Timestamp logout_time;//最后注销时间
    private Timestamp modify_time;//最后修改时间
    private String artupvote;//点赞文章
    private String artconcern;//关注文章
    private String userupvote;//点赞用户
    private String userconcern;//关注用户
    private String status;//状态
    private String remark;//备注
    private Byte[] head_thumb;//头像

INFO:03:17:23---User.java已存在,进行覆盖
INFO:03:17:23---User.java已生成
INFO:03:17:25---等待所有线程结束
INFO:03:17:27---等待所有线程结束
INFO:03:17:27---线程已结束
INFO:03:17:27---类已创建好，请刷新项目
INFO:03:17:27---statement和connection已关闭

#=============================output字典======================
INFO:03:25:54---解析配置项
INFO:03:25:54---初始化数据类型
INFO:03:25:54----------------初始化完成-------------
生成实体类型：A  生成实体字典：B 输错退出
B
获取实体：D:\workspace2\EntityGenerator////com/entity/
Article.java
Blog.java
Contact.java
Manager.java
User.java
单独生成某个字典？Y/N,默认N
N
执行自动生成字典并覆盖
D:\workspace2\EntityGenerator////com/dict/entity/
INFO:03:26:04---类型构建完成：User
INFO:03:26:04---User.java已存在,进行覆盖
INFO:03:26:04---User.java已生成
INFO:03:26:04---类型构建完成：Article
INFO:03:26:04---Article.java已存在,进行覆盖
INFO:03:26:04---Article.java已生成
INFO:03:26:04---类型构建完成：Manager
INFO:03:26:04---Manager.java已存在,进行覆盖
INFO:03:26:04---Manager.java已生成
INFO:03:26:04---类型构建完成：Blog
INFO:03:26:04---Blog.java已存在,进行覆盖
INFO:03:26:04---Blog.java已生成
INFO:03:26:04---类型构建完成：Contact
INFO:03:26:04---Contact.java已存在,进行覆盖
INFO:03:26:04---Contact.java已生成
