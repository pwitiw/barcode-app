/* Job context constants */
def pollingFrequency = 'H/2 * * * *'
def buildKeptAmount = '10'

def gradleVersion = 'Gradle 4.8.1'
def jdkVersion = 'Oracle JDK 1.8'

def host = 'str-pai-6-vagrant.iteratec.de'
def passwordVar = 'was_pwd', usernameVar = 'was_user'

def appPackageName = 'proplan.tci'
def escrowPackageName = appPackageName + '.escrow'

def deployPath = '/opt/IBM/WebSphere/Profiles/base/monitoredDeployableApps/servers/server1'
def archiveArtifactsPath = escrowPackageName + '/target/' + escrowPackageName + '.zip'

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