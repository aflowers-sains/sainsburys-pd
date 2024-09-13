# Import KafkaConsumer from Kafka library
from kafka3 import KafkaConsumer

# Import sys module
import sys

# Define server with port
bootstrap_servers = ['localhost:9092']

# Define topic name from where the message will recieve
topicName = 'sainsburys-parameters'

# Initialize consumer variable
consumer = KafkaConsumer (topicName, group_id ='python',bootstrap_servers = bootstrap_servers)

# Read and print message from consumer
for msg in consumer:
    print("Topic Name=%s,Message=%s"%(msg.topic,msg.value))

# Terminate the script
sys.exit()
