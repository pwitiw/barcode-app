/* Job context constants */
def pollingFrequency = 'H/2 * * * *'
def buildKeptAmount = '10'

def gradleVersion = 'Gradle 4.8.1'
def jdkVersion = 'Oracle JDK 1.8'

pipeline {
    parameters {
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Select to deploy')
    }

    tools {
        maven mavenVersion
        jdk jdkVersion
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: buildKeptAmount))
        disableConcurrentBuilds()
    }

    triggers {
        pollSCM(pollingFrequency)
    }

    stages {
        stage('Build') {
            steps {
                ansiColor('xterm') {
                    sh 'gradle build'
                }
            }
        }
    }

    post {
        cleanup {
            deleteDir() /* Clean up the workspace */
        }
    }
}