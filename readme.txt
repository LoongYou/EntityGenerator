entitygenerator��һ�����ڿ��ٴ���ORM��ʵ�����ļ��Ĺ��ߣ�Ϊ�˸��򵥷�������һЩ��Ŀ�еĻ����������
�������Ŀ�漰�ı�ܶ࣬�����������ʡʱ�䡣��Ȼ��mybatis��hibernate����generator���๦�ܻ��������Ǳ�����Ϊ���ǲ������׺�˳�֣�
�ر����н϶಻ȷ���Ի���ƫ��������������£����ú��ƺ���һ�������Ĺ��̣���Ȼ���ʺ��Լ��Ĳ�����õġ�

###################Instruction###################
�汾 1.0
����������dom4j-2.0.1.jar�����ڰ汾Ӧ��Ҳ��û����ģ�������mybatis��jdbc��
1��Ŀǰ��֧��mysql���ݿ⣬�������mybatis�Ƽ�ʹ�á�
2��ȷ��autogenerator.properties��datatype.xml�������ļ�������Ŀ��·����
3�����������Ŀ�޸����������ļ�������˵����
4������Ŀ�е��ã�����ֱ��ִ�У�com.yu.autogenerator.MainExecutor.main(new String[]{})���ɿ�ʼ��������
5�������ֶ����Զ�ģʽ��ִ�й��̵���Ϣ�����������̨���л�����

�����
###################config###################
# VERSION 1.0
# Ŀǰ��֧��mysql���ݿ⣬�������mybatis�Ƽ�ʹ�ã�����������ļ��и��������趨�޸ļ�ֵ����
#
#

#����������ʱ��������
mybaitsConfig=mybatis-config.xml

dbms=mysql
version=5.7

#�����mybatis����hibernate��ͬ��ע�⣬���ݿ��еı���ͨ��ΪSYS_USER��������Ҳ���Զ�ʶ��Ϊ����User,���ҽ��鰴�մ˹淶���ñ���
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/hotknow
param=?useSSL=false&characterEncoding=utf8
username=root
password=1234

#��Ŀ��·����Ҳ�������а������ϼ�Ŀ¼��maven��Ŀͨ����src\main\java
projectRoot=src

#ʵ�������ڵİ������ݴ��Զ�������
entityPackage=com/entity

#�Ƿ��Զ�ִ�У��ֶ�ִ����ͨ�����ƿ��и������
auto=false

#�Ƿ񸲸��Ѵ��ڵ��ļ�
cover=true

#�Ƿ������Խ���Ԥ��������������ڿ���̨���������������������
fieldPreview=true

#��������ʹ�ð�װ�໹�ǻ������ͣ�����������Ƽ�ʹ�ð�װ�࣬������Ǹ���ʹ�û������ͣ����������ɺ������޸�
ByteORbyte=true

#ɨ���ӱ������ͼ���������߶��ǣ�table��view��both��,���both�����ܻ����ʵ����������������ͼ���ͱ������ֲ�����ͬ��
scan=table

###################Ouput###################
INFO:17:52:58---����������
INFO:17:52:58---��ʼ����������
INFO:17:52:58----------------��ʼ�����-------------
INFO:17:52:58---�����ӵ����ݿ�jdbc:mysql://localhost:3306/hotknow
INFO:17:52:58---��ɨ���
select table_name from information_schema.TABLES where table_type='BASE TABLE' AND table_schema='hotknow'
INFO:17:52:58---core_article
INFO:17:52:58---core_manager
INFO:17:52:58---core_user
INFO:17:52:58---����ִ���ֶ�ģʽ......
INFO:17:52:58---������core_article
INFO:17:52:59---������·��D:\workspace2\autogenerator\\src\\com/entity\
INFO:17:52:59---��ʼ�������ͣ�Article
INFO:17:52:59---���͹�����ɣ�Article
core_article:
    private Integer id;//
    private String title;//����
    private String artType;//���·�������:ԭ��/ת��
    private Integer user_id;//������
    private String provenance;//��Դ�ͳ���
    private String content;//��������
    private Integer browse;//�������
    private Timestamp created;//��������
    private Timestamp published;//��������
    private Timestamp modifytime;//�޸�����
    private String status;//״̬
    private String remark;//��ע

INFO:17:52:59---��˶Ա��Ԥ�����Ƿ�����Article.java?(Y/N,E:��ֹ���񡣲����ִ�Сд)
e
INFO:17:53:45---��ֹ����
INFO:17:53:45---statement��connection�ѹر�
