# My Blog

My Blog是由Docker+SpringBoot+Mybatis+thymeleaf等技术实现的Java博客系统，博客模板是[@biezhi](https://github.com/biezhi)大神的开源项目[tale](https://github.com/otale/tale)。

本项目主要代码来源于某高级工程师[十三](https://github.com/ZHENFENG13/My-Blog "@十三")，目前已经开源，功能齐全、部署简单及完善的代码，一定会给使用者无与伦比的体验，也感谢十三长期以来对我的问题的解答。

本人将这个开源项目作为一个springboot的实战练习项目，并为其新增文件上传功能，实现在富文本编辑器编辑文章时，能够插入图片。本项目已经部署与阿里云，作为本人的技术博客[http://zlcalma.top/](http://zlcalma.top/ "我的博客")(若无法访问，是因为正在备案，可通过IP访问[http://120.78.192.167/](http://120.78.192.167/ "阿里云IP"))

# 项目部署 #

常驻云服务器

    nohup java -jar blog.jar &

如何重启
简单粗暴

直接kill掉进程再次启动jar包

    ps -ef|grep java 
    ##拿到对于Java程序的pid
    kill -9 pid
    ## 再次重启
    Java -jar  xxxx.jar
