# Tiltfile
# Ports:
# Zookeeper: 2181
# kafka: 9092
# akhq ui: 8080
# marketing db: 5432
# store db: 5433
# mongo db: 27017
# api bff marketing: 9081
# api bff web store: 10081
# web marketing frontend: 9082
# web store frontend: 10082
# create-order-command: 10083
# create-order-audit: 10084
# pg admin ui: 8090

# Load required Tilt extensions
load('ext://namespace', 'namespace_create')

# Create dedicated namespace
namespace_create('local-cicd')

# Function to build and deploy: zookeeper service
def zookeeper_service():
    k8s_yaml('./local-cicd-manifests/demo-zookeeper.yaml')
    k8s_resource('demo-zookeeper',
                 port_forwards=['2181:2181'],
                 labels="message-broker")

# Function to build and deploy: kafka service
def kafka_service():
    k8s_yaml('./local-cicd-manifests/demo-kafka.yaml')
    k8s_resource('demo-kafka',
                 port_forwards=['9092:9092'],
                 resource_deps=['demo-zookeeper'],
                 labels="message-broker")

# Function to build and deploy: akhq service
def akhq_service():
    k8s_yaml('./local-cicd-manifests/demo-akhq.yaml')
    k8s_resource('demo-akhq',
                 port_forwards=['8080:8080'],
                 resource_deps=['demo-kafka'],
                 labels="message-broker")

# Function to build and deploy: Database service
def marketing_db_service():
    docker_build('demo-marketing-db',
                 context='./src/marketing/persistent-storage/db',
                 dockerfile='./src/marketing/persistent-storage/db/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-marketing-db.yaml')
    k8s_resource('demo-marketing-db',
                 port_forwards=['5432:5432'],
                 labels="persistent-storage")

# Function to build and deploy: liquibase update for db service
def marketing_db_change_management_service():
    docker_build('demo-marketing-db-change-management',
                 context='./src/marketing/persistent-storage/db-change-management',
                 dockerfile='./src/marketing/persistent-storage/db-change-management/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-marketing-db-change-management.yaml')
    k8s_resource('demo-marketing-db-change-management',
                 port_forwards=['5430:5432'],
                 resource_deps=['demo-marketing-db'],
                 labels="persistent-storage")

# Function to build and deploy: Database service
def store_db_service():
    docker_build('demo-store-db',
                 context='./src/store/persistent-storage/db',
                 dockerfile='./src/store/persistent-storage/db/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-store-db.yaml')
    k8s_resource('demo-store-db',
                 port_forwards=['5433:5432'],
                 labels="persistent-storage")

# Function to build and deploy: liquibase update for db service
def store_db_change_management_service():
    docker_build('demo-store-db-change-management',
                 context='./src/store/persistent-storage/db-change-management',
                 dockerfile='./src/store/persistent-storage/db-change-management/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-store-db-change-management.yaml')
    k8s_resource('demo-store-db-change-management',
                 port_forwards=['5431:5432'],
                 resource_deps=['demo-store-db'],
                 labels="persistent-storage")

# Function to build and deploy: mongo db service
def mongodb_service():
    k8s_yaml('./local-cicd-manifests/demo-mongodb.yaml')
    k8s_resource('demo-mongodb',
                 port_forwards=['27017:27017'],
                 labels="persistent-storage")

# Function to build and deploy: BFF service
def bff_web_marketing_service():
    docker_build('demo-bff-web-marketing',
                 context='./src/marketing/bff',
                 dockerfile='./src/marketing/bff/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-bff-web-marketing.yaml')
    k8s_resource('demo-bff-web-marketing',
                 port_forwards=['9081:8080','5005:5005'],
                 resource_deps=['demo-marketing-db'],
                 labels="service",
                 trigger_mode=TRIGGER_MODE_MANUAL)

# Function to build and deploy: Frontend service
def marketing_frontend_service():
    docker_build('demo-marketing-frontend',
                 context='./src/marketing/frontend',
                 dockerfile='./src/marketing/frontend/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-marketing-frontend.yaml')
    k8s_resource('demo-marketing-frontend',
                 port_forwards=['9082:80'],
                 resource_deps=['demo-bff-web-marketing'],
                 labels="web",
                 trigger_mode=TRIGGER_MODE_MANUAL)

# Function to build and deploy:API service
def bff_web_store_service():
    docker_build('demo-bff-web-store',
                 context='./src/store/bff',
                 dockerfile='./src/store/bff/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-bff-web-store.yaml')
    k8s_resource('demo-bff-web-store',
                 port_forwards=['10081:8080','5006:5006'],
                 resource_deps=['demo-store-db'],
                 labels="service",
                 trigger_mode=TRIGGER_MODE_MANUAL)

# Function to build and deploy: Frontend service
def store_frontend_service():
    docker_build('demo-store-frontend',
                 context='./src/store/frontend',
                 dockerfile='./src/store/frontend/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-store-frontend.yaml')
    k8s_resource('demo-store-frontend',
                 port_forwards=['10082:80'],
                 resource_deps=['demo-bff-web-store'],
                 labels="web",
                 trigger_mode=TRIGGER_MODE_MANUAL)

# Function to build and deploy:pgAdmin service
def pgadmin_service():
    k8s_yaml('./local-cicd-manifests/demo-pgadmin.yaml')
    k8s_resource('demo-pgadmin',
                 port_forwards=['8090:80'],
                 resource_deps=['demo-frontend'],
                 labels="utility")

# Function to build and deploy: producer service
def create_order_command_service():
    docker_build('demo-create-order-command', context='./src/store/create-order-command', dockerfile='./src/store/create-order-command/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-create-order-command.yaml')
    k8s_resource('demo-create-order-command',
                 port_forwards='10083:8080',
                 resource_deps=['demo-kafka'],
                 trigger_mode=TRIGGER_MODE_MANUAL,
                 labels="service")

# Function to build and deploy: consumer service
def create_order_audit_service():
    docker_build('demo-create-order-audit', context='./src/store/create-order-audit', dockerfile='./src/store/create-order-audit/Dockerfile')
    k8s_yaml('./local-cicd-manifests/demo-create-order-audit.yaml')
    k8s_resource('demo-create-order-audit',
                 port_forwards='10084:8080',
                 resource_deps=['demo-kafka','demo-create-order-command'],
                 trigger_mode=TRIGGER_MODE_MANUAL,
                 labels="service")    
    
# Deploy services
zookeeper_service()
kafka_service()
akhq_service()
mongodb_service()
marketing_db_service()
marketing_db_change_management_service()
store_db_service()
store_db_change_management_service()
bff_web_marketing_service()
bff_web_store_service()
store_frontend_service()
marketing_frontend_service()
pgadmin_service()
create_order_command_service()
create_order_audit_service()