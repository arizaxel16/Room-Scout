pipeline {
    agent any

    tools {
        nodejs 'NodeJS'
        dockerTool 'docker'
    }

    environment {
        DOCKER_IMAGE = 'juancorral/room-scout-frontend'
        DOCKER_CREDENTIALS = 'dockerhub-credentials-id'
    }

    stages {
        stage('Clonar Repositorio') {
            steps {
                git branch: 'main', url: 'https://github.com/arizaxel16/Room-Scout.git'
            }
        }

        stage('Instalar Dependencias y Build') {
            steps {
                dir('frontend/room-scout') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Construir Imagen Docker') {
            steps {
                script {
                    def image = docker.build("${DOCKER_IMAGE}:${env.BUILD_NUMBER}", "frontend/room-scout/")
                    sh "docker images"
                }
            }
        }

        stage('Trivy Vulnerability Scan') {
            steps {
                script {
                    sh """
                    docker run --rm \
                      -v /var/run/docker.sock:/var/run/docker.sock \
                      -v $HOME/Library/Caches:/root/.cache/ \
                      aquasec/trivy image --severity HIGH,CRITICAL \
                      ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                    """
                }
            }
        }

        stage('Subir Imagen a DockerHub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS}") {
                        def image = docker.image("${DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                        image.push()
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                sh "docker rmi ${DOCKER_IMAGE}:${env.BUILD_NUMBER} || true"
            }
            cleanWs()
        }
        failure {
            echo 'Build Failed'
        }
        success {
            echo 'Build Succeeded'
        }
    }
}
