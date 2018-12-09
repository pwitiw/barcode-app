/* Job context constants */
def pollingFrequency = 'H/2 * * * *'
def buildKeptAmount = '10'

def gradleVersion = 'Gradle 4.10.3'
def jdkVersion = 'Oracle JDK 1.8'

pipeline {
    parameters {
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Select to deploy')
    }
	
	agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: buildKeptAmount))
        disableConcurrentBuilds()
    }

	    tools {
        gradle gradleVersion
        jdk jdkVersion
    }
	
    triggers {
        pollSCM(pollingFrequency)
    }

    stages {
        stage('Build') {
            steps {
                    sh 'gradle build'
            }
        }
    }
}