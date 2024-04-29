version: '3.8'

services:
  backend_itb_kk:
    build:
      context: ./INT221-NW3-PROJECT-BACKEND
      dockerfile: Dockerfile
    restart: always
    ports:
      - '8080:8080'
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://lvm65058.sit.kmutt.ac.th:3000/kradan_kanban_itb_kk
      SPRING_JPA_HIBERNATE: update
    depends_on:
      - dbserver_mysql_itb_kk

  dbserver_mysql_itb_kk:
    container_name: dbserver_mysql_itb_kk
    image: mysql/mysql-server
    #restart: always
    ports:
      - '3000:3306'
    environment:
      #MYSQL_ROOT_HOST: '%'
      MYSQL_ROOT_PASSWORD: 'mysql'
    volumes:
      - ./scrip_mytaskV3.sql:/docker-entrypoint-initdb.d/scrip_mytaskV3.sql
      #- ./scrip_mytaskV3.sql:/scrip_mytaskV3.sql
    #command: ["sh", "-c", "mysql -h localhost -P 3306 -u root -pmysql < /scrip_mytaskV3.sql"]
      #- ./scrip_mytaskV3.sql:/docker-entrypoint-initdb.d/scrip_mytaskV3.sql
      #- ./path/to/scrip_mytaskV3.sql:/docker-entrypoint-initdb.d/scrip_mytaskV3.sql
    #command: ["sh", "-c", "mysql -u root -p$MYSQL_ROOT_PASSWORD < /docker-entrypoint-initdb.d/scrip_mytaskV3.sql"]
    #command: ["docker-entrypoint.sh", "mysqld", "--init-file=/docker-entrypoint-initdb.d/scrip_mytaskV3.sql"]
    healthcheck:
      test: "exit 0"
