# ubuntu mysql8安装(mysql5.7升级为8.0)

背景：

本地开发用的是mysql8，ubuntu上的mysql版本比较老，不支持utf8mb4_0900_ai_ci字符集排序规则，在mysql8中导出的数据，想导入mysql5.7总是utf8mb4_0900_ai_ci报错，所以直接升级ubuntu服务器上的mysql为最新的mysql-apt-config_0.8.24-1_all.deb（2022.9.24）

- 查看当前软件包的最新版本：

```bash
sudo dpkg -l|grep mysql*
```

- 去mysql官网下载`.deb`软件包的配置文件，并运行。更新软件包：

```bash
wget https://repo.mysql.com//mysql-apt-config_0.8.24-1_all.deb
sudo dpkg -i mysql-apt-config_0.8.24-1_all.deb
sudo apt-get update
```

- 安装mysql最新版：

```bash
sudo apt-get install mysql-server
```

### 输入命令查看版本

```bash
mysql --version
```

### 使用`systemctl status mysql.service`查看数据库服务是否启动

![查看服务状态 ](https://img-blog.csdnimg.cn/fb4a2ea3899241dab5a65bbdd2d108b2.png)

## 配置

### 输入`vi /etc/mysql/debian.cnf`查看原始密码

![原始密码 ](https://img-blog.csdnimg.cn/9fd9775ae55e4822bcfccbaef18dce12.png)

### 输入`mysql -uroot -p`进行登录

粘入刚刚复制下来的密码

### 修改初始密码

1. 输入`use mysql;`切换到数据库中

2. **修改mysql密码并刷新**

   ```
   ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';
   
   flush privileges; 
   ```

### 开放远程登录

> 如果要在其他数据库访问工具或者其他计算机上访问此机上的数据库时，就要开启数据库远程访问，开启步骤分为两步，具体如下：

1.修改用户访问权限：将mysql数据库的登录权限设置为所有计算机，也就是设置所有的ip地址都可以通过指定的用户就行访问。

输入use mysql;进入到mysql的库中
输入update user set host='%' where user='root';将用户允许登录的ip地址设为%也就是所有ip地址，这里以root用户为例，其他用户也是一样的

![修改用户允许访问权限 ](https://img-blog.csdnimg.cn/fdfe1415bf354186b8f0905b3ee5e6bc.png)

2.开放3306端口：开放端口之后才可以让其他计算机访问的到

退出mysql，在控制台中输入`netstat -an|grep 3306`查看当前3306端口的开放情况

![3306端口 ](https://img-blog.csdnimg.cn/0fdd3bfd246c433392c34e7a9d4895ce.png)

此时3306端口是关闭的状态
打开mysql配置文件vi /etc/mysql/mysql.conf.d/mysqld.cnf，将bind-address = 127.0.0.1注销

![注销本地ip绑定 ](https://img-blog.csdnimg.cn/39192ebd9d6d4e9d97375ac9b948a7c0.png)

#### 重启服务，使配置生效  

```
 service mysql restart
```

重启服务，查看此时3306端口已经处于开放状态，也就是 所有计算机都能访问到

![image-20230221193519394](E:\typora-imag\image-20230221193519394.png)

查看mysql运行状态：sudo service mysql status

mysql开启：sudo service mysql start

mysql停止：sudo service mysql stop

## 参考资料

[(138条消息) ubuntu上安装mysql_乌班图安装mysql_HANXINHAHX的博客-CSDN博客](https://blog.csdn.net/HANXINHAHX/article/details/125704766?ops_request_misc=&request_id=&biz_id=102&utm_term=ubuntu安装mysql&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-3-125704766.142^v73^insert_down1,201^v4^add_ask,239^v2^insert_chatgpt&spm=1018.2226.3001.4187)

[(138条消息) ubantu mysql5.7升级为8.0_chiheyi1036的博客-CSDN博客](https://blog.csdn.net/chiheyi1036/article/details/100722383?ops_request_misc=&request_id=&biz_id=102&utm_term=ｕｂｕｎｔｕ　ｍｙｓｑｌ５升级到８&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-100722383.nonecase&spm=1018.2226.3001.4187)

[ubuntu安装mysql8.0_破 风的博客-CSDN博客](https://blog.csdn.net/qq_38935605/article/details/127509902?ops_request_misc=%7B%22request%5Fid%22%3A%22167696921616782428668166%22%2C%22scm%22%3A%2220140713.130102334.pc%5Fall.%22%7D&request_id=167696921616782428668166&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-17-127509902-null-null.142^v73^insert_down1,201^v4^add_ask,239^v2^insert_chatgpt&utm_term=ubuntu安装mysql&spm=1018.2226.3001.4187)