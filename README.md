# CRYPTO BETS NOTIFICATION
Notification app of CRYPTO BETS:

* Send email to winners
* Receive message from Backend through a topic of kafka

###Pre condition
* JDK 11
* maven 3 or higger
* Running kafka and zookeeper
* Running cb-test-back repository (https://github.com/guidorolando/test-cb-back)


# Getting Started

Change over application.properties:

* Add environment variables to set credentials of gmail 

        spring.mail.username=${EMAIL_USER}
        spring.mail.password=${EMAIL_PASSWORD}

* Needs configurations of kafka
    
        spring.kafka.consumer.boostrap-servers= localhost:9092
        spring.kafka.consumer.group_id=group_id
        spring.kafka.consumer.auto-offset-reset=earliest

# BUILD AND RUN

    mvn clean compile package
    mvn spring-boot:run