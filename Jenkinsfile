#!/groovy
pipeline{
	agent any

	environment {
		REPOSITORY="https://github.com/clsaa/user-story-mapping.git"
	}

	stages {
		stage('获取代码') {
			steps {
				echo "start fetch code from git:${REPOSITORY}"
				deleteDir()
				git "${REPOSITORY}"
                script {
                    time = sh(returnStdout: true, script: 'date "+%Y%m%d%H%M"').trim()
                    git_version = sh(returnStdout: true, script: 'git log -1 --pretty=format:"%h"').trim()
                    build_tag = time+git_version
                }
			}
		}

		stage('编译+单元测试') {
			steps {
				echo "star compile"
				sh "mvn -U -am clean package"
			}
		}

        stage('代码静态检查') {
            steps {
                echo "start code check"
                script {
                    scannerHome = tool 'SonarQubeScanner2.8'
                    //这里这个tool是直接根据名称，获取自动安装的插件的路径
                }
                withSonarQubeEnv('SonarQube') {
                    sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=user-story-mapping-web -Dsonar.projectName=user-story-mapping-web -Dsonar.sources=src -Dsonar.projectVersion=${build_tag} -Dsonar.language=java  -Dsonar.sourceEncoding=UTF-8 -Dsonar.java.binaries=target/classes"
                }
            }
        }

		stage('构建镜像') {
			steps {
				echo "start build image"

                echo "image tag : ${build_tag}"
				sh "docker build -t registry.cn-beijing.aliyuncs.com/usm/user-story-mapping-web:${build_tag} ."
			}
		}

        stage('推送镜像') {
            steps {
                echo "start push image"
                withCredentials([usernamePassword(credentialsId: 'aliyun', passwordVariable: 'aliyunPassword', usernameVariable: 'aliyunUsername')]) {
                    sh "docker login -u ${aliyunUsername} -p ${aliyunPassword} registry.cn-beijing.aliyuncs.com"
                    sh "docker push registry.cn-beijing.aliyuncs.com/usm/user-story-mapping-web:${build_tag}"
                }
            }
        }

        stage('更新YAML镜像版本') {
            steps{
                echo "start change yaml image tag"
                sh "sed -i 's/<BUILD_TAG>/${build_tag}/' k8s.yaml"
                sh "cat k8s.yaml"
            }
        }

		stage('发布系统') {
			steps {
				echo "start deploy"
				sh "kubectl apply -f k8s.yaml"
			}
		}
	}
}