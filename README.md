 This application generate a random messages with random lengths at range [1 - your param]
 and send it to RabbitMQ server on localhost.
 At firs you need to install RabbitMQ server on your localhost.
 To start send messages you need to start messageSender method with 3 params :
 *  Alphabet (Look at util.Alphabet class).
 *  Max length of generated message.
 *  Delay between sends messages in (ms).
 
 You also can change names of EXCHANGE, QUEUE and ROUTING_KEY but in this case you must change
 this names in messageReceiver app too.
 