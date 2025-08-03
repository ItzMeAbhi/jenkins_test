pipeline {
    agent any
    environment {
        REPO_NAME = "${env.JOB_NAME}"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    userRemoteConfigs: [[
                        url: 'git@github.com:ItzMeAbhi/jenkins_test.git',
                        credentialsId: 'github-ssh-key'
                    ]],
                    branches: [[name: '*/main']]
                ])
            }
        }

        stage('Build with Maven') {
            agent {
                docker {
                    image 'maven:3.8.6-openjdk-17'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Tag Image with Commit ID') {
            steps {
                script {
                    COMMIT_ID = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    IMAGE_TAG = "${REPO_NAME}:${COMMIT_ID}"
                    echo "Image will be tagged as ${IMAGE_TAG}"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_TAG} ."
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Replace image in deployment.yaml temporarily
                    sh """
                      sed 's|jenkins-cicd-test:latest|${IMAGE_TAG}|g' k8s/deployment.yaml > k8s/deployment-temp.yaml
                    """
                    sh 'kubectl delete -f k8s/deployment-temp.yaml --ignore-not-found'
                    sh 'kubectl delete -f k8s/service.yaml --ignore-not-found'
                    sh 'kubectl apply -f k8s/deployment-temp.yaml'
                    sh 'kubectl apply -f k8s/service.yaml'
                }
            }
        }

        stage('Verify Pods & Service') {
            steps {
                sh 'kubectl get pods'
                sh 'kubectl get svc'
            }
        }
    }
}
