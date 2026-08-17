# Dockerfile for apistatic service only

# Stage 1: Build Stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Copy the entire project structure
COPY root /build/root
COPY common /build/common
COPY constant /build/constant
COPY coreapplication /build/coreapplication
COPY coredata /build/coredata
COPY coreinfrastructure /build/coreinfrastructure
COPY coreotherentity /build/coreotherentity
COPY coreruntime /build/coreruntime
COPY divisionmanual /build/divisionmanual
COPY divisionstory /build/divisionstory
COPY librarydatasource /build/librarydatasource
COPY utilityparserexpression /build/utilityparserexpression
COPY apicore /build/apicore
COPY apidatasource /build/apidatasource
COPY apistatic /build/apistatic
COPY apiserver /build/apiserver

# Build all modules
RUN cd /build/root && mvn clean package -DskipTests -q

# Stage 2: Runtime Stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy built JAR from builder
COPY --from=builder /build/apistatic/target/apistatic-*.jar app.jar

# Create required directories
RUN mkdir -p /app/data /app/config

# Environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    SERVER_PORT=8081

EXPOSE 8081

HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8081/actuator/health || exit 1

# Start the application
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
