##添加 Java:8 镜像来源
#FROM openjdk:8
#
##添加参数
#ARG JAR_FILE
#
#
#ADD target/${JAR_FILE} app.jar
#
##添加SpringBoot包，JAR_FILE参数是从docker maven 插件中指定的
#ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]