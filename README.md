# BBS(Bulletin Board System) with SpringMVC

### Frameworks
    SpringMVC 3.1
    MyBatis 3.2
    Log4JDBC 0.2.7
    Bootstrap
    CKeditor
    Apache Commons DBCP 1.4
    Apache Commons Fileupload 1.3.1
    Code House Jackson Mapper 1.9.13
    GSON 1.2.4
    MariaDB


### Features
    Multi language Support
    Spring Based, MyBatis
    Rich Text Editting (CKEditor)
    AJAX Type Mutli File Upload
    Vote (Like) Function
    Pagination
    JSON (REST) Support


### Screen shot
    Article List

![Article List Page](https://github.com/PurpleRainForest/SpringBBS/blob/master/SpringBBS/src/main/webapp/images/screen_l1.jpg?raw=true)

    Detail View
![Detail View Page](https://github.com/PurpleRainForest/SpringBBS/blob/master/SpringBBS/src/main/webapp/images/screen_v1.jpg?raw=true)

    Edit/Register Page
![Edit / Register Page](https://github.com/PurpleRainForest/SpringBBS/blob/master/SpringBBS/src/main/webapp/images/screen_w1.jpg?raw=true)

### How to Use
### 1. Database Table Creation
`create table tb_clientside`
       `( bbsno int(10) unsigned primary key auto_increment,`
       `ref_no int(10) unsigned not null default '0',`
       `ref_parent int(10) unsigned not null default '0',`
       `ref_level int(10) unsigned not null default '0',`
       `ref_step int(10) unsigned not null default '0',`
       `title varchar(100) not null unique,`
       `cont varchar(10000),`
       `writer varchar(50),`
       `cdate timestamp default '0000-00-00 00:00:00',`
       `mdate timestamp default now(),`
       `views int(10) unsigned default '0',`
       `likes int(10) unsigned default '0',`
       `afile varchar(1000),`
       `deleted varchar(5) default 'N'`
     `)  DEFAULT CHARSET=utf8 ;`
`alter table tb_clientside add index index_tb_clientside_title (title);`

### 2. Database connectivity configuration
Edit 'src/main/java/config/jdbc/jdbc.properties'

### 3. Run index.htm
Select & Run 'src/webapp/index.htm' 


