FROM zqiannnn/commerce-suit-base:20190116
MAINTAINER ZQian<zqiannnn@gmail.com>

COPY . /app/

WORKDIR /app

RUN mvn clean install package -U -DskipTests


ENV JAVA_OPTS="-Xmx256M -Xms256M"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/target/*.jar"]


