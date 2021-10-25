import java.text.SimpleDateFormat

def buildAndTag(name) {
  script {
      docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credential') {
        def app = docker.build("$name")
        app.push("$timestamp")
        app.push("latest")
      }
  }
}

pipeline {
  agent any
  tools {
      jdk 'jdk-8u202'
  }

  stages {
    stage('Datetime') {
      steps {
        script {
          def dateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
          def current = new Date()
          def timestamp = dateFormat.format(current)
        }
        echo "Current datetime $timestamp!"
      }
    }

    stage ('build and deploy') {
      stages {
        stage('clean bootJar') {
          steps {
              sh "./gradlew clean bootJar"
          }
        }

        stage('build images and push to registry') {
            parallel{
                stage("apigateway") {
                  steps {
                    dir("$STAGE_NAME"){
                        buildAndTag(STAGE_NAME)
                    }
                  }
                }
                stage("configserver") {
                  steps {
                    dir("$STAGE_NAME"){
                        buildAndTag(STAGE_NAME)
                    }
                  }
                }
                stage("discoveryserver") {
                  steps {
                    dir("$STAGE_NAME"){
                        buildAndTag(STAGE_NAME)
                    }
                  }
                }
                stage("product") {
                  steps {
                    dir("$STAGE_NAME"){
                        buildAndTag(STAGE_NAME)
                    }
                  }
                }
                stage("review") {
                  steps {
                    dir("$STAGE_NAME"){
                        buildAndTag(STAGE_NAME)
                    }
                  }
                }
            }
        }
        stage('compose up') {
          steps {
              sh "docker-compose up -d"
          }
        }
      }
    }
  }
}